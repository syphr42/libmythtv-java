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
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.protocol.test.Utils;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class MediaServerIT
{
    private static PropertiesManager<Settings> settings;
    private static SocketManager socketManager;
    private static Protocol proto;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException
    {
        settings = Settings.createSettings();
        socketManager = Utils.connect(settings);

        proto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.BACKEND_PROTOCOL_VERSION,
                                                                        ProtocolVersion.class),
                                               socketManager);
        proto.mythProtoVersion();
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        proto.done();
        socketManager.disconnect();
    }

    @Test
    public void testAnnSlaveBackend() throws IOException, CommandException
    {
        String mediaServerHost = settings.getProperty(Settings.BACKEND_MEDIA_SERVER_HOST);
        proto.annMediaServer(InetAddress.getByName(mediaServerHost));
    }
}
