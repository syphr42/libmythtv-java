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
import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.DateUtils;

public class Command63DeleteRecordingTest
{
    private static final Channel CHANNEL = new Channel(1);
    private static final Date REC_START = new Date();

    private SocketManager socketManager;

    @Before
    public void setUp()
    {
        socketManager = EasyMock.createMock(SocketManager.class);
    }

    @Test
    public void testSendSuccessForceForget() throws IOException, CommandException
    {
        test(true, true, "-1");
    }

    @Test
    public void testSendSuccessNoForceForget() throws IOException, CommandException
    {
        test(false, true, "-1");
    }

    @Test
    public void testSendSuccessForceNoForget() throws IOException, CommandException
    {
        test(true, false, "-1");
    }

    @Test
    public void testSendSuccessNoForceNoForget() throws IOException, CommandException
    {
        test(false, false, "-1");
    }

    @Test(expected = CommandException.class)
    public void testSendFailed() throws IOException, CommandException
    {
        test(false, false, "-2");
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException, CommandException
    {
        test(false, false, "BAD");
    }

    @Test(expected = IOException.class)
    public void testSendOutOfRangeLowResponse() throws IOException, CommandException
    {
        test(false, false, "-3");
    }

    private void test(boolean force, boolean forget, String response) throws IOException,
                                                                     CommandException
    {
        setupMocks(force, forget, response);

        Command63DeleteRecording command = getCommand(force, forget);
        try
        {
            command.send(socketManager);
        }
        finally
        {
            verify();
        }
    }

    private Command63DeleteRecording getCommand(boolean force, boolean forget)
    {
        return new Command63DeleteRecording(null, null, CHANNEL, REC_START, force, forget);
    }

    private void setupMocks(boolean force, boolean forget, String response) throws IOException
    {
        /*
         * Building the message.
         */
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE_RECORDING ");
        builder.append(CHANNEL.getId());
        builder.append(' ');
        builder.append(DateUtils.getMySqlDateFormat().format(REC_START));
        builder.append(' ');
        builder.append(force ? "FORCE" : "NO_FORCE");
        builder.append(' ');
        builder.append(forget ? "FORGET" : "NO_FORGET");

        String combined = builder.toString();

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
