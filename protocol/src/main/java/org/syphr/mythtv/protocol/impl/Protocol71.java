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

import org.syphr.mythtv.protocol.InvalidProtocolVersionException;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.util.socket.SocketManager;

public class Protocol71 extends Protocol70
{
    public Protocol71(SocketManager socketManager)
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
                return "71";
            }

            @Override
            protected String getToken()
            {
                return "05e82186";
            }
        }.send(getSocketManager());
    }

    @Override
    public QueryRecorder queryRecorder(int recorderId)
    {
        return new QueryRecorder71(getTranslator(), getParser(), recorderId, getSocketManager());
    }

    @Override
    public QueryRemoteEncoder queryRemoteEncoder(int recorderId)
    {
        return new QueryRemoteEncoder71(getTranslator(),
                                        getParser(),
                                        recorderId,
                                        getSocketManager());
    }
}
