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
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.data.Program;

public class Command63CheckRecordingTest extends AbstractProtocolTest
{
    private static final Program PROGRAM = new Program(null, null);

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
        Integer expected = 1;
        Integer actual = test(expected.toString());

        Assert.assertEquals(expected, actual);
        verify();
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        test("BAD");
    }

    private Integer test(String response) throws IOException
    {
        setupMocks(response);

        Command63CheckRecording command = getCommand();
        try
        {
            return command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63CheckRecording getCommand()
    {
        return new Command63CheckRecording(null, parser, PROGRAM);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        List<String> programInfo = Arrays.asList(new String[] { "PROGRAM" });
        EasyMock.expect(parser.extractProgramInfo(PROGRAM)).andReturn(programInfo);

        List<String> args = new ArrayList<String>();
        args.add("CHECK_RECORDING");
        args.addAll(programInfo);

        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments(args)).andReturn(combined);

        /*
         * Sending the message.
         */
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(response);

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
