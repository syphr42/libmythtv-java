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

import org.syphr.mythtv.protocol.types.TvState;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */abstract class AbstractCommand63QueryRemoteEncoder<T> extends AbstractCommand<T>
{
    private final int recorderId;

    public AbstractCommand63QueryRemoteEncoder(int recorderId)
    {
        this.recorderId = recorderId;
    }

    public int getRecorderId()
    {
        return recorderId;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("QUERY_REMOTEENCODER " + recorderId, getSubCommand());
    }

    @Override
    public T send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        try
        {
            if (TvState.ERROR.equals(Protocol63Utils.getTranslator().toEnum(response, TvState.class)))
            {
                throw new CommandException("Unknown recorder ID: " + getRecorderId());
            }
        }
        catch (ProtocolException e)
        {
            /*
             * This means that the response was not a valid TvState, which is expected
             * when the response is not an error. Therefore, we eat this exception (since
             * no error actually occurred).
             */
        }

        return parseResponse(response);
    }

    protected abstract String getSubCommand() throws ProtocolException;

    protected abstract T parseResponse(String response) throws ProtocolException, CommandException;
}
