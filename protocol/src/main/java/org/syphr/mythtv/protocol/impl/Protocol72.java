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
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.FileInfo;
import org.syphr.mythtv.data.VideoEditInfo;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolVersionException;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.protocol.events.EventProtocol;
import org.syphr.mythtv.protocol.events.impl.EventProtocol70;
import org.syphr.mythtv.types.FileTransferType;

public class Protocol72 extends Protocol63
{
    public Protocol72(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, ProtocolVersionException
    {
        new Command63MythProtoVersion(getTranslator(), getParser())
        {
            @Override
            protected String getVersion()
            {
                return "72";
            }

            @Override
            protected String getToken()
            {
                return "D78EFD6F";
            }
        }.send(getSocketManager());
    }

    @Override
    public void scanVideos() throws IOException
    {
        new Command64ScanVideos(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public FileInfo queryFileExists(URI filename, String storageGroup) throws IOException
    {
        return new Command65QueryFileExists(getTranslator(), getParser(), filename, storageGroup).send(getSocketManager());
    }

    @Override
    public long queryBookmark(Channel channel, Date recStartTs) throws IOException
    {
        return new Command66QueryBookmark(getTranslator(), getParser(), channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCommBreak(Channel channel, Date recStartTs) throws IOException
    {
        return new Command66QueryCommBreak(getTranslator(), getParser(), channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<VideoEditInfo> queryCutList(Channel channel, Date recStartTs) throws IOException
    {
        return new Command66QueryCutList(getTranslator(), getParser(), channel, recStartTs).send(getSocketManager());
    }

    @Override
    public List<DriveInfo> queryFreeSpace() throws IOException
    {
        return new Command66QueryFreeSpace(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public DriveInfo queryFreeSpaceSummary() throws IOException
    {
        return new Command66QueryFreeSpaceSummary(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    public boolean setBookmark(Channel channel, Date recStartTs, long location) throws IOException,
                                                                               CommandException
    {
        return new Command66SetBookmark(getTranslator(), getParser(), channel, recStartTs, location).send(getSocketManager());
    }

    @Override
    public void annMediaServer(InetAddress address) throws IOException
    {
        new Command67AnnMediaServer(getTranslator(), getParser(), address).send(getSocketManager());
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup) throws IOException,
                                                                  CommandException
    {
        return queryFileHash(filename, storageGroup, null);
    }

    @Override
    public String queryFileHash(URI filename, String storageGroup, String host) throws IOException,
                                                                               CommandException
    {
        return new Command69QueryFileHash(getTranslator(),
                                          getParser(),
                                          filename,
                                          storageGroup,
                                          host).send(getSocketManager());
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
        return new Command70AnnFileTransfer(getTranslator(),
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
    public QueryRecorder queryRecorder(int recorderId)
    {
        return new QueryRecorder71(getTranslator(), getParser(), recorderId, getSocketManager());
    }

    @Override
    public QueryRemoteEncoder queryRemoteEncoder(int recorderId)
    {
        return new QueryRemoteEncoder71(getTranslator(),
                                        getParser(),
                                        recorderId,
                                        getSocketManager());
    }

    @Override
    public List<String> queryActiveBackends() throws IOException
    {
        return new Command72QueryActiveBackends(getTranslator(), getParser()).send(getSocketManager());
    }

    @Override
    protected EventProtocol createEventProtocol(Translator translator, Parser parser)
    {
        return new EventProtocol70(translator, parser);
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator70();
    }

    @Override
    protected Parser createParser(Translator translator)
    {
        return new Parser67(translator);
    }
}
