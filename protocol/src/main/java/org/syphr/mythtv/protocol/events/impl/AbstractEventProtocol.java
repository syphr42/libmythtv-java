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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.protocol.events.BackendEventListener;
import org.syphr.mythtv.protocol.events.EventProtocol;
import org.syphr.mythtv.protocol.impl.Parser;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.Translator;

public abstract class AbstractEventProtocol<T extends BackendEventListener> implements EventProtocol
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventProtocol.class);

    private final Translator translator;
    private final Parser parser;
    private final Class<T> type;

    private volatile EventProtocol fallbackProtocol;

    public AbstractEventProtocol(Translator translator,
                                 Parser parser,
                                 Class<T> type)
    {
        this.translator = translator;
        this.parser = parser;
        this.type = type;
    }

    @Override
    public void fireEvent(List<String> args, List<BackendEventListener> listeners) throws ProtocolException
    {
        EventSender<T> sender = null;

        for (BackendEventListener l : listeners)
        {
            if (!type.isAssignableFrom(l.getClass()))
            {
                LOGGER.debug("Skipping backend event listener of type "
                            + l.getClass()
                            + ", expecting "
                            + type);
                continue;
            }

            if (sender == null)
            {
                try
                {
                    sender = createSender(args);
                }
                catch (UnknownEventException e)
                {
                    EventProtocol fallback = getFallbackProtocol();

                    if (fallback != null)
                    {
                        fallback.fireEvent(args, listeners);
                        return;
                    }

                    throw new ProtocolException("Unknown backend message: " + e.getMessage(), Direction.RECEIVE, e);
                }
            }

            sender.sendEvent(type.cast(l));
        }
    }

    protected Translator getTranslator()
    {
        return translator;
    }

    protected Parser getParser()
    {
        return parser;
    }

    private EventProtocol getFallbackProtocol()
    {
        if (fallbackProtocol == null)
        {
            synchronized (this)
            {
                if (fallbackProtocol == null)
                {
                    fallbackProtocol = createFallbackProtocol();
                }
            }
        }

        return fallbackProtocol;
    }

    protected abstract EventSender<T> createSender(List<String> args) throws ProtocolException, UnknownEventException;

    protected abstract EventProtocol createFallbackProtocol();
}
