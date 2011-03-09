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

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.Channel;

/* default */class Command63SetChannelInfo extends AbstractCommand<Boolean>
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
            throw new ProtocolException("Cannot replace channel information across different channels");
        }

        return Protocol63Utils.getProtocolValue(String.valueOf(newChannel.getId()),
                                                String.valueOf(newChannel.getSourceId()),
                                                oldChannel.getNumber(),
                                                newChannel.getCallsign(),
                                                newChannel.getNumber(),
                                                newChannel.getName(),
                                                newChannel.getXmltvId());
    }

    @Override
    public Boolean send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("0".equals(response))
        {
            return false;
        }

        if ("1".equals(response))
        {
            return true;
        }

        throw new ProtocolException(response);
    }
}
