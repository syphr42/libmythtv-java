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
package org.syphr.mythtv.ws.backend;

import java.io.IOException;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.ws.ServiceFactory;
import org.syphr.mythtv.ws.ServiceVersion;
import org.syphr.mythtv.ws.backend.BackendServices;
import org.syphr.mythtv.ws.backend.ChannelService;
import org.syphr.prom.PropertiesManager;

public class ChannelServiceIT
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelServiceIT.class);

    private static PropertiesManager<Settings> settings;

    private static ChannelService channel;

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

            channel = services.getChannelService();
        }
        catch (IllegalArgumentException e)
        {
            LOGGER.info("Services are not available prior to version 0.25");
        }
    }
}
