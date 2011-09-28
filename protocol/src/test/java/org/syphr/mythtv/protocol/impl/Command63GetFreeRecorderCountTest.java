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
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63GetFreeRecorderCountTest
{
    private SocketManager socketManager;

    @Before
    public void setUp()
    {
        socketManager = EasyMock.createMock(SocketManager.class);
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        String response = "1";

        Integer expected = Integer.valueOf(response);
        Integer actual = test(response);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testSendTooLow() throws IOException
    {
        test("-1");
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        test("BAD");
    }

    private Integer test(String response) throws IOException
    {
        setupMocks(response);

        Command63GetFreeRecorderCount command = getCommand();
        try
        {
            return command.send(socketManager);
        }
        finally
        {
            verify();
        }
    }

    private Command63GetFreeRecorderCount getCommand()
    {
        return new Command63GetFreeRecorderCount(null, null);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "GET_FREE_RECORDER_COUNT";

        /*
         * Sending the message.
         */
        EasyMock.expect(socketManager.sendAndWait(combined)).andReturn(response);

        /*
         * Replay.
         */
        EasyMock.replay(socketManager);
    }

    private void verify()
    {
        EasyMock.verify(socketManager);
    }
}
