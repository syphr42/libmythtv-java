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
package org.syphr.mythtv.control.test;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.control.Control;
import org.syphr.mythtv.control.ControlFactory;
import org.syphr.mythtv.control.ControlVersion;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class Utils
{
    private static Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static Control connect(PropertiesManager<Settings> settings) throws IOException
    {
        Control control = ControlFactory.createInstance(settings.getEnumProperty(Settings.FRONTEND_CONTROL_VERSION,
                                                                                 ControlVersion.class));

        control.connect(settings.getProperty(Settings.FRONTEND_HOST),
                        settings.getIntegerProperty(Settings.FRONTEND_CONTROL_PORT),
                        settings.getIntegerProperty(Settings.FRONTEND_CONTROL_TIMEOUT));

        return control;
    }

    public static void waitSeconds(int seconds, String message)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e)
        {
            LOGGER.warn("Interrupted while waiting for frontend to " + message, e);
        }
    }
}
