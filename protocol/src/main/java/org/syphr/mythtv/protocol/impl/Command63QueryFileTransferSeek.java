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
import java.util.List;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.ProtocolException.Direction;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.types.SeekOrigin;

/* default */class Command63QueryFileTransferSeek extends AbstractCommand<Long>
{
    private final int socketNumber;
    private final long position;
    private final SeekOrigin origin;
    private final long curPosition;

    public Command63QueryFileTransferSeek(int socketNumber,
                                          long position,
                                          SeekOrigin origin,
                                          long curPosition)
    {
        this.socketNumber = socketNumber;
        this.position = position;
        this.origin = origin;
        this.curPosition = curPosition;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        Pair<String, String> splitPosition = ProtocolUtils.splitLong(position);
        Pair<String, String> splitCurPosition = ProtocolUtils.splitLong(curPosition);

        return Protocol63Utils.combineArguments("QUERY_FILETRANSFER "
                                                        + socketNumber,
                                                "SEEK",
                                                splitPosition.getLeftElement(),
                                                splitPosition.getRightElement(),
                                                Protocol63Utils.getTranslator().toString(origin),
                                                splitCurPosition.getLeftElement(),
                                                splitCurPosition.getRightElement());
    }

    @Override
    public Long send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.size() != 2)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            return ProtocolUtils.combineInts(args.get(0), args.get(1));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
