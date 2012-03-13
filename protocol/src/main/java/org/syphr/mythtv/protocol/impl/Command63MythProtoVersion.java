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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.protocol.ProtocolVersionException;

/* default */class Command63MythProtoVersion extends AbstractProtocolCommand<Void>
{
    public Command63MythProtoVersion(Translator translator, Parser parser)
    {
        super(translator, parser);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MYTH_PROTO_VERSION ");
        builder.append(getVersion());
        builder.append(' ');
        builder.append(getToken());

        return builder.toString();
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException,
                                                 ProtocolVersionException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<String> args = getParser().splitArguments(response);
        if (args.size() < 2)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        if ("ACCEPT".equals(args.get(0)))
        {
            return null;
        }

        if ("REJECT".equals(args.get(0)))
        {
            throw new ProtocolVersionException(getVersion(), args.get(1));
        }

        throw new ProtocolException(response, Direction.RECEIVE);
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
