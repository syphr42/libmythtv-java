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
import java.util.Date;
import java.util.List;

public class ChannelInfoList
{
    private Integer startIndex;
    private Integer count;
    private Integer currentPage;
    private Integer totalPages;
    private Integer totalAvailable;
    private Date asOf;
    private String version;
    private String protoVer;

    private List<ChannelInfo> channelInfos;

    public Integer getStartIndex()
    {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex)
    {
        this.startIndex = startIndex;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Integer getCurrentPage()
    {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage)
    {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages)
    {
        this.totalPages = totalPages;
    }

    public Integer getTotalAvailable()
    {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable)
    {
        this.totalAvailable = totalAvailable;
    }

    public Date getAsOf()
    {
        return asOf;
    }

    public void setAsOf(Date asOf)
    {
        this.asOf = asOf;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getProtoVer()
    {
        return protoVer;
    }

    public void setProtoVer(String protoVer)
    {
        this.protoVer = protoVer;
    }

    public List<ChannelInfo> getChannelInfos()
    {
        if (channelInfos == null)
        {
            channelInfos = new ArrayList<ChannelInfo>();
        }

        return channelInfos;
    }

    @Override
    public String toString()
    {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelInfoContainer [startIndex=");
        builder.append(startIndex);
        builder.append(", count=");
        builder.append(count);
        builder.append(", currentPage=");
        builder.append(currentPage);
        builder.append(", totalPages=");
        builder.append(totalPages);
        builder.append(", totalAvailable=");
        builder.append(totalAvailable);
        builder.append(", asOf=");
        builder.append(asOf);
        builder.append(", version=");
        builder.append(version);
        builder.append(", protoVer=");
        builder.append(protoVer);
        builder.append(", channelInfos=");
        builder.append(channelInfos != null ? channelInfos.subList(0, Math.min(channelInfos.size(),
                                                                               maxLen)) : null);
        builder.append("]");
        return builder.toString();
    }
}
