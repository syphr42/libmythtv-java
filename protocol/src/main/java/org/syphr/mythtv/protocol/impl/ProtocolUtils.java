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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.ProtocolException.Direction;
import org.syphr.mythtv.protocol.SocketManager;

/**
 * This class provides a set of common utilities that are useful for protocol
 * implementations. It is not meant to be used by clients of the protocol API.
 *
 * @author Gregory P. Moyer
 */
public class ProtocolUtils
{
    /**
     * Retrieve the common ISO date format.
     *
     * @return the date format
     */
    public static DateFormat getIsoDateFormat()
    {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    /**
     * Retrieve MySQL's standard date format.
     *
     * @return the date format
     */
    public static DateFormat getMySqlDateFormat()
    {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    /**
     * Combine two numbers that represent the high and low bits of a 64-bit number.
     *
     * @see #splitLong(long)
     *
     * @param high
     *            the high 32 bits
     * @param low
     *            the low 32 bits
     * @return a 64 bit number comprised of the given high and low bits concatenated
     *         together
     * @throws NumberFormatException
     *             if the strings cannot be parsed as integers
     */
    public static long combineInts(String high, String low) throws NumberFormatException
    {
        int highInt = Integer.parseInt(high);
        int lowInt = Integer.parseInt(low);

        return ((long)highInt << 32) + lowInt;
    }

    /**
     * Split a single 64 bit number into integers representing the high and low 32 bits.
     *
     * @see #combineInts(String, String)
     *
     * @param value
     *            the value to split
     * @return a pair of integers where the left is the high 32 bits and the right is the
     *         low 32 bits, represented as strings
     */
    public static Pair<String, String> splitLong(long value)
    {
        return Pair.of(String.valueOf((int)(value >> 32)), String.valueOf((int)value));
    }

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

    private ProtocolUtils()
    {
        /*
         * Static utility class
         */
    }
}
