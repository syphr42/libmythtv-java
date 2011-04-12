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

import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.RecordingStatus;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator65 extends Translator63
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

        if (RecordingCategory.class.equals(type))
        {
            return (BiMap)REC_CATEGORY_MAP;
        }

        return super.getMap(type);
    }
}
