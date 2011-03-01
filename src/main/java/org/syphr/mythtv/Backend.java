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
package org.syphr.mythtv;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.proto.Protocol;
import org.syphr.mythtv.proto.ProtocolFactory;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.ProtocolVersion;

public class Backend
{
    private static Logger LOGGER = LoggerFactory.getLogger(Backend.class);

    private final SocketManager socketManager;
    private final Protocol protocol;
    private final ConnectionType connectionType;

    public Backend(ProtocolVersion version, ConnectionType connectionType)
    {
        socketManager = new SocketManager();
        this.protocol = ProtocolFactory.createInstance(version, socketManager);
        this.connectionType = connectionType;
    }

    public void connect(String host, int port, int timeout) throws IOException
    {
        socketManager.connect(host, port, timeout);

        try
        {
            protocol.mythProtoVersion();

            // TODO don't hard-code this
            protocol.ann(connectionType,
                         InetAddress.getLocalHost().getHostName(),
                         EventLevel.NONE);
        }
        catch (IOException e)
        {
            disconnect();
            throw e;
        }
    }

    public boolean isConnected()
    {
        return socketManager.isConnected();
    }

    public void disconnect()
    {
        if (!isConnected())
        {
            return;
        }

        try
        {
            protocol.done();
        }
        catch (IOException e)
        {
            LOGGER.info("exception while disconnecting", e);
        }

        socketManager.disconnect();
    }
}
