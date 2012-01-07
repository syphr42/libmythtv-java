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
package org.syphr.mythtv.control;

import java.io.IOException;
import java.net.URL;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.control.test.Utils;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.types.FrontendLocation;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.prom.PropertiesManager;

public class ControlPlayMusicIT
{
    public static final Logger LOGGER = LoggerFactory.getLogger(ControlPlayMusicIT.class);

    private static PropertiesManager<Settings> settings;
    private static Control control;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();
        control = Utils.connect(settings);

        control.jump(FrontendLocation.PLAY_MUSIC);
        Utils.waitSeconds(10, "start music player");

        if (!FrontendLocation.PLAY_MUSIC.equals(control.queryLocation()))
        {
            Assert.fail("Frontend failed to start music player");
            return;
        }

        control.playMusicPlay();
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException, CommandException
    {
        control.playMusicStop();
        Utils.waitSeconds(5, "stop playing music");

        control.jump(FrontendLocation.MAIN_MENU);
        control.exit();
    }

    @Test
    public void testPlayMusicPause() throws IOException, CommandException
    {
        control.playMusicPause();
        Utils.waitSeconds(2, "pause music");
    }

    @Test
    public void testPlayMusicPlay() throws IOException, CommandException
    {
        control.playMusicPlay();
        Utils.waitSeconds(5, "play music");
    }

    @Test
    public void testPlayMusicSetVolume() throws IOException, CommandException
    {
        control.playMusicSetVolume(50);
        Utils.waitSeconds(2, "set music volume to 50%");
    }

    @Test(expected = ProtocolException.class)
    public void testPlayVolumeTooLow() throws IOException, CommandException
    {
        if (isVersionBefore(ControlVersion._0_25))
        {
            throw new ProtocolException("Inject protocol exception for unsupported functionality test",
                                        Direction.SEND);
        }

        control.playMusicSetVolume(-1);
        Utils.waitSeconds(2, "set music volume to -1%");
    }

    @Test(expected = ProtocolException.class)
    public void testPlayVolumeTooHigh() throws IOException, CommandException
    {
        if (isVersionBefore(ControlVersion._0_25))
        {
            throw new ProtocolException("Inject protocol exception for unsupported functionality test",
                                        Direction.SEND);
        }

        control.playMusicSetVolume(101);
        Utils.waitSeconds(2, "set music volume to 101%");
    }

    @Test
    public void testPlayMusicGetVolume() throws IOException, CommandException
    {
        LOGGER.debug("Volume: {}", control.playMusicGetVolume());
    }

    @Test
    public void testPlayMusicGetMeta() throws IOException, CommandException
    {
        LOGGER.debug("Metadata: {}", control.playMusicGetMeta());
    }

    @Test
    public void testPlayMusicFile() throws IOException, CommandException
    {
        control.playMusicFile("/var/lib/mythtv/music/test with spaces.mp3");
        Utils.waitSeconds(5, "play music file");
    }

    @Test
    public void testPlayMusicTrack() throws IOException, CommandException
    {
        control.playMusicTrack(1);
        Utils.waitSeconds(5, "play music track");
    }

    @Test
    public void testPlayMusicUrl() throws IOException, CommandException
    {
        control.playMusicUrl(new URL("http://scfire-mtc-aa05.stream.aol.com:80/stream/1010"));
        Utils.waitSeconds(5, "play music url");
    }

    private boolean isVersionBefore(ControlVersion version)
    {
        return settings.getEnumProperty(Settings.FRONTEND_CONTROL_VERSION,
                                        ControlVersion.class).compareTo(version) < 0;
    }
}
