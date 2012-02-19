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

// TODO - the documentation and the code completely disagree on this method - looks like all the code
// does is issue a tuning request, nothing to do with signal monitoring rate
/* default */class Command63QueryRecorderSetSignalMonitoringRate extends AbstractCommand63QueryRecorder<Integer>
{
    private final int millis;

    public Command63QueryRecorderSetSignalMonitoringRate(Translator translator, Parser parser, int recorderId, int millis)
    {
        super(translator, parser, recorderId);

        this.millis = millis;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        /*
         * The second argument to this sub-command is referred to in MythTV as
         * "notifyFrontend" and the documentation states that 1 means send
         * SIGNAL events to the frontend using this recorder, 0 means send no
         * SIGNAL events, and -1 means use the current setting. However, the
         * code does not use this parameter.
         */
        return getParser().combineArguments("SET_SIGNAL_MONITORING_RATE",
                                            String.valueOf(millis),
                                            "-1");
    }

    @Override
    public Integer parseResponse(String response) throws ProtocolException, CommandException
    {
        try
        {
            int oldRate = Integer.parseInt(response);

            if (oldRate < 0)
            {
                throw new CommandException("Unable to set signal monitoring rate");
            }

            return oldRate;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
