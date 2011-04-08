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

import java.io.IOException;

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;

public class CommandUtils
{
    /**
     * Send a message via the given socket manager which should always receive a case
     * insensitive "OK" as the reply.
     *
     * @param socketManager
     *            the socket manager that will be used to send and receive over the
     *            network
     * @param message
     *            the message to send
     * @throws IOException
     *             if there is a communication error or an unexpected response
     */
    public static void sendExpectOk(SocketManager socketManager, String message) throws IOException
    {
        expectOk(socketManager.sendAndWait(message));
    }

    /**
     * Check the response for an "OK" message. Throw an exception if response is not
     * expected.
     *
     * @param response
     *            the response to check
     * @throws ProtocolException
     *             if the response is not the expected case-insensitive "OK"
     */
    public static void expectOk(String response) throws ProtocolException
    {
        if (!"OK".equalsIgnoreCase(response))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }
    }

    private CommandUtils()
    {
        /*
         * Static utility class
         */
    }
}
