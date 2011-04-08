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

import org.syphr.mythtv.protocol.data.Channel;
import org.syphr.mythtv.util.exception.ProtocolException;

/* default */class Command63QueryRecorderGetChannelInfo extends AbstractCommand63QueryRecorder<Channel>
{
    private final int channelId;

    public Command63QueryRecorderGetChannelInfo(int recorderId, int channelId)
    {
        super(recorderId);
        this.channelId = channelId;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("GET_CHANNEL_INFO", String.valueOf(channelId));
    }

    @Override
    public Channel parseResponse(String response) throws ProtocolException
    {
        return Protocol63Utils.parseChannel(response);
    }
}
