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
package org.syphr.mythtv.types;

public enum RecordingType
{
    NOT_RECORDING,
    SINGLE_RECORD,
    TIMESLOT_RECORD,
    CHANNEL_RECORD,
    ALL_RECORD,
    WEEKSLOT_RECORD,
    FIND_ONE_RECORD,
    OVERRIDE_RECORD,
    DONT_RECORD,
    FIND_DAILY_RECORD,
    FIND_WEEKLY_RECORD;
}
