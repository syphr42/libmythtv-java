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

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;
import org.syphr.mythtv.proto.types.RecordingCategory;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Protocol65Utils
{
    private static final BiMap<RecordingCategory, String> REC_CATEGORY_MAP = EnumHashBiMap.create(RecordingCategory.class);
    static
    {
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDING, "Recording");
        REC_CATEGORY_MAP.put(RecordingCategory.ASCENDING, "Ascending");
        REC_CATEGORY_MAP.put(RecordingCategory.DESCENDING, "Descending");
        REC_CATEGORY_MAP.put(RecordingCategory.UNSORTED, "Unsorted");
    }

    public static RecordingCategory getRecordingCategory(String recCategory) throws ProtocolException
    {
        return ProtocolUtils.translate(recCategory, REC_CATEGORY_MAP.inverse(), Direction.RECEIVE);
    }

    public static String getRecordingCategory(RecordingCategory recCategory) throws ProtocolException
    {
        return ProtocolUtils.translate(recCategory, REC_CATEGORY_MAP, Direction.SEND);
    }
}
