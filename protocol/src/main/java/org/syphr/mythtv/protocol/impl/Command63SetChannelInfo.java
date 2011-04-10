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

import java.io.IOException;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command63SetChannelInfo extends AbstractCommand<Void>
{
    private final Channel oldChannel;
    private final Channel newChannel;

    public Command63SetChannelInfo(Channel oldChannel, Channel newChannel)
    {
        this.oldChannel = oldChannel;
        this.newChannel = newChannel;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        if (!oldChannel.equals(newChannel))
        {
            throw new ProtocolException("Cannot replace channel information across different channels",
                                        Direction.SEND);
        }

        return Protocol63Utils.combineArguments(String.valueOf(newChannel.getId()),
                                                String.valueOf(newChannel.getSourceId()),
                                                oldChannel.getNumber(),
                                                newChannel.getCallsign(),
                                                newChannel.getNumber(),
                                                newChannel.getName(),
                                                newChannel.getXmltvId());
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("1".equals(response))
        {
            return null;
        }

        if ("0".equals(response))
        {
            throw new CommandException("Unable to set channel info: " + newChannel);
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
