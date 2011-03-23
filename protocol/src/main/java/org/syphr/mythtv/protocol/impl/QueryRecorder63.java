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
import java.util.Map;

import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.Channel;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.types.ChannelChangeDirection;

public class QueryRecorder63 extends AbstractRecorderProtocol implements QueryRecorder
{
    public QueryRecorder63(int recorderId, SocketManager socketManager)
    {
        super(recorderId, socketManager);
    }

    @Override
    public void cancelNextRecording(boolean cancel) throws IOException, CommandException
    {
        new Command63QueryRecorderCancelNextRecording(getRecorderId(), cancel).send(getSocketManager());
    }

    @Override
    public void changeBrightness()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeChannel(ChannelChangeDirection direction) throws IOException, CommandException
    {
        new Command63QueryRecorderChangeChannel(getRecorderId(), direction).send(getSocketManager());
    }

    @Override
    public void changeColour()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeContrast()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeHue()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void checkChannel()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void checkChannelPrefix()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<Long, Long> fillPositionMap(long start, long end) throws IOException, CommandException
    {
        return new Command63QueryRecorderFillPositionMap(getRecorderId(), start, end).send(getSocketManager());
    }

    @Override
    public void finishRecording()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void frontendReady() throws IOException, CommandException
    {
        new Command63QueryRecorderFrontendReady(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void getBrightness()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getChannelInfo()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getColour()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getContrast()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProgramInfo getCurrentRecording() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetCurrentRecording(getRecorderId()).send(getSocketManager());
    }

    @Override
    public long getFilePosition() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetFilePosition(getRecorderId()).send(getSocketManager());
    }

    @Override
    public float getFrameRate() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetFrameRate(getRecorderId()).send(getSocketManager());
    }

    @Override
    public long getFramesWritten() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetFramesWritten(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void getFreeInputs()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getHue()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getInput()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getKeyframePos(long desiredPosition) throws IOException, CommandException
    {
        return new Command63QueryRecorderGetKeyframePos(getRecorderId(), desiredPosition).send(getSocketManager());
    }

    @Override
    public long getMaxBitrate() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetMaxBitrate(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void getNextProgramInfo()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getRecordingStatus()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRecording() throws IOException, CommandException
    {
        return new Command63QueryRecorderIsRecording(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void pause() throws IOException, CommandException
    {
        new Command63QueryRecorderPause(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void setChannel()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setInput()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSignalMonitoringRate()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shouldSwitchCard()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void spawnLiveTv(String chainId, boolean pip, Channel startChannel) throws IOException, CommandException
    {
        new Command63QueryRecorderSpawnLiveTv(getRecorderId(), chainId, pip, startChannel).send(getSocketManager());
    }

    @Override
    public void stopLiveTv() throws IOException, CommandException
    {
        new Command63QueryRecorderStopLiveTv(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void toggleChannelFavorite(String channelGroup) throws IOException, CommandException
    {
        // TODO
        throw new UnsupportedOperationException();
    }
}
