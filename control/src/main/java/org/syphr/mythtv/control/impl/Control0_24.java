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
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Load;
import org.syphr.mythtv.data.MemStats;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.types.FrontendLocation;
import org.syphr.mythtv.types.Key;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.util.exception.CommandException;
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
        new Command0_24Jump(jumpPoint).send(getSocketManager());
    }

    @Override
    public void key(char c) throws IOException
    {
        new Command0_24Key(c).send(getSocketManager());
    }

    @Override
    public void key(Key key) throws IOException
    {
        new Command0_24Key(key).send(getSocketManager());
    }

    @Override
    public void playVolume(int percent) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannelUp() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannelDown() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannel(String channelNumber) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playChannelId(int channelId) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playFile(String filename) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playProgram(int channelId, Date recStartTs, boolean resume) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSavePreview() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSavePreview(String filename) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSavePreview(String filename, int width, int height) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSeek() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSpeedPause() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playSpeed(float speed) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void playStop() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public FrontendLocation queryLocation() throws IOException
    {
        return new Command0_24QueryLocation().send(getSocketManager());
    }

    @Override
    public int queryVolume() throws IOException
    {
        return new Command0_24QueryVolume().send(getSocketManager());
    }

    @Override
    public List<Program> queryRecordings() throws IOException
    {
        return new Command0_24QueryRecordings().send(getSocketManager());
    }

    @Override
    public Program queryRecording(int channelId, Date recStartTs) throws IOException
    {
        return new Command0_24QueryRecording(channelId, recStartTs).send(getSocketManager());
    }

    @Override
    public List<Program> queryLiveTv() throws IOException
    {
        return new Command0_24QueryLiveTv().send(getSocketManager());
    }

    @Override
    public Program queryLiveTv(int channelId) throws IOException
    {
        return new Command0_24QueryLiveTvChannel(channelId).send(getSocketManager());
    }

    @Override
    public Load queryLoad() throws IOException
    {
        return new Command0_24QueryLoad().send(getSocketManager());
    }

    @Override
    public MemStats queryMemStats() throws IOException
    {
        return new Command0_24QueryMemStats().send(getSocketManager());
    }

    @Override
    public Date queryTime() throws IOException
    {
        return new Command0_24QueryTime().send(getSocketManager());
    }

    @Override
    public long queryUptime() throws IOException
    {
        return new Command0_24QueryUptime().send(getSocketManager());
    }

    @Override
    public List<Verbose> queryVerbose() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String queryVersion() throws IOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Channel> queryChannels() throws IOException
    {
        return new Command0_24QueryChannels().send(getSocketManager());
    }

    @Override
    public List<Channel> queryChannels(int start, int limit) throws IOException
    {
        return new Command0_24QueryChannels(start, limit).send(getSocketManager());
    }

    @Override
    public void setVerbose(List<Verbose> options) throws IOException, CommandException
    {
        new Command0_24SetVerbose(options).send(getSocketManager());
    }

    @Override
    public void screenshot() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void screenshot(String filename) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void screenshot(String filename, int width, int height) throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void message(String text) throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void exit() throws IOException
    {
        new Command0_24Exit().send(getSocketManager());
        super.exit();
    }

    @Override
    protected Translator getTranslator()
    {
        return Control0_24Utils.getTranslator();
    }
}
