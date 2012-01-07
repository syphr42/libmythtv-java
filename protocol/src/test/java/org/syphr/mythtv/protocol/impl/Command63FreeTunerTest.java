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

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;

public class Command63FreeTunerTest extends AbstractProtocolTest
{
    private static final int RECORDER_ID = 1;

    @Test
    public void testSendSuccess() throws IOException
    {
        Assert.assertTrue(test("OK"));
    }

    @Test
    public void testSendFailure() throws IOException
    {
        Assert.assertFalse(test("FAILED"));
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        test("BAD");
    }

    private boolean test(String response) throws IOException
    {
        setupMocks(response);

        Command63FreeTuner command = getCommand();
        try
        {
            return command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63FreeTuner getCommand()
    {
        return new Command63FreeTuner(null, null, RECORDER_ID);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "FREE_TUNER " + RECORDER_ID;

        /*
         * Sending the message.
         */
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(response);

        /*
         * Replay.
         */
        EasyMock.replay(getSocketManager());
    }
}
