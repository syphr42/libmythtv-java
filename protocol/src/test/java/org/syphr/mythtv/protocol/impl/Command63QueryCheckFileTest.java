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
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;

public class Command63QueryCheckFileTest extends AbstractProtocolTest
{
    private static final Program PROGRAM = new Program(new Channel(1), new Date());

    private Parser parser;

    @Override
    public void setUp()
    {
        super.setUp();

        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccessNull() throws IOException, CommandException
    {
        URI actual = test(false, Arrays.asList(new String[] { "0", "uri" }));

        Assert.assertNull(actual);
    }

    @Test
    public void testSendSuccessCheckSlavesUri() throws IOException, CommandException
    {
        URI expected = URI.create("uri");
        URI actual = test(true, Arrays.asList(new String[] { "1", expected.toString() }));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSendSuccessNoCheckSlavesUri() throws IOException, CommandException
    {
        URI expected = URI.create("uri");
        URI actual = test(false, Arrays.asList(new String[] { "1", expected.toString() }));

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException, CommandException
    {
        test(false, Arrays.asList(new String[] { "BAD" }));
    }

    @Test(expected = IOException.class)
    public void testSendFirstArgWrong() throws IOException, CommandException
    {
        test(false, Arrays.asList(new String[] { "BAD", "uri" }));
    }

    @Test(expected = IOException.class)
    public void testSendSecondArgWrong() throws IOException, CommandException
    {
        test(false, Arrays.asList(new String[] { "1", "://:######" }));
    }

    private URI test(boolean checkSlaves, List<String> response) throws IOException
    {
        setupMocks(checkSlaves, response);

        Command63QueryCheckFile command = getCommand(checkSlaves);
        try
        {
            return command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63QueryCheckFile getCommand(boolean checkSlaves)
    {
        return new Command63QueryCheckFile(null, parser, checkSlaves, PROGRAM);
    }

    private void setupMocks(boolean checkSlaves, List<String> response) throws IOException
    {
        /*
         * Building the message.
         */
        List<String> programInfo = Arrays.asList(new String[] { "PROGRAM" });
        EasyMock.expect(parser.extractProgramInfo(PROGRAM)).andReturn(programInfo);

        List<String> args = new ArrayList<String>();
        args.add("QUERY_CHECKFILE");
        args.add(checkSlaves ? "1" : "0");
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
        EasyMock.expect(parser.splitArguments(serverResponse)).andReturn(response);

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
