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

public enum FrontendLocation
{
    CHANNEL_PRIORITIES,
    CHANNEL_REC_PRIORITY,
    DELETE_BOX,
    DELETE_RECORDINGS,
    GUIDE_GRID,
    LIVE_TV,
    LIVE_TV_IN_GUIDE,
    MAIN_MENU,
    MANAGE_RECORDINGS,
    MUSIC_PLAYLISTS,
    MYTH_GALLERY,
    MYTH_GAME,
    MYTH_NEWS,
    MYTH_VIDEO,
    MYTH_WEATHER,
    PLAYBACK_BOX,
    PLAYBACK_RECORDINGS,
    PLAY_DVD,
    PLAY_MUSIC,
    PREVIOUS_BOX,
    PROG_FINDER,
    PROGRAM_FINDER,
    PROGRAM_GUIDE,
    PROGRAM_REC_PRIORITY,
    RECORDING_PRIORITIES,
    RIP_CD,
    STATUS_BOX,
    VIDEO_BROWSER,
    VIDEO_GALLERY,
    VIDEO_LISTINGS,
    VIDEO_MANAGER,
    VIEW_SCHEDULED,
    ZONEMINDER_CONSOLE,
    ZONEMINDER_EVENTS,
    ZONEMINDER_LIVE_VIEW,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    FLIX_BROWSE,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    FLIX_HISTORY,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    FLIX_QUEUE,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    MANUAL_RECORDING,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    MANUAL_BOX,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    MYTH_MOVIETIME,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    RIP_DVD,

    /**
     * @since 0.24
     * @deprecated 0.25
     */
    @Deprecated
    SNAPSHOT,

    /**
     * The playback location is special in that it is not valid as a jump
     * target, but it is the declared location whenever the frontend is playing
     * back video.
     * 
     * @since 0.24
     */
    PLAYBACK,

    /**
     * The unknown location is special in that it is not valid as a jump target,
     * but it is returned whenever the frontend cannot determine the current
     * location.
     * 
     * @since 0.24
     */
    UNKNOWN;
}
