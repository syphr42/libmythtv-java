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
import java.net.URI;

import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63QueryFileHash extends AbstractProtocolCommand<String>
{
    private final URI filename;
    private final String storageGroup;

    public Command63QueryFileHash(Translator translator,
                                  Parser parser,
                                  URI filename,
                                  String storageGroup)
    {
        super(translator, parser);

        this.filename = filename;
        this.storageGroup = storageGroup;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("QUERY_FILE_HASH",
                                            filename.getPath(),
                                            storageGroup);
    }

    @Override
    public String send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("NULL".equals(response))
        {
            throw new CommandException("The backend was unable to compute the hash");
        }

        return response;
    }
}
