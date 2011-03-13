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
import java.util.List;
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
        return ProtocolUtils.translate(constant, getMap(constant.getClass()), Direction.RECEIVE);
    }

    @Override
    public <E extends Enum<E>> String toString(Collection<E> constants) throws ProtocolException
    {
        if (constants.isEmpty())
        {
            return "";
        }

        E firstElement = constants.iterator().next();
        return ProtocolUtils.translateMultiple(constants, getMap(firstElement.getClass()));
    }

    @Override
    public <E extends Enum<E>> E toEnum(String value, Class<E> type) throws ProtocolException
    {
        return ProtocolUtils.translate(value, getMap(type).inverse(), Direction.SEND);
    }

    @Override
    public <E extends Enum<E>> Set<E> toEnums(String value, Class<E> type) throws ProtocolException
    {
        return ProtocolUtils.translateMultiple(value, getMap(type).inverse());
    }

    protected abstract <E extends Enum<E>> BiMap<E, String> getMap(Class<E> type);
}
