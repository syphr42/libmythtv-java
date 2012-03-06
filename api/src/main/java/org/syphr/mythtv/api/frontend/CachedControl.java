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
package org.syphr.mythtv.api.frontend;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.api.util.AbstractCachedConnection;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.unsupported.UnsupportedHandler;
import org.syphr.mythtv.control.Control;
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

/**
 * This class maintains cached connection information to automatically connect
 * and disconnect as necessary to fulfill frontend control requests.
 * 
 * @author Gregory P. Moyer
 */
public class CachedControl extends AbstractCachedConnection implements Control
{
    /**
     * The control for actual communication with the frontend.
     */
    private final Control delegate;

    /**
     * The name of the frontend.
     */
    private String remoteHost;

    /**
     * The port on the frontend that is accepting control connections.
     */
    private int remotePort;

    public CachedControl(Control control, long timeout, TimeUnit unit)
    {
        super(timeout, unit);
        this.delegate = control;
    }

    public synchronized void setConnectionParameters(String remoteHost, int remotePort)
    {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    private synchronized void connectIfNecessary() throws IOException
    {
        if (isConnectionShutdown())
        {
            throw new IOException("Connection has been permanently shutdown");
        }

        if (isConnected())
        {
            return;
        }

        delegate.connect(remoteHost, remotePort, getTimeout() / 2L);
    }

    @Override
    protected synchronized void disconnect()
    {
        try
        {
            delegate.exit();
        }
        catch (IOException e)
        {
            /*
             * Ignore this - it's time to get rid of the connection anyway.
             */
        }
    }

    @Override
    public synchronized void exit() throws IOException
    {
        if (!isConnected())
        {
            return;
        }

        delegate.exit();
    }

    @Override
    public synchronized boolean isConnected()
    {
        return delegate.isConnected();
    }

    @Override
    public void setUnsupportedHandler(UnsupportedHandler handler)
    {
        delegate.setUnsupportedHandler(handler);
    }

    @Override
    public <E extends Enum<E>> List<E> getAvailableTypes(Class<E> type)
    {
        return delegate.getAvailableTypes(type);
    }

    @Override
    public void jump(FrontendLocation jumpPoint) throws IOException
    {
        connectIfNecessary();
        delegate.jump(jumpPoint);
    }

    @Override
    public void key(char c) throws IOException
    {
        connectIfNecessary();
        delegate.key(c);
    }

    @Override
    public void key(Key key) throws IOException
    {
        connectIfNecessary();
        delegate.key(key);
    }

    @Override
    public void playVolume(int percent) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playVolume(percent);
    }

    @Override
    public void playChannelUp() throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playChannelUp();
    }

    @Override
    public void playChannelDown() throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playChannelDown();
    }

    @Override
    public void playChannel(String channelNumber) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playChannel(channelNumber);
    }

    @Override
    public void playChannel(int channelId) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playChannel(channelId);
    }

    @Override
    public void playFile(String filename) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playFile(filename);
    }

    @Override
    public void playProgram(int channelId, Date recStartTs, boolean resume) throws IOException,
                                                                           CommandException
    {
        connectIfNecessary();
        delegate.playProgram(channelId, recStartTs, resume);
    }

    @Override
    public void playSavePreview() throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playSavePreview();
    }

    @Override
    public void playSavePreview(String filename) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playSavePreview(filename);
    }

    @Override
    public void playSavePreview(String filename, int width, int height) throws IOException,
                                                                       CommandException
    {
        connectIfNecessary();
        delegate.playSavePreview(filename, width, height);
    }

    @Override
    public void playSeek(SeekTarget target) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playSeek(target);
    }

    @Override
    public void playSeek(int hour, int minute, int second) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playSeek(hour, minute, second);
    }

    @Override
    public void playSpeed(float speed) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playSpeed(speed);
    }

    @Override
    public void playStop() throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.playStop();
    }

    @Override
    public void playMusicPlay() throws IOException
    {
        connectIfNecessary();
        delegate.playMusicPlay();
    }

    @Override
    public void playMusicPause() throws IOException
    {
        connectIfNecessary();
        delegate.playMusicPause();
    }

    @Override
    public void playMusicStop() throws IOException
    {
        connectIfNecessary();
        delegate.playMusicStop();
    }

    @Override
    public void playMusicSetVolume(int percent) throws IOException
    {
        connectIfNecessary();
        delegate.playMusicSetVolume(percent);
    }

    @Override
    public int playMusicGetVolume() throws IOException
    {
        connectIfNecessary();
        return delegate.playMusicGetVolume();
    }

    @Override
    public MusicInfo playMusicGetMeta() throws IOException
    {
        connectIfNecessary();
        return delegate.playMusicGetMeta();
    }

    @Override
    public void playMusicFile(String filename) throws IOException
    {
        connectIfNecessary();
        delegate.playMusicFile(filename);
    }

    @Override
    public void playMusicTrack(int track) throws IOException
    {
        connectIfNecessary();
        delegate.playMusicTrack(track);
    }

    @Override
    public void playMusicUrl(URL url) throws IOException
    {
        connectIfNecessary();
        delegate.playMusicUrl(url);
    }

    @Override
    public FrontendLocation queryLocation() throws IOException
    {
        connectIfNecessary();
        return delegate.queryLocation();
    }

    @Override
    public PlaybackInfo queryPlaybackInfo() throws IOException
    {
        connectIfNecessary();
        return delegate.queryPlaybackInfo();
    }

    @Override
    public int queryVolume() throws IOException
    {
        connectIfNecessary();
        return delegate.queryVolume();
    }

    @Override
    public List<Program> queryRecordings() throws IOException
    {
        connectIfNecessary();
        return delegate.queryRecordings();
    }

    @Override
    public Program queryRecording(int channelId, Date recStartTs) throws IOException
    {
        connectIfNecessary();
        return delegate.queryRecording(channelId, recStartTs);
    }

    @Override
    public List<Program> queryLiveTv() throws IOException
    {
        connectIfNecessary();
        return delegate.queryLiveTv();
    }

    @Override
    public Program queryLiveTv(int channelId) throws IOException
    {
        connectIfNecessary();
        return delegate.queryLiveTv(channelId);
    }

    @Override
    public Load queryLoad() throws IOException
    {
        connectIfNecessary();
        return delegate.queryLoad();
    }

    @Override
    public MemStats queryMemStats() throws IOException
    {
        connectIfNecessary();
        return delegate.queryMemStats();
    }

    @Override
    public Date queryTime() throws IOException
    {
        connectIfNecessary();
        return delegate.queryTime();
    }

    @Override
    public long queryUptime() throws IOException
    {
        connectIfNecessary();
        return delegate.queryUptime();
    }

    @Override
    public Set<Verbose> queryVerbose() throws IOException
    {
        connectIfNecessary();
        return delegate.queryVerbose();
    }

    @Override
    public VersionInfo queryVersion() throws IOException
    {
        connectIfNecessary();
        return delegate.queryVersion();
    }

    @Override
    public List<Channel> queryChannels() throws IOException
    {
        connectIfNecessary();
        return delegate.queryChannels();
    }

    @Override
    public List<Channel> queryChannels(int start, int limit) throws IOException
    {
        connectIfNecessary();
        return delegate.queryChannels(start, limit);
    }

    @Override
    public void setVerbose(List<Verbose> options) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.setVerbose(options);
    }

    @Override
    public void screenshot() throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.screenshot();
    }

    @Override
    public void screenshot(int width, int height) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.screenshot(width, height);
    }

    @Override
    public void screenshot(String filename) throws IOException, CommandException
    {
        connectIfNecessary();
        delegate.screenshot(filename);
    }

    @Override
    public void screenshot(String filename, int width, int height) throws IOException,
                                                                  CommandException
    {
        connectIfNecessary();
        delegate.screenshot(filename, width, height);
    }

    @Override
    public void message(String text) throws IOException
    {
        connectIfNecessary();
        delegate.message(text);
    }

    @Override
    public void connect(String host, int port, long timeout) throws IOException
    {
        throw new UnsupportedOperationException();
    }
}
