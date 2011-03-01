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

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;

/* default */class Command63MythProtoVersion implements Command<Void>
{
    @Override
    public Void send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait("MYTH_PROTO_VERSION " + getVersion() + " "
                                                    + getToken());

        List<String> args = Protocol63Utils.getArguments(response);
        if (args.size() < 2)
        {
            throw new ProtocolException(response);
        }

        if (!args.get(0).equals("ACCEPT"))
        {
            throw new ProtocolException("Attempted protocol " + getVersion() + ", backend accepts "
                                        + args.get(1));
        }

        return null;
    }

    protected String getVersion()
    {
        return "63";
    }

    protected String getToken()
    {
        return "3875641D";
    }
}
