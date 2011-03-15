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
package org.syphr.mythtv.proto.impl;

import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.FileTransferType;
import org.syphr.mythtv.proto.types.Verbose;
import org.syphr.mythtv.proto.types.RecorderFlag;
import org.syphr.mythtv.proto.types.RecordingCategory;
import org.syphr.mythtv.proto.types.RecordingStatus;
import org.syphr.mythtv.proto.types.RecordingType;
import org.syphr.mythtv.proto.types.SeekOrigin;
import org.syphr.mythtv.proto.types.SleepStatus;
import org.syphr.mythtv.proto.types.TunerStatusCategory;
import org.syphr.mythtv.proto.types.TvState;
import org.syphr.mythtv.proto.types.VideoEditMark;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator63 extends AbstractTranslator
{
    private static final BiMap<RecordingStatus, String> REC_STATUS_MAP = EnumHashBiMap.create(RecordingStatus.class);
    static
    {
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

    private static final BiMap<ConnectionType, String> CONN_TYPE_MAP = EnumHashBiMap.create(ConnectionType.class);
    static
    {
        CONN_TYPE_MAP.put(ConnectionType.MONITOR, "Monitor");
        CONN_TYPE_MAP.put(ConnectionType.PLAYBACK, "Playback");
    }

    private static final BiMap<EventLevel, String> EVENT_LEVEL_MAP = EnumHashBiMap.create(EventLevel.class);
    static
    {
        EVENT_LEVEL_MAP.put(EventLevel.NONE, "0");
        EVENT_LEVEL_MAP.put(EventLevel.ALL, "1");
        EVENT_LEVEL_MAP.put(EventLevel.NO_SYSTEM, "2");
        EVENT_LEVEL_MAP.put(EventLevel.ONLY_SYSTEM, "3");
    }

    private static final BiMap<RecordingCategory, String> REC_CATEGORY_MAP = EnumHashBiMap.create(RecordingCategory.class);
    static
    {
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDING, "Recording");
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDED_UNSORTED, "Play");
    }

    private static final BiMap<RecordingType, String> REC_TYPE_MAP = EnumHashBiMap.create(RecordingType.class);
    static
    {
        REC_TYPE_MAP.put(RecordingType.NOT_RECORDING, "0");
        REC_TYPE_MAP.put(RecordingType.SINGLE_RECORD, "1");
        REC_TYPE_MAP.put(RecordingType.TIMESLOT_RECORD, "2");
        REC_TYPE_MAP.put(RecordingType.CHANNEL_RECORD, "3");
        REC_TYPE_MAP.put(RecordingType.ALL_RECORD, "4");
        REC_TYPE_MAP.put(RecordingType.WEEKSLOT_RECORD, "5");
        REC_TYPE_MAP.put(RecordingType.FIND_ONE_RECORD, "6");
        REC_TYPE_MAP.put(RecordingType.OVERRIDE_RECORD, "7");
        REC_TYPE_MAP.put(RecordingType.DONT_RECORD, "8");
        REC_TYPE_MAP.put(RecordingType.FIND_DAILY_RECORD, "9");
        REC_TYPE_MAP.put(RecordingType.FIND_WEEKLY_RECORD, "10");
    }

    private static final BiMap<TunerStatusCategory, String> TUNER_STATUS_CATEGORY_MAP = EnumHashBiMap.create(TunerStatusCategory.class);
    static
    {
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.CHANNEL_TUNED, "Channel Tuned");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_LOCK, "Signal Lock");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_POWER, "Signal Power");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PAT, "Seen PAT");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PAT, "Matching PAT");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PMT, "Seen PMT");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PMT, "Matching PMT");
    }

    private static final BiMap<FileTransferType, String> FILE_TRANSFER_TYPE_MAP = EnumHashBiMap.create(FileTransferType.class);
    static
    {
        FILE_TRANSFER_TYPE_MAP.put(FileTransferType.READ, "0");
        FILE_TRANSFER_TYPE_MAP.put(FileTransferType.WRITE, "1");
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
    }

    private static final BiMap<TvState, String> TV_STATE_MAP = EnumHashBiMap.create(TvState.class);
    static
    {
        TV_STATE_MAP.put(TvState.ERROR, "-1");
        TV_STATE_MAP.put(TvState.NONE, "0");
        TV_STATE_MAP.put(TvState.WATCHING_LIVETV, "1");
        TV_STATE_MAP.put(TvState.WATCHING_PRERECORDED, "2");
        TV_STATE_MAP.put(TvState.WATCHING_VIDEO, "3");
        TV_STATE_MAP.put(TvState.WATCHING_DVD, "4");
        TV_STATE_MAP.put(TvState.WATCHING_BD, "5");
        TV_STATE_MAP.put(TvState.WATCHING_RECORDING, "6");
        TV_STATE_MAP.put(TvState.RECORDING_ONLY, "7");
        TV_STATE_MAP.put(TvState.CHANGING_STATE, "8");
    }

    private static final BiMap<SeekOrigin, String> SEEK_ORIGIN_MAP = EnumHashBiMap.create(SeekOrigin.class);
    static
    {
        SEEK_ORIGIN_MAP.put(SeekOrigin.BEGINNING, "0");
        SEEK_ORIGIN_MAP.put(SeekOrigin.CURRENT, "1");
        SEEK_ORIGIN_MAP.put(SeekOrigin.END, "2");
    }

    private static final BiMap<SleepStatus, String> SLEEP_STATUS_MAP = EnumHashBiMap.create(SleepStatus.class);
    static
    {
        SLEEP_STATUS_MAP.put(SleepStatus.AWAKE, "0");
        SLEEP_STATUS_MAP.put(SleepStatus.ASLEEP, "1");
        SLEEP_STATUS_MAP.put(SleepStatus.FALLING_ASLEEP, "3");
        SLEEP_STATUS_MAP.put(SleepStatus.WAKING, "5");
        SLEEP_STATUS_MAP.put(SleepStatus.UNDEFINED, "8");
    }

    private static final BiMap<RecorderFlag, String> REC_FLAG_MAP = EnumHashBiMap.create(RecorderFlag.class);
    static
    {
        /*
         * In MythTV, these values are unsigned integers.
         */
        REC_FLAG_MAP.put(RecorderFlag.FRONTEND_READY, String.valueOf(0x00000001L));
        REC_FLAG_MAP.put(RecorderFlag.RUN_MAIN_LOOP, String.valueOf(0x00000002L));
        REC_FLAG_MAP.put(RecorderFlag.EXIT_PLAYER, String.valueOf(0x00000004L));
        REC_FLAG_MAP.put(RecorderFlag.FINISH_RECORDING, String.valueOf(0x00000008L));
        REC_FLAG_MAP.put(RecorderFlag.ERRORED, String.valueOf(0x00000010L));
        REC_FLAG_MAP.put(RecorderFlag.CANCEL_NEXT_RECORDING, String.valueOf(0x00000020L));
        REC_FLAG_MAP.put(RecorderFlag.LIVE_TV, String.valueOf(0x00000100L));
        REC_FLAG_MAP.put(RecorderFlag.RECORDING, String.valueOf(0x00000200L));
        REC_FLAG_MAP.put(RecorderFlag.ANTENNA_ADJUST, String.valueOf(0x00000400L));
        REC_FLAG_MAP.put(RecorderFlag.REC, String.valueOf(0x00000F00L));
        REC_FLAG_MAP.put(RecorderFlag.EIT_SCAN, String.valueOf(0x00001000L));
        REC_FLAG_MAP.put(RecorderFlag.CLOSE_REC, String.valueOf(0x00002000L));
        REC_FLAG_MAP.put(RecorderFlag.KILL_REC, String.valueOf(0x00004000L));
        REC_FLAG_MAP.put(RecorderFlag.NO_REC, String.valueOf(0x0000F000L));
        REC_FLAG_MAP.put(RecorderFlag.KILL_RING_BUFFER, String.valueOf(0x00010000L));
        REC_FLAG_MAP.put(RecorderFlag.WAITING_FOR_REC_PAUSE, String.valueOf(0x00100000L));
        REC_FLAG_MAP.put(RecorderFlag.WAITING_FOR_SIGNAL, String.valueOf(0x00200000L));
        REC_FLAG_MAP.put(RecorderFlag.NEED_TO_START_RECORDER, String.valueOf(0x00800000L));
        REC_FLAG_MAP.put(RecorderFlag.PENDING_ACTIONS, String.valueOf(0x00F00000L));
        REC_FLAG_MAP.put(RecorderFlag.SIGNAL_MONITOR_RUNNING, String.valueOf(0x01000000L));
        REC_FLAG_MAP.put(RecorderFlag.EIT_SCANNER_RUNNING, String.valueOf(0x04000000L));
        REC_FLAG_MAP.put(RecorderFlag.DUMMY_RECORDER_RUNNING, String.valueOf(0x10000000L));
        REC_FLAG_MAP.put(RecorderFlag.RECORDER_RUNNING, String.valueOf(0x20000000L));
        REC_FLAG_MAP.put(RecorderFlag.ANY_REC_RUNNING, String.valueOf(0x30000000L));
        REC_FLAG_MAP.put(RecorderFlag.ANY_RUNNING, String.valueOf(0x3F000000L));
        REC_FLAG_MAP.put(RecorderFlag.RING_BUFFER_READY, String.valueOf(0x40000000L));
        REC_FLAG_MAP.put(RecorderFlag.DETECT, String.valueOf(0x80000000L));
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
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type)
    {
        /*
         * Cast to raw BiMap necessary to appease javac (Eclipse doesn't require it).
         */

        if (RecordingStatus.class.equals(type))
        {
            return (BiMap)REC_STATUS_MAP;
        }

        if (ConnectionType.class.equals(type))
        {
            return (BiMap)CONN_TYPE_MAP;
        }

        if (EventLevel.class.equals(type))
        {
            return (BiMap)EVENT_LEVEL_MAP;
        }

        if (RecordingCategory.class.equals(type))
        {
            return (BiMap)REC_CATEGORY_MAP;
        }

        if (RecordingType.class.equals(type))
        {
            return (BiMap)REC_TYPE_MAP;
        }

        if (TunerStatusCategory.class.equals(type))
        {
            return (BiMap)TUNER_STATUS_CATEGORY_MAP;
        }

        if (FileTransferType.class.equals(type))
        {
            return (BiMap)FILE_TRANSFER_TYPE_MAP;
        }

        if (VideoEditMark.class.equals(type))
        {
            return (BiMap)VIDEO_EDIT_MARK_MAP;
        }

        if (TvState.class.equals(type))
        {
            return (BiMap)TV_STATE_MAP;
        }

        if (SeekOrigin.class.equals(type))
        {
            return (BiMap)SEEK_ORIGIN_MAP;
        }

        if (SleepStatus.class.equals(type))
        {
            return (BiMap)SLEEP_STATUS_MAP;
        }

        if (RecorderFlag.class.equals(type))
        {
            return (BiMap)REC_FLAG_MAP;
        }

        if (Verbose.class.equals(type))
        {
            return (BiMap)LOG_OPTION_MAP;
        }

        throw new IllegalArgumentException("Unknown type: " + type);
    }
}
