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

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.SystemEvent;
import org.syphr.mythtv.protocol.events.SystemEventData;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;

public class EventSender63SystemEvent implements EventSender<BackendEventListener63>
{
    private SystemEvent event;
    private Map<SystemEventData, String> dataMap;

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        List<String> args = message.getArgs();

        event = SystemEvent.valueOf(args.get(0));
        dataMap = new HashMap<SystemEventData, String>();

        for (int i = 1; i < args.size(); i += 2)
        {
            SystemEventData dataType = mapDataType(args.get(i));
            String dataValue = processData(dataType, args.get(i + 1));

            dataMap.put(dataType, dataValue);
        }
    }

    protected SystemEventData mapDataType(String dataType) throws ProtocolException
    {
        if ("SENDER".equals(dataType))
        {
            return SystemEventData.SENDER;
        }
        else if ("HOSTNAME".equals(dataType))
        {
            return SystemEventData.HOSTNAME;
        }
        else if ("CARDID".equals(dataType))
        {
            return SystemEventData.CARD_ID;
        }
        else if ("CHANID".equals(dataType))
        {
            return SystemEventData.CHAN_ID;
        }
        else if ("STARTTIME".equals(dataType))
        {
            return SystemEventData.START_TIME;
        }
        else if ("SECS".equals(dataType))
        {
            return SystemEventData.SECS;
        }

        throw new ProtocolException("Unknown system event data type", Direction.RECEIVE);
    }

    protected String processData(SystemEventData dataType, String data) throws ProtocolException
    {
        switch (dataType)
        {
            case START_TIME:
                try
                {
                    return String.valueOf(DateUtils.getIsoDateFormat().parse(data).getTime());
                }
                catch (ParseException e)
                {
                    throw new ProtocolException("Invalid start time format", Direction.RECEIVE, e);
                }

            default:
                return data;
        }
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        l.systemEvent(event, dataMap);
    }
}
