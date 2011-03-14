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

import org.syphr.mythtv.proto.types.RecordingCategory;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator65 extends Translator63
{
    private static final BiMap<RecordingCategory, String> REC_CATEGORY_MAP = EnumHashBiMap.create(RecordingCategory.class);
    static
    {
        REC_CATEGORY_MAP.put(RecordingCategory.RECORDING, "Recording");
        REC_CATEGORY_MAP.put(RecordingCategory.ASCENDING, "Ascending");
        REC_CATEGORY_MAP.put(RecordingCategory.DESCENDING, "Descending");
        REC_CATEGORY_MAP.put(RecordingCategory.UNSORTED, "Unsorted");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type)
    {
        /*
         * Cast to raw BiMap necessary to appease javac (Eclipse doesn't require it).
         */

        if (RecordingCategory.class.equals(type))
        {
            return (BiMap)REC_CATEGORY_MAP;
        }

        return super.getMap(type);
    }
}
