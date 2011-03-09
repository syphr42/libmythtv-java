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
import java.util.List;

import org.syphr.mythtv.proto.QueryRemoteEncoder;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.types.RecorderFlag;
import org.syphr.mythtv.proto.types.RecordingStatus;
import org.syphr.mythtv.proto.types.SleepStatus;
import org.syphr.mythtv.proto.types.TvState;

public class QueryRemoteEncoder63 extends AbstractRecorderProtocol implements QueryRemoteEncoder
{
    public QueryRemoteEncoder63(int recorderId, SocketManager socketManager)
    {
        super(recorderId, socketManager);
    }

    @Override
    public void cancelNextRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProgramInfo getCurrentRecording() throws IOException
    {
        return new Command63QueryRemoteEncoderGetCurrentRecording(getRecorderId()).send(getSocketManager());
    }

    @Override
    public List<RecorderFlag> getFlags() throws IOException
    {
        return new Command63QueryRemoteEncoderGetFlags(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void getFreeInputs() throws IOException
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
    public void isBusy() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void matchesRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void recordPending() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startRecording() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stopRecording() throws IOException
    {
        new Command63QueryRemoteEncoderStopRecording(getRecorderId()).send(getSocketManager());
    }
}
