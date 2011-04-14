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

/**
 * This interface represents the combined frontend control API of all MythTV versions that
 * are supported. However, any functionality that is not part of the most current stable
 * release of MythTV will be marked as deprecated.
 *
 * @author Gregory P. Moyer
 */
public interface Control
{
    /**
     * Connect to a frontend instance. This method will block until the connection completes.
     * If a connection is already active, this method will do nothing.
     *
     * @see #isConnected()
     *
     * @param host
     *            the hostname (or IP address) of the server
     * @param port
     *            the port on the server
     * @param timeout
     *            number of milliseconds to wait before assuming the connection failed
     *            (values < 1 indicate no timeout)
     * @throws IOException
     *             if the connection could not be completed
     */
    public void connect(String host, int port, final long timeout) throws IOException;

    /**
     * Determine whether or not there is an active connection to a frontend
     * instance.
     *
     * @return <code>true</code> if there is an active connection;
     *         <code>false</code> otherwise
     */
    public boolean isConnected();

    /**
     * Request that the frontend jump to the given location in the UI.
     *
     * @param jumpPoint
     *            the jump target
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public void jump(FrontendLocation jumpPoint) throws IOException;

    /**
     * Send the given character to the frontend.
     *
     * @param c
     *            the character to send
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public void key(char c) throws IOException;

    /**
     * Send the given key press to the frontend.
     *
     * @param key
     *            the key press to send
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public void key(Key key) throws IOException;

    // TODO
    public void playVolume(int percent) throws IOException;

    // TODO
    public void playChannelUp() throws IOException;

    // TODO
    public void playChannelDown() throws IOException;

    // TODO
    public void playChannel(String channelNumber) throws IOException;

    // TODO
    public void playChannelId(int channelId) throws IOException;

    // TODO
    public void playFile(String filename) throws IOException;

    // TODO
    public void playProgram(int channelId, Date recStartTs, boolean resume) throws IOException;

    // TODO
    public void playSavePreview() throws IOException;

    // TODO
    public void playSavePreview(String filename) throws IOException;

    // TODO
    public void playSavePreview(String filename, int width, int height) throws IOException;

    // TODO
    public void playSeek() throws IOException;

    // TODO
    public void playSpeedPause() throws IOException;

    // TODO
    public void playSpeed(float speed) throws IOException;

    // TODO
    public void playStop() throws IOException;

    /**
     * Request the current UI location of the frontend.
     *
     * @return the current location
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public FrontendLocation queryLocation() throws IOException;

    /**
     * Request the current volume of the frontend as an integer percentage in the range
     * 0-100.
     *
     * @return the current volume
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public int queryVolume() throws IOException;

    /**
     * Request a list of available recordings.
     *
     * @return the recordings
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public List<Program> queryRecordings() throws IOException;

    /**
     * Request details about a specific recording.
     *
     * @param channelId
     *            the ID of the channel corresponding to the desired recording
     * @param recStartTs
     *            the actual start time of the desired recording
     *
     * @return the recording, if it exists; <code>null</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public Program queryRecording(int channelId, Date recStartTs) throws IOException;

    /**
     * Request a list of all programs in the available guide data for the
     * current timeslot.
     *
     * @return the programs
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public List<Program> queryLiveTv() throws IOException;

    /**
     * Request the program that is in the guide data for the current date/time on the
     * given channel.
     *
     * @param channelId
     *            the ID of the channel to check
     *
     * @return the program, if it exists; <code>null</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public Program queryLiveTv(int channelId) throws IOException;

    /**
     * Retrieve the load factor of the frontend machine.
     *
     * @return data representing the load factor over time of the frontend machine
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public Load queryLoad() throws IOException;

    /**
     * Retrieve memory statistics of the frontend machine.
     *
     * @return data representing the current memory usage of the frontend machine
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public MemStats queryMemStats() throws IOException;

    /**
     * Retrieve the current date/time on the frontend machine.
     *
     * @return the date/time
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public Date queryTime() throws IOException;

    /**
     * Determine the up time, in seconds, of the frontend machine.
     *
     * @return the number of seconds the frontend has been up or <code>-1</code> if the up
     *         time could not be determined
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public long queryUptime() throws IOException;

    // TODO
    public List<Verbose> queryVerbose() throws IOException;

    // TODO
    public String queryVersion() throws IOException;

    /**
     * Retrieve a list of visible channels available to this frontend.<br>
     * <br>
     * Note that the resulting channel objects are not completely filled in.
     * They consist of an ID, and callsign, and a name.
     *
     * @return the list of visible channels
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public List<Channel> queryChannels() throws IOException;

    /**
     * Retrieve a list of visible channels available to this frontend within the
     * given bounds.<br>
     * <br>
     * Note that the resulting channel objects are not completely filled in.
     * They consist of an ID, and callsign, and a name.
     *
     * @param start
     *            the starting position in the total list of channels for the
     *            desired sublist
     * @param limit
     *            the number of channels to return, beginning with the channel
     *            at the start index
     *
     * @return the list of visible channels
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public List<Channel> queryChannels(int start, int limit) throws IOException;

    /**
     * Change the verbose logging options on the frontend.
     *
     * @param options
     *            the options to set
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the frontend is unable to set the verbose logging options
     *
     * @since 0.24
     */
    public void setVerbose(List<Verbose> options) throws IOException, CommandException;

    // TODO
    public void screenshot() throws IOException;

    // TODO
    public void screenshot(String filename) throws IOException;

    // TODO
    public void screenshot(String filename, int width, int height) throws IOException;

    /**
     * Send some text to be displayed on the frontend.
     *
     * @param text
     *            the text to send
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.25
     */
    public void message(String text) throws IOException;

    /**
     * Gracefully disconnect this client from the frontend.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 0.24
     */
    public void exit() throws IOException;

    /**
     * Retrieve a list of constants of the given type that are valid for this
     * control version.<br>
     * <br>
     * This is useful to know before trying a command that a certain option is
     * valid. For example, to use {@link #jump(FrontendLocation)} without the risk of
     * sending an invalid jump point, first make sure it is in the list returned
     * from <code>getAvailableTypes(JumpPoint.class)</code>
     *
     * @param <E>
     *            the generic enum type
     * @param type
     *            the enum class
     * @return a list of constants that are valid for this control
     */
    public <E extends Enum<E>> List<E> getAvailableTypes(Class<E> type);
}
