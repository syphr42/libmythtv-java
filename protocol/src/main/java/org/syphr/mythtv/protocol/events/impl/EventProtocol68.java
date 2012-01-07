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

import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.data.VideoListChange;
import org.syphr.mythtv.protocol.events.BackendEventListener68;
import org.syphr.mythtv.protocol.events.EventProtocol;
import org.syphr.mythtv.protocol.impl.Parser;
import org.syphr.mythtv.types.VideoListChangeType;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.Translator;

public class EventProtocol68 extends AbstractEventProtocol<BackendEventListener68>
{
    public EventProtocol68(Translator translator, Parser parser)
    {
        super(translator, parser, BackendEventListener68.class);
    }

    @Override
    protected EventProtocol createFallbackProtocol()
    {
        return new EventProtocol63(getTranslator(), getParser());
    }

    @Override
    protected EventSender<BackendEventListener68> createSender(List<String> fragments) throws ProtocolException, UnknownEventException
    {
        BackendMessage63 message = new BackendMessage63(fragments);

        try
        {
            String command = message.getCommand();

            if ("VIDEO_LIST_CHANGE".equals(command))
            {
                Translator translator = getTranslator();
                final List<VideoListChange> changes = new ArrayList<VideoListChange>();

                for (String change : message.getData())
                {
                    String[] pair = change.split("::");
                    changes.add(new VideoListChange(translator.toEnum(pair[0],
                                                                      VideoListChangeType.class),
                                                    Integer.parseInt(pair[1])));
                }

                return new EventSender<BackendEventListener68>()
                {
                    @Override
                    public void sendEvent(BackendEventListener68 l)
                    {
                        l.videoListChange(changes.toArray(new VideoListChange[changes.size()]));
                    }
                };
            }
            else if ("VIDEO_LIST_NO_CHANGE".equals(command))
            {
                return new EventSender<BackendEventListener68>()
                {
                    @Override
                    public void sendEvent(BackendEventListener68 l)
                    {
                        l.videoListChange();
                    }
                };
            }
        }
        catch (Exception e)
        {
            throw new ProtocolException(message.toString(), Direction.RECEIVE, e);
        }

        throw new UnknownEventException(message.toString());
    }
}
