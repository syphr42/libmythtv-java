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
import java.nio.channels.Channel;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.proto.events.BackendEventGrabber;

public class SocketManager
{
    private final Logger logger = LoggerFactory.getLogger(SocketManager.class);

    private final BlockingQueue<String> queue;
    private final ExecutorService receiverExecutor;

    private SocketChannel socket;
    private Selector readSelector;
    private Selector writeSelector;

    private Future<?> receiver;

    private BackendEventGrabber backendEventGrabber;

    private RedirectedChannel redirect;

    public SocketManager()
    {
        queue = new LinkedBlockingQueue<String>();
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

    public void setBackendEventGrabber(BackendEventGrabber backendEventGrabber)
    {
        this.backendEventGrabber = backendEventGrabber;
    }

    public void connect(String host, int port, int timeout) throws IOException
    {
        if (isConnected())
        {
            return;
        }

        logger.info("Connecting to {}:{}", host, port);

        socket = SocketChannel.open();
        socket.configureBlocking(true);
        // TODO timeout
        socket.connect(new InetSocketAddress(host, port));
        socket.configureBlocking(false);

        logger.info("Connected");

        openSelectors();
        startReceiver();
    }

    private void openSelectors() throws IOException
    {
        readSelector = Selector.open();
        socket.register(readSelector, SelectionKey.OP_READ);

        writeSelector = Selector.open();
        socket.register(writeSelector, SelectionKey.OP_WRITE);
    }

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

    private void stopReceiver()
    {
        if (receiver == null)
        {
            return;
        }

        receiver.cancel(true);
        receiver = null;
    }

    public boolean isConnected()
    {
        return socket != null && socket.isConnected();
    }

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

    public void send(String value) throws IOException
    {
        logger.trace("Sending message: {}", value);

        if (writeSelector.select() != 1 || Thread.interrupted())
        {
            throw new InterruptedIOException();
        }

        writeSelector.selectedKeys().clear();

        Packet.write(socket, value);

        logger.trace("Message sent");
    }

    public String sendAndWait(String value) throws IOException
    {
        synchronized (Lock.SEND_AND_WAIT)
        {
            send(value);

            try
            {
                logger.trace("Waiting for reply");
                return queue.take();
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

    public static interface ReadWriteByteChannel extends ReadableByteChannel, WritableByteChannel, Channel
    {
        /*
         * Composite interface
         */
    }
}
