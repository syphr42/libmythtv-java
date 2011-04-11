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

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.types.ChannelBrowseDirection;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;

/* default */class Command63QueryRecorderGetNextProgramInfo extends AbstractCommand63QueryRecorder<Program>
{
    private final Channel channel;
    private final ChannelBrowseDirection browseDirection;
    private final Date startTime;

    public Command63QueryRecorderGetNextProgramInfo(int recorderId,
                                                         Channel channel,
                                                         ChannelBrowseDirection browseDirection,
                                                         Date startTime)
    {
        super(recorderId);

        this.channel = channel;
        this.browseDirection = browseDirection;
        this.startTime = startTime;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("GET_NEXT_PROGRAM_INFO",
                                                channel.getNumber(),
                                                String.valueOf(channel.getId()),
                                                Protocol63Utils.getTranslator().toString(browseDirection),
                                                ProtocolUtils.getIsoDateFormat().format(startTime));
    }

    @Override
    protected Program parseResponse(String response) throws ProtocolException
    {
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.size() != 12)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            int i = 0;

            String title = args.get(i++);
            String subtitle = args.get(i++);
            String description = args.get(i++);
            String category = args.get(i++);
            String nextStartTime = args.get(i++);
            String nextEndTime = args.get(i++);
            String callsign = args.get(i++);
            String iconPath = args.get(i++);
            String channelname = args.get(i++);
            int channelId = Integer.parseInt(args.get(i++));
            String seriesId = args.get(i++);
            String programId = args.get(i++);

            return new Program(title,
                                   subtitle,
                                   description,
                                   category,
                                   new Channel(channelId, channelname, callsign, iconPath),
                                   " ".equals(nextStartTime)
                                           ? null
                                           : ProtocolUtils.getIsoDateFormat().parse(nextStartTime),
                                   " ".equals(nextEndTime)
                                           ? null
                                           : ProtocolUtils.getIsoDateFormat().parse(nextEndTime),
                                   seriesId,
                                   programId);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
        catch (ParseException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
