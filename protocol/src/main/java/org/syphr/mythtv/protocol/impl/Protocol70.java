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
import java.net.URI;

import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class Protocol70 extends Protocol69
{
    public Protocol70(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, CommandException
    {
        new Command63MythProtoVersion(getTranslator(), getParser())
        {
            @Override
            protected String getVersion()
            {
                return "70";
            }

            @Override
            protected String getToken()
            {
                return "53153836";
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
    protected Translator createTranslator()
    {
        return new Translator70();
    }
}
