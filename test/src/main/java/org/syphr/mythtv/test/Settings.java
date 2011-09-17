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

import org.apache.commons.lang3.SystemUtils;
import org.syphr.prom.Defaultable;
import org.syphr.prom.PropertiesManager;
import org.syphr.prom.PropertiesManagers;

public enum Settings implements Defaultable
{
    MYTH_VERSION("_0_24"),

    BACKEND_HOST("localhost"),
    BACKEND_SLAVE_HOST("localhost"),

    BACKEND_HTTP_PORT("6544"),

    BACKEND_PROTOCOL_PORT("6543"),
    BACKEND_PROTOCOL_TIMEOUT("10000"),
    BACKEND_PROTOCOL_VERSION("_63"),

    DB_HOST("localhost"),
    DB_PORT("3306"),
    DB_INSTANCE("mythconverg"),
    DB_SCHEMA("_1264"),
    DB_USER("mythtv"),
    DB_PASSWORD("mythtv"),

    FRONTEND_HOST("localhost"),

    FRONTEND_HTTP_PORT("6544"),

    FRONTEND_CONTROL_PORT("6546"),
    FRONTEND_CONTROL_TIMEOUT("25000"),
    FRONTEND_CONTROL_VERSION("_0_24"),

    BUFFER_SIZE("8192"),

    RECORDER("1");

    private static final String LOGGING_ENV_VAR = "JAVA_UTIL_LOGGING_CONFIG_FILE";

    private static final String LOGGING_SYS_PROP = "java.util.logging.config.file";

    private static final String SETTINGS_ENV_VAR = "LIBMYTHTV_JAVA_TEST_SETTINGS";

    private static final String SETTINGS_SYS_PROP = "libmythtv.java.test.settings";

    private static final File DEFAULT_SETTINGS_FILE = new File(SystemUtils.getUserHome(), ".libmythtv-java/settings.properties");

    static
    {
        String loggingProps = System.getProperty(LOGGING_SYS_PROP);
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
                System.out.println("Unable to configure logging; using default configuration");
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
        String settingsStr = System.getenv(SETTINGS_ENV_VAR);
        if (settingsStr == null)
        {
            settingsStr = System.getProperty(SETTINGS_SYS_PROP);
        }

        File settingsFile = settingsStr == null
                ? DEFAULT_SETTINGS_FILE
                : new File(settingsStr);
        PropertiesManager<Settings> settings = PropertiesManagers.newManager(settingsFile,
                                                                             Settings.class);
        settings.load();

        return settings;
    }
}
