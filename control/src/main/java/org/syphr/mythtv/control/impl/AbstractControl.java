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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.commons.socket.Interceptor;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandler;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandlerLog;
import org.syphr.mythtv.control.Control;

public abstract class AbstractControl implements Control
{
    private final SocketManager socketManager;

    private volatile CountDownLatch connectionLatch;

    private volatile Translator translator;

    private UnsupportedHandler unsupported;

    public AbstractControl(SocketManager socketManager)
    {
        this.socketManager = socketManager;
        socketManager.setInterceptor(createInterceptor());

        unsupported = new UnsupportedHandlerLog();
    }

    @Override
    public void setUnsupportedHandler(UnsupportedHandler unsupported)
    {
        this.unsupported = unsupported;
    }

    protected void handleUnsupported(String opDescription)
    {
        unsupported.handle(opDescription);
    }

    protected Interceptor createInterceptor()
    {
        return new Interceptor()
        {
            @Override
            public boolean intercept(String response)
            {
                if (response.startsWith("MythFrontend Network Control"))
                {
                    /*
                     * Signal the connecting thread that the banner has been
                     * received.
                     */
                    connectionLatch.countDown();
                    return true;
                }

                return false;
            }
        };
    }

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        connectionLatch = new CountDownLatch(1);
        socketManager.connect(host, port, timeout);

        /*
         * Wait for the initial banner to be sent from the server signaling the
         * connection is complete.
         */
        try
        {
            if (!connectionLatch.await(timeout, TimeUnit.MILLISECONDS))
            {
                throw new IOException("Connection timed out");
            }
        }
        catch (InterruptedException e)
        {
            throw new IOException("Connection interrupted");
        }

        socketManager.setDefaultTimeout(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isConnected()
    {
        return socketManager.isConnected();
    }

    protected SocketManager getSocketManager()
    {
        return socketManager;
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

    protected abstract Translator createTranslator();
}
