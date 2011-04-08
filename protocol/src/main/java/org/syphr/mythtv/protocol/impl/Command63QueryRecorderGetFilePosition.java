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

import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;

/* default */class Command63QueryRecorderGetFilePosition extends AbstractCommand63QueryRecorder<Long>
{
    public Command63QueryRecorderGetFilePosition(int recorderId)
    {
        super(recorderId);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return "GET_FILE_POSITION";
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
            throw new CommandException("Unable to determine the file position");
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
