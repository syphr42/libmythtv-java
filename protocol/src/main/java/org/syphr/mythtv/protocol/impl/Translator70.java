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
package org.syphr.mythtv.protocol.impl;

import java.util.HashMap;
import java.util.Map;

import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.RecordingStatus;
import org.syphr.mythtv.types.TunerStatusCategory;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.types.VideoEditMark;
import org.syphr.mythtv.types.VideoListChangeType;
import org.syphr.mythtv.types.VideoProperty;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator70 extends Translator63
{
    private static final BiMap<RecordingStatus, String> REC_STATUS_MAP = EnumHashBiMap.create(RecordingStatus.class);
    static
    {
        REC_STATUS_MAP.put(RecordingStatus.MISSED_FUTURE, "-11");
        REC_STATUS_MAP.put(RecordingStatus.TUNING, "-10");
        REC_STATUS_MAP.put(RecordingStatus.FAILED, "-9");
        REC_STATUS_MAP.put(RecordingStatus.TUNER_BUSY, "-8");
        REC_STATUS_MAP.put(RecordingStatus.LOW_DISK_SPACE, "-7");
        REC_STATUS_MAP.put(RecordingStatus.CANCELLED, "-6");
        REC_STATUS_MAP.put(RecordingStatus.MISSED, "-5");
        REC_STATUS_MAP.put(RecordingStatus.ABORTED, "-4");
        REC_STATUS_MAP.put(RecordingStatus.RECORDED, "-3");
        REC_STATUS_MAP.put(RecordingStatus.RECORDING, "-2");
        REC_STATUS_MAP.put(RecordingStatus.WILL_RECORD, "-1");
        REC_STATUS_MAP.put(RecordingStatus.UNKNOWN, "0");
        REC_STATUS_MAP.put(RecordingStatus.DONT_RECORD, "1");
        REC_STATUS_MAP.put(RecordingStatus.PREVIOUS_RECORDING, "2");
        REC_STATUS_MAP.put(RecordingStatus.CURRENT_RECORDING, "3");
        REC_STATUS_MAP.put(RecordingStatus.EARLIER_SHOWING, "4");
        REC_STATUS_MAP.put(RecordingStatus.TOO_MANY_RECORDINGS, "5");
        REC_STATUS_MAP.put(RecordingStatus.NOT_LISTED, "6");
        REC_STATUS_MAP.put(RecordingStatus.CONFLICT, "7");
        REC_STATUS_MAP.put(RecordingStatus.LATER_SHOWING, "8");
        REC_STATUS_MAP.put(RecordingStatus.REPEAT, "9");
        REC_STATUS_MAP.put(RecordingStatus.INACTIVE, "10");
        REC_STATUS_MAP.put(RecordingStatus.NEVER_RECORD, "11");
        REC_STATUS_MAP.put(RecordingStatus.OFFLINE, "12");
        REC_STATUS_MAP.put(RecordingStatus.OTHER_SHOWING, "13");
    }

    private static final BiMap<RecordingCategory, String> REC_CATEGORY_MAP = EnumHashBiMap.create(RecordingCategory.class);
    static
    {
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDING, "Recording");
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDED_UNSORTED, "Unsorted");
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDED_ASCENDING, "Ascending");
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDED_DESCENDING, "Descending");
    }

    private static final BiMap<TunerStatusCategory, String> TUNER_STATUS_CATEGORY_MAP = EnumHashBiMap.create(TunerStatusCategory.class);
    static
    {
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SCRIPT_STATUS, "script");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_LOCK, "slock");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_POWER, "signal");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PAT, "seen_pat");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PAT, "matching_pat");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PMT, "seen_pmt");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PMT, "matching_pmt");
    }

    private static final BiMap<Verbose, String> LOG_OPTION_MAP = EnumHashBiMap.create(Verbose.class);
    static
    {
        LOG_OPTION_MAP.put(Verbose.ALL, "all");
        LOG_OPTION_MAP.put(Verbose.NONE, "none");
        LOG_OPTION_MAP.put(Verbose.DEFAULT, "default");

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
    }

    private static final BiMap<VideoListChangeType, String> VIDEO_CHANGE_MAP = EnumHashBiMap.create(VideoListChangeType.class);
    static
    {
        VIDEO_CHANGE_MAP.put(VideoListChangeType.ADDED, "added");
        VIDEO_CHANGE_MAP.put(VideoListChangeType.MOVED, "moved");
        VIDEO_CHANGE_MAP.put(VideoListChangeType.DELETED, "deleted");
    }

    private static final BiMap<VideoEditMark, String> VIDEO_EDIT_MARK_MAP = EnumHashBiMap.create(VideoEditMark.class);
    static
    {
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ALL, "-100");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.UNSET, "-10");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.TMP_CUT_END, "-5");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.TMP_CUT_START, "-4");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.UPDATED_CUT, "-3");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.PLACEHOLDER, "-2");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.CUT_END, "0");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.CUT_START, "1");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.BOOKMARK, "2");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.BLANK_FRAME, "3");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.COMM_START, "4");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.COMM_END, "5");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.GOP_START, "6");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.KEYFRAME, "7");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.SCENE_CHANGE, "8");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.GOP_BYFRAME, "9");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_1_1, "10");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_4_3, "11");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_16_9, "12");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_2_21_1, "13");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.ASPECT_CUSTOM, "14");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.VIDEO_WIDTH, "30");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.VIDEO_HEIGHT, "31");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.VIDEO_RATE, "32");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.DURATION_MS, "33");
        VIDEO_EDIT_MARK_MAP.put(VideoEditMark.TOTAL_FRAMES, "34");
    }

    private static final BiMap<VideoProperty, String> VIDEO_PROPERTY_MAP = EnumHashBiMap.create(VideoProperty.class);
    static
    {
        VIDEO_PROPERTY_MAP.put(VideoProperty.UNKNOWN, String.valueOf(0x00L));
        VIDEO_PROPERTY_MAP.put(VideoProperty.HDTV, String.valueOf(0x01L));
        VIDEO_PROPERTY_MAP.put(VideoProperty.WIDESCREEN, String.valueOf(0x02L));
        VIDEO_PROPERTY_MAP.put(VideoProperty.AVC, String.valueOf(0x04L));
        VIDEO_PROPERTY_MAP.put(VideoProperty._720, String.valueOf(0x08L));
        VIDEO_PROPERTY_MAP.put(VideoProperty._1080, String.valueOf(0x10L));
        VIDEO_PROPERTY_MAP.put(VideoProperty.DAMAGED, String.valueOf(0x20L));
    }

    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends Enum>, BiMap<? extends Enum, String>> MAPS = new HashMap<Class<? extends Enum>, BiMap<? extends Enum, String>>();
    static
    {
        MAPS.put(RecordingStatus.class, REC_STATUS_MAP);
        MAPS.put(RecordingCategory.class, REC_CATEGORY_MAP);
        MAPS.put(TunerStatusCategory.class, TUNER_STATUS_CATEGORY_MAP);
        MAPS.put(Verbose.class, LOG_OPTION_MAP);
        MAPS.put(VideoListChangeType.class, VIDEO_CHANGE_MAP);
        MAPS.put(VideoEditMark.class, VIDEO_EDIT_MARK_MAP);
        MAPS.put(VideoProperty.class, VIDEO_PROPERTY_MAP);
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
