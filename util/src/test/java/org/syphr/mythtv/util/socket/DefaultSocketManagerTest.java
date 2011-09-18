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
package org.syphr.mythtv.util.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.util.exception.ResponseTimeoutException;

public class DefaultSocketManagerTest
{
    private static final String FIRST_RESPONSE = "first response";
    private static final String SECOND_RESPONSE = "second response";

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSocketManagerTest.class);

    private static ServerSocket server;
    private static ConnectionHandler handler;

    private DefaultSocketManager socketManager;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        Settings.createSettings();

        server = new ServerSocket(0);

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    while (true)
                    {
                        Socket client = server.accept();
                        LOGGER.info("Client connected");

                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter writer = new PrintWriter(client.getOutputStream());

                        while (client.isConnected())
                        {
                            String input = reader.readLine();
                            if (input == null)
                            {
                                break;
                            }
                            LOGGER.trace("Server received input: " + input);

                            String output = handler.handle(input);
                            LOGGER.trace("Server sending output: " + output);
                            writer.print(output + "\n");
                            writer.flush();
                        }

                        LOGGER.info("Client disconnected");
                    }
                }
                catch (SocketException e)
                {
                    // time to quit
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        server.close();
    }

    @Before
    public void setUp() throws IOException
    {
        socketManager = new DefaultSocketManager(new DefaultPacket());
        socketManager.connect(server.getInetAddress().getHostName(), server.getLocalPort(), 0);
    }

    @After
    public void tearDown()
    {
        socketManager.disconnect();
        handler = null;
    }

    @Test
    public void testSendAndWaitTimeout() throws IOException
    {
        final AtomicInteger messageCounter = new AtomicInteger();
        handler = new ConnectionHandler()
        {
            @Override
            public String handle(String input)
            {
                switch (messageCounter.getAndIncrement())
                {
                    case 0:
                        try
                        {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        catch (InterruptedException e)
                        {
                            // ignore
                        }
                        return FIRST_RESPONSE;

                    case 1:
                        return SECOND_RESPONSE;

                    default:
                        throw new IllegalStateException("Too many messages received");
                }
            }
        };

        try
        {
            socketManager.sendAndWait("first message", 10, TimeUnit.MILLISECONDS);
            Assert.fail();
        }
        catch (ResponseTimeoutException e)
        {
            // expected
            LOGGER.info("Timeout {}", e.getMessage());
        }

        String secondResponse = socketManager.sendAndWait("second message", 3, TimeUnit.SECONDS);
        Assert.assertEquals(SECOND_RESPONSE, secondResponse);
    }

    private static interface ConnectionHandler
    {
        public String handle(String input);
    }

}
