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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;

public class EventSender63DownloadFile implements EventSender<BackendEventListener63>
{
    private DownloadEventType type;
    private URL remoteUrl;
    private URI localUri;
    private long bytesReceived;
    private long bytesTotal;
    private String errorText;
    private int errorCode;

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        type = mapDataType(message.getArgs().get(0));
        processData(type, message.getData());
    }

    protected DownloadEventType mapDataType(String dataType) throws ProtocolException
    {
        if ("UPDATE".equals(dataType))
        {
            return DownloadEventType.UPDATE;
        }
        else if ("FINISHED".equals(dataType))
        {
            return DownloadEventType.FINISHED;
        }

        throw new ProtocolException("Unknown download event type", Direction.RECEIVE);
    }

    protected void processData(DownloadEventType type, List<String> data) throws ProtocolException
    {
        try
        {
            switch (type)
            {
                case UPDATE:
                    remoteUrl = new URL(data.get(0));
                    localUri = URI.create(data.get(1));
                    bytesReceived = Long.parseLong(data.get(2));
                    bytesTotal = Long.parseLong(data.get(3));
                    break;

                case FINISHED:
                    remoteUrl = new URL(data.get(0));
                    localUri = URI.create(data.get(1));
                    bytesTotal = Long.parseLong(data.get(2));
                    errorText = data.get(3);
                    errorCode = Integer.parseInt(data.get(4));
                    break;
            }
        }
        catch (MalformedURLException e)
        {
            throw new ProtocolException("Invalid remote URL format", Direction.RECEIVE, e);
        }
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        switch (type)
        {
            case UPDATE:
                l.downloadFileUpdate(remoteUrl, localUri, bytesReceived, bytesTotal);
                break;

            case FINISHED:
                l.downloadFileFinshed(remoteUrl, localUri, bytesTotal, errorText, errorCode);
                break;
        }
    }

    protected enum DownloadEventType
    {
        UPDATE, FINISHED
    }
}
