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
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.channels.ByteChannel;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandler;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.FileEntry;
import org.syphr.mythtv.data.FileInfo;
import org.syphr.mythtv.data.Load;
import org.syphr.mythtv.data.MemStats;
import org.syphr.mythtv.data.PixMap;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.RecorderDevice;
import org.syphr.mythtv.data.RecorderLocation;
import org.syphr.mythtv.data.RecordingsInProgress;
import org.syphr.mythtv.data.TimeInfo;
import org.syphr.mythtv.data.UpcomingRecordings;
import org.syphr.mythtv.data.VideoEditInfo;
import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.protocol.ProtocolVersionException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolFactory;
import org.syphr.mythtv.protocol.ProtocolSocketManager;
import org.syphr.mythtv.protocol.ProtocolVersion;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.protocol.events.BackendEventListener;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.Verbose;

/**
 * This class provides a protocol that will automatically work with any
 * supported backend version.
 * 
 * @author Gregory P. Moyer
 */
public class AutomaticProtocol implements Protocol
{
    /**
     * Standard logging facility.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AutomaticProtocol.class);

    /**
     * The protocol in use.
     */
    private Protocol delegate;

    public AutomaticProtocol()
    {
        this(ProtocolVersion.values()[0], new ProtocolSocketManager());
    }

    public AutomaticProtocol(ProtocolVersion version)
    {
        this(version, new ProtocolSocketManager());
    }

    public AutomaticProtocol(SocketManager socketManager)
    {
        this(ProtocolVersion.values()[0], socketManager);
    }

    public AutomaticProtocol(ProtocolVersion version, SocketManager socketManager)
    {
        delegate = ProtocolFactory.createInstance(version, socketManager);
    }

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        delegate.connect(host, port, timeout);
    }

    @Override
    public void mythProtoVersion() throws IOException, ProtocolVersionException
    {
        SocketManager socketManager = getSocketManager();
        InetSocketAddress server = socketManager.getConnectedAddress();

        try
        {
            delegate.mythProtoVersion();
        }
        catch (ProtocolVersionException e1)
        {
            ProtocolVersion supported = e1.getSupportedVersion();
            if (supported == null)
            {
                throw new IOException(e1);
            }

            /*
             * The specified protocol version is incorrect, but the proper
             * version has been detected and is fully supported by this
             * framework. Therefore, try again with the correct version.
             */
            LOGGER.warn("Attempted backend connection using incorrect protocol version ({}), automatically retrying with correct version ({})",
                        e1.getAttemptedVersionStr(),
                        e1.getSupportedVersionStr());

            Protocol newDelegate = ProtocolFactory.createInstance(supported, socketManager);
            delegate.copyBackendEventListeners(newDelegate);
            delegate.copyUnsupportedHandler(newDelegate);
            delegate = newDelegate;

            socketManager.connect(server, 0);

            try
            {
                delegate.mythProtoVersion();
            }
            catch (ProtocolVersionException e2)
            {
                throw new IOException(e2);
            }
        }
    }

    @Override
    public void ann(ConnectionType connectionType, String host, EventLevel level) throws IOException
    {
        delegate.ann(connectionType, host, level);
    }

    @Override
    public void annSlaveBackend(InetAddress address, Program... recordings) throws IOException
    {
        delegate.annSlaveBackend(address, recordings);
    }

    @Override
    public void annMediaServer(InetAddress address) throws IOException
    {
        delegate.annMediaServer(address);
    }

    @Override
    public QueryFileTransfer annFileTransfer(String host,
                                             FileTransferType type,
                                             boolean readAhead,
                                             long timeout,
                                             URI uri,
                                             String storageGroup,
                                             Protocol commandProtocol) throws IOException
    {
        return delegate.annFileTransfer(host,
                                        type,
                                        readAhead,
                                        timeout,
                                        uri,
                                        storageGroup,
                                        commandProtocol);
    }

    @Override
    public ByteChannel getChannel()
    {
        return delegate.getChannel();
    }

    @Override
    public boolean isConnected()
    {
        return delegate.isConnected();
    }

    @Override
    public void setUnsupportedHandler(UnsupportedHandler handler)
    {
        delegate.setUnsupportedHandler(handler);
    }

    @Override
    public <E extends Enum<E>> List<E> getAvailableTypes(Class<E> type)
    {
        return delegate.getAvailableTypes(type);
    }

    @Override
    public Protocol newProtocol() throws IOException
    {
        return delegate.newProtocol();
    }

    @Override
    public SocketManager getSocketManager()
    {
        return delegate.getSocketManager();
    }

    @Override
    public void done() throws IOException
    {
        delegate.done();
    }

    @Override
    public void allowShutdown() throws IOException
    {
        delegate.allowShutdown();
    }

    @Override
    public void blockShutdown() throws IOException
    {
        delegate.blockShutdown();
    }

    @Override
    public int checkRecording(Program program) throws IOException
    {
        return delegate.checkRecording(program);
    }

    @Override
    public boolean deleteFile(URI filename, String storageGroup) throws IOException
    {
        return delegate.deleteFile(filename, storageGroup);
    }

    @Override
    public void deleteRecording(Channel channel, Date recStartTs, boolean force, boolean forget) throws IOException,
                                                                                                CommandException
    {
        delegate.deleteRecording(channel, recStartTs, force, forget);
    }

    @Override
    public URI downloadFile(URL url, String storageGroup, URI filename) throws IOException,
                                                                       CommandException
    {
        return delegate.downloadFile(url, storageGroup, filename);
    }

    @Override
    public URI downloadFileNow(URL url, String storageGroup, URI filename) throws IOException,
                                                                          CommandException
    {
        return delegate.downloadFileNow(url, storageGroup, filename);
    }

    @Override
    public Program fillProgramInfo(String host, Program program) throws IOException
    {
        return delegate.fillProgramInfo(host, program);
    }

    @Override
    public void forgetRecording(Program program) throws IOException
    {
        delegate.forgetRecording(program);
    }

    @Override
    public boolean freeTuner(int recorderId) throws IOException
    {
        return delegate.freeTuner(recorderId);
    }

    @Override
    public RecorderLocation getFreeRecorder() throws IOException
    {
        return delegate.getFreeRecorder();
    }

    @Override
    public int getFreeRecorderCount() throws IOException
    {
        return delegate.getFreeRecorderCount();
    }

    @Override
    public List<Integer> getFreeRecorderList() throws IOException
    {
        return delegate.getFreeRecorderList();
    }

    @Override
    public RecorderLocation getNextFreeRecorder(RecorderLocation from) throws IOException
    {
        return delegate.getNextFreeRecorder(from);
    }

    @Override
    public RecorderLocation getRecorderFromNum(int recorderId) throws IOException, CommandException
    {
        return delegate.getRecorderFromNum(recorderId);
    }

    @Override
    public RecorderLocation getRecorderNum(Program program) throws IOException
    {
        return delegate.getRecorderNum(program);
    }

    @Override
    public void goToSleep() throws IOException, CommandException
    {
        delegate.goToSleep();
    }

    @Override
    public RecorderDevice lockTuner(int recorderId) throws IOException, CommandException
    {
        return delegate.lockTuner(recorderId);
    }

    @Override
    public void messageClearSettingsCache() throws IOException
    {
        delegate.messageClearSettingsCache();
    }

    @Override
    public void messageResetIdleTime() throws IOException
    {
        delegate.messageResetIdleTime();
    }

    @Override
    public void messageSetVerbose(List<Verbose> options) throws IOException, CommandException
    {
        delegate.messageSetVerbose(options);
    }

    @Override
    public long queryBookmark(Channel channel, Date recStartTs) throws IOException
    {
        return delegate.queryBookmark(channel, recStartTs);
    }

    @Override
    public URI queryCheckFile(boolean checkSlaves, Program program) throws IOException
    {
        return delegate.queryCheckFile(checkSlaves, program);
    }

    @Override
    public List<VideoEditInfo> queryCommBreak(Channel channel, Date recStartTs) throws IOException
    {
        return delegate.queryCommBreak(channel, recStartTs);
    }

    @Override
    public List<VideoEditInfo> queryCutList(Channel channel, Date recStartTs) throws IOException
    {
        return delegate.queryCutList(channel, recStartTs);
    }

    @Override
    public FileInfo queryFileExists(URI filename, String storageGroup) throws IOException
    {
        return delegate.queryFileExists(filename, storageGroup);
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup) throws IOException,
                                                                  CommandException
    {
        return delegate.queryFileHash(filename, storageGroup);
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup, String host) throws IOException,
                                                                               CommandException
    {
        return delegate.queryFileHash(filename, storageGroup, host);
    }

    @Override
    public List<DriveInfo> queryFreeSpace() throws IOException
    {
        return delegate.queryFreeSpace();
    }

    @Override
    public DriveInfo queryFreeSpaceSummary() throws IOException
    {
        return delegate.queryFreeSpaceSummary();
    }

    @Override
    public void queryGenPixMap2(String id, Program program) throws IOException, CommandException
    {
        delegate.queryGenPixMap2(id, program);
    }

    @Override
    public UpcomingRecordings queryGetAllPending() throws IOException
    {
        return delegate.queryGetAllPending();
    }

    @Override
    public List<Program> queryGetAllScheduled() throws IOException
    {
        return delegate.queryGetAllScheduled();
    }

    @Override
    public List<Program> queryGetConflicting(Program program) throws IOException
    {
        return delegate.queryGetConflicting(program);
    }

    @Override
    public List<Program> queryGetExpiring() throws IOException
    {
        return delegate.queryGetExpiring();
    }

    @Override
    public Date queryGuideDataThrough() throws IOException
    {
        return delegate.queryGuideDataThrough();
    }

    @Override
    public String queryHostname() throws IOException
    {
        return delegate.queryHostname();
    }

    @Override
    public boolean queryIsActiveBackend(String hostname) throws IOException
    {
        return delegate.queryIsActiveBackend(hostname);
    }

    @Override
    public List<String> queryActiveBackends() throws IOException
    {
        return delegate.queryActiveBackends();
    }

    @Override
    public RecordingsInProgress queryIsRecording() throws IOException
    {
        return delegate.queryIsRecording();
    }

    @Override
    public Load queryLoad() throws IOException
    {
        return delegate.queryLoad();
    }

    @Override
    public MemStats queryMemStats() throws IOException
    {
        return delegate.queryMemStats();
    }

    @Override
    public PixMap queryPixMapGetIfModified(Date timestamp, int maxFileSize, Program program) throws IOException,
                                                                                            CommandException
    {
        return delegate.queryPixMapGetIfModified(timestamp, maxFileSize, program);
    }

    @Override
    public Date queryPixMapLastModified(Program program) throws IOException
    {
        return delegate.queryPixMapLastModified(program);
    }

    @Override
    public QueryRecorder queryRecorder(int recorderId)
    {
        return delegate.queryRecorder(recorderId);
    }

    @Override
    public Program queryRecordingBasename(String basename) throws IOException, CommandException
    {
        return delegate.queryRecordingBasename(basename);
    }

    @Override
    public Program queryRecordingTimeslot(Channel channel, Date recStartTs) throws IOException,
                                                                           CommandException
    {
        return delegate.queryRecordingTimeslot(channel, recStartTs);
    }

    @Override
    public List<Program> queryRecordings(RecordingCategory recCategory) throws IOException
    {
        return delegate.queryRecordings(recCategory);
    }

    @Override
    public QueryRemoteEncoder queryRemoteEncoder(int recorderId)
    {
        return delegate.queryRemoteEncoder(recorderId);
    }

    @Override
    public String querySetting(String host, String name) throws IOException
    {
        return delegate.querySetting(host, name);
    }

    @Override
    public FileInfo querySgFileQuery(String host, String storageGroup, String path) throws IOException,
                                                                                   CommandException
    {
        return delegate.querySgFileQuery(host, storageGroup, path);
    }

    @Override
    public List<FileEntry> querySgGetFileList(String host, String storageGroup, String path) throws IOException,
                                                                                            CommandException
    {
        return delegate.querySgGetFileList(host, storageGroup, path);
    }

    @Override
    public TimeInfo queryTimeZone() throws IOException
    {
        return delegate.queryTimeZone();
    }

    @Override
    public long queryUptime() throws IOException
    {
        return delegate.queryUptime();
    }

    @Override
    public void refreshBackend() throws IOException
    {
        delegate.refreshBackend();
    }

    @Override
    public void rescheduleRecordings(int recorderId) throws IOException
    {
        delegate.rescheduleRecordings(recorderId);
    }

    @Override
    public void scanVideos() throws IOException
    {
        delegate.scanVideos();
    }

    @Override
    public boolean setBookmark(Channel channel, Date recStartTs, long location) throws IOException,
                                                                               CommandException
    {
        return delegate.setBookmark(channel, recStartTs, location);
    }

    @Override
    public void setChannelInfo(Channel oldChannel, Channel newChannel) throws IOException,
                                                                      CommandException
    {
        delegate.setChannelInfo(oldChannel, newChannel);
    }

    @Override
    public void setNextLiveTvDir(int recorderId, String path) throws IOException, CommandException
    {
        delegate.setNextLiveTvDir(recorderId, path);
    }

    @Override
    public void setSetting(String host, String name, String value) throws IOException
    {
        delegate.setSetting(host, name, value);
    }

    @Override
    public void shutdownNow(String command) throws IOException
    {
        delegate.shutdownNow(command);
    }

    @Override
    public int stopRecording(Program program) throws IOException
    {
        return delegate.stopRecording(program);
    }

    @Override
    public boolean undeleteRecording(Program program) throws IOException
    {
        return delegate.undeleteRecording(program);
    }

    @Override
    public void addBackendEventListener(BackendEventListener l)
    {
        delegate.addBackendEventListener(l);
    }

    @Override
    public void removeBackendEventListener(BackendEventListener l)
    {
        delegate.removeBackendEventListener(l);
    }

    @Override
    public void copyBackendEventListeners(Protocol protocol)
    {
        delegate.copyBackendEventListeners(protocol);
    }

    @Override
    public void copyUnsupportedHandler(Protocol protocol)
    {
        delegate.copyUnsupportedHandler(protocol);
    }
}
