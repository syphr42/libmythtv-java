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
package org.syphr.mythtv.test;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils
{
    private static Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static <T> void printFirstFive(Iterable<T> iterable)
    {
        int counter = 0;
        Iterator<T> iter = iterable.iterator();

        if (!iter.hasNext())
        {
            LOGGER.debug("No values to display.");
            return;
        }

        LOGGER.debug("(Up to) the first five:");

        while (iter.hasNext() && counter < 5)
        {
            LOGGER.debug(String.valueOf(iter.next()));
            counter++;
        }
    }

    public static void waitSeconds(int seconds, String message)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e)
        {
            LOGGER.warn("Interrupted while waiting for frontend to " + message, e);
        }
    }
}
