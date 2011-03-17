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
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.ProtocolException.Direction;
import org.syphr.mythtv.protocol.data.Channel;
import org.syphr.mythtv.protocol.data.ProgramInfo;

/* default */class Command63QueryRecordingTimeslot extends AbstractCommand<ProgramInfo>
{
    private final Channel channel;
    private final Date recStartTs;

    public Command63QueryRecordingTimeslot(Channel channel, Date recStartTs)
    {
        this.channel = channel;
        this.recStartTs = recStartTs;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("QUERY_RECORDING TIMESLOT ");
        builder.append(channel.getId());
        builder.append(' ');
        builder.append(ProtocolUtils.getMySqlDateFormat().format(recStartTs));

        return builder.toString();
    }

    @Override
    public ProgramInfo send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.isEmpty())
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        String status = args.get(0);

        if ("ERROR".equals(status))
        {
            throw new CommandException("An unexpected error occurred while retrieving recording information");
        }

        if ("OK".equals(status))
        {
            args.remove(0);
            return Protocol63Utils.parseProgramInfo(args);
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
