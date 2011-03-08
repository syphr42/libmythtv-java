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

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.QueryFileTransfer;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.types.FileTransferType;

/* default */class Command63AnnFileTransfer implements Command<QueryFileTransfer>
{
    private final String message;
    private final SocketManager commandSocketManager;

    public Command63AnnFileTransfer(String host,
                                    FileTransferType type,
                                    boolean readAhead,
                                    long timeout,
                                    URI uri,
                                    String storageGroup,
                                    SocketManager commandSocketManager) throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ANN FileTransfer ");
        builder.append(host);
        builder.append(' ');
        builder.append(Protocol63Utils.getFileTransferType(type));
        builder.append(' ');
        builder.append(readAhead ? 1 : 0);

        if (timeout > 0)
        {
            builder.append(' ');
            builder.append(timeout);
        }

        this.message = Protocol63Utils.getProtocolValue(builder.toString(),
                                                        uri.toString(),
                                                        storageGroup);

        this.commandSocketManager = commandSocketManager;
    }

    @Override
    public QueryFileTransfer send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);
        List<String> args = Protocol63Utils.getArguments(response);
        if (args.size() != 4)
        {
            throw new ProtocolException(response);
        }

        try
        {
            if (!"OK".equals(args.get(0)))
            {
                throw new ProtocolException(response);
            }

            int socketNumber = Integer.parseInt(args.get(1));
            long size = ProtocolUtils.combineInts(Integer.parseInt(args.get(2)),
                                                  Integer.parseInt(args.get(3)));

            return new QueryFileTransfer63(socketNumber, size, commandSocketManager);
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
