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

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.CommandUtils;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63QueryRecorderSetLiveRecording extends AbstractCommand63QueryRecorder<Void>
{
    public Command63QueryRecorderSetLiveRecording(Translator translator, Parser parser, int recorderId)
    {
        super(translator, parser, recorderId);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        /*
         * Note, the second argument here is documented in the code as expecting
         * 1, 0, or -1 where 1 means set to recording, 0 means set to cancelled,
         * and -1 means toggle based on the current state. However, this option
         * is read, but ignored, and the -1 argument is assumed.
         */
        return getParser().combineArguments("SET_LIVE_RECORDING", "-1");
    }

    @Override
    public Void parseResponse(String response) throws ProtocolException
    {
        CommandUtils.expectOk(response);
        return null;
    }
}
