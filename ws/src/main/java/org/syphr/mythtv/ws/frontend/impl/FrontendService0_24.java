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
package org.syphr.mythtv.ws.frontend.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.FrontendActionList;
import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.FrontendStatus;

public class FrontendService0_24 extends AbstractFrontendService
{
    @Override
    public FrontendActionList getActionList(String context)
    {
        handleUnsupported("get action list");
        return null;
    }

    @Override
    public List<String> getContextList()
    {
        handleUnsupported("get context list");
        return Collections.emptyList();
    }

    @Override
    public FrontendStatus getStatus()
    {
        handleUnsupported("get status");
        return null;
    }

    @Override
    public boolean playRecording(Integer chanID, Calendar startTime)
    {
        handleUnsupported("play recording");
        return false;
    }

    @Override
    public boolean playVideo(String id, Boolean useBookmark)
    {
        handleUnsupported("play video");
        return false;
    }

    @Override
    public boolean sendAction(String action, String value, Long width, Long height)
    {
        handleUnsupported("send action");
        return false;
    }

    @Override
    public boolean sendMessage(String message, Long timeout)
    {
        handleUnsupported("send message");
        return false;
    }

    @Override
    protected String getVersion()
    {
        return null;
    }
}
