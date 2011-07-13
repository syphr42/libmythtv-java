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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;

import org.syphr.mythtv.protocol.events.EventProtocol;
import org.syphr.mythtv.protocol.events.impl.EventProtocol68;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class Protocol68 extends Protocol67
{
    public Protocol68(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, CommandException
    {
        new Command63MythProtoVersion(getTranslator(), getParser())
        {
            @Override
            protected String getVersion()
            {
                return "68";
            }

            @Override
            protected String getToken()
            {
                return "90094EAD";
            }
        }.send(getSocketManager());
    }

    @Override
    protected EventProtocol createEventProtocol(Translator translator,
                                                Parser parser)
    {
        return new EventProtocol68(translator, parser);
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator68();
    }
}
