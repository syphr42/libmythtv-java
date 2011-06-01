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
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.VideoEditInfo;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;

public class Protocol66 extends Protocol65
{
    public Protocol66(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, CommandException
    {
        new Command63MythProtoVersion()
        {
            @Override
            protected String getVersion()
            {
                return "66";
            }

            @Override
            protected String getToken()
            {
                return "0C0FFEE0";
            }
        }.send(getSocketManager());
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
        return new Command66AnnFileTransfer(host,
                                            type,
                                            readAhead,
                                            timeout,
                                            uri,
                                            storageGroup,
                                            commandProtocol.getSocketManager()).send(getSocketManager());
    }

    @Override
    public long queryBookmark(Channel channel, Date recStartTs) throws IOException
    {
        return new Command66QueryBookmark(channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCommBreak(Channel channel, Date recStartTs) throws IOException
    {
        return new Command66QueryCommBreak(channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCutList(Channel channel, Date recStartTs) throws IOException
    {
        return new Command66QueryCutList(channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<DriveInfo> queryFreeSpace() throws IOException
    {
        return new Command66QueryFreeSpace().send(getSocketManager());
    }

    @Override
    public DriveInfo queryFreeSpaceSummary() throws IOException
    {
        return new Command66QueryFreeSpaceSummary().send(getSocketManager());
    }

    @Override
    public QueryRecorder queryRecorder(int recorderId)
    {
        return new QueryRecorder66(recorderId, getSocketManager());
    }

    @Override
    public QueryRemoteEncoder queryRemoteEncoder(int recorderId)
    {
        return new QueryRemoteEncoder66(recorderId, getSocketManager());
    }

    @Override
    public boolean setBookmark(Channel channel, Date recStartTs, long location) throws IOException,
                                                                               CommandException
    {
        return new Command66SetBookmark(channel, recStartTs, location).send(getSocketManager());
    }
}
