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

import java.io.IOException;
import java.util.List;

import javax.xml.ws.ProtocolException;

import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.DriveInfo;

/* default */class Command63QueryFreeSpaceSummary extends AbstractCommand<DriveInfo>
{
    @Override
    protected String getMessage() throws org.syphr.mythtv.protocol.ProtocolException
    {
        return "QUERY_FREE_SPACE_SUMMARY";
    }

    @Override
    public DriveInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        List<String> args = Protocol63Utils.splitArguments(response);

        if (args.size() != 4)
        {
            throw new ProtocolException(response);
        }

        try
        {
            long totalSpace = ProtocolUtils.combineInts(Integer.parseInt(args.get(0)),
                                                        Integer.parseInt(args.get(1)));
            long usedSpace = ProtocolUtils.combineInts(Integer.parseInt(args.get(2)),
                                                       Integer.parseInt(args.get(3)));

            return new DriveInfo(null,
                                 null,
                                 false,
                                 -1,
                                 -1,
                                 -1,
                                 totalSpace,
                                 usedSpace);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
