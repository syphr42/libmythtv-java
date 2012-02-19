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
package org.syphr.mythtv.control.impl;

import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.AbstractCommand;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Load;
import org.syphr.mythtv.types.LoadCategory;

/* default */class Command0_24QueryLoad extends AbstractCommand<Load>
{
    public Command0_24QueryLoad(Translator translator)
    {
        super(translator);
    }

    @Override
    protected String getMessage()
    {
        return "query load";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Load send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        String[] args = response.split("\\s+");

        if (args.length != 3)
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            return new Load(Pair.of(LoadCategory.ONE_MINUTE, Double.parseDouble(args[0])),
                            Pair.of(LoadCategory.FIVE_MINUTES, Double.parseDouble(args[1])),
                            Pair.of(LoadCategory.FIFTEEN_MINUTES, Double.parseDouble(args[2])));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
