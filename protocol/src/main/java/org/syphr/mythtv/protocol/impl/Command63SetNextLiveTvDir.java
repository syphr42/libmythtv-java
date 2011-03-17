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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;

import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.ProtocolException.Direction;

/* default */class Command63SetNextLiveTvDir extends AbstractCommand<Void>
{
    private final int recorderId;
    private final String path;

    public Command63SetNextLiveTvDir(int recorderId, String path)
    {
        this.recorderId = recorderId;
        this.path = path;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SET_NEXT_LIVETV_DIR ");
        builder.append(recorderId);
        builder.append(' ');
        builder.append(path);

        return builder.toString();
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("OK".equals(response))
        {
            return null;
        }

        if ("bad".equals(response))
        {
            throw new CommandException("Unable to set next live TV directory");
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
