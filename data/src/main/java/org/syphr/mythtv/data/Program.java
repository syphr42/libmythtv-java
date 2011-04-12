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
import java.net.URISyntaxException;
import java.util.Date;

import org.slf4j.LoggerFactory;
import org.syphr.mythtv.types.RecordingStatus;
import org.syphr.mythtv.types.RecordingType;

public class Program
{
    private final String title;
    private final String subtitle;
    private final String description;
    private final String category;
    private final Channel channel;
    private final URI filename;
    private final long fileSize;
    private final Date startTime;
    private final Date endTime;
    private final int findId;
    private final String hostname;
    private final int cardId;
    private final int inputId;
    private final int recPriority;
    private final RecordingStatus recStatus;
    private final int recordId;
    private final RecordingType recType;
    private final int dupIn;
    private final int dupMethod;
    private final Date recStartTs;
    private final Date recEndTs;
    private final long programFlags;
    private final String recGroup;
    private final String outputFilters;
    private final String seriesId;
    private final String programId;
    private final Date lastModified;
    private final float stars;
    private final Date airDate;
    private final String playGroup;
    private final int recPriority2;
    private final int parentId;
    private final String storageGroup;
    private final int audioProps;
    private final int videoProps;
    private final int subtitleType;
    private final int year;

    public Program(Channel channel, Date recStartTs)
    {
        this(channel, null, recStartTs);
    }

    public Program(Channel channel, URI filename, Date recStartTs)
    {
        this(null,
             null,
             null,
             null,
             channel,
             filename,
             0,
             null,
             null,
             0,
             null,
             0,
             0,
             0,
             null,
             0,
             null,
             0,
             0,
             recStartTs,
             null,
             0,
             null,
             null,
             null,
             null,
             null,
             0,
             null,
             null,
             0,
             0,
             null,
             0,
             0,
             0,
             0);
    }

    public Program(String title,
                   String subtitle,
                   Channel channel,
                   Date recStartTs)
    {
        this(title,
             subtitle,
             null,
             null,
             channel,
             null,
             0,
             null,
             null,
             0,
             null,
             0,
             0,
             0,
             null,
             0,
             null,
             0,
             0,
             recStartTs,
             null,
             0,
             null,
             null,
             null,
             null,
             null,
             0,
             null,
             null,
             0,
             0,
             null,
             0,
             0,
             0,
             0);
    }

    public Program(String title,
                   String subtitle,
                   String description,
                   String category,
                   Channel channel,
                   Date startTime,
                   Date endTime,
                   String seriesid,
                   String programid)
    {
        this(title,
             subtitle,
             description,
             category,
             channel,
             null,
             0,
             startTime,
             endTime,
             0,
             null,
             0,
             0,
             0,
             null,
             0,
             null,
             0,
             0,
             null,
             null,
             0,
             null,
             null,
             seriesid,
             programid,
             null,
             0,
             null,
             null,
             0,
             0,
             null,
             0,
             0,
             0,
             0);
    }

    public Program(String title,
                       String subtitle,
                       String description,
                       String category,
                       Channel channel,
                       URI filename,
                       long fileSize,
                       Date startTime,
                       Date endTime,
                       int findId,
                       String hostname,
                       int cardId,
                       int inputId,
                       int recPriority,
                       RecordingStatus recStatus,
                       int recordId,
                       RecordingType recType,
                       int dupIn,
                       int dupMethod,
                       Date recStartTs,
                       Date recEndTs,
                       long programFlags,
                       String recGroup,
                       String outputFilters,
                       String seriesId,
                       String programId,
                       Date lastModified,
                       float stars,
                       Date airDate,
                       String playGroup,
                       int recPriority2,
                       int parentId,
                       String storageGroup,
                       int audioProps,
                       int videoProps,
                       int subtitleType,
                       int year)
    {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.channel = channel;
        this.filename = filename;
        this.fileSize = fileSize;
        this.startTime = startTime != null ? new Date(startTime.getTime()) : null;
        this.endTime = endTime != null ? new Date(endTime.getTime()) : null;
        this.findId = findId;
        this.hostname = hostname;
        this.cardId = cardId;
        this.inputId = inputId;
        this.recPriority = recPriority;
        this.recStatus = recStatus;
        this.recordId = recordId;
        this.recType = recType;
        this.dupIn = dupIn;
        this.dupMethod = dupMethod;
        this.recStartTs = recStartTs != null ? new Date(recStartTs.getTime()) : null;
        this.recEndTs = recEndTs != null ? new Date(recEndTs.getTime()) : null;
        this.programFlags = programFlags;
        this.recGroup = recGroup;
        this.outputFilters = outputFilters;
        this.seriesId = seriesId;
        this.programId = programId;
        this.lastModified = lastModified != null ? new Date(lastModified.getTime()) : null;
        this.stars = stars;
        this.airDate = airDate != null ? new Date(airDate.getTime()) : null;
        this.playGroup = playGroup;
        this.recPriority2 = recPriority2;
        this.parentId = parentId;
        this.storageGroup = storageGroup;
        this.audioProps = audioProps;
        this.videoProps = videoProps;
        this.subtitleType = subtitleType;
        this.year = year;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSubtitle()
    {
        return subtitle;
    }

    public String getDescription()
    {
        return description;
    }

    public String getCategory()
    {
        return category;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public URI getFilename()
    {
        return filename;
    }

    public URI getBasename()
    {
        try
        {
            return new URI(filename.getPath());
        }
        catch (URISyntaxException e)
        {
            /*
             * This shouldn't happen.
             */
            LoggerFactory.getLogger(Program.class).warn(filename.getPath(), e);
            return null;
        }
    }

    public long getFileSize()
    {
        return fileSize;
    }

    public Date getStartTime()
    {
        return startTime != null ? new Date(startTime.getTime()) : null;
    }

    public Date getEndTime()
    {
        return endTime != null ? new Date(endTime.getTime()) : null;
    }

    public int getFindId()
    {
        return findId;
    }

    public String getHostname()
    {
        return hostname;
    }

    public int getCardId()
    {
        return cardId;
    }

    public int getInputId()
    {
        return inputId;
    }

    public int getRecPriority()
    {
        return recPriority;
    }

    public RecordingStatus getRecStatus()
    {
        return recStatus;
    }

    public int getRecordId()
    {
        return recordId;
    }

    public RecordingType getRecType()
    {
        return recType;
    }

    public int getDupIn()
    {
        return dupIn;
    }

    public int getDupMethod()
    {
        return dupMethod;
    }

    public Date getRecStartTs()
    {
        return recStartTs != null ? new Date(recStartTs.getTime()) : null;
    }

    public Date getRecEndTs()
    {
        return recEndTs != null ? new Date(recEndTs.getTime()) : null;
    }

    public long getProgramFlags()
    {
        return programFlags;
    }

    public String getRecGroup()
    {
        return recGroup;
    }

    public String getOutputFilters()
    {
        return outputFilters;
    }

    public String getSeriesId()
    {
        return seriesId;
    }

    public String getProgramId()
    {
        return programId;
    }

    public Date getLastModified()
    {
        return lastModified != null ? new Date(lastModified.getTime()) : null;
    }

    public float getStars()
    {
        return stars;
    }

    public Date getAirDate()
    {
        return airDate != null ? new Date(airDate.getTime()) : null;
    }

    public String getPlayGroup()
    {
        return playGroup;
    }

    public int getRecPriority2()
    {
        return recPriority2;
    }

    public int getParentId()
    {
        return parentId;
    }

    public String getStorageGroup()
    {
        return storageGroup;
    }

    public int getAudioProps()
    {
        return audioProps;
    }

    public int getVideoProps()
    {
        return videoProps;
    }

    public int getSubtitleType()
    {
        return subtitleType;
    }

    public int getYear()
    {
        return year;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Program [title=");
        builder.append(title);
        builder.append(", subtitle=");
        builder.append(subtitle);
        builder.append(", description=");
        builder.append(description);
        builder.append(", category=");
        builder.append(category);
        builder.append(", channel=");
        builder.append(channel);
        builder.append(", filename=");
        builder.append(filename);
        builder.append(", fileSize=");
        builder.append(fileSize);
        builder.append(", startTime=");
        builder.append(startTime);
        builder.append(", endTime=");
        builder.append(endTime);
        builder.append(", findId=");
        builder.append(findId);
        builder.append(", hostname=");
        builder.append(hostname);
        builder.append(", cardId=");
        builder.append(cardId);
        builder.append(", inputId=");
        builder.append(inputId);
        builder.append(", recPriority=");
        builder.append(recPriority);
        builder.append(", recStatus=");
        builder.append(recStatus);
        builder.append(", recordId=");
        builder.append(recordId);
        builder.append(", recType=");
        builder.append(recType);
        builder.append(", dupIn=");
        builder.append(dupIn);
        builder.append(", dupMethod=");
        builder.append(dupMethod);
        builder.append(", recStartTs=");
        builder.append(recStartTs);
        builder.append(", recEndTs=");
        builder.append(recEndTs);
        builder.append(", programFlags=");
        builder.append(programFlags);
        builder.append(", recGroup=");
        builder.append(recGroup);
        builder.append(", outputFilters=");
        builder.append(outputFilters);
        builder.append(", seriesId=");
        builder.append(seriesId);
        builder.append(", programId=");
        builder.append(programId);
        builder.append(", lastModified=");
        builder.append(lastModified);
        builder.append(", stars=");
        builder.append(stars);
        builder.append(", airDate=");
        builder.append(airDate);
        builder.append(", playGroup=");
        builder.append(playGroup);
        builder.append(", recPriority2=");
        builder.append(recPriority2);
        builder.append(", parentId=");
        builder.append(parentId);
        builder.append(", storageGroup=");
        builder.append(storageGroup);
        builder.append(", audioProps=");
        builder.append(audioProps);
        builder.append(", videoProps=");
        builder.append(videoProps);
        builder.append(", subtitleType=");
        builder.append(subtitleType);
        builder.append(", year=");
        builder.append(year);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((channel == null) ? 0 : channel.hashCode());
        result = prime
                 * result
                 + ((recStartTs == null) ? 0 : recStartTs.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Program))
        {
            return false;
        }
        Program other = (Program) obj;
        if (channel == null)
        {
            if (other.channel != null)
            {
                return false;
            }
        }
        else if (!channel.equals(other.channel))
        {
            return false;
        }
        if (recStartTs == null)
        {
            if (other.recStartTs != null)
            {
                return false;
            }
        }
        else if (!recStartTs.equals(other.recStartTs))
        {
            return false;
        }
        return true;
    }
}
