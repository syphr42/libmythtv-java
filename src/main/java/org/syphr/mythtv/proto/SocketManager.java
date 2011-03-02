/*
 * Copyright 2011 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.mythtv.proto;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.proto.events.BackendEventGrabber;

/**
 * This class manages a connection to a backend server. It provides the necessary
 * read/write capabilities as well as the ability to take over the communications channel
 * entirely to perform bulk transfer (without protocol), such as transferring a file while
 * another manager controls the flow.
 *
 * @author Gregory P. Moyer
 */
public class SocketManager
{
    private final Logger logger = LoggerFactory.getLogger(SocketManager.class);

    private final BlockingQueue<String> queue;
    private final AtomicInteger skippedResponses;
    private final ExecutorService receiverExecutor;

    private SocketChannel socket;
    private Selector readSelector;
    private Selector writeSelector;

    private Future<?> receiver;

    private BackendEventGrabber backendEventGrabber;

    private ReadWriteByteChannel redirect;

    /**
     * Construct a new socket manager that is not connected to any backend.
     */
    public SocketManager()
    {
        queue = new LinkedBlockingQueue<String>();
        skippedResponses = new AtomicInteger(0);
        receiverExecutor = Executors.newSingleThreadExecutor(new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r)
            {
                SecurityManager s = System.getSecurityManager();
                ThreadGroup group = (s != null)
                        ? s.getThreadGroup()
                        : Thread.currentThread().getThreadGroup();

                Thread t = new Thread(group, r, SocketManager.class.getSimpleName()
                                                + " Receiver Thread", 0);
                if (!t.isDaemon())
                {
                    t.setDaemon(true);
                }
                if (t.getPriority() != Thread.NORM_PRIORITY)
                {
                    t.setPriority(Thread.NORM_PRIORITY);
                }

                return t;
            }
        });
    }

    /**
     * Provide a means for backend event messages to be redirected. This defers the
     * decision of what is considered a "backend event" and how it is handled.
     *
     * @param backendEventGrabber
     *            the grabber to set
     */
    public void setBackendEventGrabber(BackendEventGrabber backendEventGrabber)
    {
        this.backendEventGrabber = backendEventGrabber;
    }

    /**
     * Connect to a backend server. This method will block until the connection completes.
     * If a connection is already active, this method will do nothing.
     *
     * @param host
     *            the hostname (or IP address) of the server
     * @param port
     *            the port on the server
     * @param timeout
     *            number of milliseconds to wait before assuming the connection failed
     *            (values < 1 indicate no timeout)
     * @throws IOException
     *             if the connection could not be completed
     */
    public void connect(String host, int port, final long timeout) throws IOException
    {
        if (isConnected())
        {
            return;
        }

        logger.info("Connecting to {}:{}", host, port);

        socket = SocketChannel.open();
        socket.configureBlocking(true);

        final Thread connectionThread = Thread.currentThread();
        Thread timeoutThread = new Thread("Connection Timeout Listener")
        {
            @Override
            public void run()
            {
                try
                {
                    if (timeout < 1)
                    {
                        return;
                    }

                    logger.trace("Starting connection timeout for {} milliseconds", timeout);
                    Thread.sleep(timeout);

                    logger.error("Connection timed out after {} milliseconds", timeout);
                    connectionThread.interrupt();
                }
                catch (InterruptedException e)
                {
                    /*
                     * Let this thread die when it's interrupted.
                     */
                    logger.trace("Connection completed, stopping timeout thread");
                }
            }
        };

        timeoutThread.start();
        try
        {
            socket.connect(new InetSocketAddress(host, port));
        }
        finally
        {
            timeoutThread.interrupt();
        }

        logger.info("Connected");

        socket.configureBlocking(false);
        openSelectors();
        startReceiver();
    }

    /**
     * Open the read and write selectors. This needs to be done before data can be send
     * through this socket manager using its own API (not the {@link #redirectChannel()
     * redirected channel}). These selectors should be {@link #closeSelectors() closed}
     * before redirecting the channel to prevent corruption.
     *
     * @throws IOException
     *             if an error occurs while opening either selector
     */
    private void openSelectors() throws IOException
    {
        readSelector = Selector.open();
        socket.register(readSelector, SelectionKey.OP_READ);

        writeSelector = Selector.open();
        socket.register(writeSelector, SelectionKey.OP_WRITE);
    }

    /**
     * Close the read and write selectors. This will prevent any further communication to
     * the connected backend through this manager until the selectors are
     * {@link #openSelectors() opened} again. This should happen before
     * {@link #redirectChannel() redirecting the channel}. If any errors occur here they
     * will be logged, but not thrown.
     */
    private void closeSelectors()
    {
        if (readSelector != null)
        {
            try
            {
                readSelector.close();
            }
            catch (IOException e)
            {
                logger.debug("Error while closing read selector", e);
            }
        }

        if (writeSelector != null)
        {
            try
            {
                writeSelector.close();
            }
            catch (IOException e)
            {
                logger.debug("Error while closing write selector", e);
            }
        }
    }

    /**
     * Start the receiver thread. This thread will wait for data to arrive from the
     * connected backend and deal with it (as either a response or an unsolicited backend
     * message). The reciever must be started before communication can proceed, but it
     * should be {@link #stopReceiver() stopped} before redirecting the channel to prevent
     * corruption.
     */
    private void startReceiver()
    {
        if (receiver != null)
        {
            return;
        }

        receiver = receiverExecutor.submit(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    try
                    {
                        if (readSelector.select() != 1 || Thread.interrupted())
                        {
                            break;
                        }

                        readSelector.selectedKeys().clear();

                        String value = Packet.read(socket);

                        logger.trace("Received message: {}", value);

                        if (backendEventGrabber != null
                            && backendEventGrabber.isBackendEvent(value))
                        {
                            continue;
                        }

                        /*
                         * If the client stops waiting for a response, it will increment
                         * this value. To keep things in sync, those skipped responses
                         * need to be thrown away when they arrive.
                         */
                        if (skippedResponses.get() > 0)
                        {
                            skippedResponses.decrementAndGet();
                            continue;
                        }

                        queue.add(value);
                    }
                    catch (InterruptedIOException e)
                    {
                        logger.info("Receiver interrupted");
                        break;
                    }
                    catch (IOException e)
                    {
                        logger.error("Connection error", e);

                        disconnect();
                        break;
                    }
                }
            }
        });
    }

    /**
     * Stop the receiver thread from pulling incoming data off the channel. Once this
     * occurs, communication from the backend will be ignored. This must occur before
     * {@link #redirectChannel() redirecting the channel}.
     */
    private void stopReceiver()
    {
        if (receiver == null)
        {
            return;
        }

        receiver.cancel(true);
        receiver = null;
    }

    /**
     * Determine whether or not this manager has an active connection to a backend server.
     *
     * @return <code>true</code> if the manager is connected; <code>false</code> otherwise
     */
    public boolean isConnected()
    {
        return socket != null && socket.isConnected();
    }

    /**
     * Close the active connection and clean up any resources associated with it. If there
     * is no active connection, this method will make sure that resources are cleaned up
     * and return.
     */
    public void disconnect()
    {
        /*
         * Make sure the receiver thread is cancelled and forgotten in case this
         * disconnect call is made after the socket has already been closed for some other
         * reason (like an error).
         */
        stopReceiver();

        if (!isConnected())
        {
            return;
        }

        logger.info("Disconnecting");

        closeSelectors();

        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            logger.debug("Error while closing socket", e);
        }

        logger.info("Disconnected");
    }

    /**
     * Send a message over the active connection. This method will not wait for a response
     * and should not be used with messages that will trigger a response from the backend.
     *
     * @param message
     *            the message to send
     * @throws IOException
     *             if this manager is not connected to a backend, is interrupted while
     *             sending the message, or some other communication error occurs
     */
    public void send(String message) throws IOException
    {
        logger.trace("Sending message: {}", message);

        if (writeSelector.select() != 1 || Thread.interrupted())
        {
            throw new InterruptedIOException();
        }

        writeSelector.selectedKeys().clear();

        Packet.write(socket, message);

        logger.trace("Message sent");
    }

    /**
     * Send a message to the backend and wait for a response. This method will wait
     * indefinitely and should not be used with messages that do not or may not cause the
     * backend to respond.
     *
     * @param message
     *            the message to send
     * @return the response from the backend or <code>null</code> if the thread is
     *         interrupted
     * @throws IOException
     *             if this manager is not connected to a backend or some other
     *             communication error occurs
     */
    public String sendAndWait(String message) throws IOException
    {
        return sendAndWait(message, 0);
    }

    /**
     * Send a message to the backend and wait for a response up to the given timeout
     * value.
     *
     * @param message
     *            the message to send
     * @param timeout
     *            the number of milliseconds to wait for a response
     * @return the response from the backend or <code>null</code> if the thread is
     *         interrupted or the timeout is reached
     * @throws IOException
     *             if this manager is not connected to a backend or some other
     *             communication error occurs
     */
    public String sendAndWait(String message, long timeout) throws IOException
    {
        synchronized (Lock.SEND_AND_WAIT)
        {
            send(message);

            try
            {
                logger.trace("Waiting for reply");

                if (timeout < 1)
                {
                    return queue.take();
                }

                String response = queue.poll(timeout, TimeUnit.MILLISECONDS);
                if (response == null)
                {
                    /*
                     * Indicate to the receiver thread that this response is being skipped
                     * so that it can be thrown away when it arrives.
                     */
                    skippedResponses.incrementAndGet();
                }

                return response;
            }
            catch (InterruptedException e)
            {
                logger.info("Interrupted while waiting for response", e);
                return null;
            }
        }
    }

    /**
     * Redirect the data channel used by this socket manager. While the channel is redirected, normal
     * communication is suspended. To release the redirected channel, simply close
     * it.
     *
     * @return the redirected channel stream
     */
    public ReadWriteByteChannel redirectChannel()
    {
        synchronized (Lock.REDIRECT_CHANNEL)
        {
            stopReceiver();
            closeSelectors();

            if (redirect == null)
            {
                redirect = new RedirectedChannel();
            }

            return redirect;
        }
    }

    private enum Lock
    {
        SEND_AND_WAIT, REDIRECT_CHANNEL
    }

    private class RedirectedChannel implements ReadWriteByteChannel
    {
        private volatile boolean closed;

        @Override
        public void close() throws IOException
        {
            synchronized (Lock.REDIRECT_CHANNEL)
            {
                if (closed)
                {
                    return;
                }

                closed = true;
                redirect = null;

                openSelectors();
                startReceiver();
            }
        }

        @Override
        public int read(ByteBuffer dst) throws IOException
        {
            checkClosed();
            return socket.read(dst);
        }

        @Override
        public int write(ByteBuffer src) throws IOException
        {
            checkClosed();
            return socket.write(src);
        }

        @Override
        public boolean isOpen()
        {
            return !closed;
        }

        private void checkClosed() throws IOException
        {
            if (!isOpen())
            {
                throw new IOException("redirected channel is no longer accessible");
            }
        }
    }

    /**
     * A composition of {@link ReadableByteChannel} and {@link WritableByteChannel}.
     *
     * @author Gregory P. Moyer
     */
    public interface ReadWriteByteChannel extends ReadableByteChannel, WritableByteChannel
    {
        /*
         * Composite interface
         */
    }
}
