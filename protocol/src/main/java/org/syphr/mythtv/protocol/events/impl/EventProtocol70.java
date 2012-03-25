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
import org.syphr.mythtv.protocol.events.impl.sender.EventSender70SystemEvent;
import org.syphr.mythtv.protocol.impl.Parser;

public class EventProtocol70 extends AbstractEventProtocol<BackendEventListener63>
{
    public EventProtocol70(Translator translator, Parser parser)
    {
        super(translator, parser, BackendEventListener63.class);
    }

    @Override
    protected EventProtocol createFallbackProtocol()
    {
        return new EventProtocol68(getTranslator(), getParser());
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

            if ("SYSTEM_EVENT".equals(command))
            {
                sender = new EventSender70SystemEvent();
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
