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
package org.syphr.mythtv.proto.impl;

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;

/* default */abstract class AbstractCommand63QuerySg<T> implements Command<T>
{
    private final String message;

    public AbstractCommand63QuerySg(String host, String storageGroup, String path)
    {
        message = Protocol63Utils.getProtocolValue(getCommand(),
                                                   host,
                                                   storageGroup,
                                                   path);
    }

    @Override
    public T send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);
        List<String> args = Protocol63Utils.getArguments(response);

        if (args.isEmpty())
        {
            throw new ProtocolException(response);
        }

        String first = args.get(0);
        if ("SLAVE UNREACHABLE: ".equals(first) || "EMPTY_LIST".equals(first))
        {
            return null;
        }

        return parseResponse(response, args);
    }

    protected abstract String getCommand();

    protected abstract T parseResponse(String response, List<String> args) throws ProtocolException;
}
