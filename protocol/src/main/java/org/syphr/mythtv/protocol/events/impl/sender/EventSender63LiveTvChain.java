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

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.protocol.events.BackendEventListener63;
import org.syphr.mythtv.protocol.events.impl.BackendMessage;
import org.syphr.mythtv.protocol.events.impl.EventSender;

public class EventSender63LiveTvChain implements EventSender<BackendEventListener63>
{
    private LiveTvChainType type;
    private String chainId;

    @Override
    public void processMessage(BackendMessage message) throws ProtocolException
    {
        type = mapDataType(message.getArgs().get(0));
        processData(type, message);
    }

    protected LiveTvChainType mapDataType(String dataType) throws ProtocolException
    {
        if ("UPDATE".equals(dataType))
        {
            return LiveTvChainType.UPDATE;
        }

        throw new ProtocolException("Unknown live TV chain event type", Direction.RECEIVE);
    }

    protected void processData(LiveTvChainType type, BackendMessage message) throws ProtocolException
    {
        switch (type)
        {
            case UPDATE:
                chainId = message.getArgs().get(1);
                break;
        }
    }

    @Override
    public void sendEvent(BackendEventListener63 l)
    {
        switch (type)
        {
            case UPDATE:
                l.liveTvChainUpdate(chainId);
                break;
        }
    }

    protected enum LiveTvChainType
    {
        UPDATE
    }
}
