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

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.util.exception.CommandException;

public class Command63MessageClearSettingsCacheTest extends AbstractProtocolTest
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
        test("OK");
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException, CommandException
    {
        test("BAD");
    }

    private void test(String response) throws IOException, CommandException
    {
        setupMocks(response);

        Command63MessageClearSettingsCache command = getCommand();
        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63MessageClearSettingsCache getCommand()
    {
        return new Command63MessageClearSettingsCache(null, parser);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments("MESSAGE", "CLEAR_SETTINGS_CACHE")).andReturn(combined);

        /*
         * Sending the message.
         */
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(response);

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
