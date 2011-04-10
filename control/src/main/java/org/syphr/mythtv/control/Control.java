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
     * @since 1
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
     * @since 1
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
     * @since 1
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
     * @since 1
     */
    public FrontendLocation queryLocation() throws IOException;

    // TODO
    public void queryVolume() throws IOException;

    // TODO
    public void queryRecordings() throws IOException;

    // TODO
    public void queryRecording(int channelId, Date recStartTs) throws IOException;

    // TODO
    public void queryLiveTv() throws IOException;

    // TODO
    public void queryLiveTv(int channelId) throws IOException;

    // TODO
    public void queryLoad() throws IOException;

    // TODO
    public void queryMemStats() throws IOException;

    // TODO
    public void queryTime() throws IOException;

    // TODO
    public void queryUptime() throws IOException;

    // TODO
    public void queryVerbose() throws IOException;

    // TODO
    public void queryVersion() throws IOException;

    // TODO
    public void queryChannels() throws IOException;

    // TODO
    public void queryChannels(int start, int limit) throws IOException;

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
     * @since 1
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
     * @since 2
     */
    public void message(String text) throws IOException;

    /**
     * Gracefully disconnect this client from the frontend.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 1
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
