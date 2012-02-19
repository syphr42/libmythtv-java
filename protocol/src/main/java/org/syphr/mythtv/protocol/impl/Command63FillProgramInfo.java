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
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Program;

/* default */class Command63FillProgramInfo extends AbstractProtocolCommand<Program>
{
    private final String host;
    private final Program program;

    public Command63FillProgramInfo(Translator translator,
                                    Parser parser,
                                    String host,
                                    Program program)
    {
        super(translator, parser);

        this.host = host;
        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> args = new ArrayList<String>();
        args.add("FILL_PROGRAM_INFO");
        args.add(host);
        args.addAll(getParser().extractProgramInfo(program));

        return getParser().combineArguments(args);
    }

    @Override
    public Program send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = getParser().splitArguments(response);

        return getParser().parseProgramInfo(args);
    }
}
