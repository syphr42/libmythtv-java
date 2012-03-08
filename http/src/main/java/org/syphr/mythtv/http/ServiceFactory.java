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
package org.syphr.mythtv.http;

import org.syphr.mythtv.http.backend.BackendService;
import org.syphr.mythtv.http.backend.impl.BackendService0_25;
import org.syphr.mythtv.http.frontend.FrontendService;
import org.syphr.mythtv.http.frontend.impl.FrontendService0_25;

public class ServiceFactory
{
    public static BackendService getBackendInstance(ServiceVersion version)
    {
        switch (version)
        {
            case _0_25:
                return new BackendService0_25();

            default:
                throw new IllegalArgumentException("Unknown service version: " + version);
        }
    }

    public static FrontendService getFrontendInstance(ServiceVersion version)
    {
        switch (version)
        {
            case _0_25:
                return new FrontendService0_25();

            default:
                throw new IllegalArgumentException("Unknown service version: " + version);
        }
    }

    private ServiceFactory()
    {
        /*
         * Factory pattern
         */
    }
}
