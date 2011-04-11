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
package org.syphr.mythtv.protocol.events;

import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.TunerStatus;

public class BackendEventListener63Adapter implements BackendEventListener63
{
    @Override
    public void clearSettingsCache()
    {
        // NOOP
    }

    @Override
    public void commFlagRequest(Channel channel, Date startTime)
    {
        // NOOP
    }

    @Override
    public void doneRecording(int recorder, long seconds, long frames)
    {
        // NOOP
    }

    @Override
    public void downloadFileUpdate(URL remoteUrl, URI localUri, long bytesReceived, long bytesTotal)
    {
        // NOOP
    }

    @Override
    public void downloadFileFinshed(URL remoteUrl,
                                    URI localUri,
                                    long bytesTotal,
                                    String errorText,
                                    int errorCode)
    {
        // NOOP
    }

    @Override
    public void generatedPixmap(Channel channel,
                                Date timestamp,
                                String comment,
                                Date timestamp2,
                                long num1,
                                long num2,
                                byte[] bytes)
    {
        // NOOP
    }

    @Override
    public void liveTvChainUpdate(String chainId)
    {
        // NOOP
    }

    @Override
    public void liveTvWatch(int recorder, boolean recordingIsActive)
    {
        // NOOP
    }

    @Override
    public void masterUpdateProgInfo(Channel channel, Date startTime)
    {
        // NOOP
    }

    @Override
    public void recordingListChangeAdd(Channel channel, Date startTime)
    {
        // NOOP
    }

    @Override
    public void recordingListChangeUpdate(Program program)
    {
        // NOOP
    }

    @Override
    public void recordingListChangeDelete(Channel channel, Date startTime)
    {
        // NOOP
    }

    @Override
    public void scheduleChange()
    {
        // NOOP
    }

    @Override
    public void signalMessage(int recorder, String message)
    {
        // NOOP
    }

    @Override
    public void signalTunerStatus(int recorder, TunerStatus tunerStatus)
    {
        // NOOP
    }

    @Override
    public void systemEvent(SystemEvent event, Map<SystemEventData, String> data)
    {
        // NOOP
    }

    @Override
    public void updateFileSize(Channel channel, Date startTime, long size)
    {
        // NOOP
    }

    @Override
    public void videoListChange()
    {
        // NOOP
    }
}
