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
import java.util.List;

import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63MessageSetVerbose extends AbstractProtocolCommand<Void>
{
    private final List<Verbose> options;

    public Command63MessageSetVerbose(Translator translator, Parser parser, List<Verbose> options)
    {
        super(translator, parser);

        this.options = options;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("MESSAGE",
                                            "SET_VERBOSE "
                                                    + getTranslator().toString(options,
                                                                               ","));
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("OK".equals(response))
        {
            return null;
        }

        if ("Failed".equals(response))
        {
            throw new CommandException("Unable to set log verbose options");
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
