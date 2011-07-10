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
package org.syphr.mythtv.types;

public enum Verbose
{
    ALL,
    NONE,

    MOST,
    GENERAL,
    RECORD,
    PLAYBACK,
    CHANNEL,
    OSD,
    FILE,
    SCHEDULE,
    NETWORK,
    COMM_FLAG,
    AUDIO,
    LIBAV,
    JOB_QUEUE,
    SI_PARSER,
    EIT,
    VBI,
    DATABASE,
    DSMCC,
    MHEG,
    UPNP,
    SOCKET,
    XMLTV,
    DVB_CAM,
    MEDIA,
    IDLE,
    CHANNEL_SCAN,
    GUI,
    SYSTEM,
    TIMESTAMP,

    NOT_MOST,
    NOT_GENERAL,
    NOT_RECORD,
    NOT_PLAYBACK,
    NOT_CHANNEL,
    NOT_OSD,
    NOT_FILE,
    NOT_SCHEDULE,
    NOT_NETWORK,
    NOT_COMM_FLAG,
    NOT_AUDIO,
    NOT_LIBAV,
    NOT_JOB_QUEUE,
    NOT_SI_PARSER,
    NOT_EIT,
    NOT_VBI,
    NOT_DATABASE,
    NOT_DSMCC,
    NOT_MHEG,
    NOT_UPNP,
    NOT_SOCKET,
    NOT_XMLTV,
    NOT_DVB_CAM,
    NOT_MEDIA,
    NOT_IDLE,
    NOT_CHANNEL_SCAN,
    NOT_GUI,
    NOT_SYSTEM,
    NOT_TIMESTAMP,

    DEFAULT,

    /**
     * @since 63
     * @deprecated 67
     */
    @Deprecated
    IMPORTANT,

    /**
     * @since 63
     * @deprecated 67
     */
    @Deprecated
    EXTRA,

    /**
     * @since 63
     * @deprecated 67
     */
    @Deprecated
    NOT_EXTRA;
}
