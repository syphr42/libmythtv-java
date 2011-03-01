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

import org.syphr.mythtv.proto.SocketManager;

public class Protocol64 extends Protocol63
{
    public Protocol64(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException
    {
        new Command63MythProtoVersion()
        {
            @Override
            protected String getVersion()
            {
                return "64";
            }

            @Override
            protected String getToken()
            {
                return "8675309J";
            }
        }.send(getSocketManager());
    }

    @Override
    public void scanVideos() throws IOException
    {
        new Command64ScanVideos().send(getSocketManager());
    }
}
