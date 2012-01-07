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
package org.syphr.mythtv.api.frontend;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.control.Control;
import org.syphr.mythtv.data.Load;
import org.syphr.mythtv.data.MemStats;

public class ServerInfo
{
    private final Load load;
    private final MemStats memory;
    private final Date time;
    private final long uptime;

    public ServerInfo(Control control) throws IOException
    {
        load = control.queryLoad();
        memory = control.queryMemStats();
        time = control.queryTime();
        uptime = control.queryUptime();
    }

    public Load getLoad()
    {
        return load;
    }

    public MemStats getMemory()
    {
        return memory;
    }

    public Date getTime()
    {
        return time;
    }

    public long getUptime(TimeUnit unit)
    {
        return unit.convert(uptime, TimeUnit.SECONDS);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ServerInfo [load=");
        builder.append(load);
        builder.append(", memory=");
        builder.append(memory);
        builder.append(", time=");
        builder.append(time);
        builder.append(", uptime=");
        builder.append(uptime);
        builder.append("]");
        return builder.toString();
    }
}
