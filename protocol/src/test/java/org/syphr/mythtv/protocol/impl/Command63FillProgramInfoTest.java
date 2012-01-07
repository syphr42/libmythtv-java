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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;

public class Command63FillProgramInfoTest extends AbstractProtocolTest
{
    private static final String HOST = "HOST";
    private static final Program PROGRAM = new Program(new Channel(1), new Date());

    private Parser parser;

    @Override
    public void setUp()
    {
        super.setUp();

        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        setupMocks();

        Command63FillProgramInfo command = getCommand();

        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63FillProgramInfo getCommand()
    {
        return new Command63FillProgramInfo(null, parser, HOST, PROGRAM);
    }

    private void setupMocks() throws IOException
    {
        /*
         * Building the message.
         */
        List<String> programInfo = Arrays.asList(new String[] { "PROGRAM" });
        EasyMock.expect(parser.extractProgramInfo(PROGRAM)).andReturn(programInfo);

        List<String> args = new ArrayList<String>();
        args.add("FILL_PROGRAM_INFO");
        args.add(HOST);
        args.addAll(programInfo);

        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments(args)).andReturn(combined);

        /*
         * Sending the message.
         */
        String serverResponse = "SERVER_RESPONSE";
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(serverResponse);

        /*
         * Parsing the response.
         */
        List<String> splitResponse = Collections.emptyList();
        EasyMock.expect(parser.splitArguments(serverResponse)).andReturn(splitResponse);
        EasyMock.expect(parser.parseProgramInfo(splitResponse)).andReturn(null);

        /*
         * Replay.
         */
        EasyMock.replay(getSocketManager(), parser);
    }

    @Override
    protected void verify()
    {
        super.verify();

        EasyMock.verify(parser);
    }
}
