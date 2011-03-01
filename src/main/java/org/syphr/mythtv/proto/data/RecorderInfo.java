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
package org.syphr.mythtv.proto.data;

public class RecorderInfo
{
    private final int id;

    private final String host;
    private final int port;

    public RecorderInfo(int id, String host, int port)
    {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public int getId()
    {
        return id;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public boolean isRecorderValid()
    {
        return id > 0;
    }

    public boolean isLocationValid()
    {
        return host != null
               && !host.isEmpty()
               && port > 0
               && port < Math.pow(2, 16);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RecorderInfo [id=");
        builder.append(id);
        builder.append(", host=");
        builder.append(host);
        builder.append(", port=");
        builder.append(port);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        if (!(obj instanceof RecorderInfo))
        {
            return false;
        }
        RecorderInfo other = (RecorderInfo) obj;
        if (id != other.id)
        {
            return false;
        }
        return true;
    }
}
