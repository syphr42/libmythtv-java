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
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.api.Database;
import org.syphr.mythtv.api.MythVersion;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.db.DatabaseException;
import org.syphr.mythtv.db.SchemaVersion;
import org.syphr.mythtv.http.backend.ConnectionManager;
import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolVersion;
import org.syphr.mythtv.protocol.events.BackendEventListener;
import org.syphr.mythtv.types.RecordingCategory;

public class Backend
{
    private static Logger LOGGER = LoggerFactory.getLogger(Backend.class);

    private static final int DEFAULT_PROTOCOL_PORT = 6543;
    private static final int DEFAULT_HTTP_PORT = 6544;

    private final AutomaticProtocol protocol;

    private final Database database;

    private ConnectionManager connMan;

    private Protocol eventMonitor;

    private String host;

    public Backend(MythVersion version)
    {
        this(version.getProtocol(), version.getSchema());
    }

    public Backend(ProtocolVersion protocolVersion, SchemaVersion schemaVersion)
    {
        protocol = new AutomaticProtocol(protocolVersion, 1L, TimeUnit.MINUTES);
        database = new Database(schemaVersion);
    }

    public void setConnectionTimeout(long timeout, TimeUnit unit)
    {
        protocol.setTimeout(timeout, unit);
    }

    public void autoConfigure() throws IOException, DatabaseException
    {
        String localHost = InetAddress.getLocalHost().getHostName();

        setBackendConnectionParameters(localHost,
                                       localHost,
                                       DEFAULT_PROTOCOL_PORT,
                                       DEFAULT_HTTP_PORT);

        database.load();
    }

    public void setBackendConnectionParameters(String localHost,
                                               String backendHost,
                                               int protocolPort,
                                               int httpPort) throws IOException
    {
        this.host = backendHost;

        protocol.setConnectionParameters(localHost, backendHost, protocolPort == 0
                ? DEFAULT_PROTOCOL_PORT
                : protocolPort);

        connMan = new ConnectionManager(backendHost, httpPort == 0 ? DEFAULT_HTTP_PORT : httpPort);
    }

    public void setDatabaseConnectionParameters(String host,
                                                int port,
                                                String name,
                                                String username,
                                                String password) throws DatabaseException
    {
        database.load(host, port, name, username, password);
    }

    public void destroy()
    {
        protocol.shutdownConnection();
        stopEventMonitor();
        connMan = null;
    }

    public void startEventMonitor(String localHost, EventLevel level) throws IOException,
                                                                     CommandException
    {
        eventMonitor = protocol.newProtocol();
        eventMonitor.mythProtoVersion();
        eventMonitor.ann(ConnectionType.MONITOR, localHost, level);
    }

    public void stopEventMonitor()
    {
        if (!isEventMonitorConnected())
        {
            return;
        }

        try
        {
            eventMonitor.done();
        }
        catch (IOException e)
        {
            LOGGER.warn("Event monitor failed to disconnect properly", e);
        }
        finally
        {
            eventMonitor = null;
        }
    }

    public boolean isEventMonitorConnected()
    {
        return eventMonitor != null && eventMonitor.isConnected();
    }

    public void addBackendEventListener(BackendEventListener l)
    {
        if (!isEventMonitorConnected())
        {
            LOGGER.warn("Attempted to add an event listener when the event monitor is not connected");
            return;
        }

        eventMonitor.addBackendEventListener(l);
    }

    public void removeBackendEventListener(BackendEventListener l)
    {
        if (!isEventMonitorConnected())
        {
            LOGGER.warn("Attempted to remove an event listener when the event monitor is not connected");
            return;
        }

        eventMonitor.removeBackendEventListener(l);
    }

    public String getHost()
    {
        return host;
    }

    // TODO users of this are broken for now - need to detect backend's db and load
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
        List<Recorder> recorders = new ArrayList<Recorder>();

        for (Integer recorderId : protocol.getFreeRecorderList())
        {
            recorders.add(new Recorder(this, recorderId, protocol));
        }

        return recorders;
    }

    /*
     * Get recordings
     */

    public boolean isRecordingScheduleConflicted() throws IOException
    {
        return protocol.queryGetAllPending().isConflicted();
    }

    public List<Recording> getScheduledRecordings() throws IOException
    {
        return Recording.getRecordings(protocol.queryGetAllPending(), protocol);
    }

    public List<Recording> getConflictingRecordings(Program program) throws IOException
    {
        return Recording.getRecordings(protocol.queryGetConflicting(program), protocol);
    }

    public List<Recording> getExpiringRecordings() throws IOException
    {
        return Recording.getRecordings(protocol.queryGetExpiring(), protocol);
    }

    public List<Recording> getRecordings(RecordingCategory category) throws IOException
    {
        return Recording.getRecordings(protocol.queryRecordings(category), protocol);
    }

    public Recording getRecording(String basename) throws IOException, CommandException
    {
        return new Recording(protocol.queryRecordingBasename(basename), protocol);
    }

    public Recording getRecording(Channel channel, Date startTime) throws IOException,
                                                                  CommandException
    {
        return new Recording(protocol.queryRecordingTimeslot(channel, startTime), protocol);
    }
}
