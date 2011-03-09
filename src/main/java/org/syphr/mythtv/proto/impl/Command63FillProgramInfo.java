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
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.ProgramInfo;

/* default */class Command63FillProgramInfo extends AbstractCommand<ProgramInfo>
{
    private final String host;
    private final ProgramInfo program;

    public Command63FillProgramInfo(String host, ProgramInfo program)
    {
        this.host = host;
        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> args = new ArrayList<String>();
        args.add("FILL_PROGRAM_INFO");
        args.add(host);
        args.addAll(Protocol63Utils.extractProgramInfo(program));

        return Protocol63Utils.getProtocolValue(args);
    }

    @Override
    public ProgramInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.getArguments(response);

        return Protocol63Utils.parseProgramInfo(args);
    }
}
