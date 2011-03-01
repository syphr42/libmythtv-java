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

import java.io.IOException;
import java.net.InetAddress;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.ProtocolVersion;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class QueryRecorderTest
{
    private static PropertiesManager<Settings> settings;

    private static SocketManager socketManager;
    private static Protocol proto;

    private static QueryRecorder queryRecorder;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();

        socketManager = new SocketManager();
        socketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                              settings.getIntegerProperty(Settings.BACKEND_PORT),
                              settings.getIntegerProperty(Settings.BACKEND_TIMEOUT));

        proto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                                        ProtocolVersion.class),
                                               socketManager);
        proto.mythProtoVersion();
        proto.ann(ConnectionType.MONITOR,
                  InetAddress.getLocalHost().getHostName(),
                  EventLevel.NONE);

        int recorder = settings.getIntegerProperty(Settings.RECORDER);
        System.out.println("Interrogating recorder " + recorder);
        queryRecorder = proto.queryRecorder(recorder);
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        proto.done();
        socketManager.disconnect();
    }

    @Test
    public void testIsRecording() throws IOException
    {
        System.out.println("Is recording? " + queryRecorder.isRecording());
    }
}
