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

import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public abstract class AbstractQueryFileTransfer implements QueryFileTransfer
{
    private final Translator translator;
    private final Parser parser;
    private final int socketNumber;
    private final long size;
    private final SocketManager socketManager;

    public AbstractQueryFileTransfer(Translator translator,
                                     Parser parser,
                                     int socketNumber,
                                     long size,
                                     SocketManager socketManager)
    {
        this.translator = translator;
        this.parser = parser;
        this.socketNumber = socketNumber;
        this.size = size;
        this.socketManager = socketManager;
    }

    protected Translator getTranslator()
    {
        return translator;
    }

    protected Parser getParser()
    {
        return parser;
    }

    protected int getSocketNumber()
    {
        return socketNumber;
    }

    @Override
    public long getSize()
    {
        return size;
    }

    protected SocketManager getSocketManager()
    {
        return socketManager;
    }
}
