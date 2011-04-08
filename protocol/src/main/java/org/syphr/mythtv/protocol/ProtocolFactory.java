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
package org.syphr.mythtv.protocol;

import org.syphr.mythtv.protocol.impl.Protocol63;
import org.syphr.mythtv.protocol.impl.Protocol64;
import org.syphr.mythtv.protocol.impl.Protocol65;
import org.syphr.mythtv.protocol.types.ProtocolVersion;
import org.syphr.mythtv.util.socket.SocketManager;

/**
 * This class acts as a bridge to the various protocol implementations.
 *
 * @author Gregory P. Moyer
 */
public class ProtocolFactory
{
    /**
     * Create a new protocol instance. This instance cannot be used until the associated
     * socket manager is connected.
     *
     * @param version
     *            the desired protocol version (this must match the backend to which the
     *            socket manager is connecting)
     * @param socketManager
     *            the socket manager that will control communication between the client
     *            and the backend
     * @return a new protocol instance
     */
    public static Protocol createInstance(ProtocolVersion version, SocketManager socketManager)
    {
        switch (version)
        {
            case _63:
                return new Protocol63(socketManager);

            case _64:
                return new Protocol64(socketManager);

            case _65:
                return new Protocol65(socketManager);

            default:
                throw new IllegalArgumentException("Unknown protocol version: "
                                                   + version);
        }
    }
}
