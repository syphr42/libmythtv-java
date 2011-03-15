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
package org.syphr.mythtv.proto;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.test.Utils;
import org.syphr.prom.PropertiesManager;

public class QueryRecorderTest
{
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
        System.out.println("Interrogating recorder " + recorderId);
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
    public void testGetFilePositionRecording() throws IOException, CommandException
    {
        if (!recording)
        {
            return;
        }

        System.out.println("File position: " + queryRecorder.getFilePosition() + "B");
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

        System.out.println("Frame rate: " + queryRecorder.getFrameRate());
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

        System.out.println("Frames written: " + queryRecorder.getFramesWritten());
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
}
