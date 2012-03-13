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
import org.syphr.mythtv.http.ServiceFactory;
import org.syphr.mythtv.http.ServiceVersion;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class MythServiceIT
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MythServiceIT.class);

    private static PropertiesManager<Settings> settings;

    private static MythService myth;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();

        try
        {
            BackendServices services = ServiceFactory.getBackendInstance(settings.getEnumProperty(Settings.BACKEND_WS_VERSION,
                                                                                                  ServiceVersion.class));
            services.configure(settings.getProperty(Settings.BACKEND_HOST),
                               settings.getIntegerProperty(Settings.BACKEND_HTTP_PORT));

            myth = services.getMythService();
        }
        catch (IllegalArgumentException e)
        {
            LOGGER.info("Services are not available prior to version 0.25");
        }
    }

    @Test
    public void testGetHostName()
    {
        LOGGER.debug("Host name: {}", myth.getHostName());
    }
}
