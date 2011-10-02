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

import junit.framework.Assert;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class ProtocolUtilsTest
{
    @Test
    public void testSplitAndCombineNoNegatives()
    {
        test(0x0FFFFFFF0FFFFFFFL);
    }

    @Test
    public void testSplitAndCombineHighNegative()
    {
        test(0xFFFFFFFF0FFFFFFFL);
    }

    @Test
    public void testSplitAndCombineLowNegative()
    {
        test(0x0FFFFFFFFFFFFFFFL);
    }

    @Test
    public void testSplitAndCombineBothNegatives()
    {
        test(0xFFFFFFFFFFFFFFFFL);
    }

    private void test(long value)
    {
        Pair<String, String> split = ProtocolUtils.splitLong(value);
        Assert.assertEquals(value, ProtocolUtils.combineInts(split.getLeft(), split.getRight()));
    }
}
