/*
 * Copyright 2011-2012 Gregory P. Moyer
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.ChannelQuery;
import org.syphr.mythtv.data.InputInfo;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.types.ChannelBrowseDirection;
import org.syphr.mythtv.types.ChannelChangeDirection;
import org.syphr.mythtv.types.PictureAdjustType;

public class QueryRecorder63 extends AbstractRecorderProtocol implements QueryRecorder
{
    public QueryRecorder63(Translator translator,
                           Parser parser,
                           int recorderId,
                           SocketManager socketManager)
    {
        super(translator, parser, recorderId, socketManager);
    }

    @Override
    public void cancelNextRecording(boolean cancel) throws IOException, CommandException
    {
        new Command63QueryRecorderCancelNextRecording(getTranslator(),
                                                      getParser(),
                                                      getRecorderId(),
                                                      cancel).send(getSocketManager());
    }

    @Override
    public int changeBrightness(PictureAdjustType type, boolean increment) throws IOException,
                                                                          CommandException
    {
        return new Command63QueryRecorderChangeBrightness(getTranslator(),
                                                          getParser(),
                                                          getRecorderId(),
                                                          type,
                                                          increment).send(getSocketManager());
    }

    @Override
    public void changeChannel(ChannelChangeDirection direction) throws IOException,
                                                               CommandException
    {
        new Command63QueryRecorderChangeChannel(getTranslator(),
                                                getParser(),
                                                getRecorderId(),
                                                direction).send(getSocketManager());
    }

    @Override
    public int changeColour(PictureAdjustType type, boolean increment) throws IOException,
                                                                      CommandException
    {
        return new Command63QueryRecorderChangeColour(getTranslator(),
                                                      getParser(),
                                                      getRecorderId(),
                                                      type,
                                                      increment).send(getSocketManager());
    }

    @Override
    public int changeContrast(PictureAdjustType type, boolean increment) throws IOException,
                                                                        CommandException
    {
        return new Command63QueryRecorderChangeContrast(getTranslator(),
                                                        getParser(),
                                                        getRecorderId(),
                                                        type,
                                                        increment).send(getSocketManager());
    }

    @Override
    public int changeHue(PictureAdjustType type, boolean increment) throws IOException,
                                                                   CommandException
    {
        return new Command63QueryRecorderChangeHue(getTranslator(),
                                                   getParser(),
                                                   getRecorderId(),
                                                   type,
                                                   increment).send(getSocketManager());
    }

    @Override
    public boolean checkChannel(String channelNumber) throws IOException, CommandException
    {
        return new Command63QueryRecorderCheckChannel(getTranslator(),
                                                      getParser(),
                                                      getRecorderId(),
                                                      channelNumber).send(getSocketManager());
    }

    @Override
    public ChannelQuery checkChannelPrefix(String channelNumberPrefix) throws IOException,
                                                                      CommandException
    {
        return new Command63QueryRecorderCheckChannelPrefix(getTranslator(),
                                                            getParser(),
                                                            getRecorderId(),
                                                            channelNumberPrefix).send(getSocketManager());
    }

    @Override
    public Map<Long, Long> fillPositionMap(long start, long end) throws IOException,
                                                                CommandException
    {
        return new Command63QueryRecorderFillPositionMap(getTranslator(),
                                                         getParser(),
                                                         getRecorderId(),
                                                         start,
                                                         end).send(getSocketManager());
    }

    @Override
    public void finishRecording() throws IOException, CommandException
    {
        new Command63QueryRecorderFinishRecording(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public void frontendReady() throws IOException, CommandException
    {
        new Command63QueryRecorderFrontendReady(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public int getBrightness() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetBrightness(getTranslator(),
                                                       getParser(),
                                                       getRecorderId()).send(getSocketManager());
    }

    @Override
    public Channel getChannelInfo(long channelId) throws IOException, CommandException
    {
        return new Command63QueryRecorderGetChannelInfo(getTranslator(),
                                                        getParser(),
                                                        getRecorderId(),
                                                        channelId).send(getSocketManager());
    }

    @Override
    public int getColour() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetColour(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public int getContrast() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetContrast(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public Program getCurrentRecording() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetCurrentRecording(getTranslator(),
                                                             getParser(),
                                                             getRecorderId()).send(getSocketManager());
    }

    @Override
    public long getFilePosition() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetFilePosition(getTranslator(),
                                                         getParser(),
                                                         getRecorderId()).send(getSocketManager());
    }

    @Override
    public float getFrameRate() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetFrameRate(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public long getFramesWritten() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetFramesWritten(getTranslator(),
                                                          getParser(),
                                                          getRecorderId()).send(getSocketManager());
    }

    @Override
    public List<InputInfo> getFreeInputs(Set<Integer> excludedCardIds) throws IOException,
                                                                      CommandException
    {
        return new Command63QueryRecorderRemoteEncoderGetFreeInputs(getTranslator(),
                                                                    getParser(),
                                                                    getRecorderId(),
                                                                    excludedCardIds).send(getSocketManager());
    }

    @Override
    public int getHue() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetHue(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public String getInput() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetInput(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public long getKeyframePos(long desiredPosition) throws IOException, CommandException
    {
        return new Command63QueryRecorderGetKeyframePos(getTranslator(),
                                                        getParser(),
                                                        getRecorderId(),
                                                        desiredPosition).send(getSocketManager());
    }

    @Override
    public long getMaxBitrate() throws IOException, CommandException
    {
        return new Command63QueryRecorderGetMaxBitrate(getTranslator(),
                                                       getParser(),
                                                       getRecorderId()).send(getSocketManager());
    }

    @Override
    public Program getNextProgramInfo(Channel channel,
                                      ChannelBrowseDirection browseDirection,
                                      Date startTime) throws IOException, CommandException
    {
        return new Command63QueryRecorderGetNextProgramInfo(getTranslator(),
                                                            getParser(),
                                                            getRecorderId(),
                                                            channel,
                                                            browseDirection,
                                                            startTime).send(getSocketManager());
    }

    @Override
    public boolean isRecording() throws IOException, CommandException
    {
        return new Command63QueryRecorderIsRecording(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public void pause() throws IOException, CommandException
    {
        new Command63QueryRecorderPause(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public void setChannel(String channelNumber) throws IOException, CommandException
    {
        new Command63QueryRecorderSetChannel(getTranslator(),
                                             getParser(),
                                             getRecorderId(),
                                             channelNumber).send(getSocketManager());
    }

    @Override
    public String setInput(String input) throws IOException, CommandException
    {
        return new Command63QueryRecorderSetInput(getTranslator(),
                                                  getParser(),
                                                  getRecorderId(),
                                                  input).send(getSocketManager());
    }

    @Override
    public String setInputNext() throws IOException, CommandException
    {
        return new Command63QueryRecorderSetInputNext(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public void setLiveRecording() throws IOException, CommandException
    {
        new Command63QueryRecorderSetLiveRecording(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public void setSignalMonitoringRate()
    {
        // TODO
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public boolean shouldSwitchCard(Channel channel) throws IOException, CommandException
    {
        return new Command63QueryRecorderShouldSwitchCard(getTranslator(),
                                                          getParser(),
                                                          getRecorderId(),
                                                          channel).send(getSocketManager());
    }

    @Override
    public void spawnLiveTv(String chainId, boolean pip, Channel startChannel) throws IOException,
                                                                              CommandException
    {
        new Command63QueryRecorderSpawnLiveTv(getTranslator(),
                                              getParser(),
                                              getRecorderId(),
                                              chainId,
                                              pip,
                                              startChannel).send(getSocketManager());
    }

    @Override
    public void stopLiveTv() throws IOException, CommandException
    {
        new Command63QueryRecorderStopLiveTv(getTranslator(), getParser(), getRecorderId()).send(getSocketManager());
    }

    @Override
    public void toggleChannelFavorite(String channelGroup) throws IOException, CommandException
    {
        new Command63QueryRecorderToggleChannelFavorite(getTranslator(),
                                                        getParser(),
                                                        getRecorderId(),
                                                        channelGroup);
    }
}
