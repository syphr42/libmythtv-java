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
package org.syphr.mythtv.data;

import java.util.Date;

public class PixMap
{
    private final long lastModified;
    private final int size;
    private final String checksum;
    private final byte[] data;

    public PixMap(Date lastModified)
    {
        this(lastModified, -1, null, null);
    }

    public PixMap(Date lastModified, int size, String checksum, byte[] data)
    {
        this.lastModified = lastModified.getTime();
        this.size = size;
        this.checksum = checksum;
        this.data = data != null ? data.clone() : null;
    }

    public Date getLastModified()
    {
        return new Date(lastModified);
    }

    public int getSize()
    {
        return size;
    }

    public String getChecksum()
    {
        return checksum;
    }

    public byte[] getData()
    {
        return data.clone();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PixMap [lastModified=");
        builder.append(lastModified);
        builder.append(", size=");
        builder.append(size);
        builder.append(", checksum=");
        builder.append(checksum);
        builder.append("]");
        return builder.toString();
    }
}
