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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.Channel;
import org.syphr.mythtv.proto.data.CommBreakInfo;

/* default */class Command63QueryCommBreak implements Command<List<CommBreakInfo>>
{
    private final String message;

    public Command63QueryCommBreak(Channel channel, Date startTime) throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("QUERY_COMMBREAK");
        builder.append(' ');
        builder.append(channel.getId());
        builder.append(' ');
        builder.append(TimeUnit.MILLISECONDS.toSeconds(startTime.getTime()));

        message = builder.toString();
    }

    @Override
    public List<CommBreakInfo> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);
        List<String> args = Protocol63Utils.getArguments(response);
        if (args.isEmpty())
        {
            throw new ProtocolException(response);
        }

        List<CommBreakInfo> breaks = new ArrayList<CommBreakInfo>();

        try
        {
            int numBreaks = Integer.parseInt(args.get(0));
            if (numBreaks < 0)
            {
                return breaks;
            }

            if (args.size() < numBreaks * 3 + 1)
            {
                throw new ProtocolException(response);
            }

            for (int i = 1; i < args.size();)
            {
                breaks.add(new CommBreakInfo(Protocol63Utils.getCommBreakType(Integer.parseInt(args.get(i++))),
                                             Protocol63Utils.combineInts(Integer.parseInt(args.get(i++)),
                                                                         Integer.parseInt(args.get(i++)))));
            }

            return breaks;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
