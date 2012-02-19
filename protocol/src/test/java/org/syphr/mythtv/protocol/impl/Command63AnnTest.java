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

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;

public class Command63AnnTest extends AbstractProtocolTest
{
    private static final ConnectionType TYPE = ConnectionType.values()[0];
    private static final String HOST = "HOST";
    private static final EventLevel LEVEL = EventLevel.values()[0];

    private Translator translator;

    @Override
    public void setUp()
    {
        super.setUp();

        translator = EasyMock.createMock(Translator.class);
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        test("OK");
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        test("BAD");
    }

    private void test(String response) throws IOException
    {
        setupMocks(response);

        Command63Ann command = getCommand();
        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63Ann getCommand()
    {
        return new Command63Ann(translator, null, TYPE, HOST, LEVEL);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String translatedType = "TRANSLATED_TYPE";
        String translatedLevel = "TRANSLATED_LEVEL";
        EasyMock.expect(translator.toString(TYPE)).andReturn(translatedType);
        EasyMock.expect(translator.toString(LEVEL)).andReturn(translatedLevel);

        StringBuilder builder = new StringBuilder();
        builder.append("ANN ");
        builder.append(translatedType);
        builder.append(' ');
        builder.append(HOST);
        builder.append(' ');
        builder.append(translatedLevel);

        String combined = builder.toString();

        /*
         * Sending the message.
         */
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(response);

        /*
         * Replay.
         */
        EasyMock.replay(getSocketManager(), translator);
    }

    @Override
    protected void verify()
    {
        super.verify();

        EasyMock.verify(translator);
    }

}
