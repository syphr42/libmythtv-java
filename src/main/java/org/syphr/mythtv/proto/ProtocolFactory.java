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
package org.syphr.mythtv.proto;

import org.syphr.mythtv.proto.impl.Protocol63;
import org.syphr.mythtv.proto.impl.Protocol64;
import org.syphr.mythtv.proto.types.ProtocolVersion;

public class ProtocolFactory
{
    public static Protocol createInstance(ProtocolVersion version, SocketManager socketManager)
    {
        switch (version)
        {
            case SIXTY_THREE:
                return new Protocol63(socketManager);

            case SIXTY_FOUR:
                return new Protocol64(socketManager);

            default:
                throw new IllegalArgumentException("Unknown protocol version: "
                                                   + version);
        }
    }
}
