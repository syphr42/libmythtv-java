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
package org.syphr.mythtv.http.backend.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.http.ServiceVersionException;
import org.syphr.mythtv.http.backend.ChannelService;
import org.syphr.mythtv.http.backend.impl._0_25.channel.Channel;
import org.syphr.mythtv.http.backend.impl._0_25.channel.ChannelInfo;
import org.syphr.mythtv.http.backend.impl._0_25.channel.ChannelServices;
import org.syphr.mythtv.http.backend.impl._0_25.channel.Lineup;
import org.syphr.mythtv.http.backend.impl._0_25.channel.VideoMultiplex;
import org.syphr.mythtv.http.backend.impl._0_25.channel.VideoSource;
import org.syphr.mythtv.http.impl.AbstractService;
import org.syphr.mythtv.http.impl.ServiceUtils;

public class ChannelService0_25 extends AbstractService implements ChannelService
{
    private static final String NAME = "Channel";

    private static final String VERSION = "1.2";

    private final Channel service;

    public ChannelService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        ChannelServices locator = new ChannelServices();
        service = locator.getBasicHttpBindingChannel();

        configureAndVerify(host, port, (BindingProvider)service);
    }

    @Override
    protected String getName()
    {
        return NAME;
    }

    @Override
    protected String getVersion()
    {
        return VERSION;
    }

    @Override
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
                                String defaultAuthority)
    {
        return ServiceUtils.toPrimitive(service.addDBChannel(mplexID,
                                                             sourceID,
                                                             channelID,
                                                             callSign,
                                                             channelName,
                                                             channelNumber,
                                                             serviceID,
                                                             atscMajorChannel,
                                                             atscMinorChannel,
                                                             useEIT,
                                                             visible,
                                                             frequencyID,
                                                             icon,
                                                             format,
                                                             xmltvid,
                                                             defaultAuthority));
    }

    @Override
    public Integer addVideoSource(String sourceName,
                                  String grabber,
                                  String userId,
                                  String freqTable,
                                  String lineupId,
                                  String password,
                                  Boolean useEIT,
                                  String configPath,
                                  Integer nitId)
    {
        return service.addVideoSource(sourceName,
                                      grabber,
                                      userId,
                                      freqTable,
                                      lineupId,
                                      password,
                                      useEIT,
                                      configPath,
                                      nitId);
    }

    @Override
    public Integer fetchChannelsFromSource(Long sourceId, Long cardId, Boolean waitForFinish)
    {
        return service.fetchChannelsFromSource(sourceId, cardId, waitForFinish);
    }

    @Override
    public ChannelInfo getChannelInfo(Integer chanID)
    {
        return service.getChannelInfo(chanID);
    }

    @Override
    public List<ChannelInfo> getChannelInfoList(Integer sourceID, Integer startIndex, Integer count)
    {
        // TODO
        return null;//service.getChannelInfoList(sourceID, startIndex, count);
    }

    @Override
    public List<Lineup> getDDLineupList(String source, String userId, String password)
    {
        // TODO
        return null;//service.getDDLineupList(source, userId, password);
    }

    @Override
    public VideoMultiplex getVideoMultiplex(Integer mplexID)
    {
        return service.getVideoMultiplex(mplexID);
    }

    @Override
    public List<VideoMultiplex> getVideoMultiplexList(Integer sourceID,
                                                      Integer startIndex,
                                                      Integer count)
    {
        // TODO
        return null;//service.getVideoMultiplexList(sourceID, startIndex, count);
    }

    @Override
    public VideoSource getVideoSource(Long sourceID)
    {
        return service.getVideoSource(sourceID);
    }

    @Override
    public List<VideoSource> getVideoSourceList()
    {
        // TODO
        return null;//service.getVideoSourceList();
    }

    @Override
    public List<String> getXMLTVIdList(Integer sourceID)
    {
        // TODO
        return null;//service.getXMLTVIdList(sourceID);
    }

    @Override
    public boolean removeDBChannel(Long channelID)
    {
        return ServiceUtils.toPrimitive(service.removeDBChannel(channelID));
    }

    @Override
    public boolean removeVideoSource(Long sourceID)
    {
        return ServiceUtils.toPrimitive(service.removeVideoSource(sourceID));
    }

    @Override
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
                                   String defaultAuthority)
    {
        return ServiceUtils.toPrimitive(service.updateDBChannel(mplexID,
                                                                sourceID,
                                                                channelID,
                                                                callSign,
                                                                channelName,
                                                                channelNumber,
                                                                serviceID,
                                                                atscMajorChannel,
                                                                atscMinorChannel,
                                                                useEIT,
                                                                visible,
                                                                frequencyID,
                                                                icon,
                                                                format,
                                                                xmltvid,
                                                                defaultAuthority));
    }

    @Override
    public boolean updateVideoSource(Long sourceID,
                                     String sourceName,
                                     String grabber,
                                     String userId,
                                     String freqTable,
                                     String lineupId,
                                     String password,
                                     Boolean useEIT,
                                     String configPath,
                                     Integer nitId)
    {
        return ServiceUtils.toPrimitive(service.updateVideoSource(sourceID,
                                                                  sourceName,
                                                                  grabber,
                                                                  userId,
                                                                  freqTable,
                                                                  lineupId,
                                                                  password,
                                                                  useEIT,
                                                                  configPath,
                                                                  nitId));
    }
}
