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
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.InputInfo;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.types.RecorderFlag;
import org.syphr.mythtv.protocol.types.RecordingStatus;
import org.syphr.mythtv.protocol.types.SleepStatus;
import org.syphr.mythtv.protocol.types.TvState;

public class QueryRemoteEncoder63 extends AbstractRecorderProtocol implements QueryRemoteEncoder
{
    public QueryRemoteEncoder63(int recorderId, SocketManager socketManager)
    {
        super(recorderId, socketManager);
    }

    @Override
    public void cancelNextRecording(boolean cancel) throws IOException
    {
        new Command63QueryRemoteEncoderCancelNextRecording(getRecorderId(), cancel).send(getSocketManager());
    }

    @Override
    public ProgramInfo getCurrentRecording() throws IOException
    {
        return new Command63QueryRemoteEncoderGetCurrentRecording(getRecorderId()).send(getSocketManager());
    }

    @Override
    public Set<RecorderFlag> getFlags() throws IOException
    {
        return new Command63QueryRemoteEncoderGetFlags(getRecorderId()).send(getSocketManager());
    }

    @Override
    public List<InputInfo> getFreeInputs() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getMaxBitrate() throws IOException
    {
        return new Command63QueryRemoteEncoderGetMaxBitrate(getRecorderId()).send(getSocketManager());
    }

    @Override
    public RecordingStatus getRecordingStatus() throws IOException
    {
        return new Command63QueryRemoteEncoderGetRecordingStatus(getRecorderId()).send(getSocketManager());
    }

    @Override
    public SleepStatus getSleepStatus() throws IOException
    {
        return new Command63QueryRemoteEncoderGetSleepStatus(getRecorderId()).send(getSocketManager());
    }

    @Override
    public TvState getState() throws IOException
    {
        return new Command63QueryRemoteEncoderGetState(getRecorderId()).send(getSocketManager());
    }

    @Override
    public Pair<Boolean, InputInfo> isBusy(int withinSeconds) throws IOException
    {
        return new Command63QueryRemoteEncoderIsBusy(getRecorderId(), withinSeconds).send(getSocketManager());
    }

    @Override
    public boolean matchesRecording(ProgramInfo program) throws IOException
    {
        return new Command63QueryRemoteEncoderMatchesRecording(getRecorderId(), program).send(getSocketManager());
    }

    @Override
    public void recordPending(int secondsLeft, boolean hasLater, ProgramInfo program) throws IOException
    {
        new Command63QueryRemoteEncoderRecordPending(getRecorderId(), secondsLeft, hasLater, program).send(getSocketManager());
    }

    @Override
    public boolean startRecording(ProgramInfo program) throws IOException
    {
        return new Command63QueryRemoteEncoderStartRecording(getRecorderId(), program).send(getSocketManager());
    }

    @Override
    public void stopRecording() throws IOException
    {
        new Command63QueryRemoteEncoderStopRecording(getRecorderId()).send(getSocketManager());
    }
}
