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
import org.junit.Test;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63DownloadFileTest
{
    private SocketManager socketManager;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        socketManager = EasyMock.createMock(SocketManager.class);
        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccess() throws IOException, CommandException
    {
        String uriStr = "uri";

        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add(uriStr);
        setupMocks("", response);

        Command63DownloadFile command = getCommand();
        URI actual = command.send(socketManager);
        Assert.assertEquals(URI.create(uriStr), actual);
    }

    @Test(expected = IOException.class)
    public void testSendTooFewArgs() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        setupMocks("", response);

        Command63DownloadFile command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendTooManyArgs() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("uri");
        response.add("third");
        setupMocks("", response);

        Command63DownloadFile command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendWrongFirstArg() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("BAD");
        response.add("uri");
        setupMocks("", response);

        Command63DownloadFile command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendWrongSecondArg() throws IOException, CommandException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("://:######");
        setupMocks("", response);

        Command63DownloadFile command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = CommandException.class)
    public void testSendErrorResponse() throws IOException, CommandException
    {
        setupMocks("ERROR", null);

        Command63DownloadFile command = getCommand();
        command.send(socketManager);
    }

    private Command63DownloadFile getCommand() throws MalformedURLException
    {
        return new Command63DownloadFile(null,
                                         parser,
                                         new URL("http://example.org"),
                                         "",
                                         URI.create("testing"),
                                         false);
    }

    private void setupMocks(String mainResponse, List<String> splitResponse) throws IOException
    {
        EasyMock.expect(socketManager.sendAndWait(EasyMock.isA(String.class))).andReturn(mainResponse);
        EasyMock.expect(parser.combineArguments(EasyMock.isA(String.class),
                                                EasyMock.isA(String.class),
                                                EasyMock.isA(String.class),
                                                EasyMock.isA(String.class))).andReturn("");
        EasyMock.expect(parser.splitArguments(EasyMock.isA(String.class))).andReturn(splitResponse);
        EasyMock.replay(socketManager, parser);
    }
}
