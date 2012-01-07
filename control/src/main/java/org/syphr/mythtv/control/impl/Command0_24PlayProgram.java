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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.util.Date;

import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.DateUtils;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command0_24PlayProgram extends AbstractCommand<Void>
{
    private final int channelId;
    private final Date recStartTs;
    private final boolean resume;

    public Command0_24PlayProgram(Translator translator,
                                  int channelId,
                                  Date recStartTs,
                                  boolean resume)
    {
        super(translator);

        this.channelId = channelId;
        this.recStartTs = recStartTs;
        this.resume = resume;
    }

    @Override
    protected String getMessage()
    {
        return "play program " + channelId + " " + DateUtils.getIsoDateFormat().format(recStartTs)
               + " " + getTranslator().toString(resume);
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException,
                                                 CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("OK".equals(response))
        {
            return null;
        }

        if (response.startsWith("ERROR: "))
        {
            /*
             * Strip off the "ERROR: " part and give back the message.
             */
            throw new CommandException(response.substring(7, response.length()));
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
