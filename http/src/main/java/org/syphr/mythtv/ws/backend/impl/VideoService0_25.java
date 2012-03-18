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

import org.syphr.mythtv.http.backend.impl._0_25.video.BlurayInfo;
import org.syphr.mythtv.http.backend.impl._0_25.video.Video;
import org.syphr.mythtv.http.backend.impl._0_25.video.VideoLookup;
import org.syphr.mythtv.http.backend.impl._0_25.video.VideoMetadataInfo;
import org.syphr.mythtv.http.backend.impl._0_25.video.VideoServices;
import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.backend.VideoService;
import org.syphr.mythtv.ws.impl.AbstractService;
import org.syphr.mythtv.ws.impl.ServiceUtils;

public class VideoService0_25 extends AbstractService implements VideoService
{
    private static final String NAME = "Video";

    private static final String VERSION = "1.2";

    private final Video service;

    public VideoService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        VideoServices locator = new VideoServices();
        service = locator.getBasicHttpBindingVideo();

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
    public boolean addVideo(String fileName, String hostName)
    {
        return ServiceUtils.toPrimitive(service.addVideo(fileName, hostName));
    }

    @Override
    public BlurayInfo getBluray(String path)
    {
        return service.getBluray(path);
    }

    @Override
    public VideoMetadataInfo getVideo(Integer id)
    {
        return service.getVideo(id);
    }

    @Override
    public VideoMetadataInfo getVideoByFileName(String fileName)
    {
        return service.getVideoByFileName(fileName);
    }

    @Override
    public List<VideoMetadataInfo> getVideoList(Boolean descending,
                                                Integer startIndex,
                                                Integer count)
    {
        // TODO
        return null;//service.getVideoList(descending, startIndex, count);
    }

    @Override
    public List<VideoLookup> lookupVideo(String title,
                                         String subtitle,
                                         String inetref,
                                         Integer season,
                                         Integer episode,
                                         String grabberType,
                                         Boolean allowGeneric)
    {
        // TODO
        return null;/*
                     * service.lookupVideo(title, subtitle, inetref, season,
                     * episode, grabberType, allowGeneric);
                     */
    }

    @Override
    public boolean removeVideoFromDB(Integer id)
    {
        return ServiceUtils.toPrimitive(service.removeVideoFromDB(id));
    }
}
