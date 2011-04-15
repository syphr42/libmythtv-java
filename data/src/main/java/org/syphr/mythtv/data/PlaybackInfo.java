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

import java.net.URI;
import java.util.Date;

import org.syphr.mythtv.types.PlaybackType;

public class PlaybackInfo
{
    private final PlaybackType type;
    private final int currentPosition;
    private final int totalLength;
    private final float speed;
    private final Channel channel;
    private final Date start;
    private final long currentFrame;
    private final URI location;
    private final float fps;

    public PlaybackInfo(PlaybackType type,
                        int currentPosition,
                        float speed,
                        long currentFrame,
                        URI location,
                        float fps)
    {
        this(type,
             currentPosition,
             -1,
             speed,
             null,
             null,
             currentFrame,
             location,
             fps);
    }

    public PlaybackInfo(PlaybackType type,
                        int currentPosition,
                        int totalLength,
                        float speed,
                        Channel channel,
                        Date start,
                        long currentFrame,
                        URI location,
                        float fps)
    {
        this.type = type;
        this.currentPosition = currentPosition;
        this.totalLength = totalLength;
        this.speed = speed;
        this.channel = channel;
        this.start = start != null ? new Date(start.getTime()) : null;
        this.currentFrame = currentFrame;
        this.location = location;
        this.fps = fps;
    }

    public PlaybackType getType()
    {
        return type;
    }

    public int getCurrentPosition()
    {
        return currentPosition;
    }

    public int getTotalLength()
    {
        return totalLength;
    }

    public float getSpeed()
    {
        return speed;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public Date getStart()
    {
        return start != null ? new Date(start.getTime()) : null;
    }

    public long getCurrentFrame()
    {
        return currentFrame;
    }

    public URI getLocation()
    {
        return location;
    }

    public float getFps()
    {
        return fps;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("PlaybackInfo [type=");
        builder.append(type);
        builder.append(", currentPosition=");
        builder.append(currentPosition);
        builder.append(", totalLength=");
        builder.append(totalLength);
        builder.append(", speed=");
        builder.append(speed);
        builder.append(", channel=");
        builder.append(channel);
        builder.append(", start=");
        builder.append(start);
        builder.append(", currentFrame=");
        builder.append(currentFrame);
        builder.append(", location=");
        builder.append(location);
        builder.append(", fps=");
        builder.append(fps);
        builder.append("]");
        return builder.toString();
    }
}
