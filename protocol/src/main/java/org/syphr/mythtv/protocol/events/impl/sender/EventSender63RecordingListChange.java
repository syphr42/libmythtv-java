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
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;
import org.syphr.mythtv.protocol.impl.Parser;

public class EventSender63RecordingListChange implements EventSender<BackendEventListener63>
{
    private final Parser parser;

    private RecordingListChangeType type;
    private Channel channel;
    private Date startTime;
    private Program program;

    public EventSender63RecordingListChange(Parser parser)
    {
        this.parser = parser;
    }

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        List<String> args = message.getArgs();

        type = mapDataType(args.isEmpty() ? null : args.get(0));
        processData(type, message);
    }

    protected RecordingListChangeType mapDataType(String dataType) throws ProtocolException
    {
        if (dataType == null)
        {
            return RecordingListChangeType.NONE;
        }
        else if ("ADD".equals(dataType))
        {
            return RecordingListChangeType.ADD;
        }
        else if ("UPDATE".equals(dataType))
        {
            return RecordingListChangeType.UPDATE;
        }
        else if ("DELETE".equals(dataType))
        {
            return RecordingListChangeType.DELETE;
        }

        throw new ProtocolException("Unknown recording list change event type", Direction.RECEIVE);
    }

    protected void processData(RecordingListChangeType type, BackendMessage message) throws ProtocolException
    {
        List<String> args = message.getArgs();

        switch (type)
        {
            case ADD:
            case DELETE:
                channel = new Channel(Integer.parseInt(args.get(1)));
                try
                {
                    startTime = DateUtils.getIsoDateFormat().parse(args.get(2));
                }
                catch (ParseException e)
                {
                    throw new ProtocolException("Invalid start time format", Direction.RECEIVE, e);
                }
                break;

            case UPDATE:
                program = parser.parseProgramInfo(message.getData());
                break;
        }
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        switch (type)
        {
            case NONE:
                l.recordingListChangeNone();
                break;

            case ADD:
                l.recordingListChangeAdd(channel, startTime);
                break;

            case UPDATE:
                l.recordingListChangeUpdate(program);
                break;

            case DELETE:
                l.recordingListChangeDelete(channel, startTime);
                break;
        }
    }

    protected enum RecordingListChangeType
    {
        NONE, ADD, UPDATE, DELETE
    }
}
