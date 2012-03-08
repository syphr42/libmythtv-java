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
package org.syphr.mythtv.api.backend;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.commons.socket.Interceptor;
import org.syphr.mythtv.commons.socket.SocketManager;

public class AutoReConnectingSocketManager implements SocketManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoReConnectingSocketManager.class);

    /**
     * The delegate socket manager.
     */
    private final SocketManager delegate;

    private InetSocketAddress server;

    private long connectionTimeout;

    public AutoReConnectingSocketManager(SocketManager delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public void setInterceptor(Interceptor interceptor)
    {
        delegate.setInterceptor(interceptor);
    }

    @Override
    public void setDefaultMessageTimeout(long time, TimeUnit unit)
    {
        delegate.setDefaultMessageTimeout(time, unit);
    }

    @Override
    public long getDefaultMessageTimeout(TimeUnit unit)
    {
        return delegate.getDefaultMessageTimeout(unit);
    }

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        connect(new InetSocketAddress(host, port), timeout);
    }

    @Override
    public synchronized void connect(InetSocketAddress addr, long timeout) throws IOException
    {
        this.server = addr;
        this.connectionTimeout = timeout;

        delegate.connect(addr, timeout);
    }

    @Override
    public boolean isConnected()
    {
        connectIfNecessary();
        return delegate.isConnected();
    }

    @Override
    public InetSocketAddress getConnectedAddress()
    {
        connectIfNecessary();
        return delegate.getConnectedAddress();
    }

    @Override
    public SocketManager newConnection() throws IOException
    {
        connectIfNecessary();
        return delegate.newConnection();
    }

    @Override
    public synchronized void disconnect()
    {
        this.server = null;
        delegate.disconnect();
    }

    @Override
    public void send(String message) throws IOException
    {
        connectIfNecessary();
        delegate.send(message);
    }

    @Override
    public String sendAndWait(String message) throws IOException
    {
        connectIfNecessary();
        return delegate.sendAndWait(message);
    }

    @Override
    public String sendAndWait(String message, long timeout, TimeUnit unit) throws IOException
    {
        connectIfNecessary();
        return delegate.sendAndWait(message, timeout, unit);
    }

    @Override
    public ByteChannel redirectChannel()
    {
        connectIfNecessary();
        return delegate.redirectChannel();
    }

    private synchronized void connectIfNecessary()
    {
        if (server == null || delegate.isConnected())
        {
            return;
        }

        try
        {
            delegate.connect(server, connectionTimeout);
        }
        catch (IOException e)
        {
            LOGGER.warn("Auto-reconnect failed", e);
        }
    }
}
