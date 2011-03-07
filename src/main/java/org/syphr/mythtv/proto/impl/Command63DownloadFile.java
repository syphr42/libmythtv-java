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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;

/* default */class Command63DownloadFile implements Command<URI>
{
    private final String message;

    public Command63DownloadFile(URL url,
                                 String storageGroup,
                                 URI filename,
                                 boolean now)
    {
        String command = now ? "DOWNLOAD_FILE_NOW" : "DOWNLOAD_FILE";

        message = Protocol63Utils.getProtocolValue(command,
                                                   url.toString(),
                                                   storageGroup,
                                                   filename.getPath());
    }

    @Override
    public URI send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(message);

        if ("ERROR".equals(response))
        {
            return null;
        }

        List<String> args = Protocol63Utils.getArguments(response);
        if (args.size() != 2 || !"OK".equals(args.get(0)))
        {
            throw new ProtocolException(response);
        }

        try
        {
            return new URI(args.get(1));
        }
        catch (URISyntaxException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
