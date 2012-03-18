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

import java.io.IOException;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.data.ChannelInfo;
import org.syphr.mythtv.data.Lineup;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.VideoSource;
import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.backend.ChannelService;
import org.syphr.mythtv.ws.backend.impl._0_25.channel.ArrayOfProgram;
import org.syphr.mythtv.ws.backend.impl._0_25.channel.Channel;
import org.syphr.mythtv.ws.backend.impl._0_25.channel.ChannelServices;
import org.syphr.mythtv.ws.backend.impl._0_25.channel.VideoMultiplex;
import org.syphr.mythtv.ws.impl.AbstractService;
import org.syphr.mythtv.ws.impl.ServiceUtils;

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
    public boolean addDBChannel(org.syphr.mythtv.data.Channel channel)
    {
        return ServiceUtils.toPrimitive(service.addDBChannel(channel.getMplexId(),
                                                             channel.getSourceId(),
                                                             channel.getId(),
                                                             channel.getCallsign(),
                                                             channel.getName(),
                                                             channel.getNumber(),
                                                             channel.getServiceId(),
                                                             channel.getAtscMajorChan(),
                                                             channel.getAtscMinorChan(),
                                                             channel.isUseEIT(),
                                                             channel.isVisible(),
                                                             channel.getFrequencyId(),
                                                             channel.getIconPath(),
                                                             channel.getFormat(),
                                                             channel.getXmltvId(),
                                                             channel.getDefaultAuth()));
    }

    @Override
    public Integer addVideoSource(VideoSource videoSource)
    {
        return service.addVideoSource(videoSource.getSourceName(),
                                      videoSource.getGrabber(),
                                      videoSource.getUserId(),
                                      videoSource.getFreqTable(),
                                      videoSource.getLineupId(),
                                      videoSource.getPassword(),
                                      videoSource.isUseEIT(),
                                      videoSource.getConfigPath(),
                                      videoSource.getNitId());
    }

    @Override
    public Integer fetchChannelsFromSource(long sourceId, long cardId, boolean waitForFinish)
    {
        return service.fetchChannelsFromSource(sourceId, cardId, waitForFinish);
    }

    @Override
    public ChannelInfo getChannelInfo(int channelId)
    {
        return convert(service.getChannelInfo(channelId));
    }

    @Override
    public List<ChannelInfo> getChannelInfoList(int sourceId, int startIndex, int count)
    {
        // TODO
        return null;//service.getChannelInfoList(sourceId, startIndex, count);
    }

    @Override
    public List<Lineup> getDDLineupList(String source, String userId, String password)
    {
        // TODO
        return null;//service.getDDLineupList(source, userId, password);
    }

    @Override
    public VideoMultiplex getVideoMultiplex(int mplexId)
    {
        return service.getVideoMultiplex(mplexId);
    }

    @Override
    public List<VideoMultiplex> getVideoMultiplexList(int sourceId, int startIndex, int count)
    {
        // TODO
        return null;//service.getVideoMultiplexList(sourceID, startIndex, count);
    }

    @Override
    public VideoSource getVideoSource(long sourceId)
    {
        // TODO
        return null;//service.getVideoSource(sourceId);
    }

    @Override
    public List<VideoSource> getVideoSourceList()
    {
        // TODO
        return null;//service.getVideoSourceList();
    }

    @Override
    public List<String> getXMLTVIdList(int sourceId)
    {
        // TODO
        return null;//service.getXMLTVIdList(sourceID);
    }

    @Override
    public boolean removeDBChannel(long channelId)
    {
        return ServiceUtils.toPrimitive(service.removeDBChannel(channelId));
    }

    @Override
    public boolean removeVideoSource(long sourceId)
    {
        return ServiceUtils.toPrimitive(service.removeVideoSource(sourceId));
    }

    @Override
    public boolean updateDBChannel(org.syphr.mythtv.data.Channel channel)
    {
        return ServiceUtils.toPrimitive(service.updateDBChannel(channel.getMplexId(),
                                                                channel.getSourceId(),
                                                                channel.getId(),
                                                                channel.getCallsign(),
                                                                channel.getName(),
                                                                channel.getNumber(),
                                                                channel.getServiceId(),
                                                                channel.getAtscMajorChan(),
                                                                channel.getAtscMinorChan(),
                                                                channel.isUseEIT(),
                                                                channel.isVisible(),
                                                                channel.getFrequencyId(),
                                                                channel.getIconPath(),
                                                                channel.getFormat(),
                                                                channel.getXmltvId(),
                                                                channel.getDefaultAuth()));
    }

    @Override
    public boolean updateVideoSource(VideoSource videoSource)
    {
        return ServiceUtils.toPrimitive(service.updateVideoSource(videoSource.getId(),
                                                                  videoSource.getSourceName(),
                                                                  videoSource.getGrabber(),
                                                                  videoSource.getUserId(),
                                                                  videoSource.getFreqTable(),
                                                                  videoSource.getLineupId(),
                                                                  videoSource.getPassword(),
                                                                  videoSource.isUseEIT(),
                                                                  videoSource.getConfigPath(),
                                                                  videoSource.getNitId()));
    }

    private ChannelInfo convert(org.syphr.mythtv.ws.backend.impl._0_25.channel.ChannelInfo rChannelInfo)
    {
        if (rChannelInfo == null)
        {
            return null;
        }

        ChannelInfo channelInfo = new ChannelInfo();

        org.syphr.mythtv.data.Channel channel = new org.syphr.mythtv.data.Channel();
        channel.setId(rChannelInfo.getChanId());
        channel.setSourceId(rChannelInfo.getSourceId());
        channel.setNumber(rChannelInfo.getChanNum());
        channel.setCallsign(rChannelInfo.getCallSign());
        channel.setIconPath(rChannelInfo.getIconURL());
        channel.setName(rChannelInfo.getChannelName());
        channel.setMplexId(rChannelInfo.getMplexId());
        channel.setTransportId(rChannelInfo.getTransportId());
        channel.setServiceId(rChannelInfo.getServiceId());
        channel.setNetworkId(rChannelInfo.getNetworkId());
        channel.setAtscMajorChan(rChannelInfo.getATSCMajorChan());
        channel.setAtscMinorChan(rChannelInfo.getATSCMinorChan());
        channel.setFormat(rChannelInfo.getFormat());
        channel.setModulation(rChannelInfo.getModulation());
        channel.setFrequency(rChannelInfo.getFrequency());
        channel.setFrequencyId(rChannelInfo.getFrequencyId());
        channel.setFrequencyTable(rChannelInfo.getFrequencyTable());
        channel.setFineTune(rChannelInfo.getFineTune());
        channel.setSiStandard(rChannelInfo.getSIStandard());
        channel.setChanFilters(rChannelInfo.getChanFilters());
        channel.setInputId(rChannelInfo.getInputId());
        channel.setCommFree(rChannelInfo.getCommFree());
        channel.setUseEIT(rChannelInfo.isUseEIT());
        channel.setVisible(rChannelInfo.isVisible());
        channel.setXmltvId(rChannelInfo.getXMLTVID());
        channel.setDefaultAuth(rChannelInfo.getDefaultAuth());
        channelInfo.setChannel(channel);

        ArrayOfProgram array = rChannelInfo.getPrograms();
        if (array != null)
        {
            List<org.syphr.mythtv.ws.backend.impl._0_25.channel.Program> rPrograms = array.getPrograms();
            if (rPrograms != null)
            {
                for (org.syphr.mythtv.ws.backend.impl._0_25.channel.Program rProgram : rPrograms)
                {
                    channelInfo.getPrograms().add(convert(rProgram, channel));
                }
            }
        }

        return channelInfo;
    }

    private Program convert(org.syphr.mythtv.ws.backend.impl._0_25.channel.Program rProgram,
                            org.syphr.mythtv.data.Channel channel)
    {
        if (rProgram == null)
        {
            return null;
        }

        // TODO
        return new Program(rProgram.getTitle(),
                           rProgram.getSubTitle(),
                           channel,
                           rProgram.getStartTime().getValue().getTime(),
                           rProgram.getEndTime().getValue().getTime());
    }
}
