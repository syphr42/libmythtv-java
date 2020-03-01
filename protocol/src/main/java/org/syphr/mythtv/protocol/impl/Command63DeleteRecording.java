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
import java.util.Date;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Channel;

/* default */class Command63DeleteRecording extends AbstractProtocolCommand<Void>
{
    private final Channel channel;
    private final Date recStartTs;
    private final boolean force;
    private final boolean forget;

    public Command63DeleteRecording(Translator translator,
                                    Parser parser,
                                    Channel channel,
                                    Date recStartTs,
                                    boolean force,
                                    boolean forget)
    {
        super(translator, parser);

        this.channel = channel;
        this.recStartTs = recStartTs;
        this.force = force;
        this.forget = forget;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE_RECORDING ");
        builder.append(channel.getId());
        builder.append(' ');
        builder.append(DateUtils.getMySqlDateFormat().format(getTranslator().toOutboundDate(recStartTs)));
        builder.append(' ');
        builder.append(force ? "FORCE" : "NO_FORCE");
        builder.append(' ');
        builder.append(forget ? "FORGET" : "NO_FORGET");

        return builder.toString();
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        try
        {
            // @formatter:off
            /*
             * Valid values:
             * 
             * -2 -> error
             * -1 -> success
             *  0 -> no channel ID or success, but expiring instead of delete
             *  + -> success, file was active recording on returned encoder number
             */
            // @formatter:on
            int result = Integer.parseInt(response);
            if (result == -2)
            {
                throw new CommandException("Unable to determine filename or the file does not exist");
            }

            if (result < -2)
            {
                throw new ProtocolException(response, Direction.RECEIVE);
            }
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }

        return null;
    }
}
