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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.syphr.mythtv.data.RecorderLocation;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63GetRecorderFromNumTest
{
    private static final int RECORDER_ID = 1;

    private SocketManager socketManager;
    private Parser parser;

    @Before
    public void setUp()
    {
        socketManager = EasyMock.createMock(SocketManager.class);
        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccess() throws IOException, CommandException
    {
        RecorderLocation expected = new RecorderLocation(RECORDER_ID, "HOST", 2);

        List<String> response = new ArrayList<String>();
        response.add(expected.getHost());
        response.add(String.valueOf(expected.getPort()));

        Assert.assertEquals(expected, test(response));
    }

    @Test(expected = CommandException.class)
    public void testSendFailure() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("nohost");
        response.add("");

        test(response);
    }

    @Test(expected = IOException.class)
    public void testSendEmptyResponse() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("");

        test(response);
    }

    @Test(expected = IOException.class)
    public void testSendTooManyArgs() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("");
        response.add("");
        response.add("");

        test(response);
    }

    @Test(expected = IOException.class)
    public void testSendSecondArgWrong() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("HOST");
        response.add("BAD");

        test(response);
    }

    private RecorderLocation test(List<String> response) throws IOException, CommandException
    {
        setupMocks(response);

        Command63GetRecorderFromNum command = getCommand();
        try
        {
            return command.send(socketManager);
        }
        finally
        {
            verify();
        }
    }

    private Command63GetRecorderFromNum getCommand() throws MalformedURLException
    {
        return new Command63GetRecorderFromNum(null, parser, RECORDER_ID);
    }

    private void setupMocks(List<String> response) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments("GET_RECORDER_FROM_NUM",
                                                String.valueOf(RECORDER_ID))).andReturn(combined);

        /*
         * Sending the message.
         */
        String serverResponse = "SERVER_RESPONSE";
        EasyMock.expect(socketManager.sendAndWait(combined)).andReturn(serverResponse);

        /*
         * Parsing the response.
         */
        EasyMock.expect(parser.splitArguments(serverResponse)).andReturn(response);

        /*
         * Replay.
         */
        EasyMock.replay(socketManager, parser);
    }

    private void verify()
    {
        EasyMock.verify(socketManager, parser);
    }
}