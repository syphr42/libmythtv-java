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
package org.syphr.mythtv.db.schema.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.syphr.mythtv.db.schema.SettingsId;

@Embeddable
public class SettingsId1264 implements SettingsId
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 128)
    private String value;

    // TODO figure out how to deal with null here
    @Column(length = 64)
    private String hostname;

    public SettingsId1264()
    {
        super();
    }

    public SettingsId1264(String value)
    {
        this.value = value;
    }

    public SettingsId1264(String value, String hostname)
    {
        this.value = value;
        this.hostname = hostname;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }

    @Override
    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public String getHostname()
    {
        return this.hostname;
    }

    @Override
    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    @Override
    public boolean equals(Object other)
    {
        if ((this == other))
        {
            return true;
        }
        if ((other == null))
        {
            return false;
        }
        if (!(other instanceof SettingsId1264))
        {
            return false;
        }
        SettingsId1264 castOther = (SettingsId1264) other;

        return ((this.getValue() == castOther.getValue()) || (this.getValue() != null
                                                              && castOther.getValue() != null && this.getValue()
                                                                                                     .equals(castOther.getValue())))
               && ((this.getHostname() == castOther.getHostname()) || (this.getHostname() != null
                                                                       && castOther.getHostname() != null && this.getHostname()
                                                                                                                 .equals(castOther.getHostname())));
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 37
                 * result
                 + (getValue() == null ? 0 : this.getValue().hashCode());
        result = 37
                 * result
                 + (getHostname() == null ? 0 : this.getHostname().hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SettingsId1264 [value=");
        builder.append(value);
        builder.append(", hostname=");
        builder.append(hostname);
        builder.append("]");
        return builder.toString();
    }
}
