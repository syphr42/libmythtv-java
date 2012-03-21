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
package org.syphr.mythtv.ws.data;

import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;

public class ChannelInfo
{
    private Channel channel;
    private List<Program> programs;

    public Channel getChannel()
    {
        return channel;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public List<Program> getPrograms()
    {
        if (programs == null)
        {
            programs = new ArrayList<Program>();
        }

        return programs;
    }

    @Override
    public String toString()
    {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelInfo [channel=");
        builder.append(channel);
        builder.append(", programs=");
        builder.append(programs != null
                ? programs.subList(0, Math.min(programs.size(), maxLen))
                : null);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((channel == null) ? 0 : channel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof ChannelInfo))
        {
            return false;
        }
        ChannelInfo other = (ChannelInfo)obj;
        if (channel == null)
        {
            if (other.channel != null)
            {
                return false;
            }
        }
        else if (!channel.equals(other.channel))
        {
            return false;
        }
        return true;
    }
}
