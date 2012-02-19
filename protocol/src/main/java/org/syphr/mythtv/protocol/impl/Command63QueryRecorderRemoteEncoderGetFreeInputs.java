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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.InputInfo;

/* default */class Command63QueryRecorderRemoteEncoderGetFreeInputs extends
                                                                    AbstractCommand63QueryRecorder<List<InputInfo>>
{
    private final Set<Integer> excludedCardIds;

    public Command63QueryRecorderRemoteEncoderGetFreeInputs(Translator translator,
                                                            Parser parser,
                                                            int recorderId,
                                                            Set<Integer> excludedCardIds)
    {
        super(translator, parser, recorderId);

        this.excludedCardIds = excludedCardIds;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        List<String> excludedCardIdStrings = new ArrayList<String>();
        if (excludedCardIds != null)
        {
            for (Integer cardId : excludedCardIds)
            {
                excludedCardIdStrings.add(String.valueOf(cardId));
            }
        }

        return getParser().combineArguments("GET_FREE_INPUTS",
                                            getParser().combineArguments(excludedCardIdStrings));
    }

    @Override
    protected List<InputInfo> parseResponse(String response) throws ProtocolException
    {
        List<InputInfo> freeInputs = new ArrayList<InputInfo>();

        if ("EMPTY_LIST".equals(response))
        {
            return freeInputs;
        }

        List<String> args = getParser().splitArguments(response);

        if (args.size() % 5 != 0)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            Iterator<String> iter = args.iterator();
            while (iter.hasNext())
            {
                freeInputs.add(new InputInfo(iter.next(),
                                             Integer.parseInt(iter.next()),
                                             Integer.parseInt(iter.next()),
                                             Integer.parseInt(iter.next()),
                                             Integer.parseInt(iter.next())));
            }

            return freeInputs;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
