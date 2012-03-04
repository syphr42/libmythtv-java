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

import org.syphr.mythtv.api.backend.AutomaticProtocol;
import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.protocol.InvalidProtocolVersionException;

public class BackendConnection implements Connection
{
    private AutomaticProtocol protocol;

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        protocol = new AutomaticProtocol();
        protocol.connect(host, port, timeout);

        try
        {
            protocol.mythProtoVersion();
            protocol.ann(ConnectionType.PLAYBACK,
                         InetAddress.getLocalHost().getHostName(),
                         EventLevel.NONE);
        }
        catch (InvalidProtocolVersionException e)
        {
            /*
             * No need to pass the nested exception along here because the cause
             * of the problem is clear.
             */
            throw new IOException("Host speaks unsupported protocol version "
                    + e.getSupportedVersionStr());
        }
    }

    @Override
    public void disconnect()
    {
        try
        {
            protocol.done();
        }
        catch (IOException e)
        {
            // ignore failure on disconnect
        }
    }

    @Override
    public String send(String command) throws IOException
    {
        return protocol.getSocketManager().sendAndWait(command);
    }
}
