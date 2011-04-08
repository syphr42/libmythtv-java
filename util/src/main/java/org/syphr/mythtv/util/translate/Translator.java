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
package org.syphr.mythtv.util.translate;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.syphr.mythtv.util.exception.ProtocolException;

public interface Translator
{
    public <E extends Enum<E>> List<E> getAllowed(Class<E> type);

    public <E extends Enum<E>> String toString(E constant) throws ProtocolException;

    public <E extends Enum<E>> String toString(Collection<E> constants) throws ProtocolException;

    public <E extends Enum<E>> String toString(Collection<E> constants, String delimiter) throws ProtocolException;

    public String toString(boolean value);

    public String toIntString(boolean value);

    public <E extends Enum<E>> E toEnum(String value, Class<E> type) throws ProtocolException;

    public <E extends Enum<E>> Set<E> toEnums(String value, Class<E> type) throws ProtocolException;

    public boolean toBooleanFromInt(String value) throws ProtocolException;

    public boolean toBooleanFromStr(String value) throws ProtocolException;
}
