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
package org.syphr.mythtv.api;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.api.Backend;
import org.syphr.mythtv.proto.CommandException;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.ProtocolVersion;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class BackendTest
{
    private static Backend backend;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException
    {
        PropertiesManager<Settings> settings = Settings.createSettings();

        backend = new Backend(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                       ProtocolVersion.class),
                              ConnectionType.MONITOR);
        backend.connect(settings.getProperty(Settings.BACKEND_HOST),
                        settings.getIntegerProperty(Settings.BACKEND_SOCKET_PORT),
                        settings.getIntegerProperty(Settings.BACKEND_SOCKET_TIMEOUT));
    }

    @AfterClass
    public static void tearDownAfterClass()
    {
        backend.disconnect();
    }

    @Test
    public void testIsConnected()
    {
        Assert.assertTrue(backend.isConnected());
    }
}
