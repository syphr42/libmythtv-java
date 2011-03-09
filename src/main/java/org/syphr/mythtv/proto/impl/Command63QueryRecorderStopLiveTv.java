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

/* default */class Command63QueryRecorderStopLiveTv extends AbstractCommand<Boolean>
{
    private final int recorder;

    public Command63QueryRecorderStopLiveTv(int recorder)
    {
        this.recorder = recorder;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return Protocol63Utils.getProtocolValue("QUERY_RECORDER " + recorder,
                                                "STOP_LIVETV");
    }

    @Override
    public Boolean send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("bad".equals(response))
        {
            return false;
        }

        if ("ok".equals(response))
        {
            return true;
        }

        throw new ProtocolException(response);
    }
}
