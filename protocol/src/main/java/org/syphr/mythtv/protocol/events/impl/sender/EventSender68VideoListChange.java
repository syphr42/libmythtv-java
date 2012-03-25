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

import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.VideoListChange;
import org.syphr.mythtv.protocol.events.BackendEventListener68;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;
import org.syphr.mythtv.types.VideoListChangeType;

public class EventSender68VideoListChange implements EventSender<BackendEventListener68>
{
    private final Translator translator;

    private List<VideoListChange> changes;

    public EventSender68VideoListChange(Translator translator)
    {
        this.translator = translator;
    }

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        changes = new ArrayList<VideoListChange>();

        for (String change : message.getData())
        {
            String[] pair = change.split("::");
            changes.add(new VideoListChange(translator.toEnum(pair[0], VideoListChangeType.class),
                                            Integer.parseInt(pair[1])));
        }
    }

    @Override
    public void sendEvent(BackendEventListener68 l)
    {
        l.videoListChange(changes.toArray(new VideoListChange[changes.size()]));
    }
}
