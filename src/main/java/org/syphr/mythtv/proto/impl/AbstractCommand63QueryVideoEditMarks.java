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
import org.syphr.mythtv.proto.data.VideoEditInfo;

/* default */abstract class AbstractCommand63QueryVideoEditMarks implements Command<List<VideoEditInfo>>
{
    private final String message;

    public AbstractCommand63QueryVideoEditMarks(Channel channel, Date recStartTs)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getCommand());
        builder.append(' ');
        builder.append(channel.getId());
        builder.append(' ');
        builder.append(TimeUnit.MILLISECONDS.toSeconds(recStartTs.getTime()));

        message = builder.toString();
    }

    @Override
    public List<VideoEditInfo> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);
        List<String> args = Protocol63Utils.getArguments(response);
        if (args.isEmpty())
        {
            throw new ProtocolException(response);
        }

        List<VideoEditInfo> edits = new ArrayList<VideoEditInfo>();

        try
        {
            int numEdits = Integer.parseInt(args.get(0));
            if (numEdits < 0)
            {
                return edits;
            }

            if (args.size() < numEdits * 3 + 1)
            {
                throw new ProtocolException(response);
            }

            for (int i = 1; i < args.size();)
            {
                edits.add(new VideoEditInfo(Protocol63Utils.getVideoEditMark(Integer.parseInt(args.get(i++))),
                                            Protocol63Utils.combineInts(Integer.parseInt(args.get(i++)),
                                                                        Integer.parseInt(args.get(i++)))));
            }

            return edits;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }

    protected abstract String getCommand();
}
