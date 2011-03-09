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
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.RecorderDevice;

/* default */class Command63LockTuner extends AbstractCommand<RecorderDevice>
{
    private final int recorderId;

    public Command63LockTuner(int recorderId)
    {
        this.recorderId = recorderId;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LOCK_TUNER");

        if (recorderId > 0)
        {
            builder.append(' ');
            builder.append(recorderId);
        }

        return builder.toString();
    }

    @Override
    public RecorderDevice send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.getArguments(response);
        if (args.isEmpty())
        {
            throw new ProtocolException(response);
        }

        try
        {
            int recorderId = Integer.parseInt(args.get(0));
            if (recorderId < 1)
            {
                return null;
            }

            if (args.size() < 4)
            {
                throw new ProtocolException(response);
            }

            return new RecorderDevice(recorderId, args.get(1), args.get(2), args.get(3));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
