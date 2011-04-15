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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.syphr.mythtv.data.VersionInfo;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command0_24QueryVersion extends AbstractCommand<VersionInfo>
{
    private static final Pattern VERSION_AND_SCM_PATTERN = Pattern.compile("(v(\\d+\\.\\d+)[^/]+)/(.*)");

    @Override
    protected String getMessage()
    {
        return "query version";
    }

    @Override
    public VersionInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());
        String[] args = response.split("\\s+");

        if (args.length != 6 || !"VERSION:".equals(args[0]))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        Matcher matcher = VERSION_AND_SCM_PATTERN.matcher(args[1]);
        if (!matcher.find())
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        String mythLongVersion = matcher.group(1);
        String mythShortVersion = matcher.group(2);
        String scmPath = matcher.group(3);

        String qtVersion = args[4].replace("QT/", "");
        String schemaVersion = args[5].replace("DBSchema/", "");

        return new VersionInfo(mythLongVersion,
                               mythShortVersion,
                               scmPath,
                               args[2],
                               args[3],
                               qtVersion,
                               schemaVersion);
    }
}
