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
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command0_24QueryRecordings extends AbstractCommand<List<Program>>
{
    @Override
    protected String getMessage() throws ProtocolException
    {
        return "query recordings";
    }

    @Override
    public List<Program> send(SocketManager socketManager) throws IOException
    {
        /*
         * Using a timeout here to avoid a bug in MythTV where having no
         * recordings causes the network control to not respond.
         */
        String response = socketManager.sendAndWait(getMessage(), 5, TimeUnit.SECONDS);

        /*
         * If there aren't any recordings (therefore the timeout was hit in the
         * previous send-and-wait), then the socket manager will be expecting
         * the next message that arrives to be an orphan connected to this
         * command that didn't come back in time. To get things back in sync, a
         * throwaway command will be sent (the help command) so that messages
         * get back in sync.
         */
        if (response.isEmpty())
        {
            socketManager.send("help");
        }

        return Control0_24Utils.parsePrograms(response);
    }
}
