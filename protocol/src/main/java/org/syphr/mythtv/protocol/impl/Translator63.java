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

import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.types.AudioProperty;
import org.syphr.mythtv.types.ChannelBrowseDirection;
import org.syphr.mythtv.types.ChannelChangeDirection;
import org.syphr.mythtv.types.FileTransferType;
import org.syphr.mythtv.types.PictureAdjustType;
import org.syphr.mythtv.types.ProgramFlag;
import org.syphr.mythtv.types.RecorderFlag;
import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.RecordingDupIn;
import org.syphr.mythtv.types.RecordingDupMethod;
import org.syphr.mythtv.types.RecordingSearchType;
import org.syphr.mythtv.types.RecordingStatus;
import org.syphr.mythtv.types.RecordingType;
import org.syphr.mythtv.types.SeekOrigin;
import org.syphr.mythtv.types.SleepStatus;
import org.syphr.mythtv.types.SubtitleType;
import org.syphr.mythtv.types.TunerStatusCategory;
import org.syphr.mythtv.types.TvState;
import org.syphr.mythtv.types.Verbose;
import org.syphr.mythtv.types.VideoEditMark;
import org.syphr.mythtv.types.VideoProperty;
import org.syphr.mythtv.util.translate.AbstractTranslator;

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
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.CHANNEL_TUNED, "tuned");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_LOCK, "slock");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SIGNAL_POWER, "signal");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PAT, "seen_pat");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PAT, "matching_pat");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.SEEN_PMT, "seen_pmt");
        TUNER_STATUS_CATEGORY_MAP.put(TunerStatusCategory.MATCHING_PMT, "matching_pmt");
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

    private static final BiMap<ChannelChangeDirection, String> CHANNEL_CHANGE_MAP = EnumHashBiMap.create(ChannelChangeDirection.class);
    static
    {
        CHANNEL_CHANGE_MAP.put(ChannelChangeDirection.UP, "0");
        CHANNEL_CHANGE_MAP.put(ChannelChangeDirection.DOWN, "1");
        CHANNEL_CHANGE_MAP.put(ChannelChangeDirection.FAVORITE, "2");
        CHANNEL_CHANGE_MAP.put(ChannelChangeDirection.SAME, "3");
    }

    private static final BiMap<ChannelBrowseDirection, String> CHANNEL_BROWSE_MAP = EnumHashBiMap.create(ChannelBrowseDirection.class);
    static
    {
        CHANNEL_BROWSE_MAP.put(ChannelBrowseDirection.INVALID, "-1");
        CHANNEL_BROWSE_MAP.put(ChannelBrowseDirection.SAME, "0");
        CHANNEL_BROWSE_MAP.put(ChannelBrowseDirection.UP, "1");
        CHANNEL_BROWSE_MAP.put(ChannelBrowseDirection.DOWN, "2");
        CHANNEL_BROWSE_MAP.put(ChannelBrowseDirection.LEFT, "3");
        CHANNEL_BROWSE_MAP.put(ChannelBrowseDirection.RIGHT, "4");
        CHANNEL_BROWSE_MAP.put(ChannelBrowseDirection.FAVORITE, "5");
    }

    private static final BiMap<PictureAdjustType, String> PICTURE_ADJUST_MAP = EnumHashBiMap.create(PictureAdjustType.class);
    static
    {
        PICTURE_ADJUST_MAP.put(PictureAdjustType.NONE, "0");
        PICTURE_ADJUST_MAP.put(PictureAdjustType.PLAYBACK, "1");
        PICTURE_ADJUST_MAP.put(PictureAdjustType.CHANNEL, "2");
        PICTURE_ADJUST_MAP.put(PictureAdjustType.RECORDING, "3");
    }

    private static final BiMap<RecordingDupIn, String> REC_DUP_IN_MAP = EnumHashBiMap.create(RecordingDupIn.class);
    static
    {
        REC_DUP_IN_MAP.put(RecordingDupIn.NOT_AVAILABLE, "0");
        REC_DUP_IN_MAP.put(RecordingDupIn.RECORDED, "1");
        REC_DUP_IN_MAP.put(RecordingDupIn.OLD_RECORDED, "2");
        REC_DUP_IN_MAP.put(RecordingDupIn.ALL, "15");
        REC_DUP_IN_MAP.put(RecordingDupIn.NEW_EPISODE, "16");
        REC_DUP_IN_MAP.put(RecordingDupIn.EXCLUDE_REPEATS, "32");
        REC_DUP_IN_MAP.put(RecordingDupIn.EXCLUDE_GENERIC, "64");
        REC_DUP_IN_MAP.put(RecordingDupIn.FIRST_NEW, "128");
    }

    private static final BiMap<RecordingDupMethod, String> REC_DUP_METHOD_MAP = EnumHashBiMap.create(RecordingDupMethod.class);
    static
    {
        REC_DUP_METHOD_MAP.put(RecordingDupMethod.NOT_AVAILABLE, "0");
        REC_DUP_METHOD_MAP.put(RecordingDupMethod.NONE, "1");
        REC_DUP_METHOD_MAP.put(RecordingDupMethod.SUBTITLE, "2");
        REC_DUP_METHOD_MAP.put(RecordingDupMethod.DESCRIPTION, "4");
        REC_DUP_METHOD_MAP.put(RecordingDupMethod.SUBTITLE_AND_DESCRIPTION, "6");
        REC_DUP_METHOD_MAP.put(RecordingDupMethod.SUBTITLE_THEN_DESCRIPTION, "8");
    }

    private static final BiMap<RecordingSearchType, String> REC_SEARCH_TYPE_MAP = EnumHashBiMap.create(RecordingSearchType.class);
    static
    {
        REC_SEARCH_TYPE_MAP.put(RecordingSearchType.NONE, "1");
        REC_SEARCH_TYPE_MAP.put(RecordingSearchType.POWER, "2");
        REC_SEARCH_TYPE_MAP.put(RecordingSearchType.TITLE, "3");
        REC_SEARCH_TYPE_MAP.put(RecordingSearchType.KEYWORD, "4");
        REC_SEARCH_TYPE_MAP.put(RecordingSearchType.PEOPLE, "5");
        REC_SEARCH_TYPE_MAP.put(RecordingSearchType.MANUAL, "6");
    }

    private static final BiMap<ProgramFlag, String> PROGRAM_FLAG_MAP = EnumHashBiMap.create(ProgramFlag.class);
    static
    {
        PROGRAM_FLAG_MAP.put(ProgramFlag.NONE, String.valueOf(0x00000000L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.COMMERCIAL_FLAG, String.valueOf(0x00000001L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.CUTLIST, String.valueOf(0x00000002L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.AUTO_EXPIRE, String.valueOf(0x00000004L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.EDITING, String.valueOf(0x00000008L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.BOOKMARK, String.valueOf(0x00000010L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.REALLY_EDITING, String.valueOf(0x00000020L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.COMMERCIAL_PROCESSING, String.valueOf(0x00000040L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.DELETE_PENDING, String.valueOf(0x00000080L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.TRANSCODED, String.valueOf(0x00000100L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.WATCHED, String.valueOf(0x00000200L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.PRESERVED, String.valueOf(0x00000400L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.CHANNEL_COMMERCIAL_FREE, String.valueOf(0x00000800L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.REPEAT, String.valueOf(0x00001000L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.DUPLICATE, String.valueOf(0x00002000L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.REACTIVATE, String.valueOf(0x00004000L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.IGNORE_BOOKMARK, String.valueOf(0x00008000L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.IN_USE_RECORDING, String.valueOf(0x00100000L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.IN_USE_PLAYING, String.valueOf(0x00200000L));
        PROGRAM_FLAG_MAP.put(ProgramFlag.IN_USE_OTHER, String.valueOf(0x00400000L));
    }

    private static final BiMap<AudioProperty, String> AUDIO_PROPERTY_MAP = EnumHashBiMap.create(AudioProperty.class);
    static
    {
        AUDIO_PROPERTY_MAP.put(AudioProperty.UNKNOWN, String.valueOf(0x00L));
        AUDIO_PROPERTY_MAP.put(AudioProperty.STEREO, String.valueOf(0x01L));
        AUDIO_PROPERTY_MAP.put(AudioProperty.MONO, String.valueOf(0x02L));
        AUDIO_PROPERTY_MAP.put(AudioProperty.SURROUND, String.valueOf(0x04L));
        AUDIO_PROPERTY_MAP.put(AudioProperty.DOLBY, String.valueOf(0x08L));
        AUDIO_PROPERTY_MAP.put(AudioProperty.HARD_HEAR, String.valueOf(0x10L));
        AUDIO_PROPERTY_MAP.put(AudioProperty.VISUALLY_IMPAIRED, String.valueOf(0x20L));
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
    }

    private static final BiMap<SubtitleType, String> SUBTITLE_TYPE_MAP = EnumHashBiMap.create(SubtitleType.class);
    static
    {
        SUBTITLE_TYPE_MAP.put(SubtitleType.UNKNOWN, String.valueOf(0x00L));
        SUBTITLE_TYPE_MAP.put(SubtitleType.HARD_HEAR, String.valueOf(0x01L));
        SUBTITLE_TYPE_MAP.put(SubtitleType.NORMAL, String.valueOf(0x02L));
        SUBTITLE_TYPE_MAP.put(SubtitleType.ON_SCREEN, String.valueOf(0x04L));
        SUBTITLE_TYPE_MAP.put(SubtitleType.SIGNED, String.valueOf(0x08L));
    }

    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends Enum>, BiMap<? extends Enum, String>> MAPS = new HashMap<Class<? extends Enum>, BiMap<? extends Enum, String>>();
    static
    {
        MAPS.put(RecordingStatus.class, REC_STATUS_MAP);
        MAPS.put(ConnectionType.class, CONN_TYPE_MAP);
        MAPS.put(EventLevel.class, EVENT_LEVEL_MAP);
        MAPS.put(RecordingCategory.class, REC_CATEGORY_MAP);
        MAPS.put(RecordingType.class, REC_TYPE_MAP);
        MAPS.put(TunerStatusCategory.class, TUNER_STATUS_CATEGORY_MAP);
        MAPS.put(FileTransferType.class, FILE_TRANSFER_TYPE_MAP);
        MAPS.put(VideoEditMark.class, VIDEO_EDIT_MARK_MAP);
        MAPS.put(TvState.class, TV_STATE_MAP);
        MAPS.put(SeekOrigin.class, SEEK_ORIGIN_MAP);
        MAPS.put(SleepStatus.class, SLEEP_STATUS_MAP);
        MAPS.put(RecorderFlag.class, REC_FLAG_MAP);
        MAPS.put(Verbose.class, LOG_OPTION_MAP);
        MAPS.put(ChannelChangeDirection.class, CHANNEL_CHANGE_MAP);
        MAPS.put(ChannelBrowseDirection.class, CHANNEL_BROWSE_MAP);
        MAPS.put(PictureAdjustType.class, PICTURE_ADJUST_MAP);
        MAPS.put(RecordingDupIn.class, REC_DUP_IN_MAP);
        MAPS.put(RecordingDupMethod.class, REC_DUP_METHOD_MAP);
        MAPS.put(RecordingSearchType.class, REC_SEARCH_TYPE_MAP);
        MAPS.put(ProgramFlag.class, PROGRAM_FLAG_MAP);
        MAPS.put(AudioProperty.class, AUDIO_PROPERTY_MAP);
        MAPS.put(VideoProperty.class, VIDEO_PROPERTY_MAP);
        MAPS.put(SubtitleType.class, SUBTITLE_TYPE_MAP);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type)
    {
        if (!MAPS.containsKey(type))
        {
            throw new IllegalArgumentException("Unknown type: " + type);
        }

        return (BiMap)MAPS.get(type);
    }
}
