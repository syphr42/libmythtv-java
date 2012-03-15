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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.data.MusicInfo;
import org.syphr.mythtv.data.Program;

public class Control0_25 extends Control0_24
{
    @Override
    public List<Program> queryLiveTv() throws IOException
    {
        return new Command0_25QueryLiveTv(getTranslator()).send(getSocketManager());
    }

    @Override
    public Program queryLiveTv(long channelId) throws IOException
    {
        return new Command0_25QueryLiveTvChannel(getTranslator(), channelId).send(getSocketManager());
    }

    @Override
    public Program queryRecording(long channelId, Date recStartTs) throws IOException
    {
        return new Command0_25QueryRecording(getTranslator(), channelId, recStartTs).send(getSocketManager());
    }

    @Override
    public List<Program> queryRecordings() throws IOException
    {
        return new Command0_25QueryRecordings(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playMusicPlay() throws IOException
    {
        new Command0_25PlayMusicPlay(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playMusicPause() throws IOException
    {
        new Command0_25PlayMusicPause(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playMusicStop() throws IOException
    {
        new Command0_25PlayMusicStop(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playMusicSetVolume(int percent) throws IOException
    {
        new Command0_25PlayMusicSetVolume(getTranslator(), percent).send(getSocketManager());
    }

    @Override
    public int playMusicGetVolume() throws IOException
    {
        return new Command0_25PlayMusicGetVolume(getTranslator()).send(getSocketManager());
    }

    @Override
    public MusicInfo playMusicGetMeta() throws IOException
    {
        return new Command0_25PlayMusicGetMeta(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playMusicFile(String filename) throws IOException
    {
        new Command0_25PlayMusicFile(getTranslator(), filename).send(getSocketManager());
    }

    @Override
    public void playMusicTrack(int track) throws IOException
    {
        new Command0_25PlayMusicTrack(getTranslator(), track).send(getSocketManager());
    }

    @Override
    public void playMusicUrl(URL url) throws IOException
    {
        new Command0_25PlayMusicUrl(getTranslator(), url).send(getSocketManager());
    }

    @Override
    public void screenshot() throws IOException, CommandException
    {
        screenshot(0, 0);
    }

    @Override
    public void screenshot(int width, int height) throws IOException, CommandException
    {
        new Command0_25Screenshot(getTranslator(), width, height).send(getSocketManager());
    }

    @Override
    public void screenshot(String filename) throws IOException, CommandException
    {
        handleUnsupported("screenshot with a filename");
        screenshot();
    }

    @Override
    public void screenshot(String filename, int width, int height) throws IOException,
                                                                  CommandException
    {
        handleUnsupported("screenshot with a filename");
        screenshot(width, height);
    }

    @Override
    public void message(String text) throws IOException
    {
        new Command0_25Message(getTranslator(), text).send(getSocketManager());
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator0_25();
    }
}
