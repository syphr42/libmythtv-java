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

import org.syphr.mythtv.data.ChannelQuery;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63QueryRecorderCheckChannelPrefix extends AbstractCommand63QueryRecorder<ChannelQuery>
{
    private final String channelNumberPrefix;

    public Command63QueryRecorderCheckChannelPrefix(Translator translator,
                                                    Parser parser,
                                                    int recorderId,
                                                    String channelNumberPrefix)
    {
        super(translator, parser, recorderId);

        this.channelNumberPrefix = channelNumberPrefix;
    }

    protected String getChannelNumberPrefix()
    {
        return channelNumberPrefix;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return getParser().combineArguments("CHECK_CHANNEL_PREFIX", getChannelNumberPrefix());
    }

    @Override
    public ChannelQuery parseResponse(String response) throws ProtocolException
    {
        List<String> args = getParser().splitArguments(response);

        if (args.size() != 4)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        String spacer = args.get(3);
        if ("X".equals(spacer))
        {
            spacer = "";
        }

        try
        {
            return new ChannelQuery(getChannelNumberPrefix(),
                                    getTranslator().toBooleanFromInt(args.get(0)),
                                    Integer.parseInt(args.get(1)),
                                    getTranslator().toBooleanFromInt(args.get(2)),
                                    spacer);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
