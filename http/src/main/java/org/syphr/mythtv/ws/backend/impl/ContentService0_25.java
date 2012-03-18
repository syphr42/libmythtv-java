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
import java.util.Calendar;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.http.backend.impl._0_25.content.ArtworkInfo;
import org.syphr.mythtv.http.backend.impl._0_25.content.Content;
import org.syphr.mythtv.http.backend.impl._0_25.content.ContentServices;
import org.syphr.mythtv.http.backend.impl._0_25.content.LiveStreamInfo;
import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.backend.ContentService;
import org.syphr.mythtv.ws.impl.AbstractService;
import org.syphr.mythtv.ws.impl.ServiceUtils;

public class ContentService0_25 extends AbstractService implements ContentService
{
    private static final String NAME = "Content";

    private static final String VERSION = "1.32";

    private final Content service;

    public ContentService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        ContentServices locator = new ContentServices();
        service = locator.getBasicHttpBindingContent();

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
        return service.addLiveStream(storageGroup,
                                     fileName,
                                     hostName,
                                     maxSegments,
                                     width,
                                     height,
                                     bitrate,
                                     audioBitrate,
                                     sampleRate);
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
        return service.addRecordingLiveStream(chanId,
                                              startTime,
                                              maxSegments,
                                              width,
                                              height,
                                              bitrate,
                                              audioBitrate,
                                              sampleRate);
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
        return service.addVideoLiveStream(id,
                                          maxSegments,
                                          width,
                                          height,
                                          bitrate,
                                          audioBitrate,
                                          sampleRate);
    }

    @Override
    public boolean downloadFile(String url, String storageGroup)
    {
        return ServiceUtils.toPrimitive(service.downloadFile(url, storageGroup));
    }

    @Override
    public String getAlbumArt(Integer id, Integer width, Integer height)
    {
        return service.getAlbumArt(id, width, height);
    }

    @Override
    public String getFile(String storageGroup, String fileName)
    {
        return service.getFile(storageGroup, fileName);
    }

    @Override
    public List<String> getFileList(String storageGroup)
    {
        // TODO
        return null;//service.getFileList(storageGroup);
    }

    @Override
    public List<LiveStreamInfo> getFilteredLiveStreamList(String fileName)
    {
        // TODO
        return null;//service.getFilteredLiveStreamList(fileName);
    }

    @Override
    public String getHash(String storageGroup, String fileName)
    {
        return service.getHash(storageGroup, fileName);
    }

    @Override
    public String getImageFile(String storageGroup, String fileName, Integer width, Integer height)
    {
        return service.getImageFile(storageGroup, fileName, width, height);
    }

    @Override
    public LiveStreamInfo getLiveStream(Integer id)
    {
        return service.getLiveStream(id);
    }

    @Override
    public List<LiveStreamInfo> getLiveStreamList()
    {
        // TODO
        return null;//service.getLiveStreamList();
    }

    @Override
    public String getMusic(Integer id)
    {
        return service.getMusic(id);
    }

    @Override
    public String getPreviewImage(Integer chanId,
                                  Calendar startTime,
                                  Integer width,
                                  Integer height,
                                  Integer secsIn)
    {
        return service.getPreviewImage(chanId, startTime, width, height, secsIn);
    }

    @Override
    public List<ArtworkInfo> getProgramArtworkList(String inetref, Integer season)
    {
        // TODO
        return null;//service.getProgramArtworkList(inetref, season);
    }

    @Override
    public String getRecording(Integer chanId, Calendar startTime)
    {
        return service.getRecording(chanId, startTime);
    }

    @Override
    public String getRecordingArtwork(String type,
                                      String inetref,
                                      Integer season,
                                      Integer width,
                                      Integer height)
    {
        return service.getRecordingArtwork(type, inetref, season, width, height);
    }

    @Override
    public List<ArtworkInfo> getRecordingArtworkList(Integer chanId, Calendar startTime)
    {
        // TODO
        return null;//service.getRecordingArtworkList(chanId, startTime);
    }

    @Override
    public String getVideo(Integer id)
    {
        return service.getVideo(id);
    }

    @Override
    public String getVideoArtwork(String type, Integer id, Integer width, Integer height)
    {
        return service.getVideoArtwork(type, id, width, height);
    }

    @Override
    public boolean removeLiveStream(Integer id)
    {
        return ServiceUtils.toPrimitive(service.removeLiveStream(id));
    }

    @Override
    public LiveStreamInfo stopLiveStream(Integer id)
    {
        return service.stopLiveStream(id);
    }
}
