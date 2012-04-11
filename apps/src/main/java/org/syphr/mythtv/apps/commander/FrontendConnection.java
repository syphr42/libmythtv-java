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
package org.syphr.mythtv.apps.commander;

import java.io.IOException;

import org.syphr.mythtv.commons.socket.Interceptor;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.control.impl.ControlSocketManager;

public class FrontendConnection implements Connection
{
    private final SocketManager socketManager;

    public FrontendConnection()
    {
        socketManager = new ControlSocketManager();

        socketManager.setInterceptor(new Interceptor()
        {
            @Override
            public boolean intercept(String response)
            {
                return response.startsWith("MythFrontend Network Control");
            }
        });
    }

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        socketManager.connect(host, port, timeout);
    }

    @Override
    public void disconnect()
    {
        socketManager.disconnect();
    }

    @Override
    public String send(String command) throws IOException
    {
        return socketManager.sendAndWait(command);
    }
}
