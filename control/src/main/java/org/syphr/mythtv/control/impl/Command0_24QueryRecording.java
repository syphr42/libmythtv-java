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
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.DateUtils;

/* default */class Command0_24QueryRecording extends AbstractCommand<Program>
{
    private final int channelId;
    private final Date recStartTs;

    public Command0_24QueryRecording(int channelId, Date recStartTs)
    {
        this.channelId = channelId;
        this.recStartTs = recStartTs;
    }

    @Override
    protected String getMessage()
    {
        return "query recording "
               + channelId
               + " "
               + DateUtils.getIsoDateFormat().format(recStartTs);
    }

    @Override
    public Program send(SocketManager socketManager) throws IOException
    {
        String response = Control0_24Utils.getResponseMaybe(socketManager, getMessage());
        List<Program> list = Control0_24Utils.parseRecordings(response);

        return list.isEmpty() ? null : list.get(0);
    }
}
