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
import org.syphr.mythtv.db.DatabaseException;
import org.syphr.mythtv.db.SchemaVersion;
import org.syphr.mythtv.http.backend.BackendFactory;
import org.syphr.mythtv.http.backend.ConnectionManager;
import org.syphr.mythtv.http.backend.ContentException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolFactory;
import org.syphr.mythtv.protocol.ProtocolSocketManager;
import org.syphr.mythtv.protocol.events.BackendEventListener;
import org.syphr.mythtv.protocol.types.ConnectionType;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.protocol.types.ProtocolVersion;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;

public class Backend
{
    private static Logger LOGGER = LoggerFactory.getLogger(Backend.class);

    private final SocketManager socketManager;
    private final Protocol protocol;

    private final Database database;

    private ConnectionManager connMan;

    public Backend(MythVersion version)
    {
        this(version.getProtocol(), version.getSchema());
    }

    public Backend(ProtocolVersion protocolVersion, SchemaVersion schemaVersion)
    {
        socketManager = new ProtocolSocketManager();
        protocol = ProtocolFactory.createInstance(protocolVersion, socketManager);

        database = new Database(schemaVersion);
    }

    public void connect(String host,
                        int protocolPort,
                        int httpPort,
                        int timeout,
                        ConnectionType connectionType,
                        EventLevel eventLevel) throws IOException,
                                              CommandException,
                                              DatabaseException
    {
        connMan = new ConnectionManager(host, httpPort);

        socketManager.connect(host, protocolPort, timeout);

        try
        {
            protocol.mythProtoVersion();
            protocol.ann(connectionType, InetAddress.getLocalHost()
                                                    .getHostName(), eventLevel);

            try
            {
                org.syphr.mythtv.http.backend.Database dbInfo = BackendFactory.getMyth(connMan)
                                                                              .getConnectionInfo()
                                                                              .getDatabase();
                database.load(normalizeDbHost(host, dbInfo.getHost()),
                              dbInfo.getPort(),
                              dbInfo.getName(),
                              dbInfo.getUserName(),
                              dbInfo.getPassword());
            }
            catch (ContentException e)
            {
                /*
                 * If this happens, we were unable to connect to the backend via
                 * http. This might be due to an unsupported version so try to
                 * connect to the database via a local config file.
                 */
                database.load();
            }
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
        catch (DatabaseException e)
        {
            disconnect();
            throw e;
        }
    }

    private String normalizeDbHost(String backendHost, String remoteDiscoveredDbHost)
    {
        if ("127.0.0.1".equals(remoteDiscoveredDbHost) || "localhost".equals(remoteDiscoveredDbHost))
        {
            return backendHost;
        }

        return remoteDiscoveredDbHost;
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
            connMan = null;
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

    public Database getDatabase()
    {
        return database;
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
            recorders.add(new Recorder(this, recorderId, protocol));
        }

        return recorders;
    }
}
