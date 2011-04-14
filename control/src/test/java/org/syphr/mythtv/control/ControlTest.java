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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.control.test.Utils;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.types.FrontendLocation;
import org.syphr.mythtv.types.Key;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.prom.PropertiesManager;

public class ControlTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlTest.class);

    private static PropertiesManager<Settings> settings;
    private static Control control;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();
        control = Utils.connect(settings);
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        control.exit();
    }

    @Test
    public void testJump() throws IOException
    {
        control.jump(FrontendLocation.MYTH_VIDEO);
        control.jump(FrontendLocation.MAIN_MENU);
    }

    @Test
    public void testKey() throws IOException
    {
        control.key('m');
        control.key(Key.ESCAPE);
    }

    @Test
    public void testQueryLocation() throws IOException
    {
        FrontendLocation location = FrontendLocation.MAIN_MENU;
        control.jump(location);
        Assert.assertEquals(location, control.queryLocation());
    }

    @Test
    public void testQueryVolume() throws IOException
    {
        LOGGER.debug("Current volume: {}", control.queryVolume());
    }

    @Test
    public void testQueryRecordings() throws IOException
    {
        org.syphr.mythtv.test.Utils.printFirstFive(control.queryRecordings(),
                                                   LOGGER);
    }

    @Test
    public void testQueryRecording() throws IOException
    {
        List<Program> recordings = control.queryRecordings();

        if (recordings.isEmpty())
        {
            LOGGER.warn("Skipping query recording test since there are no recordings");
            return;
        }

        Program request = recordings.get(recordings.size() - 1);
        Program response = control.queryRecording(request.getChannel().getId(), request.getRecStartTs());

        Assert.assertEquals(request, response);
    }

    @Test
    public void testQueryRecordingDoesNotExist() throws IOException
    {
        Program program = control.queryRecording(Integer.MAX_VALUE, new Date(0));
        Assert.assertNull(program);
    }

    @Test
    public void testQueryLiveTv() throws IOException
    {
        org.syphr.mythtv.test.Utils.printFirstFive(control.queryLiveTv(),
                                                   LOGGER);
    }

    @Test
    public void testQueryLiveTvChannel() throws IOException
    {
        List<Program> livetv = control.queryLiveTv();

        if (livetv.isEmpty())
        {
            LOGGER.warn("Skipping query livetv test since there are no listings");
            return;
        }

        Program request = livetv.get(livetv.size() - 1);
        Program response = control.queryLiveTv(request.getChannel().getId());

        Assert.assertEquals(request, response);
    }

    @Test
    public void testQueryLiveTvChannelDoesNotExist() throws IOException
    {
        Program program = control.queryLiveTv(Integer.MAX_VALUE);
        Assert.assertNull(program);
    }

    @Test
    public void testQueryLoad() throws IOException
    {
        LOGGER.debug(control.queryLoad().toString());
    }

    @Test
    public void testQueryMemStats() throws IOException
    {
        LOGGER.debug(control.queryMemStats().toString());
    }

    @Test
    public void testQueryUptime() throws IOException
    {
        LOGGER.debug("Uptime: {} secs", control.queryUptime());
    }

    @Test
    public void testQueryChannels() throws IOException
    {
        List<Channel> allChannels = control.queryChannels();

        if (allChannels.isEmpty())
        {
            /*
             * Can't test any further if there are no channels.
             */
            return;
        }

        int thirdListSize = allChannels.size() / 3;
        List<Channel> middleThirdChannels = control.queryChannels(thirdListSize, thirdListSize);

        Assert.assertTrue(allChannels.containsAll(middleThirdChannels));
    }

    @Test
    @SuppressWarnings("serial")
    public void testSetVerbose() throws IOException, CommandException
    {
        control.setVerbose(new ArrayList<Verbose>() {{ add(Verbose.ALL); }});
        control.setVerbose(new ArrayList<Verbose>() {{ add(Verbose.IMPORTANT); add(Verbose.GENERAL); }});
    }

    @Test
    public void testMessage() throws IOException
    {
        try
        {
            control.message("This is a test message!");
            control.key(Key.ENTER);
        }
        catch (UnsupportedOperationException e)
        {
            LOGGER.warn("Skipping message test since it is not supported by this control version");
            return;
        }
    }
}
