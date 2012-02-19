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

import java.net.URI;

import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandler;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.types.FileTransferType;

/* default */class Command70AnnFileTransfer extends Command66AnnFileTransfer
{
    public Command70AnnFileTransfer(Translator translator,
                                    Parser parser,
                                    String host,
                                    FileTransferType type,
                                    boolean readAhead,
                                    long timeout,
                                    URI uri,
                                    String storageGroup,
                                    SocketManager commandSocketManager,
                                    UnsupportedHandler unsupported)
    {
        super(translator,
              parser,
              host,
              type,
              readAhead,
              timeout,
              uri,
              storageGroup,
              commandSocketManager,
              unsupported);
    }

    @Override
    protected QueryFileTransfer createQueryFileTransfer(int socketNumber, long size)
    {
        return new QueryFileTransfer70(getTranslator(),
                                       getParser(),
                                       socketNumber,
                                       size,
                                       getType(),
                                       getCommandSocketManager(),
                                       getUnsupported());
    }
}
