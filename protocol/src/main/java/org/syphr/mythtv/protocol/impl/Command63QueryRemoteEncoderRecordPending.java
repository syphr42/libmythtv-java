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

import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.socket.CommandUtils;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Program;

/* default */class Command63QueryRemoteEncoderRecordPending extends AbstractCommand63QueryRemoteEncoder<Void>
{
    private final int secondsLeft;
    private final boolean hasLater;
    private final Program program;

    public Command63QueryRemoteEncoderRecordPending(Translator translator,
                                                    Parser parser,
                                                    int recorderId,
                                                    int secondsLeft,
                                                    boolean hasLater,
                                                    Program program)
    {
        super(translator, parser, recorderId);

        this.secondsLeft = secondsLeft;
        this.hasLater = hasLater;
        this.program = program;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        List<String> args = new ArrayList<String>();
        args.add("RECORD_PENDING");
        args.add(String.valueOf(secondsLeft));
        args.add(String.valueOf(hasLater));
        args.addAll(getParser().extractProgramInfo(program));

        return getParser().combineArguments(args);
    }

    @Override
    protected Void parseResponse(String response) throws ProtocolException
    {
        CommandUtils.expectOk(response);
        return null;
    }
}
