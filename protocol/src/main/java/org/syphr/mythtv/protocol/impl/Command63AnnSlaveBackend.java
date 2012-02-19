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

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Program;

/* default */class Command63AnnSlaveBackend extends AbstractCommandOkResponse
{
    private final InetAddress address;
    private final Program[] recordings;

    public Command63AnnSlaveBackend(Translator translator,
                                    Parser parser,
                                    InetAddress address,
                                    Program... recordings)
    {
        super(translator, parser);

        this.address = address;
        this.recordings = recordings;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ANN SlaveBackend ");
        builder.append(address.getHostName());
        builder.append(' ');
        builder.append(address.getHostAddress());

        List<String> args = new ArrayList<String>();
        args.add(builder.toString());

        for (Program recording : recordings)
        {
            args.addAll(getParser().extractProgramInfo(recording));
        }

        return getParser().combineArguments(args);
    }
}
