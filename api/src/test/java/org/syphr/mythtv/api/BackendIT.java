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
import org.syphr.mythtv.db.DatabaseException;
import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.prom.PropertiesManager;

public class BackendIT
{
    private static Backend backend;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException, DatabaseException
    {
        PropertiesManager<Settings> settings = Settings.createSettings();

        backend = new Backend(settings.getEnumProperty(Settings.MYTH_VERSION,
                                                       MythVersion.class));
        backend.connect(settings.getProperty(Settings.BACKEND_HOST),
                        settings.getIntegerProperty(Settings.BACKEND_PROTOCOL_PORT),
                        settings.getIntegerProperty(Settings.BACKEND_HTTP_PORT),
                        settings.getIntegerProperty(Settings.BACKEND_PROTOCOL_TIMEOUT),
                        ConnectionType.MONITOR,
                        EventLevel.NONE);
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
