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
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.ProtocolException.Direction;

public class ProtocolUtils
{
    public static DateFormat getIsoDateFormat()
    {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    public static DateFormat getMySqlDateFormat()
    {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static long combineInts(int high, int low)
    {
        return ((long)high << 32) + low;
    }

    public static Pair<Integer, Integer> splitLong(long value)
    {
        return Pair.of((int)(value >> 32), (int)value);
    }

    public static void sendExpectOk(SocketManager socketManager, String message) throws IOException
    {
        String response = socketManager.sendAndWait(message);

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