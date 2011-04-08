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

import java.util.List;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.protocol.data.InputInfo;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;

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
    protected Pair<Boolean, InputInfo> parseResponse(String response) throws ProtocolException,
                                                                     CommandException
    {
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
