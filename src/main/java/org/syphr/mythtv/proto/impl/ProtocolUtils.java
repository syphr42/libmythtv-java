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
package org.syphr.mythtv.proto.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;
import org.syphr.mythtv.proto.SocketManager;

public class ProtocolUtils
{
    public static <V, T> T translate(V value, Map<V, T> map, Direction direction) throws ProtocolException
    {
        T translated = map.get(value);
        if (translated == null)
        {
            throw new ProtocolException("Invalid value: " + value, direction);
        }

        return translated;
    }

    public static <T> Set<T> translateMultiple(String value, Map<String, T> map) throws ProtocolException
    {
        try
        {
            long longValue = Long.parseLong(value);

            Set<T> set = new HashSet<T>();

            for (Entry<String, T> entry : map.entrySet())
            {
                long longEntry = Long.parseLong(entry.getKey());

                if ((longValue & longEntry) > 0)
                {
                    set.add(entry.getValue());
                }
            }

            return set;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException("Invalid value: " + value, Direction.RECEIVE, e);
        }
    }

    public static <T> String translateMultiple(Collection<T> values, Map<T, String> map) throws ProtocolException
    {
        try
        {
            long result = 0;

            for (T value : values)
            {
                String translated = map.get(value);
                if (translated == null)
                {
                    throw new ProtocolException("Invalid value: " + value, Direction.SEND);
                }

                long longTranslated = Long.parseLong(translated);
                result |= longTranslated;
            }

            return result == 0 ? "" : String.valueOf(result);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException("Invalid values: " + values, Direction.RECEIVE, e);
        }
    }

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
