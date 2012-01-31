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
import java.net.URI;

import org.syphr.mythtv.data.FileInfo;
import org.syphr.mythtv.protocol.InvalidProtocolVersionException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class Protocol65 extends Protocol64
{
    public Protocol65(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, InvalidProtocolVersionException
    {
        new Command63MythProtoVersion(getTranslator(), getParser())
        {
            @Override
            protected String getVersion()
            {
                return "65";
            }

            @Override
            protected String getToken()
            {
                return "D2BB94C2";
            }
        }.send(getSocketManager());
    }

    @Override
    public FileInfo queryFileExists(URI filename, String storageGroup) throws IOException
    {
        return new Command65QueryFileExists(getTranslator(),
                                            getParser(),
                                            filename,
                                            storageGroup).send(getSocketManager());
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator65();
    }
}
