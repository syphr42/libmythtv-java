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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.syphr.mythtv.db.schema.ProgramId;

@Embeddable
public class ProgramId1264 implements java.io.Serializable, ProgramId
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private int chanid;

    @Column(nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;

    @Column(nullable = false)
    private int manualid;

    @Override
    public int getChanid()
    {
        return this.chanid;
    }

    @Override
    public void setChanid(int chanid)
    {
        this.chanid = chanid;
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
    public int getManualid()
    {
        return this.manualid;
    }

    @Override
    public void setManualid(int manualid)
    {
        this.manualid = manualid;
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
        if (!(other instanceof ProgramId1264))
        {
            return false;
        }
        ProgramId1264 castOther = (ProgramId1264)other;

        return (this.getChanid() == castOther.getChanid())
                && ((this.getStarttime() == castOther.getStarttime()) || (this.getStarttime() != null
                        && castOther.getStarttime() != null && this.getStarttime().equals(castOther.getStarttime())))
                && (this.getManualid() == castOther.getManualid());
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + this.getChanid();
        result = 37 * result + (getStarttime() == null ? 0 : this.getStarttime().hashCode());
        result = 37 * result + this.getManualid();
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ProgramId1264 [chanid=");
        builder.append(chanid);
        builder.append(", starttime=");
        builder.append(starttime);
        builder.append(", manualid=");
        builder.append(manualid);
        builder.append("]");
        return builder.toString();
    }
}
