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

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.SocketManager;

/* default */class Command63QueryFileTransferWriteBlock extends AbstractCommand<Long>
{
    private final int socketNumber;
    private final long bytes;

    public Command63QueryFileTransferWriteBlock(int socketNumber, long bytes)
    {
        this.socketNumber = socketNumber;
        this.bytes = bytes;
    }

    @Override
    public String getMessage() throws ProtocolException
    {
        return Protocol63Utils.getProtocolValue("QUERY_FILETRANSFER "
                                                        + socketNumber,
                                                "WRITE_BLOCK",
                                                String.valueOf(bytes));
    }

    @Override
    public Long send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        try
        {
            return Long.parseLong(response);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
