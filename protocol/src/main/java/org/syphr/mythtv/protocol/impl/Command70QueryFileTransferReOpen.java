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

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command70QueryFileTransferReOpen extends AbstractProtocolCommand<Boolean>
{
    private final int socketNumber;
    private final String filename;

    public Command70QueryFileTransferReOpen(Translator translator,
                                            Parser parser,
                                            int socketNumber,
                                            String filename)
    {
        super(translator, parser);

        this.socketNumber = socketNumber;
        this.filename = filename;
    }

    protected String getFilename()
    {
        return filename;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return getParser().combineArguments("QUERY_FILETRANSFER " + socketNumber,
                                            "REOPEN",
                                            getFilename());
    }

    @Override
    public Boolean send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if ("0".equals(response))
        {
            return false;
        }

        if ("1".equals(response))
        {
            return true;
        }

        throw new ProtocolException(response, Direction.RECEIVE);
    }
}
