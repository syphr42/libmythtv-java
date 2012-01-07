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
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.syphr.mythtv.types.SeekOrigin;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63QueryFileTransferSeek extends AbstractProtocolCommand<Long>
{
    private final int socketNumber;
    private final long position;
    private final SeekOrigin origin;
    private final long curPosition;

    public Command63QueryFileTransferSeek(Translator translator,
                                          Parser parser,
                                          int socketNumber,
                                          long position,
                                          SeekOrigin origin,
                                          long curPosition)
    {
        super(translator, parser);

        this.socketNumber = socketNumber;
        this.position = position;
        this.origin = origin;
        this.curPosition = curPosition;
    }

    protected int getSocketNumber()
    {
        return socketNumber;
    }

    protected long getPosition()
    {
        return position;
    }

    protected SeekOrigin getOrigin()
    {
        return origin;
    }

    protected long getCurPosition()
    {
        return curPosition;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        Pair<String, String> splitPosition = ProtocolUtils.splitLong(getPosition());
        Pair<String, String> splitCurPosition = ProtocolUtils.splitLong(getCurPosition());

        return getParser().combineArguments("QUERY_FILETRANSFER "
                                                    + getSocketNumber(),
                                            "SEEK",
                                            splitPosition.getLeft(),
                                            splitPosition.getRight(),
                                            getTranslator().toString(getOrigin()),
                                            splitCurPosition.getLeft(),
                                            splitCurPosition.getRight());
    }

    @Override
    public Long send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = getParser().splitArguments(response);

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
