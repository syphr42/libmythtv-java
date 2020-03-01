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
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.TimeInfo;

/* default */class Command63QueryTimeZone extends AbstractProtocolCommand<TimeInfo>
{
    public Command63QueryTimeZone(Translator translator, Parser parser)
    {
        super(translator, parser);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "QUERY_TIME_ZONE";
    }

    @Override
    public TimeInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<String> args = getParser().splitArguments(response);
        if (args.size() != 3)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            TimeZone tz = TimeZone.getTimeZone(args.get(0));
            // TODO in local?
            Date dateTime = DateUtils.getIsoDateFormat().parse(args.get(2));

            return new TimeInfo(dateTime, tz);
        }
        catch (ParseException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
