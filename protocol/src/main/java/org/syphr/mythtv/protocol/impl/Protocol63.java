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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.socket.Interceptor;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
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
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolVersionException;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.protocol.events.EventProtocol;
import org.syphr.mythtv.protocol.events.impl.EventProtocol63;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.RecordingStatus;
import org.syphr.mythtv.types.Verbose;

public class Protocol63 extends AbstractProtocol
{
    public Protocol63(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator63();
    }

    @Override
    protected Parser createParser(Translator translator)
    {
        return new Parser63(translator);
    }

    protected EventProtocol createEventProtocol(Translator translator, Parser parser)
    {
        return new EventProtocol63(translator, parser);
    }

    @Override
    protected Interceptor createEventGrabber()
    {
        return new Interceptor()
        {
            private final EventProtocol eventProto = createEventProtocol(getTranslator(),
                                                                         getParser());
            private final Logger logger = LoggerFactory.getLogger(getClass());

            @Override
            public boolean intercept(String response)
            {
                List<String> args = getParser().splitArguments(response);

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
    public void mythProtoVersion() throws IOException, ProtocolVersionException
    {
        new Command63MythProtoVersion(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public void ann(ConnectionType connectionType, String host, EventLevel level) throws IOException
    {
        new Command63Ann(getTranslator(), getParser(), connectionType, host, level).send(getSocketManager());
    }

    @Override
    public void annSlaveBackend(InetAddress address, Program... recordings) throws IOException
    {
        new Command63AnnSlaveBackend(getTranslator(), getParser(), address, recordings).send(getSocketManager());

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
        return new Command63AnnFileTransfer(getTranslator(),
                                            getParser(),
                                            host,
                                            type,
                                            readAhead,
                                            timeout,
                                            uri,
                                            storageGroup,
                                            commandProtocol.getSocketManager(),
                                            getUnsupportedHandler()).send(getSocketManager());
    }

    @Override
    public void done() throws IOException
    {
        SocketManager manager = getSocketManager();

        try
        {
            new Command63Done(getTranslator(), getParser()).send(manager);
        }
        finally
        {
            /*
             * Once done is sent, the server will disconnect, so the current
             * connection is unusable. To make sure things get cleaned up (such
             * as when generating a new protocol from an existing one),
             * disconnect is called here. It's OK if disconnect gets called more
             * than once, but it needs to be called at least once and this
             * guarantees that when the protocol is completed, the connection is
             * closed.
             */
            manager.disconnect();
        }
    }

    @Override
    public void allowShutdown() throws IOException
    {
        new Command63AllowShutdown(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public void blockShutdown() throws IOException
    {
        new Command63BlockShutdown(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public int checkRecording(Program program) throws IOException
    {
        return new Command63CheckRecording(getTranslator(), getParser(), program).send(getSocketManager());
    }

    @Override
    public boolean deleteFile(URI filename, String storageGroup) throws IOException
    {
        return new Command63DeleteFile(getTranslator(), getParser(), filename, storageGroup).send(getSocketManager());
    }

    @Override
    public void deleteRecording(Channel channel, Date recStartTs, boolean force, boolean forget) throws IOException,
                                                                                                CommandException
    {
        new Command63DeleteRecording(getTranslator(),
                                     getParser(),
                                     channel,
                                     recStartTs,
                                     force,
                                     forget).send(getSocketManager());
    }

    @Override
    public URI downloadFile(URL url, String storageGroup, URI filename) throws IOException,
                                                                       CommandException
    {
        return new Command63DownloadFile(getTranslator(),
                                         getParser(),
                                         url,
                                         storageGroup,
                                         filename,
                                         false).send(getSocketManager());
    }

    @Override
    public URI downloadFileNow(URL url, String storageGroup, URI filename) throws IOException,
                                                                          CommandException
    {
        return new Command63DownloadFile(getTranslator(),
                                         getParser(),
                                         url,
                                         storageGroup,
                                         filename,
                                         true).send(getSocketManager());
    }

    @Override
    public Program fillProgramInfo(String host, Program program) throws IOException
    {
        return new Command63FillProgramInfo(getTranslator(), getParser(), host, program).send(getSocketManager());
    }

    @Override
    public void forgetRecording(Program program) throws IOException
    {
        new Command63ForgetRecording(getTranslator(), getParser(), program).send(getSocketManager());
    }

    @Override
    public boolean freeTuner(int recorderId) throws IOException
    {
        return new Command63FreeTuner(getTranslator(), getParser(), recorderId).send(getSocketManager());
    }

    @Override
    public RecorderLocation getFreeRecorder() throws IOException
    {
        return new Command63GetFreeRecorder(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public int getFreeRecorderCount() throws IOException
    {
        return new Command63GetFreeRecorderCount(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public List<Integer> getFreeRecorderList() throws IOException
    {
        return new Command63GetFreeRecorderList(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public RecorderLocation getNextFreeRecorder(RecorderLocation from) throws IOException
    {
        return new Command63GetNextFreeRecorder(getTranslator(), getParser(), from).send(getSocketManager());
    }

    @Override
    public RecorderLocation getRecorderFromNum(int recorderId) throws IOException, CommandException
    {
        return new Command63GetRecorderFromNum(getTranslator(), getParser(), recorderId).send(getSocketManager());
    }

    @Override
    public RecorderLocation getRecorderNum(Program program) throws IOException
    {
        return new Command63GetRecorderNum(getTranslator(), getParser(), program).send(getSocketManager());
    }

    @Override
    public void goToSleep() throws IOException, CommandException
    {
        new Command63GoToSleep(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public RecorderDevice lockTuner(int recorderId) throws IOException, CommandException
    {
        return new Command63LockTuner(getTranslator(), getParser(), recorderId).send(getSocketManager());
    }

    @Override
    public void messageClearSettingsCache() throws IOException
    {
        new Command63MessageClearSettingsCache(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public void messageResetIdleTime() throws IOException
    {
        new Command63MessageResetIdleTime(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public void messageSetVerbose(List<Verbose> options) throws IOException, CommandException
    {
        new Command63MessageSetVerbose(getTranslator(), getParser(), options).send(getSocketManager());
    }

    @Override
    public long queryBookmark(Channel channel, Date recStartTs) throws IOException
    {
        return new Command63QueryBookmark(getTranslator(), getParser(), channel, recStartTs).send(getSocketManager());
    }

    @Override
    public URI queryCheckFile(boolean checkSlaves, Program program) throws IOException
    {
        return new Command63QueryCheckFile(getTranslator(), getParser(), checkSlaves, program).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCommBreak(Channel channel, Date recStartTs) throws IOException
    {
        return new Command63QueryCommBreak(getTranslator(), getParser(), channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCutList(Channel channel, Date recStartTs) throws IOException
    {
        return new Command63QueryCutList(getTranslator(), getParser(), channel, recStartTs).send(getSocketManager());
    }

    @Override
    public FileInfo queryFileExists(URI filename, String storageGroup) throws IOException
    {
        return new Command63QueryFileExists(getTranslator(), getParser(), filename, storageGroup).send(getSocketManager());
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup) throws IOException,
                                                                  CommandException
    {
        return new Command63QueryFileHash(getTranslator(), getParser(), filename, storageGroup).send(getSocketManager());
    }

    @Override
    public List<DriveInfo> queryFreeSpace() throws IOException
    {
        return new Command63QueryFreeSpace(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public DriveInfo queryFreeSpaceSummary() throws IOException
    {
        return new Command63QueryFreeSpaceSummary(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public void queryGenPixMap2(String id, Program program) throws IOException, CommandException
    {
        new Command63QueryGenPixMap2(getTranslator(), getParser(), id, program).send(getSocketManager());
    }

    @Override
    public UpcomingRecordings queryGetAllPending() throws IOException
    {
        return new Command63QueryGetAllPending(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public List<Program> queryGetAllScheduled() throws IOException
    {
        return new Command63QueryGetAllScheduled(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public List<Program> queryGetConflicting(Program program) throws IOException
    {
        return new Command63QueryGetConflicting(getTranslator(), getParser(), program).send(getSocketManager());
    }

    @Override
    public List<Program> queryGetExpiring() throws IOException
    {
        return new Command63QueryGetExpiring(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public Date queryGuideDataThrough() throws IOException
    {
        return new Command63QueryGuideDataThrough(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public String queryHostname() throws IOException
    {
        return new Command63QueryHostname(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public boolean queryIsActiveBackend(String hostname) throws IOException
    {
        return new Command63QueryIsActiveBackend(getTranslator(), getParser(), hostname).send(getSocketManager());
    }

    @Override
    public RecordingsInProgress queryIsRecording() throws IOException
    {
        return new Command63QueryIsRecording(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public Load queryLoad() throws IOException
    {
        return new Command63QueryLoad(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public MemStats queryMemStats() throws IOException
    {
        return new Command63QueryMemStats(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public PixMap queryPixMapGetIfModified(Date timestamp, int maxFileSize, Program program) throws IOException,
                                                                                            CommandException
    {
        return new Command63QueryPixMapGetIfModified(getTranslator(),
                                                     getParser(),
                                                     timestamp,
                                                     maxFileSize,
                                                     program).send(getSocketManager());
    }

    @Override
    public Date queryPixMapLastModified(Program program) throws IOException
    {
        return new Command63QueryPixMapLastModified(getTranslator(), getParser(), program).send(getSocketManager());
    }

    @Override
    public QueryRecorder queryRecorder(int recorderId)
    {
        return new QueryRecorder63(getTranslator(), getParser(), recorderId, getSocketManager());
    }

    @Override
    public Program queryRecordingBasename(String basename) throws IOException, CommandException
    {
        return new Command63QueryRecordingBasename(getTranslator(), getParser(), basename).send(getSocketManager());
    }

    @Override
    public Program queryRecordingTimeslot(Channel channel, Date recStartTs) throws IOException,
                                                                           CommandException
    {
        return new Command63QueryRecordingTimeslot(getTranslator(),
                                                   getParser(),
                                                   channel,
                                                   recStartTs).send(getSocketManager());
    }

    @Override
    public List<Program> queryRecordings(RecordingCategory recCategory) throws IOException
    {
        return new Command63QueryRecordings(getTranslator(), getParser(), recCategory).send(getSocketManager());
    }

    @Override
    public QueryRemoteEncoder queryRemoteEncoder(int recorderId)
    {
        return new QueryRemoteEncoder63(getTranslator(),
                                        getParser(),
                                        recorderId,
                                        getSocketManager());
    }

    @Override
    public String querySetting(String host, String name) throws IOException
    {
        return new Command63QuerySetting(getTranslator(), getParser(), host, name).send(getSocketManager());
    }

    @Override
    public FileInfo querySgFileQuery(String host, String storageGroup, String path) throws IOException,
                                                                                   CommandException
    {
        return new Command63QuerySgFileQuery(getTranslator(), getParser(), host, storageGroup, path).send(getSocketManager());
    }

    @Override
    public List<FileEntry> querySgGetFileList(String host, String storageGroup, String path) throws IOException,
                                                                                            CommandException
    {
        return new Command63QuerySgGetFileList(getTranslator(),
                                               getParser(),
                                               host,
                                               storageGroup,
                                               path).send(getSocketManager());
    }

    @Override
    public TimeInfo queryTimeZone() throws IOException
    {
        return new Command63QueryTimeZone(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public long queryUptime() throws IOException
    {
        return new Command63QueryUptime(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public void refreshBackend() throws IOException
    {
        new Command63RefreshBackend(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public void rescheduleRecordings(int recorderId) throws IOException
    {
        new Command63RescheduleRecordings(getTranslator(), getParser(), recorderId).send(getSocketManager());
    }

    @Override
    public boolean setBookmark(Channel channel, Date recStartTs, long location) throws IOException,
                                                                               CommandException
    {
        return new Command63SetBookmark(getTranslator(), getParser(), channel, recStartTs, location).send(getSocketManager());
    }

    @Override
    public void setChannelInfo(Channel oldChannel, Channel newChannel) throws IOException,
                                                                      CommandException
    {
        new Command63SetChannelInfo(getTranslator(), getParser(), oldChannel, newChannel).send(getSocketManager());
    }

    @Override
    public void setNextLiveTvDir(int recorderId, String path) throws IOException, CommandException
    {
        new Command63SetNextLiveTvDir(getTranslator(), getParser(), recorderId, path).send(getSocketManager());
    }

    @Override
    public void setSetting(String host, String name, String value) throws IOException
    {
        new Command63SetSetting(getTranslator(), getParser(), host, name, value).send(getSocketManager());
    }

    @Override
    public void shutdownNow(String command) throws IOException
    {
        new Command63ShutdownNow(getTranslator(), getParser(), command).send(getSocketManager());
    }

    @Override
    public int stopRecording(Program program) throws IOException
    {
        return new Command63StopRecording(getTranslator(), getParser(), program).send(getSocketManager());
    }

    @Override
    public boolean undeleteRecording(Program program) throws IOException
    {
        return new Command63UndeleteRecording(getTranslator(), getParser(), program).send(getSocketManager());
    }

    @Override
    public void scanVideos() throws IOException
    {
        handleUnsupported("scan for videos");
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup, String host) throws IOException,
                                                                               CommandException
    {
        handleUnsupported("query file hash with a host");
        return queryFileHash(filename, storageGroup);
    }

    @Override
    public List<String> queryActiveBackends() throws IOException
    {
        handleUnsupported("query for active backends");
        return new ArrayList<String>();
    }

    @Override
    public void annMediaServer(InetAddress address) throws IOException
    {
        handleUnsupported("announce as media server");
        annSlaveBackend(address);
    }

    @Override
    public void rescheduleRecordingsMatch(int recorderId,
                                          int sourceId,
                                          int mplexId,
                                          Date maxStartTime,
                                          String reason) throws IOException, CommandException
    {
        handleUnsupported("reschedule recordings match");
        rescheduleRecordings(recorderId == 0 ? -1 : recorderId);

    }

    @Override
    public void rescheduleRecordingsCheck(int recorderId,
                                          int findId,
                                          String title,
                                          String subtitle,
                                          String description,
                                          String programId,
                                          RecordingStatus recStatus,
                                          String reason) throws IOException, CommandException
    {
        handleUnsupported("reschedule recordings check");
        rescheduleRecordings(recorderId);
    }

    @Override
    public void rescheduleRecordingsCheck(String title, RecordingStatus recStatus, String reason) throws IOException,
                                                                                                 CommandException
    {
        handleUnsupported("reschedule recordings check");
        rescheduleRecordings(-1);
    }

    @Override
    public void rescheduleRecordingsPlace(String reason) throws IOException, CommandException
    {
        handleUnsupported("reschedule recordings place");
        rescheduleRecordings(-1);
    }
}
