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

public class InputInfo
{
    private final String name;
    private final int sourceId;
    private final int inputId;
    private final int cardId;
    private final int mplexId;

    public InputInfo(String name, int sourceId, int inputId, int cardId, int mplexId)
    {
        this.name = name;
        this.sourceId = sourceId;
        this.inputId = inputId;
        this.cardId = cardId;
        this.mplexId = mplexId;
    }

    public String getName()
    {
        return name;
    }

    public int getSourceId()
    {
        return sourceId;
    }

    public int getInputId()
    {
        return inputId;
    }

    public int getCardId()
    {
        return cardId;
    }

    public int getMplexId()
    {
        return mplexId;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("InputInfo [name=");
        builder.append(name);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", inputId=");
        builder.append(inputId);
        builder.append(", cardId=");
        builder.append(cardId);
        builder.append(", mplexId=");
        builder.append(mplexId);
        builder.append("]");
        return builder.toString();
    }
}
