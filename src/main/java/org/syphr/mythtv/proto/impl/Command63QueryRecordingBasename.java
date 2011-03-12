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

import org.syphr.mythtv.proto.CommandException;
import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.ProgramInfo;

/* default */class Command63QueryRecordingBasename extends AbstractCommand<ProgramInfo>
{
    private final String basename;

    public Command63QueryRecordingBasename(String basename)
    {
        this.basename = basename;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "QUERY_RECORDING BASENAME " + basename;
    }

    @Override
    public ProgramInfo send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.isEmpty())
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        String status = args.get(0);

        if ("ERROR".equals(status))
        {
            throw new CommandException("An unexpected error occurred while retrieving recording information");
        }

        if ("OK".equals(status))
        {
            args.remove(0);
            return Protocol63Utils.parseProgramInfo(args);
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
