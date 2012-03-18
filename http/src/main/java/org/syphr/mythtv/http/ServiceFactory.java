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

import org.syphr.mythtv.http.backend.BackendServices;
import org.syphr.mythtv.http.backend.impl.BackendServices0_24;
import org.syphr.mythtv.http.backend.impl.BackendServices0_25;
import org.syphr.mythtv.http.frontend.FrontendServices;
import org.syphr.mythtv.http.frontend.impl.FrontendServices0_24;
import org.syphr.mythtv.http.frontend.impl.FrontendServices0_25;

public class ServiceFactory
{
    public static BackendServices getBackendInstance(ServiceVersion version)
    {
        switch (version)
        {
            case _0_24:
                return new BackendServices0_24();

            case _0_25:
                return new BackendServices0_25();

            default:
                throw new IllegalArgumentException("Unknown service version: " + version);
        }
    }

    public static FrontendServices getFrontendInstance(ServiceVersion version)
    {
        switch (version)
        {
            case _0_24:
                return new FrontendServices0_24();

            case _0_25:
                return new FrontendServices0_25();

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
