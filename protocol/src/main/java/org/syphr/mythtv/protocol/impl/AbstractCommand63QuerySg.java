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
import java.util.List;

import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */abstract class AbstractCommand63QuerySg<T> extends AbstractCommand<T>
{
    private final String host;
    private final String storageGroup;
    private final String path;

    public AbstractCommand63QuerySg(String host,
                                    String storageGroup,
                                    String path)
    {
        this.host = host;
        this.storageGroup = storageGroup;
        this.path = path;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return Protocol63Utils.combineArguments(getCommand(),
                                                host,
                                                storageGroup,
                                                path);
    }

    @Override
    public T send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.isEmpty())
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        String first = args.get(0);
        if ("SLAVE UNREACHABLE: ".equals(first))
        {
            if (args.size() != 2)
            {
                throw new ProtocolException(response, Direction.RECEIVE);
            }

            throw new CommandException("Slave backend \"" + args.get(1) + "\" is unreachable");
        }
        else if ("EMPTY_LIST".equals(first))
        {
            return null;
        }

        return parseResponse(response, args);
    }

    protected abstract String getCommand();

    protected abstract T parseResponse(String response, List<String> args) throws ProtocolException;
}
