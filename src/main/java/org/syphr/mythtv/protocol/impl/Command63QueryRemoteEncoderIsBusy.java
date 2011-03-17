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
import java.util.List;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.ProtocolException.Direction;
import org.syphr.mythtv.protocol.data.InputInfo;

/* default */class Command63QueryRemoteEncoderIsBusy extends AbstractCommand63QueryRemoteEncoder<Pair<Boolean, InputInfo>>
{
    private final int withinSeconds;

    public Command63QueryRemoteEncoderIsBusy(int recorderId, int withinSeconds)
    {
        super(recorderId);
        this.withinSeconds = withinSeconds;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("IS_BUSY", String.valueOf(withinSeconds));
    }

    @Override
    public Pair<Boolean, InputInfo> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.size() != 6)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        boolean busy;
        if ("0".equals(args.get(0)))
        {
            busy = false;
        }
        else if ("1".equals(args.get(0)))
        {
            busy = true;
        }
        else
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            int i = 1;
            InputInfo inputInfo = new InputInfo(args.get(i++),
                                                Integer.parseInt(args.get(i++)),
                                                Integer.parseInt(args.get(i++)),
                                                Integer.parseInt(args.get(i++)),
                                                Integer.parseInt(args.get(i++)));

            return Pair.of(busy, inputInfo);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
