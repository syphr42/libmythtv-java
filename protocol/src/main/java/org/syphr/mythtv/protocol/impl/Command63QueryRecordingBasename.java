/*
 * Copyright 2011-2012 Gregory P. Moyer
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

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Program;

/* default */class Command63QueryRecordingBasename extends AbstractProtocolCommand<Program>
{
    private final String basename;

    public Command63QueryRecordingBasename(Translator translator, Parser parser, String basename)
    {
        super(translator, parser);

        this.basename = basename;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "QUERY_RECORDING BASENAME " + basename;
    }

    @Override
    public Program send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = getParser().splitArguments(response);

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
            return getParser().parseProgramInfo(args);
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
