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
import java.util.List;

import org.syphr.mythtv.commons.socket.Command;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.control.data.UIPathElement;
import org.syphr.mythtv.types.FrontendLocation;

/* default */class Command0_24QueryLocationSimple implements Command<FrontendLocation>
{
    private final Command0_24QueryLocation delegate;

    public Command0_24QueryLocationSimple(Translator translator)
    {
        delegate = new Command0_24QueryLocation(translator, false, true);
    }

    @Override
    public FrontendLocation send(SocketManager socketManager) throws IOException
    {
        List<UIPathElement> path = delegate.send(socketManager);
        if (path.isEmpty())
        {
            return FrontendLocation.UNKNOWN;
        }

        UIPathElement element = path.get(0);
        if (!element.isLocation())
        {
            return FrontendLocation.UNKNOWN;
        }

        return element.getLocation();
    }
}
