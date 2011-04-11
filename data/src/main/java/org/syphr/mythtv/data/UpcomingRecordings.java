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
package org.syphr.mythtv.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UpcomingRecordings implements Iterable<Program>
{
    private final boolean conflicted;
    private final List<Program> programs;

    public UpcomingRecordings(boolean conflicted, List<Program> programs)
    {
        this.conflicted = conflicted;
        this.programs = programs;
    }

    public boolean isConflicted()
    {
        return conflicted;
    }

    public List<Program> getPrograms()
    {
        return new ArrayList<Program>(programs);
    }

    public int size()
    {
        return programs.size();
    }

    public boolean isEmpty()
    {
        return programs.isEmpty();
    }

    public boolean contains(Program program)
    {
        return programs.contains(program);
    }

    public boolean containsAll(Collection<Program> c)
    {
        return programs.containsAll(c);
    }

    public Program get(int index)
    {
        return programs.get(index);
    }

    public int indexOf(Object o)
    {
        return programs.indexOf(o);
    }

    public int lastIndexOf(Object o)
    {
        return programs.lastIndexOf(o);
    }

    @Override
    public Iterator<Program> iterator()
    {
        return getPrograms().iterator();
    }

    public ListIterator<Program> listIterator()
    {
        return getPrograms().listIterator();
    }

    public ListIterator<Program> listIterator(int index)
    {
        return getPrograms().listIterator(index);
    }

    public List<Program> subList(int fromIndex, int toIndex)
    {
        return getPrograms().subList(fromIndex, toIndex);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("UpcomingRecordings [conflicted=");
        builder.append(conflicted);
        builder.append(", programs=");
        builder.append(programs);
        builder.append("]");
        return builder.toString();
    }
}
