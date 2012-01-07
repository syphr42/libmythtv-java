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
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import org.syphr.mythtv.data.Program;

public class Command63AnnSlaveBackendTest extends AbstractProtocolTest
{
    private static final Program PROGRAM = new Program(null, null);

    private Parser parser;
    private InetAddress host;

    @Override
    public void setUp()
    {
        super.setUp();

        parser = EasyMock.createMock(Parser.class);
        host = EasyMock.createMock(InetAddress.class);
    }

    @Test
    public void testSendSuccess() throws IOException
    {
        test("OK");
    }

    @Test(expected = IOException.class)
    public void testSendBadResponse() throws IOException
    {
        test("BAD");
    }

    private void test(String response) throws IOException
    {
        setupMocks(response);

        Command63AnnSlaveBackend command = getCommand();
        try
        {
            command.send(getSocketManager());
        }
        finally
        {
            verify();
        }
    }

    private Command63AnnSlaveBackend getCommand()
    {
        return new Command63AnnSlaveBackend(null, parser, host, PROGRAM);
    }

    private void setupMocks(String response) throws IOException
    {
        /*
         * Building the message.
         */
        String hostName = "HOSTNAME";
        String hostAddress = "HOSTADDRESS";
        List<String> programInfo = Arrays.asList(new String[] { "PROGRAM" });
        EasyMock.expect(host.getHostName()).andReturn(hostName);
        EasyMock.expect(host.getHostAddress()).andReturn(hostAddress);
        EasyMock.expect(parser.extractProgramInfo(PROGRAM)).andReturn(programInfo);

        StringBuilder builder = new StringBuilder();
        builder.append("ANN SlaveBackend ");
        builder.append(hostName);
        builder.append(' ');
        builder.append(hostAddress);

        List<String> args = new ArrayList<String>();
        args.add(builder.toString());
        args.addAll(programInfo);

        String combined = "COMBINED";
        EasyMock.expect(parser.combineArguments(args)).andReturn(combined);

        /*
         * Sending the message.
         */
        EasyMock.expect(getSocketManager().sendAndWait(combined)).andReturn(response);

        /*
         * Replay.
         */
        EasyMock.replay(getSocketManager(), parser, host);
    }

    @Override
    protected void verify()
    {
        super.verify();

        EasyMock.verify(parser, host);
    }
}
