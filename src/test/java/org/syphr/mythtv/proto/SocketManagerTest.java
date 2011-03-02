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

import junit.framework.Assert;

import org.junit.Test;
import org.syphr.mythtv.proto.impl.Protocol63;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class SocketManagerTest
{
    @Test
    public void testSendAndWaitTimeout() throws IOException
    {
        PropertiesManager<Settings> settings = Settings.createSettings();

        SocketManager socketManager = new SocketManager();
        socketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                              settings.getIntegerProperty(Settings.BACKEND_PORT),
                              settings.getIntegerProperty(Settings.BACKEND_TIMEOUT));

        Protocol proto = new Protocol63(socketManager);
        proto.mythProtoVersion();
        proto.ann(ConnectionType.MONITOR,
                  InetAddress.getLocalHost().getHostName(),
                  EventLevel.NONE);

        /*
         * The response to this message does not parse as a long. It should be discarded
         * and not returned to the second command.
         */
        Assert.assertNull(socketManager.sendAndWait("QUERY_LOAD", 1));

        String uptime = socketManager.sendAndWait("QUERY_UPTIME");
        Assert.assertNotNull(uptime);
        Long.parseLong(uptime);

        proto.done();
        socketManager.disconnect();
    }
}
