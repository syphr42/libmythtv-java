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
package org.syphr.mythtv.proto.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.QueryFileTransfer;
import org.syphr.mythtv.proto.QueryRecorder;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.Channel;
import org.syphr.mythtv.proto.data.DriveInfo;
import org.syphr.mythtv.proto.data.FileTransferType;
import org.syphr.mythtv.proto.data.GenPixMapResponse;
import org.syphr.mythtv.proto.data.Load;
import org.syphr.mythtv.proto.data.MemStats;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.data.TimeInfo;
import org.syphr.mythtv.proto.data.UpcomingRecordings;
import org.syphr.mythtv.proto.events.BackendEventGrabber;
import org.syphr.mythtv.proto.events.impl.EventProtocol63;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.RecordingCategory;

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
                List<String> args = Protocol63Utils.getArguments(value);

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
    public void mythProtoVersion() throws IOException
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
                                             File file,
                                             String storageGroup,
                                             SocketManager commandSocketManager) throws IOException
    {
        return new Command63AnnFileTransfer(host,
                                            type,
                                            readAhead,
                                            timeout,
                                            file,
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
    public void deleteFile() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void downloadFile() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void downloadFileNow() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fillProgramInfo() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forceDeleteRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forgetRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void freeTuner() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getFreeRecorder() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getFreeRecorderCount() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getFreeRecorderList() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getNextFreeRecorder() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getRecorderFromNum() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getRecorderNum() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void goToSleep() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lockTuner() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void queryBookmark() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void queryCheckFile() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void queryCommBreak() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void queryCutList() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void queryFileExists() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void queryFileHash() throws IOException
    {
        throw new UnsupportedOperationException();
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
    public GenPixMapResponse queryGenPixMap2(String id, ProgramInfo program) throws IOException
    {
        return new Command63GenPixMap2(id, program).send(getSocketManager());
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
    public void queryGetConflicting() throws IOException
    {
        throw new UnsupportedOperationException();
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
    public void queryIsRecording() throws IOException
    {
        throw new UnsupportedOperationException();
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
    public void queryPixMapLastModified() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public QueryRecorder queryRecorder(int recorderId)
    {
        return new QueryRecorder63(recorderId, getSocketManager());
    }

    @Override
    public void queryRecordingBasename() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProgramInfo queryRecordingTimeslot(Channel channel, Date startTime) throws IOException
    {
        return new Command63QueryRecordingTimeslot(channel, startTime).send(getSocketManager());
    }

    @Override
    public List<ProgramInfo> queryRecordings(RecordingCategory recType) throws IOException
    {
        return new Command63QueryRecordings(recType).send(getSocketManager());
    }

    @Override
    public void queryRemoteEncoder() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void querySetting() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void querySgFileQuery() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void querySgGetFileList() throws IOException
    {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void rescheduleRecordings() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBookmark() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setChannelInfo() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setNextLiveTvDir() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSetting() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shutdownNow() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stopRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void undeleteRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void scanVideos() throws IOException
    {
        throw new UnsupportedOperationException();
    }
}
