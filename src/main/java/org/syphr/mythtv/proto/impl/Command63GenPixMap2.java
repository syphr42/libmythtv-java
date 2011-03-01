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
import org.syphr.mythtv.proto.data.GenPixMapResponse;
import org.syphr.mythtv.proto.data.ProgramInfo;

/* default */class Command63GenPixMap2 implements Command<GenPixMapResponse>
{
    private final String message;

    public Command63GenPixMap2(String id, ProgramInfo program) throws ProtocolException
    {
        List<String> messageList = new ArrayList<String>();
        messageList.add("QUERY_GENPIXMAP2");
        messageList.add(id);
        messageList.addAll(Protocol63Utils.extractProgramInfo(program));

        message = Protocol63Utils.getProtocolValue(messageList);
    }

    @Override
    public GenPixMapResponse send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);
        List<String> args = Protocol63Utils.getArguments(response);

        try
        {
            return Protocol63Utils.getGenPixMapResponse("BAD".equals(args.get(0))
                    ? args.get(1)
                    : args.get(0));
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
