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
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.TimeInfo;

/* default */class Command63QueryTimeZone extends AbstractCommand<TimeInfo>
{
    @Override
    protected String getMessage() throws ProtocolException
    {
        return "QUERY_TIME_ZONE";
    }

    @Override
    public TimeInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<String> args = Protocol63Utils.getArguments(response);
        if (args.size() != 3)
        {
            throw new ProtocolException(response);
        }

        try
        {
            TimeZone tz = TimeZone.getTimeZone(args.get(0));
            Date dateTime = ProtocolUtils.getIsoDateFormat().parse(args.get(2));

            return new TimeInfo(dateTime, tz);
        }
        catch (ParseException e)
        {
            throw new ProtocolException(e);
        }
    }
}
