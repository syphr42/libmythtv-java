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

/* default */class Command66QueryRecorderGetKeyframePos extends Command63QueryRecorderGetKeyframePos
{
    public Command66QueryRecorderGetKeyframePos(Translator translator,
                                                Parser parser,
                                                int recorderId,
                                                long desiredPosition)
    {
        super(translator, parser, recorderId, desiredPosition);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return getParser().combineArguments("GET_KEYFRAME_POS",
                                            String.valueOf(getDesiredPosition()));
    }

    @Override
    public Long parseResponse(String response) throws ProtocolException, CommandException
    {
        if ("-1".equals(response))
        {
            throw new CommandException("Unable to determine the keyframe position");
        }

        try
        {
            return Long.parseLong(response);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
