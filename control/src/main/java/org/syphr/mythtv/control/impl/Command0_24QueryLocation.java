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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.socket.AbstractCommand;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.control.data.UIPathElement;
import org.syphr.mythtv.types.FrontendLocation;

/* default */class Command0_24QueryLocation extends AbstractCommand<List<UIPathElement>>
{
    private final boolean fullPath;
    private final boolean mainStackOnly;

    public Command0_24QueryLocation(Translator translator, boolean fullPath, boolean mainStackOnly)
    {
        super(translator);

        this.fullPath = fullPath;
        this.mainStackOnly = mainStackOnly;
    }

    @Override
    protected String getMessage()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("query location ").append(fullPath).append(' ').append(mainStackOnly);

        return builder.toString();
    }

    @Override
    public List<UIPathElement> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<UIPathElement> path = new ArrayList<UIPathElement>();
        if ("UNKNOWN".equals(response))
        {
            path.add(new UIPathElement(FrontendLocation.UNKNOWN));
            return path;
        }

        if (response.startsWith("Playback"))
        {
            path.add(new UIPathElement(FrontendLocation.PLAYBACK));
            return path;
        }

        for (String element : response.split("/"))
        {
            try
            {
                FrontendLocation location = getTranslator().toEnum(response, FrontendLocation.class);
                path.add(new UIPathElement(location));
            }
            catch (ProtocolException e)
            {
                /*
                 * This element is not a valid jump location, so treat it as a
                 * custom element.
                 */
                path.add(new UIPathElement(element));
            }
        }

        return path;
    }
}
