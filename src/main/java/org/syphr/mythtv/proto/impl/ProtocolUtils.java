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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.proto.ProtocolException;

public class ProtocolUtils
{
    public static <K, V> V translate(K key, Map<K, V> map) throws ProtocolException
    {
        V translated = map.get(key);
        if (translated == null)
        {
            throw new ProtocolException("Invalid argument: " + key);
        }

        return translated;
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

    private ProtocolUtils()
    {
        /*
         * Static utility class
         */
    }
}
