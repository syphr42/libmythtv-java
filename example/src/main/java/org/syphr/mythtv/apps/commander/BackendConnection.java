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
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.api.backend.AutomaticProtocol;

public class BackendConnection implements Connection
{
    private AutomaticProtocol protocol;

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        protocol = new AutomaticProtocol(timeout, TimeUnit.MILLISECONDS);
        protocol.setConnectionParameters(InetAddress.getLocalHost().getHostName(), host, port);
    }

    @Override
    public void disconnect()
    {
        protocol.shutdownConnection();
    }

    @Override
    public String send(String command) throws IOException
    {
        return protocol.getSocketManager().sendAndWait(command);
    }
}
