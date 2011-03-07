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
    private final String name;
    private final long deviceId;
    private final long inodeNum;
    private final long mode;
    private final long links;
    private final long userId;
    private final long groupId;
    private final long specialDeviceId;
    private final long size;
    private final long blockSize;
    private final long blocks;
    private final long accessedTime;
    private final long modifiedTime;
    private final long createdTime;

    public FileInfo(String name)
    {
        this(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, null);
    }

    public FileInfo(String name, long size, Date modifiedTime)
    {
        this(name, 0, 0, 0, 0, 0, 0, 0, size, 0, 0, null, modifiedTime, null);
    }

    public FileInfo(String name,
                    long deviceId,
                    long inodeNum,
                    long mode,
                    long links,
                    long userId,
                    long groupId,
                    long specialDeviceId,
                    long size,
                    long blockSize,
                    long blocks,
                    Date accessedTime,
                    Date modifiedTime,
                    Date createdTime)
    {
        this.name = name;
        this.deviceId = deviceId;
        this.inodeNum = inodeNum;
        this.mode = mode;
        this.links = links;
        this.userId = userId;
        this.groupId = groupId;
        this.specialDeviceId = specialDeviceId;
        this.size = size;
        this.blockSize = blockSize;
        this.blocks = blocks;
        this.accessedTime = accessedTime == null ? 0 : accessedTime.getTime();
        this.modifiedTime = modifiedTime == null ? 0 : modifiedTime.getTime();
        this.createdTime = createdTime == null ? 0 : createdTime.getTime();
    }

    public String getName()
    {
        return name;
    }

    public long getDeviceId()
    {
        return deviceId;
    }

    public long getInodeNum()
    {
        return inodeNum;
    }

    public long getMode()
    {
        return mode;
    }

    public long getLinks()
    {
        return links;
    }

    public long getUserId()
    {
        return userId;
    }

    public long getGroupId()
    {
        return groupId;
    }

    public long getSpecialDeviceId()
    {
        return specialDeviceId;
    }

    public long getSize()
    {
        return size;
    }

    public long getBlockSize()
    {
        return blockSize;
    }

    public long getBlocks()
    {
        return blocks;
    }

    public Date getAccessedTime()
    {
        return new Date(accessedTime);
    }

    public Date getModifiedTime()
    {
        return new Date(modifiedTime);
    }

    public Date getCreatedTime()
    {
        return new Date(createdTime);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("FileInfo [name=");
        builder.append(name);
        builder.append(", deviceId=");
        builder.append(deviceId);
        builder.append(", inodeNum=");
        builder.append(inodeNum);
        builder.append(", mode=");
        builder.append(mode);
        builder.append(", links=");
        builder.append(links);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", specialDeviceId=");
        builder.append(specialDeviceId);
        builder.append(", size=");
        builder.append(size);
        builder.append(", blockSize=");
        builder.append(blockSize);
        builder.append(", blocks=");
        builder.append(blocks);
        builder.append(", accessedTime=");
        builder.append(getAccessedTime());
        builder.append(", modifiedTime=");
        builder.append(getModifiedTime());
        builder.append(", createdTime=");
        builder.append(getCreatedTime());
        builder.append("]");
        return builder.toString();
    }
}
