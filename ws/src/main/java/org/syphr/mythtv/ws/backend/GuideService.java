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
package org.syphr.mythtv.ws.backend;

import java.util.Calendar;

import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.ws.backend.impl._0_25.guide.ProgramGuide;

//TODO remove versioned bean references
public interface GuideService
{
    public String getChannelIcon(Integer chanId, Integer width, Integer height);

    public Program getProgramDetails(Integer chanId, Calendar startTime);

    public ProgramGuide getProgramGuide(Calendar startTime,
                                        Calendar endTime,
                                        Integer startChanId,
                                        Integer numChannels,
                                        Boolean details);
}