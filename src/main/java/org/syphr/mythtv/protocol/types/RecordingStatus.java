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
package org.syphr.mythtv.protocol.types;

public enum RecordingStatus
{
    TUNING,
    FAILED,
    TUNER_BUSY,
    LOW_DISK_SPACE,
    CANCELLED,
    MISSED,
    ABORTED,
    RECORDED,
    RECORDING,
    WILL_RECORD,
    UNKNOWN,
    DONT_RECORD,
    PREVIOUS_RECORDING,
    CURRENT_RECORDING,
    EARLIER_SHOWING,
    TOO_MANY_RECORDINGS,
    NOT_LISTED,
    CONFLICT,
    LATER_SHOWING,
    REPEAT,
    INACTIVE,
    NEVER_RECORD,
    OFFLINE,
    OTHER_SHOWING;
}
