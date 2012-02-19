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
package org.syphr.mythtv.protocol.impl;

import java.io.IOException;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;

/* default */abstract class AbstractCommand63QueryRecorder<T> extends AbstractProtocolCommand<T>
{
    private final int recorderId;

    public AbstractCommand63QueryRecorder(Translator translator, Parser parser, int recorderId)
    {
        super(translator, parser);
        this.recorderId = recorderId;
    }

    public int getRecorderId()
    {
        return recorderId;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("QUERY_RECORDER " + recorderId, getSubCommand());
    }

    @Override
    public T send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("bad".equals(response))
        {
            throw new CommandException("Unknown recorder ID: " + getRecorderId());
        }

        return parseResponse(response);
    }

    protected abstract String getSubCommand() throws ProtocolException;

    protected abstract T parseResponse(String response) throws ProtocolException, CommandException;
}
