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

/* default */class Command63QueryRecorderToggleChannelFavorite implements Command<Void>
{
    private final String message;

    public Command63QueryRecorderToggleChannelFavorite(int recorder, String channelGroup)
    {
        message = Protocol63Utils.getProtocolValue("QUERY_RECORDER " + recorder,
                                                   "TOGGLE_CHANNEL_FAVORITE",
                                                   channelGroup);
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);

        /*
         * A response of "bad" only indicates that the recorder was not connected. A
         * response of "ok" does not imply that that channel was actually toggled, only
         * that the recorder was connected when the request was received. Since there is
         * no way to know if the toggle actually succeeded, this command will not return a
         * boolean value.
         */
        if (!"bad".equals(response) && !"ok".equals(response))
        {
            throw new ProtocolException(response);
        }

        return null;
    }
}
