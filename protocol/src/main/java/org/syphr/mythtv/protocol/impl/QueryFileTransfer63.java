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

import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.types.SeekOrigin;

public class QueryFileTransfer63 extends AbstractQueryFileTransfer
{
    public QueryFileTransfer63(int socketNumber,
                               long size,
                               SocketManager socketManager)
    {
        super(socketNumber, size, socketManager);
    }

    @Override
    public boolean isOpen() throws IOException
    {
        return new Command63QueryFileTransferIsOpen(getSocketNumber()).send(getSocketManager());
    }

    @Override
    public void done() throws IOException
    {
        new Command63QueryFileTransferDone(getSocketNumber()).send(getSocketManager());
    }

    @Override
    public long requestBlock(long bytes) throws IOException
    {
        return new Command63QueryFileTransferRequestBlock(getSocketNumber(),
                                                          bytes).send(getSocketManager());
    }

    @Override
    public long writeBlock(long bytes) throws IOException
    {
        return new Command63QueryFileTransferWriteBlock(getSocketNumber(),
                                                        bytes).send(getSocketManager());
    }

    @Override
    public long seek(long position, SeekOrigin origin, long curPosition) throws IOException
    {
        return new Command63QueryFileTransferSeek(getSocketNumber(), position, origin, curPosition).send(getSocketManager());
    }

    @Override
    public void setTimeout(boolean fast) throws IOException
    {
        new Command63QueryFileTransferSetTimeout(getSocketNumber(), fast).send(getSocketManager());
    }
}
