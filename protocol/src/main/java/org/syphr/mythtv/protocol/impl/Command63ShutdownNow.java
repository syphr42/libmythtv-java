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

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63ShutdownNow extends AbstractProtocolCommand<Void>
{
    private final String command;

    public Command63ShutdownNow(Translator translator, Parser parser, String command)
    {
        super(translator, parser);

        this.command = command;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("SHUTDOWN_NOW", command);
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException
    {
        socketManager.send(getMessage());
        return null;
    }
}
