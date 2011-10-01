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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.util.exception.CommandException;

public class Command63QueryBookmarkTest extends AbstractProtocolTest
{
    private static final Channel CHANNEL = new Channel(1);
    private static final Date REC_START = new Date();

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
        String highBits = "1";
        String lowBits = "2";

        Long expected = ProtocolUtils.combineInts(highBits, lowBits);
        Long actual = test(Arrays.asList(new String[] { highBits, lowBits }));

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        test(Arrays.asList(new String[] { "BAD" }));
    }

    @Test(expected = IOException.class)
    public void testSendFirstArgWrong() throws IOException
    {
        test(Arrays.asList(new String[] { "BAD", "2" }));
    }

    @Test(expected = IOException.class)
    public void testSendSecondArgWrong() throws IOException
    {
        test(Arrays.asList(new String[] { "1", "BAD" }));
    }

    private Long test(List<String> response) throws IOException
    {
        setupMocks(response);

        Command63QueryBookmark command = getCommand();
        try
        {
            return command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63QueryBookmark getCommand()
    {
        return new Command63QueryBookmark(null, parser, CHANNEL, REC_START);
    }

    private void setupMocks(List<String> response) throws IOException
    {
        /*
         * Building the message.
         */
        StringBuilder builder = new StringBuilder();
        builder.append("QUERY_BOOKMARK ");
        builder.append(CHANNEL.getId());
        builder.append(' ');
        builder.append(TimeUnit.MILLISECONDS.toSeconds(REC_START.getTime()));

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
