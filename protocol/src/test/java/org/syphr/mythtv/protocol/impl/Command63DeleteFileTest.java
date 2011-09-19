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

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63DeleteFileTest
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
    public void testSendSuccessTrue() throws IOException
    {
        setupMocks("1");

        Command63DeleteFile command = getCommand();
        Boolean actual = command.send(socketManager);
        Assert.assertEquals(Boolean.TRUE, actual);
    }

    @Test
    public void testSendSuccessFalse() throws IOException
    {
        setupMocks("0");

        Command63DeleteFile command = getCommand();
        Boolean actual = command.send(socketManager);
        Assert.assertEquals(Boolean.FALSE, actual);
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        setupMocks("BAD");

        Command63DeleteFile command = getCommand();
        command.send(socketManager);
    }

    private Command63DeleteFile getCommand()
    {
        return new Command63DeleteFile(null, parser, URI.create("testing"), "");
    }

    private void setupMocks(String response) throws IOException
    {
        EasyMock.expect(socketManager.sendAndWait(EasyMock.isA(String.class))).andReturn(response);
        EasyMock.expect(parser.combineArguments(EasyMock.isA(String.class),
                                                EasyMock.isA(String.class),
                                                EasyMock.isA(String.class))).andReturn("");
        EasyMock.replay(socketManager, parser);
    }
}
