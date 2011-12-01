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
import org.syphr.mythtv.util.unsupported.UnsupportedHandler;

/* default */class Command66AnnFileTransfer extends Command63AnnFileTransfer
{
    public Command66AnnFileTransfer(Translator translator,
                                    Parser parser,
                                    String host,
                                    FileTransferType type,
                                    boolean readAhead,
                                    long timeout,
                                    URI uri,
                                    String storageGroup,
                                    SocketManager commandSocketManager,
                                    UnsupportedHandler unsupported)
    {
        super(translator,
              parser,
              host,
              type,
              readAhead,
              timeout,
              uri,
              storageGroup,
              commandSocketManager,
              unsupported);
    }

    @Override
    public QueryFileTransfer send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = getParser().splitArguments(response);
        if (args.size() != 3)
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
            long size = Long.parseLong(args.get(2));

            return createQueryFileTransfer(socketNumber, size);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }

    @Override
    protected QueryFileTransfer createQueryFileTransfer(int socketNumber, long size)
    {
        return new QueryFileTransfer66(getTranslator(),
                                       getParser(),
                                       socketNumber,
                                       size,
                                       getType(),
                                       getCommandSocketManager(),
                                       getUnsupported());
    }
}
