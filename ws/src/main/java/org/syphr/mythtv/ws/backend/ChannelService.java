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
package org.syphr.mythtv.ws.backend;

import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.ChannelInfo;
import org.syphr.mythtv.data.Lineup;
import org.syphr.mythtv.data.VideoSource;
import org.syphr.mythtv.ws.backend.impl._0_25.channel.VideoMultiplex;

public interface ChannelService
{
    public boolean addDBChannel(Channel channel);

    public Integer addVideoSource(VideoSource videoSource);

    public Integer fetchChannelsFromSource(long sourceId, long cardId, boolean waitForFinish);

    public ChannelInfo getChannelInfo(int channelId);

    public List<ChannelInfo> getChannelInfoList(int sourceId, int startIndex, int count);

    public List<Lineup> getDDLineupList(String source, String userId, String password);

    public VideoMultiplex getVideoMultiplex(int mplexId);

    public List<VideoMultiplex> getVideoMultiplexList(int sourceId, int startIndex, int count);

    public VideoSource getVideoSource(long sourceId);

    public List<VideoSource> getVideoSourceList();

    public List<String> getXMLTVIdList(int sourceId);

    public boolean removeDBChannel(long channelId);

    public boolean removeVideoSource(long sourceId);

    public boolean updateDBChannel(Channel channel);

    public boolean updateVideoSource(VideoSource videoSource);
}