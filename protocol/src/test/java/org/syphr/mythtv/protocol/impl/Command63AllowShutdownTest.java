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

public class Command63AllowShutdownTest extends AbstractProtocolTest
{
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

        Command63AllowShutdown command = getCommand();
        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63AllowShutdown getCommand()
    {
        return new Command63AllowShutdown(null, null);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String message = "ALLOW_SHUTDOWN";

        /*
         * Sending the message.
         */
        EasyMock.expect(getSocketManager().sendAndWait(message)).andReturn(response);

        /*
         * Replay.
         */
        EasyMock.replay(getSocketManager());
    }
}
