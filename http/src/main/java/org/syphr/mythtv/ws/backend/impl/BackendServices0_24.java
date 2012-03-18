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
import org.syphr.mythtv.ws.backend.CaptureService;
import org.syphr.mythtv.ws.backend.ChannelService;
import org.syphr.mythtv.ws.backend.ContentService;
import org.syphr.mythtv.ws.backend.DvrService;
import org.syphr.mythtv.ws.backend.GuideService;
import org.syphr.mythtv.ws.backend.MythService;
import org.syphr.mythtv.ws.backend.VideoService;

public class BackendServices0_24 extends AbstractBackendServices
{
    private final CaptureService captureService;
    private final ChannelService channelService;
    private final ContentService contentService;
    private final DvrService dvrService;
    private final GuideService guideService;
    private final MythService mythService;
    private final VideoService videoService;

    public BackendServices0_24()
    {
        /*
         * Services are unsupported in 0.24 so there is no actual functionality.
         */
        captureService = new CaptureService0_24();
        channelService = new ChannelService0_24();
        contentService = new ContentService0_24();
        dvrService = new DvrService0_24();
        guideService = new GuideService0_24();
        mythService = new MythService0_24();
        videoService = new VideoService0_24();
    }

    @Override
    public void configure(String host) throws ServiceVersionException, IOException
    {
        configure(host, 0);
    }

    @Override
    public void configure(String host, int port) throws ServiceVersionException, IOException
    {
        // NOOP
    }

    @Override
    public CaptureService getCaptureService()
    {
        return captureService;
    }

    @Override
    public ChannelService getChannelService()
    {
        return channelService;
    }

    @Override
    public ContentService getContentService()
    {
        return contentService;
    }

    @Override
    public DvrService getDvrService()
    {
        return dvrService;
    }

    @Override
    public GuideService getGuideService()
    {
        return guideService;
    }

    @Override
    public MythService getMythService()
    {
        return mythService;
    }

    @Override
    public VideoService getVideoService()
    {
        return videoService;
    }
}
