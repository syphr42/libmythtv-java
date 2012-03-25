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
package org.syphr.mythtv.protocol.events.impl.sender;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;

public class EventSender63GeneratedPixmap implements EventSender<BackendEventListener63>
{
    private boolean success;
    private Channel channel;
    private Date startTime;
    private String comment;
    private Date timestamp;
    private long size;
    private long checksum;
    private byte[] imageData;
    private List<String> receivers;

    protected boolean isSuccess()
    {
        return success;
    }

    protected void setSuccess(boolean success)
    {
        this.success = success;
    }

    protected void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    protected void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    protected void setComment(String comment)
    {
        this.comment = comment;
    }

    protected void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    protected void setSize(long size)
    {
        this.size = size;
    }

    protected void setChecksum(long checksum)
    {
        this.checksum = checksum;
    }

    protected void setImageData(byte[] imageData)
    {
        this.imageData = imageData;
    }

    protected void setReceivers(List<String> receivers)
    {
        this.receivers = receivers;
    }

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        List<String> data = message.getData();
        setSuccess("OK".equals(data.get(0)));

        if (isSuccess())
        {
            processSuccess(data.subList(1, data.size()));
        }
        else
        {
            processFailure(data);
        }
    }

    protected void processSuccess(List<String> data) throws ProtocolException
    {
        DateFormat isoFormat = DateUtils.getIsoDateFormat();

        try
        {
            String[] chanIdTimestamp = data.get(0).split("_");
            setChannel(new Channel(Integer.parseInt(chanIdTimestamp[0])));
            setStartTime(isoFormat.parse(chanIdTimestamp[1]));

            setComment(data.get(1));
            setTimestamp(isoFormat.parse(data.get(2)));
            setSize(Long.parseLong(data.get(3)));
            setChecksum(Long.parseLong(data.get(4)));
            setImageData(data.get(5).getBytes());

            // TODO receivers
        }
        catch (ParseException e)
        {
            throw new ProtocolException("Invalid time format", Direction.RECEIVE, e);
        }
    }

    protected void processFailure(List<String> data) throws ProtocolException
    {
        DateFormat isoFormat = DateUtils.getIsoDateFormat();

        try
        {
            String[] chanIdTimestamp = data.get(0).split("_");
            setChannel(new Channel(Integer.parseInt(chanIdTimestamp[0])));
            setStartTime(isoFormat.parse(chanIdTimestamp[1]));

            setComment(data.get(1));

            // TODO receivers
        }
        catch (ParseException e)
        {
            throw new ProtocolException("Invalid time format", Direction.RECEIVE, e);
        }
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        l.generatedPixmap(success,
                          channel,
                          startTime,
                          comment,
                          timestamp,
                          size,
                          checksum,
                          imageData);
    }
}
