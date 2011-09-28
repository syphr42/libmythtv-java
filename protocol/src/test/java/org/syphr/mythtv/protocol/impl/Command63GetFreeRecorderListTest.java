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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.syphr.mythtv.util.socket.SocketManager;

public class Command63GetFreeRecorderListTest
{
    private SocketManager socketManager;
    private Parser parser;

    @Before
    public void setUp()
    {
        socketManager = EasyMock.createMock(SocketManager.class);
        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccessNoFree() throws IOException
    {
        List<Integer> expected = Collections.emptyList();
        List<Integer> actual = test("0", null);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSendSuccessWithFree() throws IOException
    {
        List<Integer> expected = Arrays.asList(new Integer[] { 1, 2 });
        List<Integer> actual = test(null, Arrays.asList(new String[] { "1", "2" }));

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IOException.class)
    public void testSendEmptyResponse() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("");

        test(null, response);
    }

    @Test(expected = IOException.class)
    public void testSendBadArg() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("1");
        response.add("2");
        response.add("BAD");

        test(null, response);
    }

    private List<Integer> test(String mainResponse, List<String> splitResponse) throws IOException
    {
        setupMocks(mainResponse, splitResponse);

        Command63GetFreeRecorderList command = getCommand();
        try
        {
            return command.send(socketManager);
        }
        finally
        {
            verify();
        }
    }

    private Command63GetFreeRecorderList getCommand()
    {
        return new Command63GetFreeRecorderList(null, parser);
    }

    private void setupMocks(String mainResponse, List<String> splitResponse) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "GET_FREE_RECORDER_LIST";

        /*
         * Sending the message.
         */
        EasyMock.expect(socketManager.sendAndWait(combined)).andReturn(mainResponse);

        /*
         * Parsing the response.
         */
        EasyMock.expect(parser.splitArguments(mainResponse)).andReturn(splitResponse).times(0, 1);

        /*
         * Replay.
         */
        EasyMock.replay(socketManager, parser);
    }

    private void verify()
    {
        EasyMock.verify(socketManager, parser);
    }
}
