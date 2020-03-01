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

import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.protocol.ProtocolVersionException;

public class Protocol75 extends Protocol74
{
    /*
     * This protocol version has no functional changes other than the switch
     * from local times to UTC times.
     */

    public Protocol75(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, ProtocolVersionException
    {
        new Command63MythProtoVersion(getTranslator(), getParser())
        {
            @Override
            protected String getVersion()
            {
                return "75";
            }

            @Override
            protected String getToken()
            {
                return "SweetRock";
            }
        }.send(getSocketManager());
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator75();
    }
}
