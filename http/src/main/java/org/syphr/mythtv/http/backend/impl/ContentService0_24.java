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

import java.util.Calendar;
import java.util.List;

import org.syphr.mythtv.http.backend.impl._0_25.content.ArtworkInfo;
import org.syphr.mythtv.http.backend.impl._0_25.content.LiveStreamInfo;

public class ContentService0_24 extends AbstractContentService
{
    @Override
    public LiveStreamInfo addLiveStream(String storageGroup,
                                        String fileName,
                                        String hostName,
                                        Integer maxSegments,
                                        Integer width,
                                        Integer height,
                                        Integer bitrate,
                                        Integer audioBitrate,
                                        Integer sampleRate)
    {
        handleUnsupported("add live stream");
        return null;
    }

    @Override
    public LiveStreamInfo addRecordingLiveStream(Integer chanId,
                                                 Calendar startTime,
                                                 Integer maxSegments,
                                                 Integer width,
                                                 Integer height,
                                                 Integer bitrate,
                                                 Integer audioBitrate,
                                                 Integer sampleRate)
    {
        handleUnsupported("add recording live stream");
        return null;
    }

    @Override
    public LiveStreamInfo addVideoLiveStream(Integer id,
                                             Integer maxSegments,
                                             Integer width,
                                             Integer height,
                                             Integer bitrate,
                                             Integer audioBitrate,
                                             Integer sampleRate)
    {
        handleUnsupported("add video live stream");
        return null;
    }

    @Override
    public boolean downloadFile(String url, String storageGroup)
    {
        handleUnsupported("download file");
        return false;
    }

    @Override
    public String getAlbumArt(Integer id, Integer width, Integer height)
    {
        handleUnsupported("get album art");
        return null;
    }

    @Override
    public String getFile(String storageGroup, String fileName)
    {
        handleUnsupported("get file");
        return null;
    }

    @Override
    public List<String> getFileList(String storageGroup)
    {
        handleUnsupported("get file list");
        return null;
    }

    @Override
    public List<LiveStreamInfo> getFilteredLiveStreamList(String fileName)
    {
        handleUnsupported("get filtered live stream list");
        return null;
    }

    @Override
    public String getHash(String storageGroup, String fileName)
    {
        handleUnsupported("get hash");
        return null;
    }

    @Override
    public String getImageFile(String storageGroup, String fileName, Integer width, Integer height)
    {
        handleUnsupported("get image file");
        return null;
    }

    @Override
    public LiveStreamInfo getLiveStream(Integer id)
    {
        handleUnsupported("get live stream");
        return null;
    }

    @Override
    public List<LiveStreamInfo> getLiveStreamList()
    {
        handleUnsupported("get live stream list");
        return null;
    }

    @Override
    public String getMusic(Integer id)
    {
        handleUnsupported("get music");
        return null;
    }

    @Override
    public String getPreviewImage(Integer chanId,
                                  Calendar startTime,
                                  Integer width,
                                  Integer height,
                                  Integer secsIn)
    {
        handleUnsupported("get preview image");
        return null;
    }

    @Override
    public List<ArtworkInfo> getProgramArtworkList(String inetref, Integer season)
    {
        handleUnsupported("get program artwork list");
        return null;
    }

    @Override
    public String getRecording(Integer chanId, Calendar startTime)
    {
        handleUnsupported("get recording");
        return null;
    }

    @Override
    public String getRecordingArtwork(String type,
                                      String inetref,
                                      Integer season,
                                      Integer width,
                                      Integer height)
    {
        handleUnsupported("get recording artwork");
        return null;
    }

    @Override
    public List<ArtworkInfo> getRecordingArtworkList(Integer chanId, Calendar startTime)
    {
        handleUnsupported("get recording artwork list");
        return null;
    }

    @Override
    public String getVideo(Integer id)
    {
        handleUnsupported("get video");
        return null;
    }

    @Override
    public String getVideoArtwork(String type, Integer id, Integer width, Integer height)
    {
        handleUnsupported("get video artwork");
        return null;
    }

    @Override
    public boolean removeLiveStream(Integer id)
    {
        handleUnsupported("remove live stream");
        return false;
    }

    @Override
    public LiveStreamInfo stopLiveStream(Integer id)
    {
        handleUnsupported("stop live stream");
        return null;
    }

    @Override
    protected String getVersion()
    {
        return null;
    }
}