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
package org.syphr.mythtv.proto;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Pair;
import org.syphr.mythtv.proto.data.InputInfo;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.types.RecorderFlag;
import org.syphr.mythtv.proto.types.RecordingStatus;
import org.syphr.mythtv.proto.types.SleepStatus;
import org.syphr.mythtv.proto.types.TvState;

public interface QueryRemoteEncoder
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
     *
     * @since 63
     */
    public void cancelNextRecording(boolean cancel) throws IOException;

    /**
     * Retrieve the currently recording program.
     *
     * @return the currently recording program or <code>null</code> if nothing is
     *         recording
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public ProgramInfo getCurrentRecording() throws IOException;

    /**
     * Retrieve state information about various parts of the recorder.
     *
     * @return all set flags
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public Set<RecorderFlag> getFlags() throws IOException;

    // TODO
    public List<InputInfo> getFreeInputs() throws IOException;

    /**
     * Retrieve the maximum bits per second for this recorder.
     *
     * @return the max bitrate
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public long getMaxBitrate() throws IOException;

    /**
     * Retrieve the current recording status of this recorder.
     *
     * @return the status
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public RecordingStatus getRecordingStatus() throws IOException;

    /**
     * Retrieve the current sleep/awake state of this recorder.
     *
     * @return the status
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public SleepStatus getSleepStatus() throws IOException;

    /**
     * Retrieve the current state of this recorder.
     *
     * @return the state
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public TvState getState() throws IOException;

    /**
     * Determine whether or not this recorder is busy or will be within the given number
     * of seconds.
     *
     * @param withinSeconds
     *            the number of seconds ahead to check
     * @return information about the busy input or <code>null</code> if the recorder will
     *         not be busy
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public Pair<Boolean, InputInfo> isBusy(int withinSeconds) throws IOException;

    /**
     * Determine whether or not the recorder is currently recording the given program.
     *
     * @param program
     *            the program to check
     * @return <code>true</code> if the recorder is recording the given program;
     *         <code>false</code> otherwise
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public boolean matchesRecording(ProgramInfo program) throws IOException;

    /**
     * Inform the backend that the given program is scheduled for this recorder in the
     * given number of seconds.
     *
     * @param secondsLeft
     *            the number of seconds until the recording is sceduled to start
     * @param hasLater
     *            <code>true</code> indicates that there is a later showing of this
     *            program that is not conflicted
     * @param program
     *            the program that is scheduled to be recorded
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public void recordPending(int secondsLeft, boolean hasLater, ProgramInfo program) throws IOException;

    // TODO
    public void startRecording() throws IOException;

    /**
     * Request that this recorder stop recording, if it is currently recording.
     *
     * @throws IOException
     *             if there is a communication or protocol error
     *
     * @since 63
     */
    public void stopRecording() throws IOException;
}
