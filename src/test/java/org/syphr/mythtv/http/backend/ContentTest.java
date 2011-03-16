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
package org.syphr.mythtv.http.backend;

import org.junit.BeforeClass;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class ContentTest
{
    private static PropertiesManager<Settings> settings;

    private static Content content;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        settings = Settings.createSettings();

        ConnectionManager connMan = new ConnectionManager(settings.getProperty(Settings.BACKEND_HOST),
                                                          settings.getIntegerProperty(Settings.BACKEND_HTTP_PORT));
        content = BackendFactory.getContent(connMan);
    }
}
