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

/* default */class Command63FreeTuner extends AbstractCommand<Boolean>
{
    private final int recorderId;

    public Command63FreeTuner(int recorderId)
    {
        this.recorderId = recorderId;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        return "FREE_TUNER " + recorderId;
    }

    @Override
    public Boolean send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        if (!"OK".equals(response))
        {
            return true;
        }

        if (!"FAILED".equals(response))
        {
            return false;
        }

        throw new ProtocolException(response);
    }
}
