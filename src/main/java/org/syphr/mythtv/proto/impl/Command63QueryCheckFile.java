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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.ProgramInfo;

/* default */class Command63QueryCheckFile extends AbstractCommand<URI>
{
    private final boolean checkSlaves;
    private final ProgramInfo program;

    public Command63QueryCheckFile(boolean checkSlaves, ProgramInfo program)
    {
        this.checkSlaves = checkSlaves;
        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> messageList = new ArrayList<String>();
        messageList.add("QUERY_CHECKFILE");
        messageList.add(checkSlaves ? "1" : "0");
        messageList.addAll(Protocol63Utils.extractProgramInfo(program));

        return Protocol63Utils.getProtocolValue(messageList);
    }

    @Override
    public URI send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.getArguments(response);
        if (args.size() != 2)
        {
            throw new ProtocolException(response);
        }

        String indicator = args.get(0);
        if ("0".equals(indicator))
        {
            return null;
        }
        if ("1".equals(indicator))
        {
            try
            {
                return new URI(args.get(1));
            }
            catch (URISyntaxException e)
            {
                throw new ProtocolException(response, e);
            }
        }

        throw new ProtocolException(response);
    }
}
