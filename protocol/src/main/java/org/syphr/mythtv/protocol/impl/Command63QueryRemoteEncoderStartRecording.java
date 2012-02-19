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

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Program;

/* default */class Command63QueryRemoteEncoderStartRecording extends AbstractCommand63QueryRemoteEncoder<Boolean>
{
    private final Program program;

    public Command63QueryRemoteEncoderStartRecording(Translator translator, Parser parser, int recorderId, Program program)
    {
        super(translator, parser, recorderId);

        this.program = program;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        List<String> args = new ArrayList<String>();
        args.add("START_RECORDING");
        args.addAll(getParser().extractProgramInfo(program));

        return getParser().combineArguments(args);
    }

    @Override
    protected Boolean parseResponse(String response) throws ProtocolException, CommandException
    {
        if ("-1".equals(response) || "0".equals(response))
        {
            return false;
        }

        if ("1".equals(response))
        {
            return true;
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
