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
import java.util.List;

import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.types.RecordingCategory;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command63QueryRecordings extends AbstractCommand<List<ProgramInfo>>
{
    private final RecordingCategory recCategory;

    public Command63QueryRecordings(RecordingCategory recCategory)
    {
        this.recCategory = recCategory;
    }

    public RecordingCategory getRecCategory()
    {
        return recCategory;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "QUERY_RECORDINGS "
               + Protocol63Utils.getTranslator().toString(getRecCategory());
    }

    @Override
    public List<ProgramInfo> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.isEmpty())
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        args.remove(0);
        return Protocol63Utils.parseProgramInfos(args);
    }
}
