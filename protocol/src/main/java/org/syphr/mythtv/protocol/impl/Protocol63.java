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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.Channel;
import org.syphr.mythtv.protocol.data.DriveInfo;
import org.syphr.mythtv.protocol.data.FileEntry;
import org.syphr.mythtv.protocol.data.FileInfo;
import org.syphr.mythtv.protocol.data.Load;
import org.syphr.mythtv.protocol.data.MemStats;
import org.syphr.mythtv.protocol.data.PixMap;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.data.RecorderDevice;
import org.syphr.mythtv.protocol.data.RecorderLocation;
import org.syphr.mythtv.protocol.data.RecordingsInProgress;
import org.syphr.mythtv.protocol.data.TimeInfo;
import org.syphr.mythtv.protocol.data.UpcomingRecordings;
import org.syphr.mythtv.protocol.data.VideoEditInfo;
import org.syphr.mythtv.protocol.events.BackendEventGrabber;
import org.syphr.mythtv.protocol.events.impl.EventProtocol63;
import org.syphr.mythtv.protocol.types.ConnectionType;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.protocol.types.FileTransferType;
import org.syphr.mythtv.protocol.types.RecordingCategory;
import org.syphr.mythtv.protocol.types.Verbose;

public class Protocol63 extends AbstractProtocol
{
    public Protocol63(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    protected BackendEventGrabber createEventGrabber()
    {
        return new BackendEventGrabber()
        {
            private final EventProtocol63 eventProto = new EventProtocol63();
            private final Logger logger = LoggerFactory.getLogger(getClass());

            @Override
            public boolean isBackendEvent(String value)
            {
                List<String> args = Protocol63Utils.splitArguments(value);

                if (!args.isEmpty() && "BACKEND_MESSAGE".equals(args.get(0)))
                {
                    args.remove(0);

                    try
                    {
                        eventProto.fireEvent(args, getListeners());
                    }
                    catch (ProtocolException e)
                    {
                        logger.warn(e.getMessage(), e);
                    }

                    return true;
                }

                return false;
            }
        };
    }

    @Override
    public void mythProtoVersion() throws IOException, CommandException
    {
        new Command63MythProtoVersion().send(getSocketManager());
    }

    @Override
    public void ann(ConnectionType connectionType, String host, EventLevel level) throws IOException
    {
        new Command63Ann(connectionType, host, level).send(getSocketManager());
    }

    @Override
    public QueryFileTransfer annFileTransfer(String host,
                                             FileTransferType type,
                                             boolean readAhead,
                                             long timeout,
                                             URI uri,
                                             String storageGroup,
                                             SocketManager commandSocketManager) throws IOException
    {
        return new Command63AnnFileTransfer(host,
                                            type,
                                            readAhead,
                                            timeout,
                                            uri,
                                            storageGroup,
                                            commandSocketManager).send(getSocketManager());
    }

    @Override
    public void done() throws IOException
    {
        new Command63Done().send(getSocketManager());
    }

    @Override
    public void allowShutdown() throws IOException
    {
        new Command63AllowShutdown().send(getSocketManager());
    }

    @Override
    public void blockShutdown() throws IOException
    {
        new Command63BlockShutdown().send(getSocketManager());
    }

    @Override
    public int checkRecording(ProgramInfo program) throws IOException
    {
        return new Command63CheckRecording(program).send(getSocketManager());
    }

    @Override
    public boolean deleteFile(URI filename, String storageGroup) throws IOException
    {
        return new Command63DeleteFile(filename, storageGroup).send(getSocketManager());
    }

    @Override
    public void deleteRecording(Channel channel,
                                   Date recStartTs,
                                   boolean force,
                                   boolean forget) throws IOException, CommandException
    {
        new Command63DeleteRecording(channel, recStartTs, force, forget).send(getSocketManager());
    }

    @Override
    public URI downloadFile(URL url, String storageGroup, URI filename) throws IOException, CommandException
    {
        return new Command63DownloadFile(url, storageGroup, filename, false).send(getSocketManager());
    }

    @Override
    public URI downloadFileNow(URL url, String storageGroup, URI filename) throws IOException, CommandException
    {
        return new Command63DownloadFile(url, storageGroup, filename, true).send(getSocketManager());
    }

    @Override
    public ProgramInfo fillProgramInfo(String host, ProgramInfo program) throws IOException
    {
        return new Command63FillProgramInfo(host, program).send(getSocketManager());
    }

    @Override
    public void forgetRecording(ProgramInfo program) throws IOException
    {
        new Command63ForgetRecording(program).send(getSocketManager());
    }

    @Override
    public boolean freeTuner(int recorderId) throws IOException
    {
        return new Command63FreeTuner(recorderId).send(getSocketManager());
    }

    @Override
    public RecorderLocation getFreeRecorder() throws IOException
    {
        return new Command63GetFreeRecorder().send(getSocketManager());
    }

    @Override
    public int getFreeRecorderCount() throws IOException
    {
        return new Command63GetFreeRecorderCount().send(getSocketManager());
    }

    @Override
    public List<Integer> getFreeRecorderList() throws IOException
    {
        return new Command63GetFreeRecorderList().send(getSocketManager());
    }

    @Override
    public RecorderLocation getNextFreeRecorder(RecorderLocation from) throws IOException
    {
        return new Command63GetNextFreeRecorder(from).send(getSocketManager());
    }

    @Override
    public RecorderLocation getRecorderFromNum(int recorderId) throws IOException, CommandException
    {
        return new Command63GetRecorderFromNum(recorderId).send(getSocketManager());
    }

    @Override
    public RecorderLocation getRecorderNum(ProgramInfo program) throws IOException
    {
        return new Command63GetRecorderNum(program).send(getSocketManager());
    }

    @Override
    public void goToSleep() throws IOException, CommandException
    {
        new Command63GoToSleep().send(getSocketManager());
    }

    @Override
    public RecorderDevice lockTuner(int recorderId) throws IOException, CommandException
    {
        return new Command63LockTuner(recorderId).send(getSocketManager());
    }

    @Override
    public void messageClearSettingsCache() throws IOException
    {
        new Command63MessageClearSettingsCache().send(getSocketManager());
    }

    @Override
    public void messageSetVerbose(List<Verbose> options) throws IOException, CommandException
    {
        new Command63MessageSetVerbose(options).send(getSocketManager());
    }

    @Override
    public long queryBookmark(Channel channel, Date recStartTs) throws IOException
    {
        return new Command63QueryBookmark(channel, recStartTs).send(getSocketManager());
    }

    @Override
    public URI queryCheckFile(boolean checkSlaves, ProgramInfo program) throws IOException
    {
        return new Command63QueryCheckFile(checkSlaves, program).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCommBreak(Channel channel, Date recStartTs) throws IOException
    {
        return new Command63QueryCommBreak(channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCutList(Channel channel, Date recStartTs) throws IOException
    {
        return new Command63QueryCutList(channel, recStartTs).send(getSocketManager());
    }

    @Override
    public FileInfo queryFileExists(URI filename, String storageGroup) throws IOException
    {
        return new Command63QueryFileExists(filename, storageGroup).send(getSocketManager());
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup) throws IOException, CommandException
    {
        return new Command63QueryFileHash(filename, storageGroup).send(getSocketManager());
    }

    @Override
    public List<DriveInfo> queryFreeSpace() throws IOException
    {
        return new Command63QueryFreeSpace().send(getSocketManager());
    }

    @Override
    public DriveInfo queryFreeSpaceSummary() throws IOException
    {
        return new Command63QueryFreeSpaceSummary().send(getSocketManager());
    }

    @Override
    public void queryGenPixMap2(String id, ProgramInfo program) throws IOException, CommandException
    {
        new Command63QueryGenPixMap2(id, program).send(getSocketManager());
    }

    @Override
    public UpcomingRecordings queryGetAllPending() throws IOException
    {
        return new Command63QueryGetAllPending().send(getSocketManager());
    }

    @Override
    public List<ProgramInfo> queryGetAllScheduled() throws IOException
    {
        return new Command63QueryGetAllScheduled().send(getSocketManager());
    }

    @Override
    public List<ProgramInfo> queryGetConflicting(ProgramInfo program) throws IOException
    {
        return new Command63QueryGetConflicting(program).send(getSocketManager());
    }

    @Override
    public List<ProgramInfo> queryGetExpiring() throws IOException
    {
        return new Command63QueryGetExpiring().send(getSocketManager());
    }

    @Override
    public Date queryGuideDataThrough() throws IOException
    {
        return new Command63QueryGuideDataThrough().send(getSocketManager());
    }

    @Override
    public String queryHostname() throws IOException
    {
        return new Command63QueryHostname().send(getSocketManager());
    }

    @Override
    public boolean queryIsActiveBackend(String hostname) throws IOException
    {
        return new Command63QueryIsActiveBackend(hostname).send(getSocketManager());
    }

    @Override
    public RecordingsInProgress queryIsRecording() throws IOException
    {
        return new Command63QueryIsRecording().send(getSocketManager());
    }

    @Override
    public Load queryLoad() throws IOException
    {
        return new Command63QueryLoad().send(getSocketManager());
    }

    @Override
    public MemStats queryMemStats() throws IOException
    {
        return new Command63QueryMemStats().send(getSocketManager());
    }

    @Override
    public PixMap queryPixMapGetIfModified(Date timestamp,
                                         int maxFileSize,
                                         ProgramInfo program) throws IOException,
                                                             CommandException
    {
        return new Command63QueryPixMapGetIfModified(timestamp, maxFileSize, program).send(getSocketManager());
    }

    @Override
    public Date queryPixMapLastModified(ProgramInfo program) throws IOException
    {
        return new Command63QueryPixMapLastModified(program).send(getSocketManager());
    }

    @Override
    public QueryRecorder queryRecorder(int recorderId)
    {
        return new QueryRecorder63(recorderId, getSocketManager());
    }

    @Override
    public ProgramInfo queryRecordingBasename(String basename) throws IOException, CommandException
    {
        return new Command63QueryRecordingBasename(basename).send(getSocketManager());
    }

    @Override
    public ProgramInfo queryRecordingTimeslot(Channel channel, Date recStartTs) throws IOException, CommandException
    {
        return new Command63QueryRecordingTimeslot(channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<ProgramInfo> queryRecordings(RecordingCategory recCategory) throws IOException
    {
        return new Command63QueryRecordings(recCategory).send(getSocketManager());
    }

    @Override
    public QueryRemoteEncoder queryRemoteEncoder(int recorderId)
    {
        return new QueryRemoteEncoder63(recorderId, getSocketManager());
    }

    @Override
    public String querySetting(String host, String name) throws IOException
    {
        return new Command63QuerySetting(host, name).send(getSocketManager());
    }

    @Override
    public FileInfo querySgFileQuery(String host, String storageGroup, String path) throws IOException, CommandException
    {
        return new Command63QuerySgFileQuery(host, storageGroup, path).send(getSocketManager());
    }

    @Override
    public List<FileEntry> querySgGetFileList(String host, String storageGroup, String path) throws IOException, CommandException
    {
        return new Command63QuerySgGetFileList(host, storageGroup, path).send(getSocketManager());
    }

    @Override
    public TimeInfo queryTimeZone() throws IOException
    {
        return new Command63QueryTimeZone().send(getSocketManager());
    }

    @Override
    public long queryUptime() throws IOException
    {
        return new Command63QueryUptime().send(getSocketManager());
    }

    @Override
    public void refreshBackend() throws IOException
    {
        new Command63RefreshBackend().send(getSocketManager());
    }

    @Override
    public void rescheduleRecordings(int recorderId) throws IOException
    {
        new Command63RescheduleRecordings(recorderId).send(getSocketManager());
    }

    @Override
    public boolean setBookmark(Channel channel, Date recStartTs, long location) throws IOException, CommandException
    {
        return new Command63SetBookmark(channel, recStartTs, location).send(getSocketManager());
    }

    @Override
    public void setChannelInfo(Channel oldChannel, Channel newChannel) throws IOException, CommandException
    {
        new Command63SetChannelInfo(oldChannel, newChannel).send(getSocketManager());
    }

    @Override
    public void setNextLiveTvDir(int recorderId, String path) throws IOException, CommandException
    {
        new Command63SetNextLiveTvDir(recorderId, path).send(getSocketManager());
    }

    @Override
    public void setSetting(String host, String name, String value) throws IOException
    {
        new Command63SetSetting(host, name, value).send(getSocketManager());
    }

    @Override
    public void shutdownNow(String command) throws IOException
    {
        new Command63ShutdownNow(command).send(getSocketManager());
    }

    @Override
    public int stopRecording(ProgramInfo program) throws IOException
    {
        return new Command63StopRecording(program).send(getSocketManager());
    }

    @Override
    public boolean undeleteRecording(ProgramInfo program) throws IOException
    {
        return new Command63UndeleteRecording(program).send(getSocketManager());
    }

    @Override
    public void scanVideos() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Translator getTranslator()
    {
        return Protocol63Utils.getTranslator();
    }
}