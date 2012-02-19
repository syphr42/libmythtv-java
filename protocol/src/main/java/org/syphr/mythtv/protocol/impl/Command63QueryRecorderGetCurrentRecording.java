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

import java.util.List;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.types.RecordingType;

/* default */class Command63QueryRecorderGetCurrentRecording extends AbstractCommand63QueryRecorder<Program>
{
    public Command63QueryRecorderGetCurrentRecording(Translator translator, Parser parser, int recorderId)
    {
        super(translator, parser, recorderId);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return "GET_CURRENT_RECORDING";
    }

    @Override
    protected Program parseResponse(String response) throws ProtocolException
    {
        List<String> args = getParser().splitArguments(response);

        Program program = getParser().parseProgramInfo(args);
        if (RecordingType.NOT_RECORDING.equals(program.getRecType()))
        {
            return null;
        }

        return program;
    }
}
