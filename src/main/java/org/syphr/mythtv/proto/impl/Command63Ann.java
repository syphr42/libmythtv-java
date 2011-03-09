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
package org.syphr.mythtv.proto.impl;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;

/* default */class Command63Ann extends AbstractCommandOkResponse
{
    private final ConnectionType connectionType;
    private final String host;
    private final EventLevel eventLevel;

    public Command63Ann(ConnectionType connectionType,
                        String host,
                        EventLevel eventLevel)
    {
        this.connectionType = connectionType;
        this.host = host;
        this.eventLevel = eventLevel;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ANN ");
        builder.append(Protocol63Utils.getConnectionType(connectionType));
        builder.append(' ');
        builder.append(host);
        builder.append(' ');
        builder.append(Protocol63Utils.getEventLevel(eventLevel));

        return builder.toString();
    }
}
