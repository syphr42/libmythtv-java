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
import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class Command63AnnTest
{
    private static final String HOST = "localhost";
    private static final String MESSAGE_PREFIX = "ANN ";

    private SocketManager socketManager;
    private Translator translator;

    @Before
    public void setUp()
    {
        socketManager = EasyMock.createMock(SocketManager.class);
        translator = EasyMock.createMock(Translator.class);
    }

    @Test
    public void testGetMessage() throws ProtocolException
    {
        for (ConnectionType type : ConnectionType.values())
        {
            for (EventLevel level : EventLevel.values())
            {
                EasyMock.expect(translator.toString(type)).andReturn(type.name());
                EasyMock.expect(translator.toString(level)).andReturn(level.name());
                EasyMock.replay(translator);

                Command63Ann command = new Command63Ann(translator, null, type, HOST, level);
                Assert.assertEquals(getMessage(type, level), command.getMessage());

                EasyMock.verify(translator);
                EasyMock.reset(translator);
            }
        }
    }

    private String getMessage(ConnectionType type, EventLevel level)
    {
        return MESSAGE_PREFIX + type.name() + " " + HOST + " " + level.name();
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        ConnectionType type = ConnectionType.values()[0];
        EventLevel level = EventLevel.values()[0];

        Command63Ann command = new Command63Ann(translator, null, type, HOST, level);

        EasyMock.expect(socketManager.sendAndWait(EasyMock.isA(String.class))).andReturn("OK");
        EasyMock.expect(translator.toString(type)).andReturn("");
        EasyMock.expect(translator.toString(level)).andReturn("");
        EasyMock.replay(socketManager, translator);

        command.send(socketManager);

        EasyMock.verify(socketManager);
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        ConnectionType type = ConnectionType.values()[0];
        EventLevel level = EventLevel.values()[0];

        Command63Ann command = new Command63Ann(translator, null, type, HOST, level);

        EasyMock.expect(socketManager.sendAndWait(EasyMock.isA(String.class))).andReturn("BAD");
        EasyMock.expect(translator.toString(type)).andReturn("");
        EasyMock.expect(translator.toString(level)).andReturn("");
        EasyMock.replay(socketManager, translator);

        try
        {
            command.send(socketManager);
        }
        finally {
            EasyMock.verify(socketManager);
        }
    }

}
