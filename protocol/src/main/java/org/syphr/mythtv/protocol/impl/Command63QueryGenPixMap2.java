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
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.data.ProgramInfo;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command63QueryGenPixMap2 extends AbstractCommand<Void>
{
    private final String id;
    private final ProgramInfo program;

    public Command63QueryGenPixMap2(String id, ProgramInfo program)
    {
        this.id = id;
        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> messageList = new ArrayList<String>();
        messageList.add("QUERY_GENPIXMAP2");
        messageList.add(id);
        messageList.addAll(Protocol63Utils.extractProgramInfo(program));

        return Protocol63Utils.combineArguments(messageList);
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.size() == 1 && "OK".equals(args.get(0)))
        {
            return null;
        }

        if (args.size() == 2 && "BAD".equals(args.get(0)))
        {
            String error = args.get(0);

            if ("ERROR_INVALID_REQUEST".equals(error))
            {
                throw new CommandException("Invalid request");
            }

            if ("ERROR_NOFILE".equals(error))
            {
                throw new CommandException("File not found");
            }

            if ("ERROR_UNKNOWN".equals(error))
            {
                throw new CommandException("Unexpected error occurred");
            }

            if ("NO_PATHNAME".equals(error))
            {
                throw new CommandException("No pathname was given");
            }
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
