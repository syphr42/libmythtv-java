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
package org.syphr.mythtv.http.backend;

import java.util.List;

import org.syphr.mythtv.http.backend.impl._0_25.channel.ChannelInfo;
import org.syphr.mythtv.http.backend.impl._0_25.channel.Lineup;
import org.syphr.mythtv.http.backend.impl._0_25.channel.VideoMultiplex;
import org.syphr.mythtv.http.backend.impl._0_25.channel.VideoSource;

public interface ChannelService
{
    public boolean addDBChannel(Long mplexID,
                                Long sourceID,
                                Long channelID,
                                String callSign,
                                String channelName,
                                String channelNumber,
                                Long serviceID,
                                Long atscMajorChannel,
                                Long atscMinorChannel,
                                Boolean useEIT,
                                Boolean visible,
                                String frequencyID,
                                String icon,
                                String format,
                                String xmltvid,
                                String defaultAuthority);

    public Integer addVideoSource(String sourceName,
                                  String grabber,
                                  String userId,
                                  String freqTable,
                                  String lineupId,
                                  String password,
                                  Boolean useEIT,
                                  String configPath,
                                  Integer nitId);

    public Integer fetchChannelsFromSource(Long sourceId, Long cardId, Boolean waitForFinish);

    public ChannelInfo getChannelInfo(Integer chanID);

    public List<ChannelInfo> getChannelInfoList(Integer sourceID, Integer startIndex, Integer count);

    public List<Lineup> getDDLineupList(String source, String userId, String password);

    public VideoMultiplex getVideoMultiplex(Integer mplexID);

    public List<VideoMultiplex> getVideoMultiplexList(Integer sourceID,
                                                      Integer startIndex,
                                                      Integer count);

    public VideoSource getVideoSource(Long sourceID);

    public List<VideoSource> getVideoSourceList();

    public List<String> getXMLTVIdList(Integer sourceID);

    public boolean removeDBChannel(Long channelID);

    public boolean removeVideoSource(Long sourceID);

    public boolean updateDBChannel(Long mplexID,
                                   Long sourceID,
                                   Long channelID,
                                   String callSign,
                                   String channelName,
                                   String channelNumber,
                                   Long serviceID,
                                   Long atscMajorChannel,
                                   Long atscMinorChannel,
                                   Boolean useEIT,
                                   Boolean visible,
                                   String frequencyID,
                                   String icon,
                                   String format,
                                   String xmltvid,
                                   String defaultAuthority);

    public boolean updateVideoSource(Long sourceID,
                                     String sourceName,
                                     String grabber,
                                     String userId,
                                     String freqTable,
                                     String lineupId,
                                     String password,
                                     Boolean useEIT,
                                     String configPath,
                                     Integer nitId);
}