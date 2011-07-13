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

import org.syphr.mythtv.types.VideoListChangeType;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator68 extends Translator67
{
    private static final BiMap<VideoListChangeType, String> VIDEO_CHANGE_MAP = EnumHashBiMap.create(VideoListChangeType.class);
    static
    {
        VIDEO_CHANGE_MAP.put(VideoListChangeType.ADDED, "added");
        VIDEO_CHANGE_MAP.put(VideoListChangeType.MOVED, "moved");
        VIDEO_CHANGE_MAP.put(VideoListChangeType.DELETED, "deleted");
    }

    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends Enum>, BiMap<? extends Enum, String>> MAPS = new HashMap<Class<? extends Enum>, BiMap<? extends Enum, String>>();
    static
    {
        MAPS.put(VideoListChangeType.class, VIDEO_CHANGE_MAP);
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
