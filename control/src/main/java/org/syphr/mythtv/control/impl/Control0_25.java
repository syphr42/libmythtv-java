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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.util.exception.CommandException;

public class Control0_25 extends Control0_24
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Control0_25.class);

    @Override
    public void playMusicPlay() throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void playMusicPause() throws IOException
    {
        // TODO
    }

    @Override
    public void playMusicStop() throws IOException
    {
        // TODO
    }

    @Override
    public void playMusicSetvolume(int percent) throws IOException
    {
        // TODO
    }

    @Override
    public int playMusicGetvolume() throws IOException
    {
        // TODO
        return 0;
    }

    @Override
    public void playMusicGetmeta() throws IOException
    {
        // TODO
    }

    @Override
    public void playMusicFile(String filename) throws IOException
    {
        // TODO
    }

    @Override
    public void playMusicTrack(int track) throws IOException
    {
        // TODO
    }

    @Override
    public void playMusicUrl(URL url) throws IOException
    {
        // TODO
    }

    @Override
    public void screenshot() throws IOException, CommandException
    {
        screenshot(0, 0);
    }

    @Override
    public void screenshot(int width, int height) throws IOException, CommandException
    {
        new Command0_25Screenshot(width, height).send(getSocketManager());
    }

    @Override
    public void screenshot(String filename) throws IOException, CommandException
    {
        LOGGER.warn("The screen shot method no longer accepts a filename argument, it will be ignored");
        screenshot();
    }

    @Override
    public void screenshot(String filename, int width, int height) throws IOException,
                                                                  CommandException
    {
        LOGGER.warn("The screen shot method no longer accepts a filename argument, it will be ignored");
        screenshot(width, height);
    }

    @Override
    public void message(String text) throws IOException
    {
        new Command0_25Message(text).send(getSocketManager());
    }
}
