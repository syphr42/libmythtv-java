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
package org.syphr.mythtv.control.impl;

import java.util.Date;

import org.syphr.mythtv.util.translate.DateUtils;

/* default */class Command0_24PlayProgram extends AbstractCommand0_24Play
{
    private final int channelId;
    private final Date recStartTs;
    private final boolean resume;

    public Command0_24PlayProgram(int channelId, Date recStartTs, boolean resume)
    {
        this.channelId = channelId;
        this.recStartTs = recStartTs;
        this.resume = resume;
    }

    @Override
    protected String getMessage()
    {
        return "play program " + channelId + " " + DateUtils.getIsoDateFormat().format(recStartTs)
               + " " + Control0_24Utils.getTranslator().toString(resume);
    }
}
