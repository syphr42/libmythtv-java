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
package org.syphr.mythtv.control;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.control.test.Utils;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.PlaybackInfo;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.types.FrontendLocation;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.prom.PropertiesManager;

public class ControlPlayChannelTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlPlayChannelTest.class);

    private static PropertiesManager<Settings> settings;
    private static Control control;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();
        control = Utils.connect(settings);

        control.jump(FrontendLocation.LIVE_TV);

        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            LOGGER.warn("Interrupted while waiting for frontend to start playing live TV", e);
        }

        PlaybackInfo pbInfo = control.queryPlaybackInfo();
        if (pbInfo == null)
        {
            Assert.fail("Frontend failed to start playing live TV");
            return;
        }

        LOGGER.debug(pbInfo.toString());
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException, CommandException
    {
        control.playStop();
        control.jump(FrontendLocation.MAIN_MENU);
        control.exit();
    }

    @Test
    public void testPlayVolume()
    {
        // TODO
    }

    @Test
    public void testPlayChannelUp()
    {
        // TODO
    }

    @Test
    public void testPlayChannelDown()
    {
        // TODO
    }

    @Test
    public void testPlayChannel()
    {
        // TODO
    }

    @Test
    public void testPlayChannelId() throws IOException, CommandException
    {
        List<Channel> channels = control.queryChannels();
        if (channels.isEmpty())
        {
            LOGGER.warn("Skipping play channel ID test because no channels were found");
            return;
        }

        Channel channel = channels.get(0);
        control.playChannelId(channel.getId());

        try
        {
            Thread.sleep(5000);
        }
        catch (InterruptedException e)
        {
            LOGGER.warn("Interrupted while waiting for frontend to change channels", e);
        }
    }

    @Test
    public void testPlaySavePreview()
    {
        // TODO
    }

    @Test
    public void testPlaySavePreviewFilename()
    {
        // TODO
    }

    @Test
    public void testPlaySavePreviewFilenameDimensions()
    {
        // TODO
    }

    @Test
    public void testPlaySpeedPause()
    {
        // TODO
    }
}
