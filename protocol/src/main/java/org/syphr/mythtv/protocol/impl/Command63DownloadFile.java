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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command63DownloadFile extends AbstractProtocolCommand<URI>
{
    private final URL url;
    private final String storageGroup;
    private final URI filename;
    private final boolean now;

    public Command63DownloadFile(Translator translator,
                                 Parser parser,
                                 URL url,
                                 String storageGroup,
                                 URI filename,
                                 boolean now)
    {
        super(translator, parser);

        this.url = url;
        this.storageGroup = storageGroup;
        this.filename = filename;
        this.now = now;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        String command = now ? "DOWNLOAD_FILE_NOW" : "DOWNLOAD_FILE";
        return getParser().combineArguments(command,
                                            url.toString(),
                                            storageGroup,
                                            filename.getPath());
    }

    @Override
    public URI send(SocketManager socketManager) throws IOException, CommandException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("ERROR".equals(response))
        {
            throw new CommandException("Error occurred while attempting download");
        }

        List<String> args = getParser().splitArguments(response);
        if (args.size() != 2 || !"OK".equals(args.get(0)))
        {
            throw new ProtocolException(response, Direction.RECEIVE);
        }

        try
        {
            return new URI(args.get(1));
        }
        catch (URISyntaxException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}
