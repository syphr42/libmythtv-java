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

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.translate.Translator;

public class Command63MessageSetVerboseTest extends AbstractProtocolTest
{
    private final List<Verbose> OPTIONS = Arrays.asList(new Verbose[] { Verbose.ALL });

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
    public void testSendSuccess() throws IOException, CommandException
    {
        test("OK");
    }

    @Test(expected = CommandException.class)
    public void testSendFailed() throws IOException, CommandException
    {
        test("Failed");
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException, CommandException
    {
        test("BAD");
    }

    private void test(String response) throws IOException, CommandException
    {
        setupMocks(response);

        Command63MessageSetVerbose command = getCommand();
        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63MessageSetVerbose getCommand()
    {
        return new Command63MessageSetVerbose(translator, parser, OPTIONS);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String translated = "TRANSLATED";
        EasyMock.expect(translator.toString(OPTIONS, ",")).andReturn(translated);

        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments("MESSAGE", "SET_VERBOSE " + translated)).andReturn(combined);

        /*
         * Sending the message.
         */
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(response);

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
