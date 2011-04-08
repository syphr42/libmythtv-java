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

import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command63ForgetRecording extends AbstractCommand<Void>
{
    private final ProgramInfo program;

    public Command63ForgetRecording(ProgramInfo program)
    {
        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> args = new ArrayList<String>();
        args.add("FORGET_RECORDING");
        args.addAll(Protocol63Utils.extractProgramInfo(program));

        return Protocol63Utils.combineArguments(args);
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if (!"0".equals(response))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        return null;
    }
}
