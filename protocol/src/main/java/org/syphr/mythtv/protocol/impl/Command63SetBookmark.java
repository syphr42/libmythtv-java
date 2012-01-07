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
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.tuple.Pair;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63SetBookmark extends AbstractProtocolCommand<Boolean>
{
    private final Channel channel;
    private final Date recStartTs;
    private final long location;

    public Command63SetBookmark(Translator translator,
                                Parser parser,
                                Channel channel,
                                Date recStartTs,
                                long location)
    {
        super(translator, parser);

        this.channel = channel;
        this.recStartTs = recStartTs;
        this.location = location;
    }

    protected Channel getChannel()
    {
        return channel;
    }

    protected Date getRecStartTs()
    {
        return recStartTs;
    }

    protected long getLocation()
    {
        return location;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SET_BOOKMARK");
        builder.append(' ');
        builder.append(getChannel().getId());
        builder.append(' ');
        builder.append(TimeUnit.MILLISECONDS.toSeconds(getRecStartTs().getTime()));
        builder.append(' ');

        Pair<String, String> ints = ProtocolUtils.splitLong(getLocation());
        builder.append(ints.getLeft());
        builder.append(' ');
        builder.append(ints.getRight());

        return builder.toString();
    }

    @Override
    public Boolean send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("OK".equals(response))
        {
            return true;
        }

        if ("FAILED".equals(response))
        {
            throw new CommandException("Unable to set bookmark");
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
