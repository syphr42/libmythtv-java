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

import java.util.List;

import org.syphr.mythtv.http.backend.impl._0_25.video.BlurayInfo;
import org.syphr.mythtv.http.backend.impl._0_25.video.VideoLookup;
import org.syphr.mythtv.http.backend.impl._0_25.video.VideoMetadataInfo;

public class VideoService0_24 extends AbstractVideoService
{
    @Override
    public boolean addVideo(String fileName, String hostName)
    {
        handleUnsupported("add video");
        return false;
    }

    @Override
    public BlurayInfo getBluray(String path)
    {
        handleUnsupported("get bluray");
        return null;
    }

    @Override
    public VideoMetadataInfo getVideo(Integer id)
    {
        handleUnsupported("get video");
        return null;
    }

    @Override
    public VideoMetadataInfo getVideoByFileName(String fileName)
    {
        handleUnsupported("get video by file name");
        return null;
    }

    @Override
    public List<VideoMetadataInfo> getVideoList(Boolean descending,
                                                Integer startIndex,
                                                Integer count)
    {
        handleUnsupported("get video list");
        return null;
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
        handleUnsupported("lookup video");
        return null;
    }

    @Override
    public boolean removeVideoFromDB(Integer id)
    {
        handleUnsupported("remove video from DB");
        return false;
    }

    @Override
    protected String getVersion()
    {
        return null;
    }
}