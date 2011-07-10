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

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command0_24QueryChannels extends AbstractCommand<List<Channel>>
{
    private final int start;
    private final int limit;

    public Command0_24QueryChannels(Translator translator)
    {
        this(translator, -1, -1);
    }

    public Command0_24QueryChannels(Translator translator, int start, int limit)
    {
        super(translator);

        this.start = start;
        this.limit = limit;
    }

    @Override
    protected String getMessage()
    {
        String message = "query channels";

        if (limit >= 0)
        {
            message += " " + start + " " + limit;
        }

        return message;
    }

    @Override
    public List<Channel> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        return Control0_24Utils.parseChannels(response);
    }
}
