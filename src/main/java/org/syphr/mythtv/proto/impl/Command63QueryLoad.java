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
package org.syphr.mythtv.proto.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.Load;
import org.syphr.mythtv.proto.types.LoadCategory;

/* default */class Command63QueryLoad implements Command<Load>
{
    @SuppressWarnings("unchecked")
    @Override
    public Load send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait("QUERY_LOAD");
        List<String> args = Protocol63Utils.getArguments(response);

        if (args.size() != 3)
        {
            throw new ProtocolException(response);
        }

        try
        {
            return new Load(Pair.of(LoadCategory.ONE_MINUTE, Double.parseDouble(args.get(0))),
                            Pair.of(LoadCategory.FIVE_MINUTES, Double.parseDouble(args.get(1))),
                            Pair.of(LoadCategory.FIFTEEN_MINUTES, Double.parseDouble(args.get(2))));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
