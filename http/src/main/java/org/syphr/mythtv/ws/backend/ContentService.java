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

import java.util.Calendar;
import java.util.List;

import org.syphr.mythtv.http.backend.impl._0_25.content.ArtworkInfo;
import org.syphr.mythtv.http.backend.impl._0_25.content.LiveStreamInfo;

public interface ContentService
{
    public LiveStreamInfo addLiveStream(String storageGroup,
                                        String fileName,
                                        String hostName,
                                        Integer maxSegments,
                                        Integer width,
                                        Integer height,
                                        Integer bitrate,
                                        Integer audioBitrate,
                                        Integer sampleRate);

    public LiveStreamInfo addRecordingLiveStream(Integer chanId,
                                                 Calendar startTime,
                                                 Integer maxSegments,
                                                 Integer width,
                                                 Integer height,
                                                 Integer bitrate,
                                                 Integer audioBitrate,
                                                 Integer sampleRate);

    public LiveStreamInfo addVideoLiveStream(Integer id,
                                             Integer maxSegments,
                                             Integer width,
                                             Integer height,
                                             Integer bitrate,
                                             Integer audioBitrate,
                                             Integer sampleRate);

    public boolean downloadFile(String url, String storageGroup);

    public String getAlbumArt(Integer id, Integer width, Integer height);

    public String getFile(String storageGroup, String fileName);

    public List<String> getFileList(String storageGroup);

    public List<LiveStreamInfo> getFilteredLiveStreamList(String fileName);

    public String getHash(String storageGroup, String fileName);

    public String getImageFile(String storageGroup, String fileName, Integer width, Integer height);

    public LiveStreamInfo getLiveStream(Integer id);

    public List<LiveStreamInfo> getLiveStreamList();

    public String getMusic(Integer id);

    public String getPreviewImage(Integer chanId,
                                  Calendar startTime,
                                  Integer width,
                                  Integer height,
                                  Integer secsIn);

    public List<ArtworkInfo> getProgramArtworkList(String inetref, Integer season);

    public String getRecording(Integer chanId, Calendar startTime);

    public String getRecordingArtwork(String type,
                                      String inetref,
                                      Integer season,
                                      Integer width,
                                      Integer height);

    public List<ArtworkInfo> getRecordingArtworkList(Integer chanId, Calendar startTime);

    public String getVideo(Integer id);

    public String getVideoArtwork(String type, Integer id, Integer width, Integer height);

    public boolean removeLiveStream(Integer id);

    public LiveStreamInfo stopLiveStream(Integer id);
}