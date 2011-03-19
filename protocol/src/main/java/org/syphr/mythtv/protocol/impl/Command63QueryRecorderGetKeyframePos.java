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
import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.ProtocolException.Direction;

/* default */class Command63QueryRecorderGetKeyframePos extends AbstractCommand63QueryRecorder<Long>
{
    private final long desiredPosition;

    public Command63QueryRecorderGetKeyframePos(int recorderId, long desiredPosition)
    {
        super(recorderId);
        this.desiredPosition = desiredPosition;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        Pair<String, String> ints = ProtocolUtils.splitLong(desiredPosition);

        return Protocol63Utils.combineArguments("GET_KEYFRAME_POS",
                                                ints.getLeftElement(),
                                                ints.getRightElement());
    }

    @Override
    public Long parseResponse(String response) throws ProtocolException, CommandException
    {
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.size() != 2)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        if ("-1".equals(args.get(0)))
        {
            throw new CommandException("Unable to determine the keyframe position");
        }

        try
        {
            return ProtocolUtils.combineInts(args.get(0), args.get(1));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
