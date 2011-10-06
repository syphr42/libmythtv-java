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
package org.syphr.mythtv.api;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.Load;
import org.syphr.mythtv.data.MemStats;
import org.syphr.mythtv.data.TimeInfo;
import org.syphr.mythtv.protocol.Protocol;

public class ServerInfo
{
    private final String hostname;
    private final Load load;
    private final MemStats memory;
    private final TimeInfo time;
    private final long uptime;
    private final List<DriveInfo> drives;

    public ServerInfo(Protocol protocol) throws IOException
    {
        hostname = protocol.queryHostname();
        load = protocol.queryLoad();
        memory = protocol.queryMemStats();
        time = protocol.queryTimeZone();
        uptime = protocol.queryUptime();
        drives = protocol.queryFreeSpace();
    }

    public String getHostname()
    {
        return hostname;
    }

    public Load getLoad()
    {
        return load;
    }

    public MemStats getMemory()
    {
        return memory;
    }

    public TimeInfo getTime()
    {
        return time;
    }

    public long getUptime(TimeUnit unit)
    {
        return unit.convert(uptime, TimeUnit.SECONDS);
    }

    public List<DriveInfo> getDrives()
    {
        return Collections.unmodifiableList(drives);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ServerInfo [hostname=");
        builder.append(hostname);
        builder.append(", load=");
        builder.append(load);
        builder.append(", memory=");
        builder.append(memory);
        builder.append(", time=");
        builder.append(time);
        builder.append(", uptime=");
        builder.append(uptime);
        builder.append(", drives=");
        builder.append(drives);
        builder.append("]");
        return builder.toString();
    }
}
