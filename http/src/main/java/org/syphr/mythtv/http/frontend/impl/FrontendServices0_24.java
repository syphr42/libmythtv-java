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
package org.syphr.mythtv.http.frontend.impl;

import java.io.IOException;

import org.syphr.mythtv.http.ServiceVersionException;
import org.syphr.mythtv.http.frontend.FrontendService;

public class FrontendServices0_24 extends AbstractFrontendServices
{
    private final FrontendService frontendService;

    public FrontendServices0_24()
    {
        /*
         * Services are unsupported in 0.24 so there is no actual functionality.
         */
        frontendService = new FrontendService0_24();
    }

    @Override
    public void configure(String host) throws ServiceVersionException, IOException
    {
        configure(host, 0);
    }

    @Override
    public void configure(String host, int port) throws ServiceVersionException, IOException
    {
        // NOOP
    }

    @Override
    public FrontendService getFrontendService()
    {
        return frontendService;
    }
}
