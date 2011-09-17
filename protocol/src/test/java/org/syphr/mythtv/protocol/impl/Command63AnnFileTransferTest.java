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
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class Command63AnnFileTransferTest
{
    private SocketManager socketManager;
    private Translator translator;
    private Parser parser;

    @Before
    public void setUp() throws Exception
    {
        socketManager = EasyMock.createMock(SocketManager.class);
        translator = EasyMock.createMock(Translator.class);
        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("1");
        response.add("2");
        response.add("3");
        setupMocks(response);

        Command63AnnFileTransfer command = getCommand();

        QueryFileTransfer result = command.send(socketManager);
        Assert.assertEquals(QueryFileTransfer63.class, result.getClass());
    }

    @Test(expected = IOException.class)
    public void testSendEmptyResponse() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("");
        setupMocks(response);

        Command63AnnFileTransfer command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendTooManyArgs() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("");
        response.add("");
        response.add("");
        response.add("");
        response.add("");
        setupMocks(response);

        Command63AnnFileTransfer command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendFirstArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("BAD");
        response.add("1");
        response.add("2");
        response.add("3");
        setupMocks(response);

        Command63AnnFileTransfer command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendSecondArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("BAD");
        response.add("2");
        response.add("3");
        setupMocks(response);

        Command63AnnFileTransfer command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendThirdArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("1");
        response.add("BAD");
        response.add("3");
        setupMocks(response);

        Command63AnnFileTransfer command = getCommand();
        command.send(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendFourthArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("1");
        response.add("2");
        response.add("BAD");
        setupMocks(response);

        Command63AnnFileTransfer command = getCommand();
        command.send(socketManager);
    }

    private Command63AnnFileTransfer getCommand()
    {
        FileTransferType type = FileTransferType.values()[0];

        return new Command63AnnFileTransfer(translator,
                                            parser,
                                            "",
                                            type,
                                            false,
                                            0,
                                            URI.create("uri"),
                                            "",
                                            socketManager);
    }

    private void setupMocks(List<String> response) throws IOException
    {
        EasyMock.expect(socketManager.sendAndWait(EasyMock.isA(String.class))).andReturn("");
        EasyMock.expect(translator.toString(EasyMock.isA(FileTransferType.class))).andReturn("");
        EasyMock.expect(parser.combineArguments(EasyMock.isA(String.class),
                                                EasyMock.isA(String.class),
                                                EasyMock.isA(String.class))).andReturn("");
        EasyMock.expect(parser.splitArguments(EasyMock.isA(String.class))).andReturn(response);
        EasyMock.replay(socketManager, translator, parser);
    }

}
