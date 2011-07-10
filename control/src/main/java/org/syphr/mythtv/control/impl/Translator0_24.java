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

import java.util.HashMap;
import java.util.Map;

import org.syphr.mythtv.types.FrontendLocation;
import org.syphr.mythtv.types.Key;
import org.syphr.mythtv.types.PlaybackType;
import org.syphr.mythtv.types.SeekTarget;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.util.translate.AbstractTranslator;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator0_24 extends AbstractTranslator
{
    private static final BiMap<FrontendLocation, String> FE_LOCATION_MAP = EnumHashBiMap.create(FrontendLocation.class);
    static
    {
        FE_LOCATION_MAP.put(FrontendLocation.CHANNEL_PRIORITIES, "channelpriorities");
        FE_LOCATION_MAP.put(FrontendLocation.CHANNEL_REC_PRIORITY, "channelrecpriority");
        FE_LOCATION_MAP.put(FrontendLocation.DELETE_BOX, "deletebox");
        FE_LOCATION_MAP.put(FrontendLocation.DELETE_RECORDINGS, "deleterecordings");
        FE_LOCATION_MAP.put(FrontendLocation.FLIX_BROWSE, "flixbrowse");
        FE_LOCATION_MAP.put(FrontendLocation.FLIX_HISTORY, "flixhistory");
        FE_LOCATION_MAP.put(FrontendLocation.FLIX_QUEUE, "flixqueue");
        FE_LOCATION_MAP.put(FrontendLocation.GUIDE_GRID, "guidegrid");
        FE_LOCATION_MAP.put(FrontendLocation.LIVE_TV, "livetv");
        FE_LOCATION_MAP.put(FrontendLocation.LIVE_TV_IN_GUIDE, "livetvinguide");
        FE_LOCATION_MAP.put(FrontendLocation.MAIN_MENU, "mainmenu");
        FE_LOCATION_MAP.put(FrontendLocation.MANAGE_RECORDINGS, "managerecordings");
        FE_LOCATION_MAP.put(FrontendLocation.MANUAL_BOX, "manualbox");
        FE_LOCATION_MAP.put(FrontendLocation.MANUAL_RECORDING, "manualrecording");
        FE_LOCATION_MAP.put(FrontendLocation.MUSIC_PLAYLISTS, "musicplaylists");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_GALLERY, "mythgallery");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_GAME, "mythgame");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_MOVIETIME, "mythmovietime");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_NEWS, "mythnews");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_VIDEO, "mythvideo");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_WEATHER, "mythweather");
        FE_LOCATION_MAP.put(FrontendLocation.PLAYBACK_BOX, "playbackbox");
        FE_LOCATION_MAP.put(FrontendLocation.PLAYBACK_RECORDINGS, "playbackrecordings");
        FE_LOCATION_MAP.put(FrontendLocation.PLAY_DVD, "playdvd");
        FE_LOCATION_MAP.put(FrontendLocation.PLAY_MUSIC, "playmusic");
        FE_LOCATION_MAP.put(FrontendLocation.PREVIOUS_BOX, "previousbox");
        FE_LOCATION_MAP.put(FrontendLocation.PROG_FINDER, "progfinder");
        FE_LOCATION_MAP.put(FrontendLocation.PROGRAM_FINDER, "programfinder");
        FE_LOCATION_MAP.put(FrontendLocation.PROGRAM_GUIDE, "programguide");
        FE_LOCATION_MAP.put(FrontendLocation.PROGRAM_REC_PRIORITY, "programrecpriority");
        FE_LOCATION_MAP.put(FrontendLocation.RECORDING_PRIORITIES, "recordingpriorities");
        FE_LOCATION_MAP.put(FrontendLocation.RIP_CD, "ripcd");
        FE_LOCATION_MAP.put(FrontendLocation.RIP_DVD, "ripdvd");
        FE_LOCATION_MAP.put(FrontendLocation.SNAPSHOT, "snapshot");
        FE_LOCATION_MAP.put(FrontendLocation.STATUS_BOX, "statusbox");
        FE_LOCATION_MAP.put(FrontendLocation.VIDEO_BROWSER, "videobrowser");
        FE_LOCATION_MAP.put(FrontendLocation.VIDEO_GALLERY, "videogallery");
        FE_LOCATION_MAP.put(FrontendLocation.VIDEO_LISTINGS, "videolistings");
        FE_LOCATION_MAP.put(FrontendLocation.VIDEO_MANAGER, "videomanager");
        FE_LOCATION_MAP.put(FrontendLocation.VIEW_SCHEDULED, "viewscheduled");
        FE_LOCATION_MAP.put(FrontendLocation.ZONEMINDER_CONSOLE, "zoneminderconsole");
        FE_LOCATION_MAP.put(FrontendLocation.ZONEMINDER_EVENTS, "zoneminderevents");
        FE_LOCATION_MAP.put(FrontendLocation.ZONEMINDER_LIVE_VIEW, "zoneminderliveview");
    }

    private static final BiMap<Key, String> KEY_MAP = EnumHashBiMap.create(Key.class);
    static
    {
        KEY_MAP.put(Key.BACKSPACE, "backspace");
        KEY_MAP.put(Key.BACKTAB, "backtab");
        KEY_MAP.put(Key.DELETE, "delete");
        KEY_MAP.put(Key.UP, "up");
        KEY_MAP.put(Key.LEFT, "left");
        KEY_MAP.put(Key.DOWN, "down");
        KEY_MAP.put(Key.RIGHT, "right");
        KEY_MAP.put(Key.PAGEDOWN, "pagedown");
        KEY_MAP.put(Key.PAGEUP, "pageup");
        KEY_MAP.put(Key.END, "end");
        KEY_MAP.put(Key.ENTER, "enter");
        KEY_MAP.put(Key.ESCAPE, "escape");
        KEY_MAP.put(Key.HOME, "home");
        KEY_MAP.put(Key.INSERT, "insert");
        KEY_MAP.put(Key.SPACE, "space");
        KEY_MAP.put(Key.TAB, "tab");
        KEY_MAP.put(Key.F1, "f1");
        KEY_MAP.put(Key.F2, "f2");
        KEY_MAP.put(Key.F3, "f3");
        KEY_MAP.put(Key.F4, "f4");
        KEY_MAP.put(Key.F5, "f5");
        KEY_MAP.put(Key.F6, "f6");
        KEY_MAP.put(Key.F7, "f7");
        KEY_MAP.put(Key.F8, "f8");
        KEY_MAP.put(Key.F9, "f9");
        KEY_MAP.put(Key.F10, "f10");
        KEY_MAP.put(Key.F11, "f11");
        KEY_MAP.put(Key.F12, "f12");
        KEY_MAP.put(Key.F13, "f13");
        KEY_MAP.put(Key.F14, "f14");
        KEY_MAP.put(Key.F15, "f15");
        KEY_MAP.put(Key.F16, "f16");
        KEY_MAP.put(Key.F17, "f17");
        KEY_MAP.put(Key.F18, "f18");
        KEY_MAP.put(Key.F19, "f19");
        KEY_MAP.put(Key.F20, "f20");
        KEY_MAP.put(Key.F21, "f21");
        KEY_MAP.put(Key.F22, "f22");
        KEY_MAP.put(Key.F23, "f23");
        KEY_MAP.put(Key.F24, "f24");
    }

    private static final BiMap<Verbose, String> LOG_OPTION_MAP = EnumHashBiMap.create(Verbose.class);
    static
    {
        LOG_OPTION_MAP.put(Verbose.ALL, "all");
        LOG_OPTION_MAP.put(Verbose.IMPORTANT, "important");
        LOG_OPTION_MAP.put(Verbose.NONE, "none");

        LOG_OPTION_MAP.put(Verbose.MOST, "most");
        LOG_OPTION_MAP.put(Verbose.GENERAL, "general");
        LOG_OPTION_MAP.put(Verbose.RECORD, "record");
        LOG_OPTION_MAP.put(Verbose.PLAYBACK, "playback");
        LOG_OPTION_MAP.put(Verbose.CHANNEL, "channel");
        LOG_OPTION_MAP.put(Verbose.OSD, "osd");
        LOG_OPTION_MAP.put(Verbose.FILE, "file");
        LOG_OPTION_MAP.put(Verbose.SCHEDULE, "schedule");
        LOG_OPTION_MAP.put(Verbose.NETWORK, "network");
        LOG_OPTION_MAP.put(Verbose.COMM_FLAG, "commflag");
        LOG_OPTION_MAP.put(Verbose.AUDIO, "audio");
        LOG_OPTION_MAP.put(Verbose.LIBAV, "libav");
        LOG_OPTION_MAP.put(Verbose.JOB_QUEUE, "jobqueue");
        LOG_OPTION_MAP.put(Verbose.SI_PARSER, "siparser");
        LOG_OPTION_MAP.put(Verbose.EIT, "eit");
        LOG_OPTION_MAP.put(Verbose.VBI, "vbi");
        LOG_OPTION_MAP.put(Verbose.DATABASE, "database");
        LOG_OPTION_MAP.put(Verbose.DSMCC, "dsmcc");
        LOG_OPTION_MAP.put(Verbose.MHEG, "mheg");
        LOG_OPTION_MAP.put(Verbose.UPNP, "upnp");
        LOG_OPTION_MAP.put(Verbose.SOCKET, "socket");
        LOG_OPTION_MAP.put(Verbose.XMLTV, "xmltv");
        LOG_OPTION_MAP.put(Verbose.DVB_CAM, "dvbcam");
        LOG_OPTION_MAP.put(Verbose.MEDIA, "media");
        LOG_OPTION_MAP.put(Verbose.IDLE, "idle");
        LOG_OPTION_MAP.put(Verbose.CHANNEL_SCAN, "channelscan");
        LOG_OPTION_MAP.put(Verbose.GUI, "gui");
        LOG_OPTION_MAP.put(Verbose.SYSTEM, "system");
        LOG_OPTION_MAP.put(Verbose.EXTRA, "extra");
        LOG_OPTION_MAP.put(Verbose.TIMESTAMP, "timestamp");

        LOG_OPTION_MAP.put(Verbose.NOT_MOST, "nomost");
        LOG_OPTION_MAP.put(Verbose.NOT_GENERAL, "nogeneral");
        LOG_OPTION_MAP.put(Verbose.NOT_RECORD, "norecord");
        LOG_OPTION_MAP.put(Verbose.NOT_PLAYBACK, "noplayback");
        LOG_OPTION_MAP.put(Verbose.NOT_CHANNEL, "nochannel");
        LOG_OPTION_MAP.put(Verbose.NOT_OSD, "noosd");
        LOG_OPTION_MAP.put(Verbose.NOT_FILE, "nofile");
        LOG_OPTION_MAP.put(Verbose.NOT_SCHEDULE, "noschedule");
        LOG_OPTION_MAP.put(Verbose.NOT_NETWORK, "nonetwork");
        LOG_OPTION_MAP.put(Verbose.NOT_COMM_FLAG, "nocommflag");
        LOG_OPTION_MAP.put(Verbose.NOT_AUDIO, "noaudio");
        LOG_OPTION_MAP.put(Verbose.NOT_LIBAV, "nolibav");
        LOG_OPTION_MAP.put(Verbose.NOT_JOB_QUEUE, "nojobqueue");
        LOG_OPTION_MAP.put(Verbose.NOT_SI_PARSER, "nosiparser");
        LOG_OPTION_MAP.put(Verbose.NOT_EIT, "noeit");
        LOG_OPTION_MAP.put(Verbose.NOT_VBI, "novbi");
        LOG_OPTION_MAP.put(Verbose.NOT_DATABASE, "nodatabase");
        LOG_OPTION_MAP.put(Verbose.NOT_DSMCC, "nodsmcc");
        LOG_OPTION_MAP.put(Verbose.NOT_MHEG, "nomheg");
        LOG_OPTION_MAP.put(Verbose.NOT_UPNP, "noupnp");
        LOG_OPTION_MAP.put(Verbose.NOT_SOCKET, "nosocket");
        LOG_OPTION_MAP.put(Verbose.NOT_XMLTV, "noxmltv");
        LOG_OPTION_MAP.put(Verbose.NOT_DVB_CAM, "nodvbcam");
        LOG_OPTION_MAP.put(Verbose.NOT_MEDIA, "nomedia");
        LOG_OPTION_MAP.put(Verbose.NOT_IDLE, "noidle");
        LOG_OPTION_MAP.put(Verbose.NOT_CHANNEL_SCAN, "nochannelscan");
        LOG_OPTION_MAP.put(Verbose.NOT_GUI, "nogui");
        LOG_OPTION_MAP.put(Verbose.NOT_SYSTEM, "nosystem");
        LOG_OPTION_MAP.put(Verbose.NOT_EXTRA, "noextra");
        LOG_OPTION_MAP.put(Verbose.NOT_TIMESTAMP, "notimestamp");

        LOG_OPTION_MAP.put(Verbose.DEFAULT, "default");
    }

    private static final BiMap<PlaybackType, String> PLAYBACK_TYPE_MAP = EnumHashBiMap.create(PlaybackType.class);
    static
    {
        PLAYBACK_TYPE_MAP.put(PlaybackType.LIVE_TV, "LiveTV");
        PLAYBACK_TYPE_MAP.put(PlaybackType.DVD, "DVD");
        PLAYBACK_TYPE_MAP.put(PlaybackType.RECORDED, "Recorded");
        PLAYBACK_TYPE_MAP.put(PlaybackType.VIDEO, "Video");
    }

    private static final BiMap<SeekTarget, String> SEEK_TARGET_MAP = EnumHashBiMap.create(SeekTarget.class);
    static
    {
        SEEK_TARGET_MAP.put(SeekTarget.BEGINNING, "beginning");
        SEEK_TARGET_MAP.put(SeekTarget.FORWARD, "forward");
        SEEK_TARGET_MAP.put(SeekTarget.BACKWARD, "backward");
    }

    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends Enum>, BiMap<? extends Enum, String>> MAPS = new HashMap<Class<? extends Enum>, BiMap<? extends Enum, String>>();
    static
    {
        MAPS.put(FrontendLocation.class, FE_LOCATION_MAP);
        MAPS.put(Key.class, KEY_MAP);
        MAPS.put(Verbose.class, LOG_OPTION_MAP);
        MAPS.put(PlaybackType.class, PLAYBACK_TYPE_MAP);
        MAPS.put(SeekTarget.class, SEEK_TARGET_MAP);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type)
    {
        if (!MAPS.containsKey(type))
        {
            throw new IllegalArgumentException("Unknown type: " + type);
        }

        return (BiMap) MAPS.get(type);
    }
}
