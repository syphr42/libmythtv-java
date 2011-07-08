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

import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public abstract class AbstractRecorderProtocol
{
    private final Translator translator;
    private final Parser parser;
    private final int recorderId;
    private final SocketManager socketManager;

    public AbstractRecorderProtocol(Translator translator,
                                    Parser parser,
                                    int recorderId,
                                    SocketManager socketManager)
    {
        this.translator = translator;
        this.parser = parser;
        this.recorderId = recorderId;
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

    protected int getRecorderId()
    {
        return recorderId;
    }

    protected SocketManager getSocketManager()
    {
        return socketManager;
    }
}
