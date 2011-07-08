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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63QueryCheckFile extends AbstractProtocolCommand<URI>
{
    private final boolean checkSlaves;
    private final Program program;

    public Command63QueryCheckFile(Translator translator,
                                   Parser parser,
                                   boolean checkSlaves,
                                   Program program)
    {
        super(translator, parser);

        this.checkSlaves = checkSlaves;
        this.program = program;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        List<String> messageList = new ArrayList<String>();
        messageList.add("QUERY_CHECKFILE");
        messageList.add(checkSlaves ? "1" : "0");
        messageList.addAll(getParser().extractProgramInfo(program));

        return getParser().combineArguments(messageList);
    }

    @Override
    public URI send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = getParser().splitArguments(response);
        if (args.size() != 2)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
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
                throw new ProtocolException(response, Direction.RECEIVE, e);
            }
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
