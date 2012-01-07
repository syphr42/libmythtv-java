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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.data.FileInfo;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63QueryFileExists extends AbstractProtocolCommand<FileInfo>
{
    private final URI filename;
    private final String storageGroup;

    public Command63QueryFileExists(Translator translator, Parser parser, URI filename, String storageGroup)
    {
        super(translator, parser);

        this.filename = filename;
        this.storageGroup = storageGroup;
    }

    public URI getFilename()
    {
        return filename;
    }

    public String getStorageGroup()
    {
        return storageGroup;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("QUERY_FILE_EXISTS",
                                            getFilename().getPath(),
                                            getStorageGroup());
    }

    @Override
    public FileInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        if ("0".equals(response))
        {
            return null;
        }

        List<String> args = getParser().splitArguments(response);
        if (args.size() < 2 || !"1".equals(args.get(0)))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        if (args.size() == 2)
        {
            return new FileInfo(args.get(1));
        }

        if (args.size() == 15)
        {
            int i = 1;

            try
            {
                return new FileInfo(args.get(i++),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    Long.parseLong(args.get(i++)),
                                    new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(args.get(i++)))),
                                    new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(args.get(i++)))),
                                    new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(args.get(i++)))));
            }
            catch (NumberFormatException e)
            {
                throw new ProtocolException(response, Direction.RECEIVE, e);
            }
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
