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

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63AllowShutdownTest
{
    private static final String MESSAGE = "ALLOW_SHUTDOWN";

    private SocketManager socketManager;

    @Before
    public void setUp()
    {
        socketManager = EasyMock.createMock(SocketManager.class);
    }

    @Test
    public void testGetMessage() throws ProtocolException
    {
        Assert.assertEquals(MESSAGE, getCommand().getMessage());
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        setupMocks("OK");

        Command63AllowShutdown command = getCommand();
        command.send(socketManager);

        EasyMock.verify(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        setupMocks("BAD");

        try
        {
            Command63AllowShutdown command = getCommand();
            command.send(socketManager);
        }
        finally
        {
            EasyMock.verify(socketManager);
        }
    }

    private Command63AllowShutdown getCommand()
    {
        return new Command63AllowShutdown(null, null);
    }

    private void setupMocks(String response) throws IOException
    {
        EasyMock.expect(socketManager.sendAndWait(MESSAGE)).andReturn(response);
        EasyMock.replay(socketManager);
    }

}
