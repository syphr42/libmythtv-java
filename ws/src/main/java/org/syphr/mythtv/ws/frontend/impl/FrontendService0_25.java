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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.data.Action;
import org.syphr.mythtv.ws.frontend.FrontendService;
import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.ArrayOfString;
import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.Frontend;
import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.FrontendActionList;
import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.FrontendServices;
import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.FrontendStatus;
import org.syphr.mythtv.ws.frontend.impl._0_25.frontend.MapOfStringAction;
import org.syphr.mythtv.ws.impl.AbstractService;
import org.syphr.mythtv.ws.impl.ServiceUtils;

public class FrontendService0_25 extends AbstractService implements FrontendService
{
    private static final String NAME = "Frontend";

    private static final String VERSION = "1.0";

    private final Frontend service;

    public FrontendService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        FrontendServices locator = new FrontendServices();
        service = locator.getBasicHttpBindingFrontend();

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
    public List<Action> getActionList(String context)
    {
        return toActionList(service.getActionList(context));
    }

    @Override
    public List<String> getContextList()
    {
        return toStringList(service.getContextList());
    }

    @Override
    public FrontendStatus getStatus()
    {
        return service.getStatus();
    }

    @Override
    public boolean playRecording(Integer chanID, Calendar startTime)
    {
        return ServiceUtils.toPrimitive(service.playRecording(chanID, startTime));
    }

    @Override
    public boolean playVideo(String id, Boolean useBookmark)
    {
        return ServiceUtils.toPrimitive(service.playVideo(id, useBookmark));
    }

    @Override
    public boolean sendAction(String action, String value, Long width, Long height)
    {
        return ServiceUtils.toPrimitive(service.sendAction(action, value, width, height));
    }

    @Override
    public boolean sendMessage(String message, Long timeout)
    {
        return ServiceUtils.toPrimitive(service.sendMessage(message, timeout));
    }

    protected List<Action> toActionList(FrontendActionList frontendActionList)
    {
        List<Action> list = new ArrayList<Action>();

        if (frontendActionList == null)
        {
            return list;
        }

        MapOfStringAction mapOfStringAction = frontendActionList.getActionList();
        if (mapOfStringAction == null)
        {
            return list;
        }

        List<org.syphr.mythtv.ws.frontend.impl._0_25.frontend.MapOfStringAction.Action> actions = mapOfStringAction.getActions();
        if (actions == null)
        {
            return list;
        }

        for (org.syphr.mythtv.ws.frontend.impl._0_25.frontend.MapOfStringAction.Action rAction : actions)
        {
            Action action = new Action();
            action.setValue(rAction.getKey());
            action.setDescription(rAction.getValue());

            list.add(action);
        }

        return list;
    }

    protected List<String> toStringList(ArrayOfString arrayOfString)
    {
        List<String> list = new ArrayList<String>();

        if (arrayOfString == null)
        {
            return list;
        }

        List<String> strings = arrayOfString.getStrings();
        if (strings == null)
        {
            return list;
        }

        list.addAll(strings);
        return list;
    }
}
