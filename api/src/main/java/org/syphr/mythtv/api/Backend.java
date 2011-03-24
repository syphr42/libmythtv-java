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
package org.syphr.mythtv.api;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolFactory;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.events.BackendEventListener;
import org.syphr.mythtv.protocol.types.ConnectionType;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.protocol.types.ProtocolVersion;

public class Backend
{
    private static Logger LOGGER = LoggerFactory.getLogger(Backend.class);

    private final SocketManager socketManager;
    private final Protocol protocol;

    public Backend(ProtocolVersion version)
    {
        socketManager = new SocketManager();
        protocol = ProtocolFactory.createInstance(version, socketManager);
    }

    public void connect(String host,
                        int port,
                        int timeout,
                        ConnectionType connectionType,
                        EventLevel eventLevel) throws IOException, CommandException
    {
        socketManager.connect(host, port, timeout);

        try
        {
            protocol.mythProtoVersion();
            protocol.ann(connectionType,
                         InetAddress.getLocalHost().getHostName(),
                         eventLevel);
        }
        catch (IOException e)
        {
            disconnect();
            throw e;
        }
        catch (CommandException e)
        {
            disconnect();
            throw e;
        }
    }

    public boolean isConnected()
    {
        return socketManager.isConnected();
    }

    private void verifyConnected() throws IOException
    {
        if (!isConnected())
        {
            throw new IOException("not connected");
        }
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

    public void addBackendEventListener(BackendEventListener l)
    {
        protocol.addBackendEventListener(l);
    }

    public void removeBackendEventListener(BackendEventListener l)
    {
        protocol.removeBackendEventListener(l);
    }

    public ServerInfo getInfo() throws IOException
    {
        return new ServerInfo(protocol);
    }

    public List<Recorder> getFreeRecorders() throws IOException
    {
        verifyConnected();

        List<Recorder> recorders = new ArrayList<Recorder>();

        for (Integer recorderId : protocol.getFreeRecorderList())
        {
            recorders.add(new Recorder(recorderId, protocol));
        }

        return recorders;
    }
}
