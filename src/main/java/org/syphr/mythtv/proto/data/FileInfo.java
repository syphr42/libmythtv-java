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

import java.util.Date;

public class FileInfo
{
    private final Date lastModified;
    private final long size;

    public FileInfo(Date lastModified, long size)
    {
        this.lastModified = new Date(lastModified.getTime());
        this.size = size;
    }

    public Date getLastModified()
    {
        return new Date(lastModified.getTime());
    }

    public long getSize()
    {
        return size;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("FileInfo [lastModified=");
        builder.append(lastModified);
        builder.append(", size=");
        builder.append(size);
        builder.append("]");
        return builder.toString();
    }
}
