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
import org.syphr.mythtv.protocol.events.SystemEventData;

public class EventSender70SystemEvent extends EventSender63SystemEvent
{
    @Override
    protected SystemEventData mapDataType(String dataType) throws ProtocolException
    {
        if ("CREATED".equals(dataType))
        {
            return SystemEventData.CREATED;
        }
        else if ("DESTROYED".equals(dataType))
        {
            return SystemEventData.DESTROYED;
        }
        else if ("RECSTATUS".equals(dataType))
        {
            return SystemEventData.REC_STATUS;
        }

        return super.mapDataType(dataType);
    }
}
