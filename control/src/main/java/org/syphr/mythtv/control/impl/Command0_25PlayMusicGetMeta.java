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

import org.syphr.mythtv.data.MusicInfo;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.socket.SocketManager;

/* default */class Command0_25PlayMusicGetMeta extends AbstractCommand<MusicInfo>
{
    private static final Pattern MUSIC_INFO_PATTERN = Pattern.compile("(.*)\\sby\\s(.*)\\sfrom\\s(.*)");

    @Override
    protected String getMessage()
    {
        return "play music getmeta";
    }

    @Override
    public MusicInfo send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("unknown".equals(response) || "Unknown Track2".equals(response))
        {
            return new MusicInfo("Unknown", "Unknown", "Unknown");
        }

        Matcher matcher = MUSIC_INFO_PATTERN.matcher(response);
        if (!matcher.find())
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        return new MusicInfo(matcher.group(1),
                             matcher.group(2),
                             matcher.group(3));
    }
}
