/*
 * Copyright 2011-2012 Gregory P. Moyer
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
package org.syphr.mythtv.util.socket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
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
import org.syphr.mythtv.util.exception.ResponseTimeoutException;

/**
 * This class manages a low-level network connection. It provides the necessary
 * read/write capabilities as well as the ability to take over the
 * communications channel entirely to perform bulk raw data transfer, such as
 * transferring a file while another manager controls the flow.
 * 
 * @author Gregory P. Moyer
 */
public class DefaultSocketManager implements SocketManager
{
    private final Logger logger = LoggerFactory.getLogger(DefaultSocketManager.class);

    private final Packet packet;

    private final BlockingQueue<String> queue;
    private final AtomicInteger skippedResponses;
    private final ExecutorService receiverExecutor;

    private SocketChannel socket;
    private Selector readSelector;
    private Selector writeSelector;

    private Future<?> receiver;

    private Interceptor interceptor;

    private ByteChannel redirect;

    private long defaultTimeout;

    /**
     * Construct a new socket manager that is not connected to a server.
     * 
     * @param packet
     *            a packet implementation that will handle formatting outgoing
     *            messages and parsing incoming messages
     */
    public DefaultSocketManager(Packet packet)
    {
        this.packet = packet;

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

                Thread t = new Thread(group, r, DefaultSocketManager.class.getSimpleName()
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

    @Override
    public void setInterceptor(Interceptor interceptor)
    {
        this.interceptor = interceptor;
    }

    @Override
    public void setDefaultTimeout(long time, TimeUnit unit)
    {
        this.defaultTimeout = unit.toMillis(time);
    }

    @Override
    public long getDefaultTimeout(TimeUnit unit)
    {
        return unit.convert(defaultTimeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void connect(String host, int port, final long timeout) throws IOException
    {
        connect(new InetSocketAddress(host, port), timeout);
    }

    @Override
    public void connect(InetSocketAddress addr, final long timeout) throws IOException
    {
        if (isConnected())
        {
            disconnect();
        }

        logger.info("Connecting to {}:{}", addr.getHostName(), addr.getPort());

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
            socket.connect(addr);
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
     * Open the read and write selectors. This needs to be done before data can
     * be send through this socket manager using its own API (not the
     * {@link #redirectChannel() redirected channel}). These selectors should be
     * {@link #closeSelectors() closed} before redirecting the channel to
     * prevent corruption.
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
     * Close the read and write selectors. This will prevent any further
     * communication to the connected server through this manager until the
     * selectors are {@link #openSelectors() opened} again. This should happen
     * before {@link #redirectChannel() redirecting the channel}. If any errors
     * occur here they will be logged, but not thrown.
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
     * Start the receiver thread. This thread will wait for data to arrive from
     * the connected server and deal with it (as either a response or an
     * unsolicited message). The receiver must be started before communication
     * can proceed, but it should be {@link #stopReceiver() stopped} before
     * redirecting the channel to prevent corruption.
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

                        for (String value : packet.read(socket))
                        {
                            logger.trace("Received message: {}", value);

                            if (interceptor != null && interceptor.intercept(value))
                            {
                                continue;
                            }

                            /*
                             * If the client stops waiting for a response, it
                             * will increment this value. To keep things in
                             * sync, those skipped responses need to be thrown
                             * away when they arrive.
                             */
                            if (skippedResponses.get() > 0)
                            {
                                logger.trace("Discarding skipped message: {}", value);

                                skippedResponses.decrementAndGet();
                                continue;
                            }

                            queue.add(value);
                        }
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
     * Stop the receiver thread from pulling incoming data off the channel. Once
     * this occurs, communication from the server will be ignored. This must
     * occur before {@link #redirectChannel() redirecting the channel}.
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

    @Override
    public boolean isConnected()
    {
        return socket != null && socket.isConnected();
    }

    @Override
    public InetSocketAddress getConnectedAddress()
    {
        return isConnected() ? (InetSocketAddress)socket.socket().getRemoteSocketAddress() : null;
    }

    @Override
    public SocketManager newConnection() throws IOException
    {
        SocketManager newManager = createSocketManager(packet);

        if (isConnected())
        {
            newManager.connect(getConnectedAddress(), 0);
        }

        return newManager;
    }

    /**
     * Build a new {@link DefaultSocketManager} in a default state.
     * 
     * @param packet
     *            the packet implementation to use
     * @return the new manager
     */
    protected SocketManager createSocketManager(Packet packet)
    {
        return new DefaultSocketManager(packet);
    }

    @Override
    public void disconnect()
    {
        /*
         * Make sure the receiver thread is cancelled and forgotten in case this
         * disconnect call is made after the socket has already been closed for
         * some other reason (like an error).
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

    @Override
    public void send(String message) throws IOException
    {
        logger.trace("Sending message: {}", message);

        if (writeSelector.select() != 1 || Thread.interrupted())
        {
            throw new InterruptedIOException();
        }

        writeSelector.selectedKeys().clear();

        packet.write(socket, message);

        logger.trace("Message sent");
    }

    @Override
    public String sendAndWait(String message) throws ResponseTimeoutException, IOException
    {
        return sendAndWait(message, defaultTimeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public String sendAndWait(String message, long timeout, TimeUnit unit) throws ResponseTimeoutException,
                                                                          IOException
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

                String response = queue.poll(timeout, unit);
                if (response == null)
                {
                    /*
                     * Indicate to the receiver thread that this response is
                     * being skipped so that it can be thrown away when it
                     * arrives.
                     */
                    skippedResponses.incrementAndGet();

                    throw new ResponseTimeoutException(TimeUnit.MILLISECONDS.convert(timeout, unit)
                            + " ms");
                }

                return response;
            }
            catch (InterruptedException e)
            {
                logger.info("Interrupted while waiting for response", e);
                return "";
            }
        }
    }

    @Override
    public ByteChannel redirectChannel()
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

    private class RedirectedChannel implements ByteChannel
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
}
