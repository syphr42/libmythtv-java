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
package org.syphr.mythtv.protocol.impl;

import java.util.HashMap;
import java.util.Map;

import org.syphr.mythtv.types.VideoEditMark;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator64 extends Translator63
{
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

    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends Enum>, BiMap<? extends Enum, String>> MAPS = new HashMap<Class<? extends Enum>, BiMap<? extends Enum, String>>();
    static
    {
        MAPS.put(VideoEditMark.class, VIDEO_EDIT_MARK_MAP);
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
