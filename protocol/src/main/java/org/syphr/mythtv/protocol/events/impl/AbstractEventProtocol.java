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
package org.syphr.mythtv.protocol.events.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.events.BackendEventListener;

public abstract class AbstractEventProtocol<T extends BackendEventListener>
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventProtocol.class);

    private final Class<T> type;

    public AbstractEventProtocol(Class<T> type)
    {
        this.type = type;
    }

    public void fireEvent(List<String> args, List<BackendEventListener> listeners) throws ProtocolException
    {
        EventSender<T> sender = null;

        for (BackendEventListener l : listeners)
        {
            if (!type.isAssignableFrom(l.getClass()))
            {
                LOGGER.warn("Skipping backend event listener of type "
                            + l.getClass()
                            + ", expecting "
                            + type);
                continue;
            }

            if (sender == null)
            {
                sender = createSender(args);
            }

            sender.sendEvent(type.cast(l));
        }
    }

    protected abstract EventSender<T> createSender(List<String> args) throws ProtocolException;
}
