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

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.types.RecordingCategory;

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
               + Protocol63Utils.getRecordingCategory(getRecCategory());
    }

    @Override
    public List<ProgramInfo> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.getArguments(response);

        try
        {
            args.remove(0);
            return Protocol63Utils.parseProgramInfos(args);
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
