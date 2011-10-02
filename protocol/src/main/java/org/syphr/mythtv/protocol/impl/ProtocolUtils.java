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

import org.apache.commons.lang3.tuple.Pair;

/**
 * This class provides a set of common utilities that are useful for protocol
 * implementations. It is not meant to be used by clients of the protocol API.
 * 
 * @author Gregory P. Moyer
 */
public class ProtocolUtils
{
    /**
     * Combine two numbers that represent the high and low bits of a 64-bit
     * number.
     * 
     * @see #splitLong(long)
     * 
     * @param high
     *            the high 32 bits
     * @param low
     *            the low 32 bits
     * @return a 64 bit number comprised of the given high and low bits
     *         concatenated together
     * @throws NumberFormatException
     *             if the strings cannot be parsed as integers
     */
    public static long combineInts(String high, String low) throws NumberFormatException
    {
        int highInt = Integer.parseInt(high);
        int lowInt = Integer.parseInt(low);

        /*
         * Shift the high integer into the upper 32 bits and add the low
         * integer. However, since this is really a single set of bits split in
         * half, the low part cannot be treated as negative by itself. Since
         * Java has no unsigned types, the top half of the long created by
         * up-casting the lower integer must be zeroed out before it's added.
         */
        return ((long)highInt << 32) + (lowInt & 0x00000000FFFFFFFFL);
    }

    /**
     * Split a single 64 bit number into integers representing the high and low
     * 32 bits.
     * 
     * @see #combineInts(String, String)
     * 
     * @param value
     *            the value to split
     * @return a pair of integers where the left is the high 32 bits and the
     *         right is the low 32 bits, represented as strings
     */
    public static Pair<String, String> splitLong(long value)
    {
        return Pair.of(String.valueOf((int)(value >> 32)), String.valueOf((int)value));
    }

    private ProtocolUtils()
    {
        /*
         * Static utility class
         */
    }
}
