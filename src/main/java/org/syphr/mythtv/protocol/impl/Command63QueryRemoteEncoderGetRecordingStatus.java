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
import org.syphr.mythtv.protocol.types.RecordingStatus;

/* default */class Command63QueryRemoteEncoderGetRecordingStatus extends AbstractCommand63QueryRemoteEncoder<RecordingStatus>
{
    public Command63QueryRemoteEncoderGetRecordingStatus(int recorderId)
    {
        super(recorderId);
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        return "GET_RECORDING_STATUS";
    }

    @Override
    public RecordingStatus send(SocketManager socketManager) throws IOException
    {
        String response = socketManager.sendAndWait(getMessage());

        try
        {
            return Protocol63Utils.getTranslator().toEnum(response, RecordingStatus.class);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, Direction.RECEIVE, e);
        }
    }
}