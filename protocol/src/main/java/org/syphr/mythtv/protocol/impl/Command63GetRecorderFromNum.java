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

import org.syphr.mythtv.protocol.data.RecorderLocation;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command63GetRecorderFromNum extends AbstractCommand<RecorderLocation>
{
    private final int recorderId;

    public Command63GetRecorderFromNum(int recorderId)
    {
        this.recorderId = recorderId;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("GET_RECORDER_FROM_NUM",
                                                String.valueOf(recorderId));
    }

    @Override
    public RecorderLocation send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<String> args = Protocol63Utils.splitArguments(response);
        if (args.size() != 2)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        String host = args.get(0);
        if ("nohost".equals(host))
        {
            throw new CommandException("Unknown recorder ID: " + recorderId);
        }

        try
        {
            return new RecorderLocation(recorderId,
                                        host,
                                        Integer.parseInt(args.get(1)));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
