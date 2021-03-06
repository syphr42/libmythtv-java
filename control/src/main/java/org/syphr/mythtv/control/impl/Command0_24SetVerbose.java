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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.AbstractCommand;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.types.Verbose;

/* default */class Command0_24SetVerbose extends AbstractCommand<Void>
{
    private final List<Verbose> options;

    public Command0_24SetVerbose(Translator translator, List<Verbose> options)
    {
        super(translator);

        this.options = options;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "set verbose " + getTranslator().toString(options, ",");
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if (response.startsWith("OK"))
        {
            return null;
        }

        if (response.startsWith("Failed"))
        {
            throw new CommandException("Unable to set log verbose options");
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
