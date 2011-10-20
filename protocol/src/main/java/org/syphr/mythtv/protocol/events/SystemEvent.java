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

public enum SystemEvent
{
    CLIENT_CONNECTED,
    CLIENT_DISCONNECTED,
    SLAVE_CONNECTED,
    SLAVE_DISCONNECTED,
    SCHEDULER_RAN,
    REC_PENDING,
    REC_STARTED,
    REC_FINISHED,
    REC_DELETED,
    REC_EXPIRED,
    LIVETV_STARTED,
    PLAY_STARTED,
    PLAY_STOPPED,
    PLAY_PAUSED,
    PLAY_UNPAUSED,
    PLAY_CHANGED,
    MASTER_STARTED,
    MASTER_SHUTDOWN,
    NET_CTRL_CONNECTED,
    NET_CTRL_DISCONNECTED,
    MYTHFILLDATABASE_RAN,
    SETTINGS_CACHE_CLEARED,
    SCREEN_TYPE,
    USER_1,
    USER_2,
    USER_3,
    USER_4,
    USER_5,
    USER_6,
    USER_7,
    USER_8,
    USER_9
}
