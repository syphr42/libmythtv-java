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
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;
import org.syphr.mythtv.proto.SocketManager;

/* default */class Command63GetFreeRecorderList extends AbstractCommand<List<Integer>>
{
    @Override
    protected String getMessage() throws ProtocolException
    {
        return "GET_FREE_RECORDER_LIST";
    }

    @Override
    public List<Integer> send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        List<Integer> freeRecorders = new ArrayList<Integer>();

        if ("0".equals(response))
        {
            return freeRecorders;
        }

        List<String> args = Protocol63Utils.getArguments(response);
        try
        {
            for (String arg : args)
            {
                freeRecorders.add(Integer.valueOf(arg));
            }

            return freeRecorders;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
