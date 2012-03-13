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
import java.util.Calendar;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.http.ServiceVersionException;
import org.syphr.mythtv.http.backend.GuideService;
import org.syphr.mythtv.http.backend.impl._0_25.guide.Guide;
import org.syphr.mythtv.http.backend.impl._0_25.guide.GuideServices;
import org.syphr.mythtv.http.backend.impl._0_25.guide.Program;
import org.syphr.mythtv.http.backend.impl._0_25.guide.ProgramGuide;
import org.syphr.mythtv.http.impl.AbstractService;

public class GuideService0_25 extends AbstractService implements GuideService
{
    private static final String NAME = "Guide";

    private static final String VERSION = "1.0";

    private final Guide service;

    public GuideService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        GuideServices locator = new GuideServices();
        service = locator.getBasicHttpBindingGuide();

        configureAndVerify(host, port, (BindingProvider)service);
    }

    @Override
    protected String getName()
    {
        return NAME;
    }

    @Override
    protected String getVersion()
    {
        return VERSION;
    }

    @Override
    public String getChannelIcon(Integer chanId, Integer width, Integer height)
    {
        return service.getChannelIcon(chanId, width, height);
    }

    @Override
    public Program getProgramDetails(Integer chanId, Calendar startTime)
    {
        return service.getProgramDetails(chanId, startTime);
    }

    @Override
    public ProgramGuide getProgramGuide(Calendar startTime,
                                        Calendar endTime,
                                        Integer startChanId,
                                        Integer numChannels,
                                        Boolean details)
    {
        return service.getProgramGuide(startTime, endTime, startChanId, numChannels, details);
    }
}
