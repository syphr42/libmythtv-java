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
package org.syphr.mythtv.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.syphr.mythtv.types.TunerStatusCategory;

public class TunerStatus
{
    private final Map<TunerStatusCategory, TunerData> map;

    public TunerStatus(Pair<TunerStatusCategory, TunerData>... dataArray)
    {
        map = new EnumMap<TunerStatusCategory, TunerData>(TunerStatusCategory.class);

        for (Pair<TunerStatusCategory, TunerData> data : dataArray)
        {
            map.put(data.getLeft(), data.getRight());
        }
    }

    public TunerData getData(TunerStatusCategory category)
    {
        TunerData data = map.get(category);
        if (data == null)
        {
            return new TunerData("");
        }

        return data;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TunerStatus [map=");
        builder.append(map);
        builder.append("]");
        return builder.toString();
    }

    public static class TunerData
    {
        private final String name;
        private final List<Integer> data;

        public TunerData(String name, int... data)
        {
            this.name = name;

            List<Integer> internalList = new ArrayList<Integer>();
            for (int datum : data)
            {
                internalList.add(datum);
            }
            this.data = Collections.unmodifiableList(internalList);
        }

        public String getName()
        {
            return name;
        }

        public List<Integer> getData()
        {
            return data;
        }

        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("TunerData [name=");
            builder.append(name);
            builder.append(", data=");
            builder.append(data);
            builder.append("]");
            return builder.toString();
        }
    }
}
