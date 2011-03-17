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

import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.ProtocolException.Direction;

/* default */class Command63QueryFileTransferIsOpen extends AbstractCommand<Boolean>
{
    private final int socketNumber;

    public Command63QueryFileTransferIsOpen(int socketNumber)
    {
        this.socketNumber = socketNumber;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return Protocol63Utils.combineArguments("QUERY_FILETRANSFER "
                                                + socketNumber, "IS_OPEN");
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
