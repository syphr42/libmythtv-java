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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.VideoEditInfo;
import org.syphr.mythtv.types.VideoEditMark;

/* default */abstract class AbstractCommand63QueryVideoEditMarks extends
                                                                 AbstractProtocolCommand<List<VideoEditInfo>>
{
    private final Channel channel;
    private final Date recStartTs;

    public AbstractCommand63QueryVideoEditMarks(Translator translator,
                                                Parser parser,
                                                Channel channel,
                                                Date recStartTs)
    {
        super(translator, parser);

        this.channel = channel;
        this.recStartTs = recStartTs;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append(getCommand());
        builder.append(' ');
        builder.append(channel.getId());
        builder.append(' ');
        builder.append(TimeUnit.MILLISECONDS.toSeconds(getTranslator().toOutboundDate(recStartTs).getTime()));

        return builder.toString();
    }

    @Override
    public List<VideoEditInfo> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = getParser().splitArguments(response);
        if (args.isEmpty())
        {
            throw new ProtocolException(response, Direction.RECEIVE);
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
                throw new ProtocolException(response, Direction.RECEIVE);
            }

            for (int i = 1; i < args.size();)
            {
                edits.add(new VideoEditInfo(getTranslator().toEnum(args.get(i++),
                                                                   VideoEditMark.class),
                                            ProtocolUtils.combineInts(args.get(i++), args.get(i++))));
            }

            return edits;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }

    protected abstract String getCommand();
}
