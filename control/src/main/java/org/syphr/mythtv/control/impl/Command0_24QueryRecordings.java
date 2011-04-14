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

import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command0_24QueryRecordings extends AbstractCommand<List<Program>>
{
    @Override
    protected String getMessage()
    {
        return "query recordings";
    }

    @Override
    public List<Program> send(SocketManager socketManager) throws IOException
    {
        String response = Control0_24Utils.getResponseMaybeNothing(socketManager, getMessage());
        return Control0_24Utils.parseRecordings(response);
    }
}
