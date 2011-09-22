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
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63DownloadFileTest
{
    private static URL TARGET;
    private static final String STORAGE_GROUP = "storageGroup";
    private static final URI FILE_PATH = URI.create("uri");

    private SocketManager socketManager;
    private Parser parser;

    @BeforeClass
    public static void setUpBeforeClass() throws MalformedURLException
    {
        TARGET = new URL("http://example.org");
    }

    @Before
    public void setUp()
    {
        socketManager = EasyMock.createMock(SocketManager.class);
        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccessNow() throws IOException, CommandException
    {
        URI expected = URI.create("uri");

        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add(expected.toString());

        URI actual = test(true, null, response);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSendSuccessNotNow() throws IOException, CommandException
    {
        URI expected = URI.create("uri");

        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add(expected.toString());

        URI actual = test(false, null, response);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testSendTooFewArgs() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");

        test(false, null, response);
    }

    @Test(expected = IOException.class)
    public void testSendTooManyArgs() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("uri");
        response.add("third");

        test(false, null, response);
    }

    @Test(expected = IOException.class)
    public void testSendWrongFirstArg() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("BAD");
        response.add("uri");

        test(false, null, response);
    }

    @Test(expected = IOException.class)
    public void testSendWrongSecondArg() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("://:######");

        test(false, null, response);
    }

    @Test(expected = CommandException.class)
    public void testSendErrorResponse() throws IOException, CommandException
    {
        test(false, "ERROR", null);
    }

    private URI test(boolean now, String combinedResponse, List<String> splitResponse) throws IOException,
                                                                                      CommandException
    {
        setupMocks(now, combinedResponse, splitResponse);

        Command63DownloadFile command = getCommand(now);
        try
        {
            return command.send(socketManager);
        }
        finally
        {
            verify();
        }
    }

    private Command63DownloadFile getCommand(boolean now) throws MalformedURLException
    {
        return new Command63DownloadFile(null, parser, TARGET, STORAGE_GROUP, FILE_PATH, now);
    }

    private void setupMocks(boolean now, String combinedResponse, List<String> splitResponse) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments(now ? "DOWNLOAD_FILE_NOW" : "DOWNLOAD_FILE",
                                                TARGET.toString(),
                                                STORAGE_GROUP,
                                                FILE_PATH.getPath())).andReturn(combined);

        /*
         * Sending the message.
         */
        EasyMock.expect(socketManager.sendAndWait(combined)).andReturn(combinedResponse);

        /*
         * Parsing the response.
         */
        EasyMock.expect(parser.splitArguments(combinedResponse)).andReturn(splitResponse).times(0,
                                                                                                1);

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
