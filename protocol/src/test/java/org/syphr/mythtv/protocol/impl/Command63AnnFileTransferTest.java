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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.util.translate.Translator;
import org.syphr.mythtv.util.unsupported.UnsupportedHandler;
import org.syphr.mythtv.util.unsupported.UnsupportedHandlerLog;

public class Command63AnnFileTransferTest extends AbstractProtocolTest
{
    private static final String HOST = "host";
    private static final FileTransferType TRANSFER_TYPE = FileTransferType.values()[0];
    private static final URI FILE_PATH = URI.create("uri");
    private static final String STORAGE_GROUP = "storageGroup";

    private static final UnsupportedHandler UNSUPPORTED_HANDLER = new UnsupportedHandlerLog();

    private Translator translator;
    private Parser parser;

    @Override
    public void setUp()
    {
        super.setUp();

        translator = EasyMock.createMock(Translator.class);
        parser = EasyMock.createMock(Parser.class);
    }

    @Test
    public void testSendSuccessReadAheadZeroTimeout() throws IOException
    {
        testSuccess(true, 0);
    }

    @Test
    public void testSendSuccessNoReadAheadZeroTimeout() throws IOException
    {
        testSuccess(false, 0);
    }

    @Test
    public void testSendSuccessReadAheadNonZeroTimeout() throws IOException
    {
        testSuccess(true, 1);
    }

    @Test
    public void testSendSuccessNoReadAheadNonZeroTimeout() throws IOException
    {
        testSuccess(false, 1);
    }

    @Test(expected = IOException.class)
    public void testSendEmptyResponse() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("");

        testFailure(response);
    }

    @Test(expected = IOException.class)
    public void testSendTooManyArgs() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("");
        response.add("");
        response.add("");
        response.add("");
        response.add("");

        testFailure(response);
    }

    @Test(expected = IOException.class)
    public void testSendFirstArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("BAD");
        response.add("1");
        response.add("2");
        response.add("3");

        testFailure(response);
    }

    @Test(expected = IOException.class)
    public void testSendSecondArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("BAD");
        response.add("2");
        response.add("3");

        testFailure(response);
    }

    @Test(expected = IOException.class)
    public void testSendThirdArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("1");
        response.add("BAD");
        response.add("3");

        testFailure(response);
    }

    @Test(expected = IOException.class)
    public void testSendFourthArgWrong() throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("1");
        response.add("2");
        response.add("BAD");

        testFailure(response);
    }

    private void testSuccess(boolean readAhead, long timeout) throws IOException
    {
        List<String> response = new ArrayList<String>();
        response.add("OK");
        response.add("1");
        response.add("2");
        response.add("3");
        setupMocks(readAhead, timeout, response);

        Command63AnnFileTransfer command = getCommand(readAhead, timeout);
        QueryFileTransfer result = command.send(getSocketManager());
        Assert.assertEquals(QueryFileTransfer63.class, result.getClass());

        verify();
    }

    private void testFailure(List<String> response) throws IOException
    {
        boolean readAhead = false;
        long timeout = 0;

        setupMocks(readAhead, timeout, response);

        Command63AnnFileTransfer command = getCommand(readAhead, timeout);
        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63AnnFileTransfer getCommand(boolean readAhead, long timeout)
    {
        return new Command63AnnFileTransfer(translator,
                                            parser,
                                            HOST,
                                            TRANSFER_TYPE,
                                            readAhead,
                                            timeout,
                                            FILE_PATH,
                                            STORAGE_GROUP,
                                            getSocketManager(),
                                            UNSUPPORTED_HANDLER);
    }

    private void setupMocks(boolean readAhead, long timeout, List<String> response) throws IOException
    {
        /*
         * Building the message.
         */
        String translated = "TRANSLATED";
        EasyMock.expect(translator.toString(EasyMock.isA(FileTransferType.class))).andReturn(translated);

        StringBuilder builder = new StringBuilder();
        builder.append("ANN FileTransfer ");
        builder.append(HOST);
        builder.append(' ');
        builder.append(translated);
        builder.append(' ');
        builder.append(readAhead ? 1 : 0);
        if (timeout > 0)
        {
            builder.append(' ');
            builder.append(timeout);
        }

        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments(builder.toString(),
                                                FILE_PATH.toString(),
                                                STORAGE_GROUP)).andReturn(combined);

        /*
         * Sending the message.
         */
        String serverResponse = "RESPONSE";
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(serverResponse);

        /*
         * Parsing the response.
         */
        EasyMock.expect(parser.splitArguments(serverResponse)).andReturn(response);

        /*
         * Replay.
         */
        EasyMock.replay(getSocketManager(), translator, parser);
    }

    @Override
    protected void verify()
    {
        super.verify();

        EasyMock.verify(translator, parser);
    }

}
