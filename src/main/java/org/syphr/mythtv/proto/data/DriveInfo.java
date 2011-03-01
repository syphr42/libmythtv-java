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

import java.io.File;

public class DriveInfo
{
    private final String hostname;
    private final File driveRoot;
    private final boolean local;
    private final long driveNumber;
    private final long storageGroupId;
    private final long blockSize;
    private final long totalSpace;
    private final long usedSpace;

    public DriveInfo(String hostname,
                     File driveRoot,
                     boolean local,
                     long driveNumber,
                     long storageGroupId,
                     long blockSize,
                     long totalSpace,
                     long usedSpace)
    {
        this.hostname = hostname;
        this.driveRoot = driveRoot;
        this.local = local;
        this.driveNumber = driveNumber;
        this.storageGroupId = storageGroupId;
        this.blockSize = blockSize;
        this.totalSpace = totalSpace;
        this.usedSpace = usedSpace;
    }

    public String getHostname()
    {
        return hostname;
    }

    public File getDriveRoot()
    {
        return driveRoot;
    }

    public boolean isLocal()
    {
        return local;
    }

    public long getDriveNumber()
    {
        return driveNumber;
    }

    public long getStorageGroupId()
    {
        return storageGroupId;
    }

    public long getBlockSize()
    {
        return blockSize;
    }

    public long getTotalSpace()
    {
        return totalSpace;
    }

    public long getUsedSpace()
    {
        return usedSpace;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("DriveInfo [hostname=");
        builder.append(hostname);
        builder.append(", driveRoot=");
        builder.append(driveRoot);
        builder.append(", local=");
        builder.append(local);
        builder.append(", driveNumber=");
        builder.append(driveNumber);
        builder.append(", storageGroupId=");
        builder.append(storageGroupId);
        builder.append(", blockSize=");
        builder.append(blockSize);
        builder.append("B, totalSpace=");
        builder.append(totalSpace);
        builder.append("K, usedSpace=");
        builder.append(usedSpace);
        builder.append("K]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                 * result
                 + ((driveRoot == null) ? 0 : driveRoot.hashCode());
        result = prime
                 * result
                 + ((hostname == null) ? 0 : hostname.hashCode());
        result = prime
                 * result
                 + (int) (storageGroupId ^ (storageGroupId >>> 32));
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
        if (!(obj instanceof DriveInfo))
        {
            return false;
        }
        DriveInfo other = (DriveInfo) obj;
        if (driveRoot == null)
        {
            if (other.driveRoot != null)
            {
                return false;
            }
        }
        else if (!driveRoot.equals(other.driveRoot))
        {
            return false;
        }
        if (hostname == null)
        {
            if (other.hostname != null)
            {
                return false;
            }
        }
        else if (!hostname.equals(other.hostname))
        {
            return false;
        }
        if (storageGroupId != other.storageGroupId)
        {
            return false;
        }
        return true;
    }
}
