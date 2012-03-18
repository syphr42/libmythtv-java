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
package org.syphr.mythtv.ws.backend.impl;

import java.util.Collections;
import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.ChannelInfo;
import org.syphr.mythtv.data.Lineup;
import org.syphr.mythtv.data.VideoSource;
import org.syphr.mythtv.ws.backend.impl._0_25.channel.VideoMultiplex;

public class ChannelService0_24 extends AbstractChannelService
{
    @Override
    public boolean addDBChannel(Channel channel)
    {
        handleUnsupported("add DB channel");
        return false;
    }

    @Override
    public Integer addVideoSource(VideoSource videoSource)
    {
        handleUnsupported("add video source");
        return null;
    }

    @Override
    public Integer fetchChannelsFromSource(long sourceId, long cardId, boolean waitForFinish)
    {
        handleUnsupported("fetch channels from source");
        return null;
    }

    @Override
    public ChannelInfo getChannelInfo(int channelId)
    {
        handleUnsupported("get channel info");
        return null;
    }

    @Override
    public List<ChannelInfo> getChannelInfoList(int sourceId, int startIndex, int count)
    {
        handleUnsupported("get channel info list");
        return Collections.emptyList();
    }

    @Override
    public List<Lineup> getDDLineupList(String source, String userId, String password)
    {
        handleUnsupported("get DD lineup list");
        return Collections.emptyList();
    }

    @Override
    public VideoMultiplex getVideoMultiplex(int mplexId)
    {
        handleUnsupported("get video multiplex");
        return null;
    }

    @Override
    public List<VideoMultiplex> getVideoMultiplexList(int sourceId, int startIndex, int count)
    {
        handleUnsupported("get video multiplex list");
        return Collections.emptyList();
    }

    @Override
    public VideoSource getVideoSource(long sourceId)
    {
        handleUnsupported("get video source");
        return null;
    }

    @Override
    public List<VideoSource> getVideoSourceList()
    {
        handleUnsupported("get video sources list");
        return Collections.emptyList();
    }

    @Override
    public List<String> getXMLTVIdList(int sourceId)
    {
        handleUnsupported("get XMLTV ID list");
        return Collections.emptyList();
    }

    @Override
    public boolean removeDBChannel(long channelId)
    {
        handleUnsupported("remove DB channel");
        return false;
    }

    @Override
    public boolean removeVideoSource(long sourceId)
    {
        handleUnsupported("remove video source");
        return false;
    }

    @Override
    public boolean updateDBChannel(Channel channel)
    {
        handleUnsupported("update DB channel");
        return false;
    }

    @Override
    public boolean updateVideoSource(VideoSource videoSource)
    {
        handleUnsupported("update video source");
        return false;
    }

    @Override
    protected String getVersion()
    {
        return null;
    }
}