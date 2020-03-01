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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command63QueryGuideDataThrough extends AbstractProtocolCommand<Date>
{
    private static final String UNKNOWN = "0000-00-00 00:00";

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Command63QueryGuideDataThrough(Translator translator, Parser parser)
    {
        super(translator, parser);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "QUERY_GUIDEDATATHROUGH";
    }

    @Override
    public Date send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        if (UNKNOWN.equals(response))
        {
            return null;
        }

        try
        {
            return getTranslator().toInboundDate(format.parse(response));
        }
        catch (ParseException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
