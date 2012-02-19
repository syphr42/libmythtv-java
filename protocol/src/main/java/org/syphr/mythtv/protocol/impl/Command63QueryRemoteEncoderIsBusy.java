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

import org.apache.commons.lang3.tuple.Pair;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.TunedInputInfo;

/* default */class Command63QueryRemoteEncoderIsBusy extends
                                                     AbstractCommand63QueryRemoteEncoder<Pair<Boolean, TunedInputInfo>>
{
    private final int withinSeconds;

    public Command63QueryRemoteEncoderIsBusy(Translator translator,
                                             Parser parser,
                                             int recorderId,
                                             int withinSeconds)
    {
        super(translator, parser, recorderId);

        this.withinSeconds = withinSeconds;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return getParser().combineArguments("IS_BUSY", String.valueOf(withinSeconds));
    }

    @Override
    protected Pair<Boolean, TunedInputInfo> parseResponse(String response) throws ProtocolException,
                                                                          CommandException
    {
        List<String> args = getParser().splitArguments(response);

        if (args.size() != 7)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        boolean busy;
        if ("0".equals(args.get(0)))
        {
            busy = false;
        }
        else if ("1".equals(args.get(0)))
        {
            busy = true;
        }
        else
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            int i = 1;
            TunedInputInfo inputInfo = new TunedInputInfo(args.get(i++),
                                                          Integer.parseInt(args.get(i++)),
                                                          Integer.parseInt(args.get(i++)),
                                                          Integer.parseInt(args.get(i++)),
                                                          Integer.parseInt(args.get(i++)),
                                                          Integer.parseInt(args.get(i++)));

            return Pair.of(busy, inputInfo);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
