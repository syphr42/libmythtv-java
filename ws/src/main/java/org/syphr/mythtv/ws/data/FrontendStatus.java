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
package org.syphr.mythtv.ws.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FrontendStatus
{
    private Map<String, String> state;
    private List<String> chapterTimes;
    private Map<String, String> subtitleTracks;
    private Map<String, String> audioTracks;

    public Map<String, String> getState()
    {
        if (state == null)
        {
            state = new HashMap<String, String>();
        }
        return state;
    }

    public List<String> getChapterTimes()
    {
        if (chapterTimes == null)
        {
            chapterTimes = new ArrayList<String>();
        }
        return chapterTimes;
    }

    public Map<String, String> getSubtitleTracks()
    {
        if (subtitleTracks == null)
        {
            subtitleTracks = new HashMap<String, String>();
        }
        return subtitleTracks;
    }

    public Map<String, String> getAudioTracks()
    {
        if (audioTracks == null)
        {
            audioTracks = new HashMap<String, String>();
        }
        return audioTracks;
    }

    @Override
    public String toString()
    {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("FrontendStatus [state=");
        builder.append(state != null ? toString(state.entrySet(), maxLen) : null);
        builder.append(", chapterTimes=");
        builder.append(chapterTimes != null ? toString(chapterTimes, maxLen) : null);
        builder.append(", subtitleTracks=");
        builder.append(subtitleTracks != null ? toString(subtitleTracks.entrySet(), maxLen) : null);
        builder.append(", audioTracks=");
        builder.append(audioTracks != null ? toString(audioTracks.entrySet(), maxLen) : null);
        builder.append("]");
        return builder.toString();
    }

    private String toString(Collection<?> collection, int maxLen)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++)
        {
            if (i > 0)
            {
                builder.append(", ");
            }
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }
}
