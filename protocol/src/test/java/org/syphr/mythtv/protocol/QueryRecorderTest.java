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
package org.syphr.mythtv.protocol;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.protocol.test.Utils;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class QueryRecorderTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryRecorderTest.class);

    private static PropertiesManager<Settings> settings;

    private static SocketManager socketManager;
    private static Protocol proto;

    private static QueryRecorder queryRecorder;

    private static boolean recording;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException
    {
        settings = Settings.createSettings();
        socketManager = Utils.connect(settings);
        proto = Utils.announceMonitor(settings, socketManager, EventLevel.NONE);

        int recorderId = settings.getIntegerProperty(Settings.RECORDER);
        LOGGER.debug("Interrogating recorder {}", recorderId);
        queryRecorder = proto.queryRecorder(recorderId);

        recording = queryRecorder.isRecording();
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        proto.done();
        socketManager.disconnect();
    }

    @Test
    public void testCancelNextRecording() throws IOException
    {
        // TODO
    }

    @Test
    public void testFillPositionMapRecording() throws IOException, CommandException
    {
        if (!recording)
        {
            return;
        }

        long start = 0;
        long end = -1;
        LOGGER.debug("Keyframe position map: {}", queryRecorder.fillPositionMap(start, end));
    }

    @Test(expected = CommandException.class)
    public void testFillPositionMapNotRecording() throws IOException, CommandException
    {
        if (recording)
        {
            throw new CommandException();
        }

        long start = 0;
        long end = -1;
        queryRecorder.fillPositionMap(start, end);
    }

    @Test
    public void testFinishRecording() throws IOException
    {
        // TODO
    }

    @Test
    public void testFrontendReady() throws IOException, CommandException
    {
        queryRecorder.frontendReady();
    }

    @Test
    public void testGetCurrentRecording() throws IOException, CommandException
    {
        LOGGER.debug("Current recording: {}", queryRecorder.getCurrentRecording());
    }

    @Test
    public void testGetFilePositionRecording() throws IOException, CommandException
    {
        if (!recording)
        {
            return;
        }

        LOGGER.debug("File position: {}B", queryRecorder.getFilePosition());
    }

    @Test(expected = CommandException.class)
    public void testGetFilePositionNotRecording() throws IOException, CommandException
    {
        if (recording)
        {
            throw new CommandException();
        }

        queryRecorder.getFilePosition();
    }

    @Test
    public void testGetFrameRateRecording() throws IOException, CommandException
    {
        if (!recording)
        {
            return;
        }

        LOGGER.debug("Frame rate: {}", queryRecorder.getFrameRate());
    }

    @Test(expected = CommandException.class)
    public void testGetFrameRateNotRecording() throws IOException, CommandException
    {
        if (recording)
        {
            throw new CommandException();
        }

        queryRecorder.getFrameRate();
    }

    @Test
    public void testGetFramesWrittenRecording() throws IOException, CommandException
    {
        if (!recording)
        {
            return;
        }

        LOGGER.debug("Frames written: {}", queryRecorder.getFramesWritten());
    }

    @Test(expected = CommandException.class)
    public void testGetFramesWrittenNotRecording() throws IOException, CommandException
    {
        if (recording)
        {
            throw new CommandException();
        }

        queryRecorder.getFramesWritten();
    }

    @Test
    public void testGetKeyframePosRecording() throws IOException, CommandException
    {
        if (!recording)
        {
            return;
        }

        long desiredPosition = queryRecorder.getFramesWritten() / 2;
        LOGGER.debug("Keyframe position: {}", queryRecorder.getKeyframePos(desiredPosition));
    }

    @Test(expected = CommandException.class)
    public void testGetKeyframePosNotRecording() throws IOException, CommandException
    {
        if (recording)
        {
            throw new CommandException();
        }

        long desiredPosition = queryRecorder.getFramesWritten() / 2;
        queryRecorder.getKeyframePos(desiredPosition);
    }

    @Test
    public void testGetMaxBitRate() throws IOException, CommandException
    {
        long bps = queryRecorder.getMaxBitrate();
        long kbps = bps / 1024;
        long mbps = kbps / 1024;

        LOGGER.debug(String.format("Max bitrate: %dbps / %dkbps / %dmbps", bps, kbps, mbps));
    }

    @Test
    public void testPause() throws IOException
    {
        // TODO
    }
}
