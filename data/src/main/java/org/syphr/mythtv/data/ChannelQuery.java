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

public class ChannelQuery
{
    private final String prefix;
    private final boolean valid;

    private final int recorderId;
    private final boolean extraCharacterNeeded;
    private final String spacer;

    public ChannelQuery(String prefix, boolean valid, int recorderId, boolean extraCharacterNeeded, String spacer)
    {
        this.prefix = prefix;
        this.valid = valid;
        this.recorderId = recorderId;
        this.extraCharacterNeeded = extraCharacterNeeded;
        this.spacer = spacer;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public boolean isValid()
    {
        return valid;
    }

    public int getRecorderId()
    {
        return recorderId;
    }

    public boolean hasRecorderId()
    {
        return getRecorderId() > 0;
    }

    public boolean isExtraCharacterNeeded()
    {
        return extraCharacterNeeded;
    }

    public String getSpacer()
    {
        return spacer;
    }

    public String getChannelWithSpacer()
    {
        return prefix.substring(0, prefix.length() - 1) + getSpacer()
               + prefix.charAt(prefix.length() - 1);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelQuery [prefix=");
        builder.append(prefix);
        builder.append(", valid=");
        builder.append(valid);
        builder.append(", recorderId=");
        builder.append(recorderId);
        builder.append(", extraCharacterNeeded=");
        builder.append(extraCharacterNeeded);
        builder.append(", spacer=");
        builder.append(spacer);
        builder.append("]");
        return builder.toString();
    }
}
