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
import org.syphr.mythtv.protocol.types.PictureAdjustType;

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
    public int changeBrightness(PictureAdjustType type, boolean increment) throws IOException, CommandException
    {
        return new Command63QueryRecorderChangeBrightness(getRecorderId(), type, increment).send(getSocketManager());
    }

    @Override
    public void changeChannel(ChannelChangeDirection direction) throws IOException, CommandException
    {
        new Command63QueryRecorderChangeChannel(getRecorderId(), direction).send(getSocketManager());
    }

    @Override
    public int changeColour(PictureAdjustType type, boolean increment) throws IOException, CommandException
    {
        return new Command63QueryRecorderChangeColour(getRecorderId(), type, increment).send(getSocketManager());
    }

    @Override
    public int changeContrast(PictureAdjustType type, boolean increment) throws IOException, CommandException
    {
        return new Command63QueryRecorderChangeContrast(getRecorderId(), type, increment).send(getSocketManager());
    }

    @Override
    public int changeHue(PictureAdjustType type, boolean increment) throws IOException, CommandException
    {
        return new Command63QueryRecorderChangeHue(getRecorderId(), type, increment).send(getSocketManager());
    }

    @Override
    public boolean checkChannel(String chanNum) throws IOException, CommandException
    {
        return new Command63QueryRecorderCheckChannel(getRecorderId(), chanNum).send(getSocketManager());
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
    public void finishRecording() throws IOException, CommandException
    {
        new Command63QueryRecorderFinishRecording(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void frontendReady() throws IOException, CommandException
    {
        new Command63QueryRecorderFrontendReady(getRecorderId()).send(getSocketManager());
    }

    @Override
    public int getBrightness() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetBrightness(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void getChannelInfo()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getColour() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetColour(getRecorderId()).send(getSocketManager());
    }

    @Override
    public int getContrast() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetContrast(getRecorderId()).send(getSocketManager());
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
    public int getHue() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetHue(getRecorderId()).send(getSocketManager());
    }

    @Override
    public String getInput() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetInput(getRecorderId()).send(getSocketManager());
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
    public void setChannel(String chanNum) throws IOException, CommandException
    {
        new Command63QueryRecorderSetChannel(getRecorderId(), chanNum).send(getSocketManager());
    }

    @Override
    public String setInput(String input) throws IOException, CommandException
    {
        return new Command63QueryRecorderSetInput(getRecorderId(), input).send(getSocketManager());
    }

    @Override
    public String setInputNext() throws IOException, CommandException
    {
        return new Command63QueryRecorderSetInputNext(getRecorderId()).send(getSocketManager());
    }

    @Override
    public void setLiveRecording() throws IOException, CommandException
    {
        new Command63QueryRecorderSetLiveRecording(getRecorderId()).send(getSocketManager());
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
