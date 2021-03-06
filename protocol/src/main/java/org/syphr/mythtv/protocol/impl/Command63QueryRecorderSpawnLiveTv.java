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

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Channel;

/* default */class Command63QueryRecorderSpawnLiveTv extends AbstractCommand63QueryRecorder<Void>
{
    private final String chainId;
    private final boolean pip;
    private final Channel startChannel;

    public Command63QueryRecorderSpawnLiveTv(Translator translator,
                                             Parser parser,
                                             int recorderId,
                                             String chainId,
                                             boolean pip,
                                             Channel startChannel)
    {
        super(translator, parser, recorderId);

        this.chainId = chainId;
        this.pip = pip;
        this.startChannel = startChannel;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return getParser().combineArguments("SPAWN_LIVETV",
                                            chainId,
                                            pip ? "1" : "0",
                                            startChannel.getNumber());
    }

    @Override
    protected Void parseResponse(String response) throws ProtocolException, CommandException
    {
        if (!"ok".equals(response))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        return null;
    }
}
