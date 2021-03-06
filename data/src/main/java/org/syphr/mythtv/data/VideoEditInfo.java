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
package org.syphr.mythtv.data;

import org.syphr.mythtv.types.VideoEditMark;

public class VideoEditInfo
{
    private final VideoEditMark type;
    private final long frameCount;

    public VideoEditInfo(VideoEditMark type, long frameCount)
    {
        this.type = type;
        this.frameCount = frameCount;
    }

    public VideoEditMark getType()
    {
        return type;
    }

    public long getFrameCount()
    {
        return frameCount;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("VideoEditInfo [type=");
        builder.append(type);
        builder.append(", frameCount=");
        builder.append(frameCount);
        builder.append("]");
        return builder.toString();
    }
}
