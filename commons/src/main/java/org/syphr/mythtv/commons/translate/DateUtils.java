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
package org.syphr.mythtv.commons.translate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class DateUtils
{
    private static final Pattern ISO_DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");

    private static final Pattern MYSQL_DATE_PATTERN = Pattern.compile("\\d{14}");

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
     * Retrieve the regular expression pattern for the common ISO date format.
     * 
     * @return the date pattern
     */
    public static Pattern getIsoDatePattern()
    {
        return ISO_DATE_PATTERN;
    }

    /**
     * Retrieve the regular expression pattern for the MySQL standard date
     * format.
     * 
     * @return the date pattern
     */
    public static Pattern getMySqlDatePattern()
    {
        return MYSQL_DATE_PATTERN;
    }

    public static Date toUtc(Date date)
    {
        if (date == null)
        {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MILLISECOND, -TimeZone.getDefault().getOffset(date.getTime()));

        return cal.getTime();
    }

    public static Date toLocal(Date date)
    {
        if (date == null)
        {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(date.getTime()));

        return cal.getTime();
    }

    private DateUtils()
    {
        /*
         * Static utility class
         */
    }
}
