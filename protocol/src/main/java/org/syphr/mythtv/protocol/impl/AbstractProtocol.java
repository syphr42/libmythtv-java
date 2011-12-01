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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.events.BackendEventListener;
import org.syphr.mythtv.util.socket.Interceptor;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;
import org.syphr.mythtv.util.unsupported.UnsupportedHandler;
import org.syphr.mythtv.util.unsupported.UnsupportedHandlerLog;

public abstract class AbstractProtocol implements Protocol
{
    private final SocketManager socketManager;
    private final List<BackendEventListener> listeners;

    private volatile Translator translator;
    private volatile Parser parser;

    private UnsupportedHandler unsupported;

    public AbstractProtocol(SocketManager socketManager)
    {
        this.socketManager = socketManager;
        this.listeners = new CopyOnWriteArrayList<BackendEventListener>();

        socketManager.setInterceptor(createEventGrabber());

        unsupported = new UnsupportedHandlerLog();
    }

    @Override
    public void setUnsupportedHandler(UnsupportedHandler unsupported)
    {
        this.unsupported = unsupported;
    }

    protected UnsupportedHandler getUnsupportedHandler()
    {
        return unsupported;
    }

    protected void handleUnsupported(String opDescription)
    {
        unsupported.handle(opDescription);
    }

    @Override
    public Protocol newProtocol() throws IOException
    {
        SocketManager newManager = getSocketManager().newConnection();

        try
        {
            return getClass().getConstructor(SocketManager.class).newInstance(newManager);
        }
        catch (Exception e)
        {
            throw new IllegalStateException("Unable to create new protocol instance", e);
        }
    }

    @Override
    public ByteChannel getChannel()
    {
        return getSocketManager().redirectChannel();
    }

    @Override
    public SocketManager getSocketManager()
    {
        return socketManager;
    }

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        getSocketManager().connect(host, port, timeout);
    }

    @Override
    public boolean isConnected()
    {
        return getSocketManager().isConnected();
    }

    @Override
    public void addBackendEventListener(BackendEventListener l)
    {
        listeners.add(l);
    }

    @Override
    public void removeBackendEventListener(BackendEventListener l)
    {
        listeners.remove(l);
    }

    protected List<BackendEventListener> getListeners()
    {
        return listeners;
    }

    @Override
    public <E extends Enum<E>> List<E> getAvailableTypes(Class<E> type)
    {
        return getTranslator().getAllowed(type);
    }

    protected Translator getTranslator()
    {
        if (translator == null)
        {
            synchronized (this)
            {
                if (translator == null)
                {
                    translator = createTranslator();
                }
            }
        }

        return translator;
    }

    protected Parser getParser()
    {
        if (parser == null)
        {
            synchronized (this)
            {
                if (parser == null)
                {
                    parser = createParser(getTranslator());
                }
            }
        }

        return parser;
    }

    protected abstract Translator createTranslator();

    protected abstract Parser createParser(Translator translator);

    protected abstract Interceptor createEventGrabber();
}
