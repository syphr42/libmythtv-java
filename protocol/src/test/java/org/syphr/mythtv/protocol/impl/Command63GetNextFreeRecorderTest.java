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
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.data.RecorderLocation;

public class Command63GetNextFreeRecorderTest extends AbstractProtocolTest
{
    private static final RecorderLocation FROM = new RecorderLocation(1, "HOST", 1);

    private Parser parser;

    @Override
    public void setUp()
    {
        super.setUp();

        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        RecorderLocation expected = new RecorderLocation(1, "HOST", 2);

        List<String> response = new ArrayList<String>();
        response.add(String.valueOf(expected.getId()));
        response.add(expected.getHost());
        response.add(String.valueOf(expected.getPort()));

        Assert.assertEquals(expected, test(response));
    }

    @Test
    public void testSendFailure() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("-1");
        response.add("");
        response.add("");

        Assert.assertNull(test(response));
    }

    @Test(expected = IOException.class)
    public void testSendEmptyResponse() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("");

        test(response);
    }

    @Test(expected = IOException.class)
    public void testSendTooManyArgs() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("");
        response.add("");
        response.add("");
        response.add("");

        test(response);
    }

    @Test(expected = IOException.class)
    public void testSendFirstArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("BAD");
        response.add("HOST");
        response.add("2");

        test(response);
    }

    @Test(expected = IOException.class)
    public void testSendThirdArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("1");
        response.add("HOST");
        response.add("BAD");

        test(response);
    }

    private RecorderLocation test(List<String> response) throws IOException
    {
        setupMocks(response);

        Command63GetNextFreeRecorder command = getCommand();
        try
        {
            return command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63GetNextFreeRecorder getCommand()
    {
        return new Command63GetNextFreeRecorder(null, parser, FROM);
    }

    private void setupMocks(List<String> response) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments("GET_NEXT_FREE_RECORDER",
                                                String.valueOf(FROM.getId()))).andReturn(combined);

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
