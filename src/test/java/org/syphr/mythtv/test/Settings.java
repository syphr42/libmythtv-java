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
import java.io.IOException;

import org.syphr.mythtv.proto.types.ProtocolVersion;
import org.syphr.prom.Defaultable;
import org.syphr.prom.PropertiesManager;
import org.syphr.prom.PropertiesManagers;

public enum Settings implements Defaultable
{
    BACKEND_HOST("localhost"),

    BACKEND_SOCKET_PORT("6543"),
    BACKEND_SOCKET_TIMEOUT("10000"),

    BACKEND_HTTP_PORT("6544"),

    FRONTEND_HOST("localhost"),

    PROTOCOL_VERSION(ProtocolVersion.SIXTY_THREE.name()),

    BUFFER_SIZE("8192"),

    RECORDER("1");

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
        PropertiesManager<Settings> settings = PropertiesManagers.newManager(new File("src/test/resources/settings.properties"),
                                                                             Settings.class);
        settings.load();

        return settings;
    }
}
