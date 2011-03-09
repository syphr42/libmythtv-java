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
import java.util.Set;

import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.types.RecorderFlag;
import org.syphr.mythtv.proto.types.RecordingStatus;
import org.syphr.mythtv.proto.types.SleepStatus;
import org.syphr.mythtv.proto.types.TvState;

public interface QueryRemoteEncoder
{
    // TODO
    public void cancelNextRecording() throws IOException;

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
    public void getFreeInputs() throws IOException;

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

    // TODO
    public void isBusy() throws IOException;

    // TODO
    public void matchesRecording() throws IOException;

    // TODO
    public void recordPending() throws IOException;

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
