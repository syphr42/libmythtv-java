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
import java.util.List;

import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63AnnFileTransfer extends AbstractProtocolCommand<QueryFileTransfer>
{
    private final String host;
    private final FileTransferType type;
    private final boolean readAhead;
    private final long timeout;
    private final URI uri;
    private final String storageGroup;
    private final SocketManager commandSocketManager;

    public Command63AnnFileTransfer(Translator translator,
                                    Parser parser,
                                    String host,
                                    FileTransferType type,
                                    boolean readAhead,
                                    long timeout,
                                    URI uri,
                                    String storageGroup,
                                    SocketManager commandSocketManager)
    {
        super(translator, parser);

        this.host = host;
        this.type = type;
        this.readAhead = readAhead;
        this.timeout = timeout;
        this.uri = uri;
        this.storageGroup = storageGroup;
        this.commandSocketManager = commandSocketManager;
    }

    protected String getHost()
    {
        return host;
    }

    protected FileTransferType getType()
    {
        return type;
    }

    protected boolean isReadAhead()
    {
        return readAhead;
    }

    protected long getTimeout()
    {
        return timeout;
    }

    protected URI getUri()
    {
        return uri;
    }

    protected String getStorageGroup()
    {
        return storageGroup;
    }

    protected SocketManager getCommandSocketManager()
    {
        return commandSocketManager;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ANN FileTransfer ");
        builder.append(getHost());
        builder.append(' ');
        builder.append(getTranslator().toString(getType()));
        builder.append(' ');
        builder.append(isReadAhead() ? 1 : 0);

        if (getTimeout() > 0)
        {
            builder.append(' ');
            builder.append(getTimeout());
        }

        return getParser().combineArguments(builder.toString(),
                                            getUri().toString(),
                                            getStorageGroup());
    }

    @Override
    public QueryFileTransfer send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = getParser().splitArguments(response);
        if (args.size() != 4)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            if (!"OK".equals(args.get(0)))
            {
                throw new ProtocolException(response, Direction.RECEIVE);
            }

            int socketNumber = Integer.parseInt(args.get(1));
            long size = ProtocolUtils.combineInts(args.get(2), args.get(3));

            return new QueryFileTransfer63(getTranslator(),
                                           getParser(),
                                           socketNumber,
                                           size,
                                           getCommandSocketManager());
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
