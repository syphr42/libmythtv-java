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

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command66SetBookmark extends Command63SetBookmark
{
    public Command66SetBookmark(Translator translator,
                                Parser parser,
                                Channel channel,
                                Date recStartTs,
                                long location)
    {
        super(translator, parser, channel, recStartTs, location);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SET_BOOKMARK");
        builder.append(' ');
        builder.append(getChannel().getId());
        builder.append(' ');
        builder.append(TimeUnit.MILLISECONDS.toSeconds(getRecStartTs().getTime()));
        builder.append(' ');
        builder.append(getLocation());

        return builder.toString();
    }
}
