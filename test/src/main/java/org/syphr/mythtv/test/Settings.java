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
package org.syphr.mythtv.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

import org.syphr.prom.Defaultable;
import org.syphr.prom.PropertiesManager;
import org.syphr.prom.PropertiesManagers;

public enum Settings implements Defaultable
{
    BACKEND_HOST("localhost"),

    BACKEND_HTTP_PORT("6544"),

    BACKEND_PROTOCOL_PORT("6543"),
    BACKEND_PROTOCOL_TIMEOUT("10000"),
    BACKEND_PROTOCOL_VERSION("SIXTY_THREE"),

    FRONTEND_HOST("localhost"),

    BUFFER_SIZE("8192"),

    RECORDER("1");

    private static final String LOGGING_ENV_VAR = "JAVA_UTIL_LOGGING_CONFIG_FILE";

    private static final String SETTINGS_ENV_VAR = "LIBMYTHTV_JAVA_TEST_SETTINGS";

    private static final String SETTINGS_SYS_PROP = "libmythtv.java.test.settings";

    private static final String DEFAULT_SETTINGS_FILE = "src/test/resources/settings.properties";

    static
    {
        String loggingProps = System.getProperty("java.util.logging.config.file");
        if (loggingProps == null)
        {
            try
            {
                InputStream in;

                loggingProps = System.getenv(LOGGING_ENV_VAR);
                if (loggingProps != null)
                {
                    in = new FileInputStream(loggingProps);
                }
                else
                {
                    in = Settings.class.getResourceAsStream("logging.properties");
                }

                try
                {
                    LogManager.getLogManager().readConfiguration(in);
                }
                finally
                {
                    in.close();
                }
            }
            catch (Exception e)
            {
                System.out.println("Unable to configure logging");
                e.printStackTrace();
            }
        }
    }

    private final String defaultValue;

    private Settings(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getDefaultValue()
    {
        return defaultValue;
    }

    public static PropertiesManager<Settings> createSettings() throws IOException
    {
        String settingsFile = System.getenv(SETTINGS_ENV_VAR);
        if (settingsFile == null)
        {
            settingsFile = System.getProperty(SETTINGS_SYS_PROP);
        }
        if (settingsFile == null)
        {
            settingsFile = DEFAULT_SETTINGS_FILE;
        }

        PropertiesManager<Settings> settings = PropertiesManagers.newManager(new File(settingsFile),
                                                                             Settings.class);
        settings.load();

        return settings;
    }
}
