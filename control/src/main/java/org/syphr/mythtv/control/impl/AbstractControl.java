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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.control.Control;
import org.syphr.mythtv.util.socket.Interceptor;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public abstract class AbstractControl implements Control
{
    private final SocketManager socketManager;

    public AbstractControl(SocketManager socketManager)
    {
        this.socketManager = socketManager;
        socketManager.setInterceptor(createInterceptor());
    }

    protected Interceptor createInterceptor()
    {
        return new Interceptor()
        {
            @Override
            public boolean intercept(String response)
            {
                return response.startsWith("MythFrontend Network Control");
            }
        };
    }

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        socketManager.connect(host, port, timeout);
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

    @Override
    public void exit() throws IOException
    {
        socketManager.disconnect();
    }

    protected abstract Translator getTranslator();
}
