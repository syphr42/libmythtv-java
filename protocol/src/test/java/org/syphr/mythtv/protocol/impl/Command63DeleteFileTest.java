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
import java.net.URI;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;

public class Command63DeleteFileTest extends AbstractProtocolTest
{
    private static final URI FILE_PATH = URI.create("uri");
    private static final String STORAGE_GROUP = "storageGroup";

    private Parser parser;

    @Override
    public void setUp()
    {
        super.setUp();

        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccessTrue() throws IOException
    {
        Boolean actual = test("1");
        Assert.assertEquals(Boolean.TRUE, actual);
    }

    @Test
    public void testSendSuccessFalse() throws IOException
    {
        Boolean actual = test("0");
        Assert.assertEquals(Boolean.FALSE, actual);
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        test("BAD");
    }

    private Boolean test(String response) throws IOException
    {
        setupMocks(response);

        Command63DeleteFile command = getCommand();
        try
        {
            return command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63DeleteFile getCommand()
    {
        return new Command63DeleteFile(null, parser, FILE_PATH, STORAGE_GROUP);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments("DELETE_FILE", FILE_PATH.toString(), STORAGE_GROUP)).andReturn(combined);

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
