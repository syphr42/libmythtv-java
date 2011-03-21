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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.ProtocolException.Direction;

/* default */class Command63QueryRecorderFillPositionMap extends AbstractCommand63QueryRecorder<Map<Long, Long>>
{
    private final long start;
    private final long end;

    public Command63QueryRecorderFillPositionMap(int recorderId, long start, long end)
    {
        super(recorderId);

        this.start = start;
        this.end = end;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("FILL_POSITION_MAP",
                                                String.valueOf(start),
                                                String.valueOf(end));
    }

    @Override
    public Map<Long, Long> parseResponse(String response) throws ProtocolException, CommandException
    {
        if ("ok".equals(response))
        {
            return Collections.emptyMap();
        }

        if ("error".equals(response))
        {
            throw new CommandException("Unable to retrieve position map");
        }

        Map<Long, Long> positionMap = new TreeMap<Long, Long>();
        int i = 0;

        List<String> args = Protocol63Utils.splitArguments(response);

        try
        {
            positionMap.put(Long.parseLong(args.get(i++)),
                            Long.parseLong(args.get(i++)));

            return positionMap;
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
