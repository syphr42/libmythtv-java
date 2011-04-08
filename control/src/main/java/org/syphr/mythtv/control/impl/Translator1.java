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
package org.syphr.mythtv.control.impl;

import org.syphr.mythtv.control.types.JumpPoint;
import org.syphr.mythtv.util.translate.AbstractTranslator;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator1 extends AbstractTranslator
{
    private static final BiMap<JumpPoint, String> JUMP_POINT_MAP = EnumHashBiMap.create(JumpPoint.class);
    static
    {
        JUMP_POINT_MAP.put(JumpPoint.CHANNEL_PRIORITIES, "channelpriorities");
        JUMP_POINT_MAP.put(JumpPoint.CHANNEL_REC_PRIORITY, "channelrecpriority");
        JUMP_POINT_MAP.put(JumpPoint.DELETE_BOX, "deletebox");
        JUMP_POINT_MAP.put(JumpPoint.DELETE_RECORDINGS, "deleterecordings");
        JUMP_POINT_MAP.put(JumpPoint.FLIX_BROWSE, "flixbrowse");
        JUMP_POINT_MAP.put(JumpPoint.FLIX_HISTORY, "flixhistory");
        JUMP_POINT_MAP.put(JumpPoint.FLIX_QUEUE, "flixqueue");
        JUMP_POINT_MAP.put(JumpPoint.GUIDE_GRID, "guidegrid");
        JUMP_POINT_MAP.put(JumpPoint.LIVE_TV, "livetv");
        JUMP_POINT_MAP.put(JumpPoint.LIVE_TV_IN_GUIDE, "livetvinguide");
        JUMP_POINT_MAP.put(JumpPoint.MAIN_MENU, "mainmenu");
        JUMP_POINT_MAP.put(JumpPoint.MANAGE_RECORDINGS, "managerecordings");
        JUMP_POINT_MAP.put(JumpPoint.MANUAL_BOX, "manualbox");
        JUMP_POINT_MAP.put(JumpPoint.MANUAL_RECORDING, "manualrecording");
        JUMP_POINT_MAP.put(JumpPoint.MUSIC_PLAYLISTS, "musicplaylists");
        JUMP_POINT_MAP.put(JumpPoint.MYTH_GALLERY, "mythgallery");
        JUMP_POINT_MAP.put(JumpPoint.MYTH_GAME, "mythgame");
        JUMP_POINT_MAP.put(JumpPoint.MYTH_MOVIETIME, "mythmovietime");
        JUMP_POINT_MAP.put(JumpPoint.MYTH_NEWS, "mythnews");
        JUMP_POINT_MAP.put(JumpPoint.MYTH_VIDEO, "mythvideo");
        JUMP_POINT_MAP.put(JumpPoint.MYTH_WEATHER, "mythweather");
        JUMP_POINT_MAP.put(JumpPoint.PLAYBACK_BOX, "playbackbox");
        JUMP_POINT_MAP.put(JumpPoint.PLAYBACK_RECORDINGS, "playbackrecordings");
        JUMP_POINT_MAP.put(JumpPoint.PLAY_DVD, "playdvd");
        JUMP_POINT_MAP.put(JumpPoint.PLAY_MUSIC, "playmusic");
        JUMP_POINT_MAP.put(JumpPoint.PREVIOUS_BOX, "previousbox");
        JUMP_POINT_MAP.put(JumpPoint.PROG_FINDER, "progfinder");
        JUMP_POINT_MAP.put(JumpPoint.PROGRAM_FINDER, "programfinder");
        JUMP_POINT_MAP.put(JumpPoint.PROGRAM_GUIDE, "programguide");
        JUMP_POINT_MAP.put(JumpPoint.PROGRAM_REC_PRIORITY, "programrecpriority");
        JUMP_POINT_MAP.put(JumpPoint.RECORDING_PRIORITIES, "recordingpriorities");
        JUMP_POINT_MAP.put(JumpPoint.RIP_CD, "ripcd");
        JUMP_POINT_MAP.put(JumpPoint.RIP_DVD, "ripdvd");
        JUMP_POINT_MAP.put(JumpPoint.SNAPSHOT, "snapshot");
        JUMP_POINT_MAP.put(JumpPoint.STATUS_BOX, "statusbox");
        JUMP_POINT_MAP.put(JumpPoint.VIDEO_BROWSER, "videobrowser");
        JUMP_POINT_MAP.put(JumpPoint.VIDEO_GALLERY, "videogallery");
        JUMP_POINT_MAP.put(JumpPoint.VIDEO_LISTINGS, "videolistings");
        JUMP_POINT_MAP.put(JumpPoint.VIDEO_MANAGER, "videomanager");
        JUMP_POINT_MAP.put(JumpPoint.VIEW_SCHEDULED, "viewscheduled");
        JUMP_POINT_MAP.put(JumpPoint.ZONEMINDER_CONSOLE, "zoneminderconsole");
        JUMP_POINT_MAP.put(JumpPoint.ZONEMINDER_EVENTS, "zoneminderevents");
        JUMP_POINT_MAP.put(JumpPoint.ZONEMINDER_LIVE_VIEW, "zoneminderliveview");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type)
    {
        /*
         * Cast to raw BiMap necessary to appease javac (Eclipse doesn't require it).
         */

        if (JumpPoint.class.equals(type))
        {
            return (BiMap)JUMP_POINT_MAP;
        }

        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
