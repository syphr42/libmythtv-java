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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;

import com.google.common.collect.BiMap;

public abstract class AbstractTranslator implements Translator
{
    @Override
    public <E extends Enum<E>> List<E> getAllowed(Class<E> type)
    {
        return new ArrayList<E>(getMap(type).keySet());
    }

    @Override
    public <E extends Enum<E>> String toString(E constant) throws ProtocolException
    {
        return translate(constant, getMap(constant.getClass()), Direction.RECEIVE);
    }

    @Override
    public <E extends Enum<E>> String toString(Collection<E> constants) throws ProtocolException
    {
        if (constants.isEmpty())
        {
            return "";
        }

        E firstElement = constants.iterator().next();
        return translateMultiple(constants, getMap(firstElement.getClass()));
    }

    @Override
    public <E extends Enum<E>> E toEnum(String value, Class<E> type) throws ProtocolException
    {
        return translate(value, getMap(type).inverse(), Direction.SEND);
    }

    @Override
    public <E extends Enum<E>> Set<E> toEnums(String value, Class<E> type) throws ProtocolException
    {
        return translateMultiple(value, getMap(type).inverse());
    }

    private <V, T> T translate(V value, Map<V, T> map, Direction direction) throws ProtocolException
    {
        T translated = map.get(value);
        if (translated == null)
        {
            throw new ProtocolException("Invalid value: " + value, direction);
        }

        return translated;
    }

    private <T> String translateMultiple(Collection<T> values, Map<T, String> map) throws ProtocolException
    {
        try
        {
            long result = 0;

            for (T value : values)
            {
                String translated = map.get(value);
                if (translated == null)
                {
                    throw new ProtocolException("Invalid value: " + value, Direction.SEND);
                }

                long longTranslated = Long.parseLong(translated);
                result |= longTranslated;
            }

            return result == 0 ? "" : String.valueOf(result);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException("Invalid values: " + values, Direction.RECEIVE, e);
        }
    }

    private <T> Set<T> translateMultiple(String value, Map<String, T> map) throws ProtocolException
    {
        try
        {
            long longValue = Long.parseLong(value);

            Set<T> set = new HashSet<T>();

            for (Entry<String, T> entry : map.entrySet())
            {
                long longEntry = Long.parseLong(entry.getKey());

                if ((longValue & longEntry) > 0)
                {
                    set.add(entry.getValue());
                }
            }

            return set;
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException("Invalid value: " + value, Direction.RECEIVE, e);
        }
    }

    protected abstract <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type);
}
