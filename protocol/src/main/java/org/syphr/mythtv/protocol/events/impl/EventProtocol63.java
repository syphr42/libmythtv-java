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
package org.syphr.mythtv.protocol.events.impl;

import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.EventProtocol;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63ClearSettingsCache;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63CommercialFlagRequest;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63DoneRecording;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63DownloadFile;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63GeneratedPixmap;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63LiveTvChain;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63LiveTvWatch;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63MasterUpdateProgramInfo;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63RecordingListChange;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63ResetIdleTime;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63ScheduleChange;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63Signal;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63SystemEvent;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63UpdateFileSize;
import org.syphr.mythtv.protocol.events.impl.sender.EventSender63VideoListChange;
import org.syphr.mythtv.protocol.impl.Parser;

public class EventProtocol63 extends AbstractEventProtocol<BackendEventListener63>
{
    public EventProtocol63(Translator translator, Parser parser)
    {
        super(translator, parser, BackendEventListener63.class);
    }

    @Override
    protected EventProtocol createFallbackProtocol()
    {
        return null;
    }

    @Override
    protected EventSender<BackendEventListener63> createSender(List<String> fragments) throws ProtocolException,
                                                                                      UnknownEventException
    {
        BackendMessage message = new BackendMessage63(fragments);

        try
        {
            String command = message.getCommand();
            EventSender<BackendEventListener63> sender = null;

            if ("CLEAR_SETTINGS_CACHE".equals(command))
            {
                sender = new EventSender63ClearSettingsCache();
            }
            else if ("COMMFLAG_REQUEST".equals(command))
            {
                sender = new EventSender63CommercialFlagRequest();
            }
            else if ("DONE_RECORDING".equals(command))
            {
                sender = new EventSender63DoneRecording();
            }
            else if ("DOWNLOAD_FILE".equals(command))
            {
                sender = new EventSender63DownloadFile();
            }
            else if ("GENERATED_PIXMAP".equals(command))
            {
                sender = new EventSender63GeneratedPixmap();
            }
            else if ("LIVETV_CHAIN".equals(command))
            {
                sender = new EventSender63LiveTvChain();
            }
            else if ("LIVETV_WATCH".equals(command))
            {
                sender = new EventSender63LiveTvWatch();
            }
            else if ("MASTER_UPDATE_PROG_INFO".equals(command))
            {
                sender = new EventSender63MasterUpdateProgramInfo();
            }
            else if ("RECORDING_LIST_CHANGE".equals(command))
            {
                sender = new EventSender63RecordingListChange(getParser());
            }
            else if ("SCHEDULE_CHANGE".equals(command))
            {
                sender = new EventSender63ScheduleChange();
            }
            else if ("SIGNAL".equals(command))
            {
                sender = new EventSender63Signal(getTranslator());
            }
            else if ("SYSTEM_EVENT".equals(command))
            {
                sender = new EventSender63SystemEvent();
            }
            else if ("UPDATE_FILE_SIZE".equals(command))
            {
                sender = new EventSender63UpdateFileSize();
            }
            else if ("VIDEO_LIST_CHANGE".equals(command))
            {
                sender = new EventSender63VideoListChange();
            }
            else if ("RESET_IDLETIME".equals(command))
            {
                sender = new EventSender63ResetIdleTime();
            }

            if (sender != null)
            {
                sender.processMessage(message);
                return sender;
            }
        }
        catch (Exception e)
        {
            throw new ProtocolException(message.toString(), Direction.RECEIVE, e);
        }

        throw new UnknownEventException(message.toString());
    }
}
