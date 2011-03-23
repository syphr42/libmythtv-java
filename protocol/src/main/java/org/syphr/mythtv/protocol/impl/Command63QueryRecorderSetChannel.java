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

import org.syphr.mythtv.protocol.ProtocolException;

/* default */class Command63QueryRecorderSetChannel extends AbstractCommand63QueryRecorder<Void>
{
    private final String chanNum;

    public Command63QueryRecorderSetChannel(int recorderId, String chanNum)
    {
        super(recorderId);
        this.chanNum = chanNum;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("SET_CHANNEL", chanNum);
    }

    @Override
    public Void parseResponse(String response) throws ProtocolException
    {
        ProtocolUtils.expectOk(response);
        return null;
    }
}
