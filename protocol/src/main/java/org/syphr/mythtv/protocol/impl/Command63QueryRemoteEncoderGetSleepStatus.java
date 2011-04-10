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

import org.syphr.mythtv.types.SleepStatus;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;

/* default */class Command63QueryRemoteEncoderGetSleepStatus extends AbstractCommand63QueryRemoteEncoder<SleepStatus>
{
    public Command63QueryRemoteEncoderGetSleepStatus(int recorderId)
    {
        super(recorderId);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return "GET_SLEEPSTATUS";
    }

    @Override
    protected SleepStatus parseResponse(String response) throws ProtocolException, CommandException
    {
        return Protocol63Utils.getTranslator().toEnum(response, SleepStatus.class);
    }
}
