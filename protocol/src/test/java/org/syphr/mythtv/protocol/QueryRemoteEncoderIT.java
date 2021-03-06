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
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.data.InputInfo;
import org.syphr.mythtv.data.TunedInputInfo;
import org.syphr.mythtv.protocol.test.Utils;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class QueryRemoteEncoderIT
{
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryRemoteEncoderIT.class);

    private static PropertiesManager<Settings> settings;

    private static SocketManager socketManager;
    private static Protocol proto;

    private static int recorderId;
    private static QueryRemoteEncoder queryRemoteEncoder;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException
    {
        settings = Settings.createSettings();
        socketManager = Utils.connect(settings);
        proto = Utils.announceMonitor(settings, socketManager, EventLevel.NONE);

        recorderId = settings.getIntegerProperty(Settings.RECORDER);
        LOGGER.debug("Interrogating recorder {}", recorderId);
        queryRemoteEncoder = proto.queryRemoteEncoder(recorderId);
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
    public void testGetCurrentRecording() throws IOException, CommandException
    {
        LOGGER.debug("Current recording: {}", queryRemoteEncoder.getCurrentRecording());
    }

    @Test
    public void testGetFlags() throws IOException, CommandException
    {
        LOGGER.debug("Flags: {}", queryRemoteEncoder.getFlags());
    }

    @Test
    public void testGetFreeInputsNoExclusion() throws IOException,
                                              CommandException,
                                              InterruptedException
    {
        boolean busy = queryRemoteEncoder.isBusy(1).getLeft();
        Thread.sleep(1000);

        List<InputInfo> freeInputs = queryRemoteEncoder.getFreeInputs(null);

        if (busy)
        {
            Assert.assertTrue(freeInputs.isEmpty());
        }

        Assert.assertFalse(freeInputs.isEmpty());
        LOGGER.debug("Free input: {}", freeInputs.get(0));
    }

    @Test
    public void testGetFreeInputsWithExclusion() throws IOException, CommandException
    {
        Set<Integer> excluded = new HashSet<Integer>();
        excluded.add(recorderId);

        List<InputInfo> freeInputs = queryRemoteEncoder.getFreeInputs(excluded);

        Assert.assertFalse(freeInputs.isEmpty());
        LOGGER.debug("Free input: {}", freeInputs.get(0));
    }

    @Test
    public void testGetMaxBitrate() throws IOException, CommandException
    {
        long bps = queryRemoteEncoder.getMaxBitrate();
        long kbps = bps / 1024;
        long mbps = kbps / 1024;

        LOGGER.debug(String.format("Max bitrate: %dbps / %dkbps / %dmbps", bps, kbps, mbps));
    }

    @Test
    public void testGetRecordingStatus() throws IOException, CommandException
    {
        LOGGER.debug("Recording status: {}", queryRemoteEncoder.getRecordingStatus());
    }

    @Test
    public void testGetSleepStatus() throws IOException, CommandException
    {
        LOGGER.debug("Sleep status: {}", queryRemoteEncoder.getSleepStatus());
    }

    @Test
    public void testGetState() throws IOException, CommandException
    {
        LOGGER.debug("State: {}", queryRemoteEncoder.getState());
    }

    @Test
    public void testIsBusy() throws IOException, CommandException
    {
        Pair<Boolean, TunedInputInfo> response = queryRemoteEncoder.isBusy(5);
        LOGGER.debug("Is busy? {}", response.getLeft());
        LOGGER.debug("Info: {}", response.getRight());
    }

    @Test
    public void testMatchesRecording() throws IOException
    {
        // TODO
    }

    @Test
    public void testRecordPending() throws IOException
    {
        // TODO
    }

    @Test
    public void testStartRecording() throws IOException
    {
        // TODO
    }

    /*
     * ---------------------------------------------------------------- The
     * following unit tests can cause side effects. Use with care.
     */
    //    @Test
    //    public void testStopRecording() throws IOException, CommandException
    //    {
    //        queryRemoteEncoder.stopRecording();
    //    }
}
