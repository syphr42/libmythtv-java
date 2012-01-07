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
package org.syphr.mythtv.data;

import java.util.Date;
import java.util.TimeZone;

public class TimeInfo
{
    private final Date dateTime;
    private final TimeZone timeZone;

    public TimeInfo(Date dateTime, TimeZone timeZone)
    {
        this.dateTime = new Date(dateTime.getTime());
        this.timeZone = timeZone;
    }

    public Date getDateTime()
    {
        return new Date(dateTime.getTime());
    }

    public TimeZone getTimeZone()
    {
        return timeZone;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TimeInfo [dateTime=");
        builder.append(dateTime);
        builder.append(", timeZone=");
        builder.append(timeZone);
        builder.append("]");
        return builder.toString();
    }
}
