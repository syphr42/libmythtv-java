/*
 * Copyright 2011 Gregory P. Moyer
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
package org.syphr.mythtv.data;

public class RecorderDevice
{
    private final int id;
    private final String video;
    private final String audio;
    private final String vbi;

    public RecorderDevice(int id, String video, String audio, String vbi)
    {
        this.id = id;
        this.video = video;
        this.audio = audio;
        this.vbi = vbi;
    }

    public int getId()
    {
        return id;
    }

    public String getVideo()
    {
        return video;
    }

    public String getAudio()
    {
        return audio;
    }

    public String getVbi()
    {
        return vbi;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RecorderDevice [id=");
        builder.append(id);
        builder.append(", video=");
        builder.append(video);
        builder.append(", audio=");
        builder.append(audio);
        builder.append(", vbi=");
        builder.append(vbi);
        builder.append("]");
        return builder.toString();
    }
}
