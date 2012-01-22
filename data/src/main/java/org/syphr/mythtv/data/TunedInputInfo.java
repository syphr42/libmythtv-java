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

public class TunedInputInfo extends InputInfo
{
    private final int chanId;

    public TunedInputInfo(String name,
                          int sourceId,
                          int inputId,
                          int cardId,
                          int mplexId,
                          int chanId)
    {
        this(name, sourceId, inputId, cardId, mplexId, 0, chanId);
    }

    public TunedInputInfo(String name,
                          int sourceId,
                          int inputId,
                          int cardId,
                          int mplexId,
                          int liveTvOrder,
                          int chanId)
    {
        super(name, sourceId, inputId, cardId, mplexId, liveTvOrder);
        this.chanId = chanId;
    }

    public int getChanId()
    {
        return chanId;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TunedInputInfo [chanId=");
        builder.append(chanId);
        builder.append(", getName()=");
        builder.append(getName());
        builder.append(", getSourceId()=");
        builder.append(getSourceId());
        builder.append(", getInputId()=");
        builder.append(getInputId());
        builder.append(", getCardId()=");
        builder.append(getCardId());
        builder.append(", getMplexId()=");
        builder.append(getMplexId());
        builder.append(", getLiveTvOrder()=");
        builder.append(getLiveTvOrder());
        builder.append("]");
        return builder.toString();
    }
}
