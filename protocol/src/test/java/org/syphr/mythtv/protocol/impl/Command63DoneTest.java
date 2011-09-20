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
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63DoneTest
{
    private static final String MESSAGE = "DONE";

    private SocketManager socketManager;

    @Before
    public void setUp() throws Exception
    {
        socketManager = EasyMock.createMock(SocketManager.class);
    }

    @Test
    public void testGetMessage() throws ProtocolException
    {
        Assert.assertEquals(MESSAGE, getCommand().getMessage());
    }

    @Test
    public void testSendSuccess() throws IOException, CommandException
    {
        setupMocks();

        Command63Done command = getCommand();
        command.send(socketManager);

        EasyMock.verify(socketManager);
    }

    private Command63Done getCommand()
    {
        return new Command63Done(null, null);
    }

    private void setupMocks() throws IOException
    {
        socketManager.send(MESSAGE);
        EasyMock.replay(socketManager);
    }
}
