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

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.RecorderLocation;

/* default */class Command63GetNextFreeRecorder extends AbstractProtocolCommand<RecorderLocation>
{
    private final RecorderLocation from;

    public Command63GetNextFreeRecorder(Translator translator, Parser parser, RecorderLocation from)
    {
        super(translator, parser);

        this.from = from;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("GET_NEXT_FREE_RECORDER",
                                            String.valueOf(from.getId()));
    }

    @Override
    public RecorderLocation send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<String> args = getParser().splitArguments(response);
        if (args.size() != 3)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            int recorderId = Integer.parseInt(args.get(0));
            if (recorderId == -1)
            {
                return null;
            }

            return new RecorderLocation(recorderId,
                                        args.get(1),
                                        Integer.parseInt(args.get(2)));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
