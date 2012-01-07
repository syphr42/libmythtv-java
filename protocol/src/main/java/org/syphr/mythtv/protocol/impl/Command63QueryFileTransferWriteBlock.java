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

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63QueryFileTransferWriteBlock extends AbstractProtocolCommand<Long>
{
    private final int socketNumber;
    private final long bytes;

    public Command63QueryFileTransferWriteBlock(Translator translator,
                                                Parser parser,
                                                int socketNumber,
                                                long bytes)
    {
        super(translator, parser);

        this.socketNumber = socketNumber;
        this.bytes = bytes;
    }

    @Override
    public String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("QUERY_FILETRANSFER "
                                                    + socketNumber,
                                            "WRITE_BLOCK",
                                            String.valueOf(bytes));
    }

    @Override
    public Long send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        try
        {
            return Long.parseLong(response);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
