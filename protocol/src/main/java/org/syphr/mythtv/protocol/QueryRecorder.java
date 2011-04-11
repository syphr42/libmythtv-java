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
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.ChannelQuery;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.types.ChannelBrowseDirection;
import org.syphr.mythtv.types.ChannelChangeDirection;
import org.syphr.mythtv.types.PictureAdjustType;
import org.syphr.mythtv.util.exception.CommandException;

/**
 * This interface is a sub-protocol to {@link Protocol} and represents the
 * combined recorder information API of all MythTV protocols that are supported.
 * However, any functionality that is not part of the protocol present in the
 * most current stable release of MythTV will be marked as deprecated.
 *
 * @see QueryRemoteEncoder
 *
 * @author Gregory P. Moyer
 */
public interface QueryRecorder
{
    /**
     * Inform this encoder to cancel or continue recording the next program scheduled to
     * be recorded. This is useful when the encoder is recording live TV and there is a
     * scheduled recording that needs this encoder.
     *
     * @param cancel
     *            if <code>true</code>, the next recording will be cancelled; otherwise
     *            the recording will continue as scheduled
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public void cancelNextRecording(boolean cancel) throws IOException, CommandException;

    /**
     * Increment or decrement the current brightness value for this recorder. This command
     * is likely to only be useful for frame grabbing recorders.
     *
     * @param type
     *            the level of permanence for this change (i.e. everything recorded on
     *            this channel vs. this recording vs. this playback only)
     * @param increment
     *            if <code>true</code>, increment the current value; otherwise decrement
     *            the current value
     * @return the new value [0, 100] or <code>-1</code> if the recorder is not local to
     *         this backend or the value cannot be changed
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int changeBrightness(PictureAdjustType type, boolean increment) throws IOException, CommandException;

    /**
     * Change the channel in the given direction of the currently recording stream.<br>
     * <br>
     * Note that {@link #pause()} must be called before this command.<br>
     * <br>
     * Note that this command only works for recorders local to this backend.
     *
     * @param direction
     *            the direction in which to change the channel
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public void changeChannel(ChannelChangeDirection direction) throws IOException, CommandException;

    /**
     * Increment or decrement the current color value for this recorder. This command is
     * likely to only be useful for frame grabbing recorders.
     *
     * @param type
     *            the level of permanence for this change (i.e. everything recorded on
     *            this channel vs. this recording vs. this playback only)
     * @param increment
     *            if <code>true</code>, increment the current value; otherwise decrement
     *            the current value
     * @return the new value [0, 100] or <code>-1</code> if the recorder is not local to
     *         this backend or the value cannot be changed
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int changeColour(PictureAdjustType type, boolean increment) throws IOException, CommandException;

    /**
     * Increment or decrement the current contrast value for this recorder. This command
     * is likely to only be useful for frame grabbing recorders.
     *
     * @param type
     *            the level of permanence for this change (i.e. everything recorded on
     *            this channel vs. this recording vs. this playback only)
     * @param increment
     *            if <code>true</code>, increment the current value; otherwise decrement
     *            the current value
     * @return the new value [0, 100] or <code>-1</code> if the recorder is not local to
     *         this backend or the value cannot be changed
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int changeContrast(PictureAdjustType type, boolean increment) throws IOException, CommandException;

    /**
     * Increment or decrement the current contrast value for this recorder. This command
     * is likely to only be useful for frame grabbing recorders.
     *
     * @param type
     *            the level of permanence for this change (i.e. everything recorded on
     *            this channel vs. this recording vs. this playback only)
     * @param increment
     *            if <code>true</code>, increment the current value; otherwise decrement
     *            the current value
     * @return the new value [0, 100] or <code>-1</code> if the recorder is not local to
     *         this backend or the value cannot be changed
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int changeHue(PictureAdjustType type, boolean increment) throws IOException, CommandException;

    /**
     * Determine whether or not the given channel exists on this recorder.<br>
     * <br>
     * Note, this command will return <code>false</code> if the recorder is not
     * local to the connected backend.
     *
     * @param channelNumber
     *            the channel number to check
     * @return <code>true</code> if the channel exists; <code>false</code> if it
     *         does not exist or this recorder is not local to the connected
     *         backend
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public boolean checkChannel(String channelNumber) throws IOException, CommandException;

    /**
     * Query the backend to determine if the given channel number prefix either matches a
     * valid channel or is a prefix to a valid channel.<br>
     * <br>
     * An example use case for this method is a user typing in a channel number. This
     * method can be used to check what the user types to look for a valid channel.<br>
     * <br>
     * Note, this command will return {@link ChannelQuery#isValid() invalid} if the
     * recorder is not local to the connected backend.
     *
     * @param channelNumberPrefix
     *            the prefix of the desired channel number
     * @return a query object with details about the availability of a channel matching
     *         the given prefix
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public ChannelQuery checkChannelPrefix(String channelNumberPrefix) throws IOException, CommandException;

    /**
     * Get a map of keyframe positions to file byte offsets for all of the
     * keyframes within the given range.
     *
     * @param start
     *            the start of the desired range
     * @param end
     *            the end of the desired range or a negative number to indicate
     *            no end to the range
     * @return the relevant keyframe positions and file offsets
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public Map<Long, Long> fillPositionMap(long start, long end) throws IOException, CommandException;

    /**
     * Request that this recorder stop recording as soon as possible.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public void finishRecording() throws IOException, CommandException;

    /**
     * Inform the backend that this client is ready to receive messages.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public void frontendReady() throws IOException, CommandException;

    /**
     * Get the current brightness value for this recorder. This command is
     * likely to only be useful for frame grabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getBrightness() throws IOException, CommandException;

    /**
     * Get information about the channel identified with the given ID.<br>
     * <br>
     * Note that this command only works for recorders local to this backend.
     *
     * @param channelId
     *            the unique ID of the channel whose information is requested
     * @return the corresponding channel information
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public Channel getChannelInfo(int channelId) throws IOException, CommandException;

    /**
     * Get the current color value for this recorder. This command is likely to
     * only be useful for frame grabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getColour() throws IOException, CommandException;

    /**
     * Get the current contrast value for this recorder. This command is likely
     * to only be useful for frame grabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getContrast() throws IOException, CommandException;

    /**
     * Retrieve the currently recording program.
     *
     * @return the currently recording program or <code>null</code> if nothing is
     *         recording
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public Program getCurrentRecording() throws IOException, CommandException;

    /**
     * Get the number of bytes written to disk for the current recording.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the number of bytes written
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public long getFilePosition() throws IOException, CommandException;

    /**
     * Get the frame rate of the current recording.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the frame rate
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public float getFrameRate() throws IOException, CommandException;

    /**
     * Get the number of frames written to disk for the current recording.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the number of frames written
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public long getFramesWritten() throws IOException, CommandException;

    // TODO
    public void getFreeInputs() throws IOException, CommandException;

    /**
     * Get the current hue value for this recorder. This command is likely to
     * only be useful for frame grabbing recorders.
     *
     * @return the current value [0, 100] or <code>-1</code> if the recorder is
     *         not local to this backend or the value cannot be determined
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public int getHue() throws IOException, CommandException;

    /**
     * Get the input on the card to which this recorder is connected.<br>
     * <br>
     * Note, this command will throw an exception if the recorder is not local
     * to the connected backend.
     *
     * @return the name of the input
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public String getInput() throws IOException, CommandException;

    /**
     * Get the closest keyframe position to the desired position.
     *
     * @param desiredPosition
     *            the desired frame position
     * @return the keyframe position closest to the desired location
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public long getKeyframePos(long desiredPosition) throws IOException, CommandException;

    /**
     * Retrieve the maximum bits per second for this recorder.
     *
     * @return the max bitrate
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public long getMaxBitrate() throws IOException, CommandException;

    /**
     * Retrieve information about the program found by moving in the given direction from
     * the given channel and start time.<br>
     * <br>
     * Note, this command will retrieve an empty {@link Program} if the recorder is
     * not local to the connected backend.
     *
     * @param channel
     *            the channel from which to start
     * @param browseDirection
     *            the direction to move to find the next program
     * @param startTime
     *            the base start time
     * @return the next program
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public Program getNextProgramInfo(Channel channel,
                                          ChannelBrowseDirection browseDirection,
                                          Date startTime) throws IOException, CommandException;

    /**
     * Determine whether or not this recorder is currently recording.
     *
     * @return <code>true</code> if this recorder is actively recording,
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public boolean isRecording() throws IOException, CommandException;

    /**
     * Request that the backend pause this recorder.<br>
     * <br>
     * Note, this command will do nothing if the recorder is not local to the
     * connected backend.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void pause() throws IOException, CommandException;

    /**
     * Change to the given channel in the currently recording stream.<br>
     * <br>
     * Note that {@link #pause()} must be called before this command.<br>
     * <br>
     * Note that this command only works for recorders local to this backend.
     *
     * @param channelNumber
     *            the channel number (which does not necessarily have to be a number)
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is unknown
     *
     * @since 63
     */
    public void setChannel(String channelNumber) throws IOException, CommandException;

    /**
     * Request that this recorder switch to the given input.<br>
     * <br>
     * Note that {@link #pause()} must be called before this command.
     *
     * @see #setInputNext()
     *
     * @param input
     *            the input to which to the recorder should switch
     * @return the current input after the switch
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public String setInput(String input) throws IOException, CommandException;

    /**
     * Request that this recorder switch to the next available input.<br>
     * <br>
     * Note that {@link #pause()} must be called before this command.
     *
     * @see #setInput(String)
     *
     * @return the current input after the switch
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if the recorder is not local or it is not currently recording
     *
     * @since 63
     */
    public String setInputNext() throws IOException, CommandException;

    /**
     * Toggle the current state of the current recording between a actual
     * "recording" that will be preserved and a live show.<br>
     * <br>
     * Note, this command will do nothing if the recorder is not local to the
     * connected backend.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void setLiveRecording() throws IOException, CommandException;

    // TODO
    public void setSignalMonitoringRate() throws IOException, CommandException;

    /**
     * Determine if the given channel exists on a recorder other than this one.
     *
     * @param channel
     *            the channel to check
     * @return <code>true</code> if the channel exists on another recorder;
     *         <code>false</code> if the channel does not exist or this recorder
     *         is not local to the connected backend
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public boolean shouldSwitchCard(Channel channel) throws IOException, CommandException;

    /**
     * Request a new LiveTV chain to start recording.
     *
     * @param chainId
     *            the ID of the new chain (suggest live-[host]-[current datetime])
     * @param pip
     *            tell the backend whether or not this chain will be used for
     *            Picture-In-Picture
     * @param startChannel
     *            the channel to start recording
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void spawnLiveTv(String chainId, boolean pip, Channel startChannel) throws IOException, CommandException;

    /**
     * Request that the recorder stop recording and cancel it's live TV chain.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void stopLiveTv() throws IOException, CommandException;

    /**
     * Toggle the current channel as a member of the given channel group. The request only
     * works for recorders that are local to the connected backend. If the recorder is
     * remote, the request will be silently ignored.
     *
     * @param channelGroup
     *            the group in which to toggle the current channel
     * @throws IOException
     *             if there is a communication or protocol error
     * @throws CommandException
     *             if this recorder is unknown
     *
     * @since 63
     */
    public void toggleChannelFavorite(String channelGroup) throws IOException, CommandException;
}
