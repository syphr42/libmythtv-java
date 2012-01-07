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

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.VideoEditInfo;
import org.syphr.mythtv.types.VideoEditMark;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */abstract class AbstractCommand66QueryVideoEditMarks extends AbstractCommand63QueryVideoEditMarks
{
    public AbstractCommand66QueryVideoEditMarks(Translator translator,
                                                Parser parser,
                                                Channel channel,
                                                Date recStartTs)
    {
        super(translator, parser, channel, recStartTs);
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

            if (args.size() < numEdits * 2 + 1)
            {
                throw new ProtocolException(response, Direction.RECEIVE);
            }

            for (int i = 1; i < args.size();)
            {
                edits.add(new VideoEditInfo(getTranslator().toEnum(args.get(i++), VideoEditMark.class),
                                            Long.parseLong(args.get(i++))));
            }

            return edits;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }

    @Override
    protected abstract String getCommand();
}
