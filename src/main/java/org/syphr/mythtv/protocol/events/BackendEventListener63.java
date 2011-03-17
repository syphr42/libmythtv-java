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
package org.syphr.mythtv.protocol.events;

import org.syphr.mythtv.protocol.events.impl.BackendEvent63ClearSettingsCache;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63CommFlagRequest;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63DoneRecording;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63DownloadFileFinished;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63DownloadFileUpdate;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63GeneratedPixmap;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63LiveTvChainUpdate;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63LiveTvWatch;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63MasterUpdateProgInfo;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63RecordingListChangeAdd;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63RecordingListChangeDelete;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63RecordingListChangeUpdate;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63ScheduleChange;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63SignalMessage;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63SignalTunerStatus;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63SystemEvent;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63UpdateFileSize;
import org.syphr.mythtv.protocol.events.impl.BackendEvent63VideoListChange;

public interface BackendEventListener63 extends BackendEventListener,
                                       BackendEvent63ClearSettingsCache,
                                       BackendEvent63CommFlagRequest,
                                       BackendEvent63DoneRecording,
                                       BackendEvent63DownloadFileUpdate,
                                       BackendEvent63DownloadFileFinished,
                                       BackendEvent63GeneratedPixmap,
                                       BackendEvent63LiveTvChainUpdate,
                                       BackendEvent63LiveTvWatch,
                                       BackendEvent63MasterUpdateProgInfo,
                                       BackendEvent63RecordingListChangeAdd,
                                       BackendEvent63RecordingListChangeUpdate,
                                       BackendEvent63RecordingListChangeDelete,
                                       BackendEvent63ScheduleChange, BackendEvent63SignalMessage,
                                       BackendEvent63SignalTunerStatus, BackendEvent63SystemEvent,
                                       BackendEvent63UpdateFileSize, BackendEvent63VideoListChange
{
    /*
     * Composite interface.
     */
}
