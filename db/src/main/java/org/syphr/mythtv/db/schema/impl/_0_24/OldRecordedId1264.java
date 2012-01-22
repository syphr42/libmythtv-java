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
package org.syphr.mythtv.db.schema.impl._0_24;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.syphr.mythtv.db.schema.OldRecordedId;

@Embeddable
public class OldRecordedId1264 implements OldRecordedId
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "station", nullable = false, length = 20)
    private String station;

    @Column(name = "starttime", nullable = false, length = 19)
    private Date starttime;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Override
    public String getStation()
    {
        return this.station;
    }

    @Override
    public void setStation(String station)
    {
        this.station = station;
    }

    @Override
    public Date getStarttime()
    {
        return this.starttime;
    }

    @Override
    public void setStarttime(Date starttime)
    {
        this.starttime = starttime;
    }

    @Override
    public String getTitle()
    {
        return this.title;
    }

    @Override
    public void setTitle(String title)
    {
        this.title = title;
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
        if (!(other instanceof OldRecordedId1264))
        {
            return false;
        }
        OldRecordedId castOther = (OldRecordedId)other;

        return ((this.getStation() == castOther.getStation()) || (this.getStation() != null
                && castOther.getStation() != null && this.getStation().equals(castOther.getStation())))
                && ((this.getStarttime() == castOther.getStarttime()) || (this.getStarttime() != null
                        && castOther.getStarttime() != null && this.getStarttime().equals(castOther.getStarttime())))
                && ((this.getTitle() == castOther.getTitle()) || (this.getTitle() != null
                        && castOther.getTitle() != null && this.getTitle().equals(castOther.getTitle())));
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + (getStation() == null ? 0 : this.getStation().hashCode());
        result = 37 * result + (getStarttime() == null ? 0 : this.getStarttime().hashCode());
        result = 37 * result + (getTitle() == null ? 0 : this.getTitle().hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("OldRecordedId1264 [station=");
        builder.append(station);
        builder.append(", starttime=");
        builder.append(starttime);
        builder.append(", title=");
        builder.append(title);
        builder.append("]");
        return builder.toString();
    }
}
