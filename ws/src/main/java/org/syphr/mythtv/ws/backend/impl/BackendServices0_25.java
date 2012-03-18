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
package org.syphr.mythtv.ws.backend.impl;

import java.io.IOException;

import org.syphr.mythtv.ws.ServiceVersionException;

public class BackendServices0_25 extends AbstractBackendServices
{
    @Override
    public void configure(String host, int port) throws ServiceVersionException, IOException
    {
        setCaptureService(new CaptureService0_25(host, getPort(port)));
        setChannelService(new ChannelService0_25(host, getPort(port)));
        setContentService(new ContentService0_25(host, getPort(port)));
        setDvrService(new DvrService0_25(host, getPort(port)));
        setGuideService(new GuideService0_25(host, getPort(port)));
        setMythService(new MythService0_25(host, getPort(port)));
        setVideoService(new VideoService0_25(host, getPort(port)));
    }
}
