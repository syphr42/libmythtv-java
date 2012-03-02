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
package org.syphr.mythtv.control.impl;

import java.util.HashMap;
import java.util.Map;

import org.syphr.mythtv.types.FrontendLocation;
import org.syphr.mythtv.types.Verbose;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator0_25 extends Translator0_24
{
    private static final BiMap<FrontendLocation, String> FE_LOCATION_MAP = EnumHashBiMap.create(FrontendLocation.class);
    static
    {
        FE_LOCATION_MAP.put(FrontendLocation.CHANNEL_PRIORITIES, "channelpriorities");
        FE_LOCATION_MAP.put(FrontendLocation.CHANNEL_REC_PRIORITY, "channelrecpriority");
        FE_LOCATION_MAP.put(FrontendLocation.DELETE_BOX, "deletebox");
        FE_LOCATION_MAP.put(FrontendLocation.DELETE_RECORDINGS, "deleterecordings");
        FE_LOCATION_MAP.put(FrontendLocation.GUIDE_GRID, "guidegrid");
        FE_LOCATION_MAP.put(FrontendLocation.LIVE_TV, "livetv");
        FE_LOCATION_MAP.put(FrontendLocation.LIVE_TV_IN_GUIDE, "livetvinguide");
        FE_LOCATION_MAP.put(FrontendLocation.MAIN_MENU, "mainmenu");
        FE_LOCATION_MAP.put(FrontendLocation.MANAGE_RECORDINGS, "managerecordings");
        FE_LOCATION_MAP.put(FrontendLocation.MUSIC_PLAYLISTS, "musicplaylists");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_GALLERY, "mythgallery");
        FE_LOCATION_MAP.put(FrontendLocation.MYTH_GAME, "mythgame");
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

    private static final BiMap<Verbose, String> LOG_OPTION_MAP = EnumHashBiMap.create(Verbose.class);
    static
    {
        LOG_OPTION_MAP.put(Verbose.ALL, "all");
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
        LOG_OPTION_MAP.put(Verbose.NOT_TIMESTAMP, "notimestamp");

        LOG_OPTION_MAP.put(Verbose.DEFAULT, "default");
    }

    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends Enum>, BiMap<? extends Enum, String>> MAPS = new HashMap<Class<? extends Enum>, BiMap<? extends Enum, String>>();
    static
    {
        MAPS.put(FrontendLocation.class, FE_LOCATION_MAP);
        MAPS.put(Verbose.class, LOG_OPTION_MAP);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type)
    {
        if (!MAPS.containsKey(type))
        {
            return super.getMap(type);
        }

        return (BiMap)MAPS.get(type);
    }
}
