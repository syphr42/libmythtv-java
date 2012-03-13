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
package org.syphr.mythtv.http.backend.impl;

import java.io.IOException;

import org.syphr.mythtv.http.ServiceVersionException;
import org.syphr.mythtv.http.backend.CaptureService;
import org.syphr.mythtv.http.backend.MythService;

public class BackendService0_25 extends AbstractBackendServices
{
    private CaptureService captureService;
    private MythService mythService;

    @Override
    public void configure(String host) throws ServiceVersionException, IOException
    {
        configure(host, 0);
    }

    @Override
    public void configure(String host, int port) throws ServiceVersionException, IOException
    {
        captureService = new CaptureService0_25(host, getPort(port));
        mythService = new MythService0_25(host, getPort(port));
    }

    @Override
    public CaptureService getCaptureService()
    {
        return captureService;
    }

    @Override
    public MythService getMythService()
    {
        return mythService;
    }
}
