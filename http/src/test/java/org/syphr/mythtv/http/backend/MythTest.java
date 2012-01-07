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
package org.syphr.mythtv.http.backend;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class MythTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MythTest.class);

    private static PropertiesManager<Settings> settings;

    private static Myth myth;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();

        ConnectionManager connMan = new ConnectionManager(settings.getProperty(Settings.BACKEND_HOST),
                                                          settings.getIntegerProperty(Settings.BACKEND_HTTP_PORT));

        try
        {
            myth = BackendFactory.getMyth(connMan);
        }
        catch (ContentException e)
        {
            LOGGER.warn("Backend HTTP test are disabled: " + e.getMessage());
        }
    }

    @Test
    public void testGetConnectionInfo() throws IOException
    {
        if (myth == null)
        {
            return;
        }

        LOGGER.debug(myth.getConnectionInfo().toString());
    }
}
