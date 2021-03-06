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

import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.backend.BackendServices;
import org.syphr.mythtv.ws.backend.CaptureService;
import org.syphr.mythtv.ws.backend.ChannelService;
import org.syphr.mythtv.ws.backend.ContentService;
import org.syphr.mythtv.ws.backend.DvrService;
import org.syphr.mythtv.ws.backend.GuideService;
import org.syphr.mythtv.ws.backend.MythService;
import org.syphr.mythtv.ws.backend.VideoService;

public abstract class AbstractBackendServices implements BackendServices
{
    private static final int DEFAULT_PORT = 6544;

    private CaptureService captureService;
    private ChannelService channelService;
    private ContentService contentService;
    private DvrService dvrService;
    private GuideService guideService;
    private MythService mythService;
    private VideoService videoService;

    protected int getPort(int port)
    {
        if (port <= 0)
        {
            return DEFAULT_PORT;
        }

        return port;
    }

    @Override
    public void configure(String host) throws ServiceVersionException, IOException
    {
        configure(host, 0);
    }

    @Override
    public void removeConfiguration()
    {
        setCaptureService(null);
        setChannelService(null);
        setContentService(null);
        setDvrService(null);
        setGuideService(null);
        setMythService(null);
        setVideoService(null);
    }

    @Override
    public CaptureService getCaptureService()
    {
        return captureService;
    }

    protected void setCaptureService(CaptureService captureService)
    {
        this.captureService = captureService;
    }

    @Override
    public ChannelService getChannelService()
    {
        return channelService;
    }

    protected void setChannelService(ChannelService channelService)
    {
        this.channelService = channelService;
    }

    @Override
    public ContentService getContentService()
    {
        return contentService;
    }

    protected void setContentService(ContentService contentService)
    {
        this.contentService = contentService;
    }

    @Override
    public DvrService getDvrService()
    {
        return dvrService;
    }

    protected void setDvrService(DvrService dvrService)
    {
        this.dvrService = dvrService;
    }

    @Override
    public GuideService getGuideService()
    {
        return guideService;
    }

    protected void setGuideService(GuideService guideService)
    {
        this.guideService = guideService;
    }

    @Override
    public MythService getMythService()
    {
        return mythService;
    }

    protected void setMythService(MythService mythService)
    {
        this.mythService = mythService;
    }

    @Override
    public VideoService getVideoService()
    {
        return videoService;
    }

    protected void setVideoService(VideoService videoService)
    {
        this.videoService = videoService;
    }
}
