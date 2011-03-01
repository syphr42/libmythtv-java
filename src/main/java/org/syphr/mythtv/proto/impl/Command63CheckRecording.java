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
import org.syphr.mythtv.proto.data.RecorderInfo;

/* default */class Command63CheckRecording implements Command<RecorderInfo>
{
    private final String message;

    public Command63CheckRecording(ProgramInfo program) throws ProtocolException
    {
        List<String> extracted = Protocol63Utils.extractProgramInfo(program);
        extracted.add(0, "CHECK_RECORDING");

        message = Protocol63Utils.getProtocolValue(extracted);
    }

    @Override
    public RecorderInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);

        try
        {
            return new RecorderInfo(Integer.valueOf(response), null, -1);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
