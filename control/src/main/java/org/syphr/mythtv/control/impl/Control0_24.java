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
import java.util.Set;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Load;
import org.syphr.mythtv.data.MemStats;
import org.syphr.mythtv.data.MusicInfo;
import org.syphr.mythtv.data.PlaybackInfo;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.VersionInfo;
import org.syphr.mythtv.types.FrontendLocation;
import org.syphr.mythtv.types.Key;
import org.syphr.mythtv.types.SeekTarget;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.mythtv.util.translate.Translator;

public class Control0_24 extends AbstractControl
{
    public Control0_24()
    {
        super(new ControlSocketManager());
    }

    @Override
    public void jump(FrontendLocation jumpPoint) throws IOException
    {
        new Command0_24Jump(getTranslator(), jumpPoint).send(getSocketManager());
    }

    @Override
    public void key(char c) throws IOException
    {
        new Command0_24Key(getTranslator(), c).send(getSocketManager());
    }

    @Override
    public void key(Key key) throws IOException
    {
        new Command0_24Key(getTranslator(), key).send(getSocketManager());
    }

    @Override
    public void playVolume(int percent) throws IOException, CommandException
    {
        new Command0_24PlayVolume(getTranslator(), percent).send(getSocketManager());
    }

    @Override
    public void playChannelUp() throws IOException, CommandException
    {
        new Command0_24PlayChannelUp(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playChannelDown() throws IOException, CommandException
    {
        new Command0_24PlayChannelDown(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playChannel(String channelNumber) throws IOException, CommandException
    {
        new Command0_24PlayChannel(getTranslator(), channelNumber).send(getSocketManager());
    }

    @Override
    public void playChannel(int channelId) throws IOException, CommandException
    {
        new Command0_24PlayChannel(getTranslator(), channelId).send(getSocketManager());
    }

    @Override
    public void playFile(String filename) throws IOException, CommandException
    {
        new Command0_24PlayFile(getTranslator(), filename).send(getSocketManager());
    }

    @Override
    public void playProgram(int channelId, Date recStartTs, boolean resume) throws IOException, CommandException
    {
        new Command0_24PlayProgram(getTranslator(), channelId, recStartTs, resume).send(getSocketManager());
    }

    @Override
    public void playSavePreview() throws IOException, CommandException
    {
        playSavePreview(null);
    }

    @Override
    public void playSavePreview(String filename) throws IOException, CommandException
    {
        playSavePreview(filename, 0, 0);
    }

    @Override
    public void playSavePreview(String filename, int width, int height) throws IOException, CommandException
    {
        new Command0_24PlaySavePreview(getTranslator(), filename, width, height).send(getSocketManager());
    }

    @Override
    public void playSeek(SeekTarget target) throws IOException, CommandException
    {
        new Command0_24PlaySeek(getTranslator(), target).send(getSocketManager());
    }

    @Override
    public void playSeek(int hour, int minute, int second) throws IOException, CommandException
    {
        new Command0_24PlaySeek(getTranslator(), hour, minute, second).send(getSocketManager());
    }

    @Override
    public void playSpeed(float speed) throws IOException, CommandException
    {
        new Command0_24PlaySpeed(getTranslator(), speed).send(getSocketManager());
    }

    @Override
    public void playStop() throws IOException, CommandException
    {
        new Command0_24PlayStop(getTranslator()).send(getSocketManager());
    }

    @Override
    public void playMusicPlay() throws IOException
    {
        handleUnsupported("play music");
    }

    @Override
    public void playMusicPause() throws IOException
    {
        handleUnsupported("pause music");
    }

    @Override
    public void playMusicStop() throws IOException
    {
        handleUnsupported("stop music");
    }

    @Override
    public void playMusicSetVolume(int percent) throws IOException
    {
        handleUnsupported("set music volume");
    }

    @Override
    public int playMusicGetVolume() throws IOException
    {
        handleUnsupported("get music volume");
        return -1;
    }

    @Override
    public MusicInfo playMusicGetMeta() throws IOException
    {
        handleUnsupported("get music metadata");
        return new MusicInfo(null, null, null);
    }

    @Override
    public void playMusicFile(String filename) throws IOException
    {
        handleUnsupported("play music file");
    }

    @Override
    public void playMusicTrack(int track) throws IOException
    {
        handleUnsupported("play music track");
    }

    @Override
    public void playMusicUrl(URL url) throws IOException
    {
        handleUnsupported("play music url");
    }

    @Override
    public FrontendLocation queryLocation() throws IOException
    {
        return new Command0_24QueryLocation(getTranslator()).send(getSocketManager());
    }

    @Override
    public PlaybackInfo queryPlaybackInfo() throws IOException
    {
        return new Command0_24QueryPlaybackInfo(getTranslator()).send(getSocketManager());
    }

    @Override
    public int queryVolume() throws IOException
    {
        return new Command0_24QueryVolume(getTranslator()).send(getSocketManager());
    }

    @Override
    public List<Program> queryRecordings() throws IOException
    {
        return new Command0_24QueryRecordings(getTranslator()).send(getSocketManager());
    }

    @Override
    public Program queryRecording(int channelId, Date recStartTs) throws IOException
    {
        return new Command0_24QueryRecording(getTranslator(), channelId, recStartTs).send(getSocketManager());
    }

    @Override
    public List<Program> queryLiveTv() throws IOException
    {
        return new Command0_24QueryLiveTv(getTranslator()).send(getSocketManager());
    }

    @Override
    public Program queryLiveTv(int channelId) throws IOException
    {
        return new Command0_24QueryLiveTvChannel(getTranslator(), channelId).send(getSocketManager());
    }

    @Override
    public Load queryLoad() throws IOException
    {
        return new Command0_24QueryLoad(getTranslator()).send(getSocketManager());
    }

    @Override
    public MemStats queryMemStats() throws IOException
    {
        return new Command0_24QueryMemStats(getTranslator()).send(getSocketManager());
    }

    @Override
    public Date queryTime() throws IOException
    {
        return new Command0_24QueryTime(getTranslator()).send(getSocketManager());
    }

    @Override
    public long queryUptime() throws IOException
    {
        return new Command0_24QueryUptime(getTranslator()).send(getSocketManager());
    }

    @Override
    public Set<Verbose> queryVerbose() throws IOException
    {
        return new Command0_24QueryVerbose(getTranslator()).send(getSocketManager());
    }

    @Override
    public VersionInfo queryVersion() throws IOException
    {
        return new Command0_24QueryVersion(getTranslator()).send(getSocketManager());
    }

    @Override
    public List<Channel> queryChannels() throws IOException
    {
        return new Command0_24QueryChannels(getTranslator()).send(getSocketManager());
    }

    @Override
    public List<Channel> queryChannels(int start, int limit) throws IOException
    {
        return new Command0_24QueryChannels(getTranslator(), start, limit).send(getSocketManager());
    }

    @Override
    public void setVerbose(List<Verbose> options) throws IOException, CommandException
    {
        new Command0_24SetVerbose(getTranslator(), options).send(getSocketManager());
    }

    @Override
    public void screenshot() throws IOException, CommandException
    {
        screenshot(null);
    }

    @Override
    public void screenshot(int width, int height) throws IOException, CommandException
    {
        handleUnsupported("screenshot with only width and height (no filename)");
    }

    @Override
    public void screenshot(String filename) throws IOException, CommandException
    {
        screenshot(filename, 0, 0);
    }

    @Override
    public void screenshot(String filename, int width, int height) throws IOException, CommandException
    {
        new Command0_24Screenshot(getTranslator(), filename, width, height).send(getSocketManager());
    }

    @Override
    public void message(String text) throws IOException
    {
        handleUnsupported("send message");
    }

    @Override
    public void exit() throws IOException
    {
        SocketManager socketManager = getSocketManager();

        try
        {
            new Command0_24Exit(getTranslator()).send(socketManager);
        }
        finally
        {
            /*
             * Make sure the connection is closed after an exit command.
             */
            socketManager.disconnect();
        }
    }

    @Override
    protected Translator createTranslator()
    {
        return new Translator0_24();
    }
}
