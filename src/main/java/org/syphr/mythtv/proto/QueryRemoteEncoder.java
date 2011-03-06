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

public interface QueryRemoteEncoder
{
    // TODO
    public void cancelNextRecording() throws IOException;

    // TODO
    public void getCurrentRecording() throws IOException;

    // TODO
    public void getFlags() throws IOException;

    // TODO
    public void getFreeInputs() throws IOException;

    // TODO
    public void getMaxBitrate() throws IOException;

    // TODO
    public void getRecordingStatus() throws IOException;

    // TODO
    public void getSleepStatus() throws IOException;

    // TODO
    public void getState() throws IOException;

    // TODO
    public void isBusy() throws IOException;

    // TODO
    public void matchesRecording() throws IOException;

    // TODO
    public void recordPending() throws IOException;

    // TODO
    public void startRecording() throws IOException;

    // TODO
    public void stopRecording() throws IOException;
}
