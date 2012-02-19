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

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command63QuerySetting extends AbstractProtocolCommand<String>
{
    private final String host;
    private final String name;

    public Command63QuerySetting(Translator translator,
                                 Parser parser,
                                 String host,
                                 String name)
    {
        super(translator, parser);

        this.host = host;
        this.name = name;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("QUERY_SETTING ");
        builder.append(host);
        builder.append(' ');
        builder.append(name);

        return builder.toString();
    }

    @Override
    public String send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("-1".equals(response))
        {
            return null;
        }

        return response;
    }
}
