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
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.data.RecorderDevice;
import org.syphr.mythtv.util.exception.CommandException;

public class Command63LockTunerTest extends AbstractProtocolTest
{
    private Parser parser;

    @Override
    public void setUp()
    {
        super.setUp();

        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccessTuner() throws IOException, CommandException
    {
        RecorderDevice expected = new RecorderDevice(1, "VIDEO", "AUDIO", "VBI");
        RecorderDevice actual = test(expected.getId(),
                                     Arrays.asList(new String[] { String.valueOf(expected.getId()),
                                                                 expected.getVideo(),
                                                                 expected.getAudio(),
                                                                 expected.getVbi() }));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSendSuccessNoTuner() throws IOException, CommandException
    {
        RecorderDevice expected = new RecorderDevice(1, "VIDEO", "AUDIO", "VBI");
        RecorderDevice actual = test(0,
                                     Arrays.asList(new String[] { String.valueOf(expected.getId()),
                                                                 expected.getVideo(),
                                                                 expected.getAudio(),
                                                                 expected.getVbi() }));

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = CommandException.class)
    public void testSendFailure1() throws IOException, CommandException
    {
        test(1, Arrays.asList(new String[] { "-1" }));
    }

    @Test(expected = CommandException.class)
    public void testSendFailure2() throws IOException, CommandException
    {
        test(1, Arrays.asList(new String[] { "-2" }));
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException, CommandException
    {
        test(1, Arrays.asList(new String[] { "BAD" }));
    }

    @Test(expected = IOException.class)
    public void testSendEmptyResponse() throws IOException, CommandException
    {
        test(1, Arrays.asList(new String[0]));
    }

    @Test(expected = IOException.class)
    public void testSendTooFewArgs() throws IOException, CommandException
    {
        test(1, Arrays.asList(new String[] { "1" }));
    }

    private RecorderDevice test(int recorderId, List<String> response) throws IOException,
                                                                      CommandException
    {
        setupMocks(recorderId, response);

        Command63LockTuner command = getCommand(recorderId);
        try
        {
            return command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63LockTuner getCommand(int recorderId)
    {
        return new Command63LockTuner(null, parser, recorderId);
    }

    private void setupMocks(int recorderId, List<String> response) throws IOException
    {
        /*
         * Building the message.
         */
        StringBuilder builder = new StringBuilder();
        builder.append("LOCK_TUNER");

        if (recorderId > 0)
        {
            builder.append(' ');
            builder.append(recorderId);
        }

        String combined = builder.toString();

        /*
         * Sending the message.
         */
        String serverResponse = "SERVER_RESPONSE";
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(serverResponse);

        /*
         * Parsing the response.
         */
        EasyMock.expect(parser.splitArguments(serverResponse)).andReturn(response);

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
