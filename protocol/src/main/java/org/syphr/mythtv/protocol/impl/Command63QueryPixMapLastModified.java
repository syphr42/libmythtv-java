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
import org.syphr.mythtv.data.Program;

/* default */class Command63QueryPixMapLastModified extends AbstractProtocolCommand<Date>
{
    private final Program program;

    public Command63QueryPixMapLastModified(Translator translator, Parser parser, Program program)
    {
        super(translator, parser);

        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> messageList = new ArrayList<String>();
        messageList.add("QUERY_PIXMAP_LASTMODIFIED");
        messageList.addAll(getParser().extractProgramInfo(program));

        return getParser().combineArguments(messageList);
    }

    @Override
    public Date send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("BAD".equals(response))
        {
            return null;
        }

        try
        {
            return new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(response)));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
