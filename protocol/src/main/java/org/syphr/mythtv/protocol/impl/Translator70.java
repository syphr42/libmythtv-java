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

import org.syphr.mythtv.types.VideoProperty;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

public class Translator70 extends Translator69
{
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
