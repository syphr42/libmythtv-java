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
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.commons.exception.CommandException;

public class Command63MythProtoVersionTest extends AbstractProtocolTest
{
    private Parser parser;

    @Override
    public void setUp()
    {
        super.setUp();

        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccess() throws IOException, CommandException
    {
        test(Arrays.asList(new String[] { "ACCEPT", "VERSION" }));
    }

    @Test(expected = CommandException.class)
    public void testSendFailure() throws IOException, CommandException
    {
        test(Arrays.asList(new String[] { "REJECT", "VERSION" }));
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException, CommandException
    {
        test(Arrays.asList(new String[] { "BAD" }));
    }

    @Test(expected = IOException.class)
    public void testSendFirstArgWrong() throws IOException, CommandException
    {
        test(Arrays.asList(new String[] { "BAD", "VERSION" }));
    }

    private void test(List<String> response) throws IOException, CommandException
    {
        setupMocks(response);

        Command63MythProtoVersion command = getCommand();
        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63MythProtoVersion getCommand()
    {
        return new Command63MythProtoVersion(null, parser);
    }

    private void setupMocks(List<String> response) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "MYTH_PROTO_VERSION 63 3875641D";

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
