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
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.ProgramInfo;

/* default */class Command63QueryRemoteEncoderRecordPending extends AbstractCommand63QueryRemoteEncoder<Void>
{
    private final int secondsLeft;
    private final boolean hasLater;
    private final ProgramInfo program;

    public Command63QueryRemoteEncoderRecordPending(int recorderId, int secondsLeft, boolean hasLater, ProgramInfo program)
    {
        super(recorderId);
        this.secondsLeft = secondsLeft;
        this.hasLater = hasLater;
        this.program = program;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        List<String> args = new ArrayList<String>();
        args.add("RECORD_PENDING");
        args.add(String.valueOf(secondsLeft));
        args.add(String.valueOf(hasLater));
        args.addAll(Protocol63Utils.extractProgramInfo(program));

        return Protocol63Utils.combineArguments(args);
    }

    @Override
    public Void send(SocketManager socketManager) throws IOException
    {
        ProtocolUtils.sendExpectOk(socketManager, getMessage());
        return null;
    }
}
