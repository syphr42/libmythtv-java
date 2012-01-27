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
package org.syphr.mythtv.db.schema.impl._0_25;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.syphr.mythtv.db.schema.RecordMatchId;

@Embeddable
public class RecordMatchId1272 implements RecordMatchId
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "recordid", nullable = false)
    private int recordid;

    @Column(name = "chanid", nullable = false)
    private int chanid;

    @Column(name = "starttime", nullable = false, length = 19)
    private Date starttime;

    @Column(name = "manualid", nullable = false)
    private int manualid;

    @Column(name = "oldrecduplicate")
    private Boolean oldrecduplicate;

    @Column(name = "recduplicate")
    private Boolean recduplicate;

    @Column(name = "findduplicate")
    private Boolean findduplicate;

    @Column(name = "oldrecstatus")
    private Integer oldrecstatus;

    @Override
    public Integer getRecordid()
    {
        return this.recordid;
    }

    @Override
    public void setRecordid(int recordid)
    {
        this.recordid = recordid;
    }

    @Override
    public Integer getChanid()
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
    public Integer getManualid()
    {
        return this.manualid;
    }

    @Override
    public void setManualid(int manualid)
    {
        this.manualid = manualid;
    }

    @Override
    public Boolean getOldrecduplicate()
    {
        return this.oldrecduplicate;
    }

    @Override
    public void setOldrecduplicate(Boolean oldrecduplicate)
    {
        this.oldrecduplicate = oldrecduplicate;
    }

    @Override
    public Boolean getRecduplicate()
    {
        return this.recduplicate;
    }

    @Override
    public void setRecduplicate(Boolean recduplicate)
    {
        this.recduplicate = recduplicate;
    }

    @Override
    public Boolean getFindduplicate()
    {
        return this.findduplicate;
    }

    @Override
    public void setFindduplicate(Boolean findduplicate)
    {
        this.findduplicate = findduplicate;
    }

    @Override
    public Integer getOldrecstatus()
    {
        return this.oldrecstatus;
    }

    @Override
    public void setOldrecstatus(Integer oldrecstatus)
    {
        this.oldrecstatus = oldrecstatus;
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
        if (!(other instanceof RecordMatchId1272))
        {
            return false;
        }
        RecordMatchId1272 castOther = (RecordMatchId1272)other;

        return (this.getRecordid() == castOther.getRecordid())
                && (this.getChanid() == castOther.getChanid())
                && ((this.getStarttime() == castOther.getStarttime()) || (this.getStarttime() != null
                        && castOther.getStarttime() != null && this.getStarttime().equals(castOther.getStarttime())))
                && (this.getManualid() == castOther.getManualid())
                && ((this.getOldrecduplicate() == castOther.getOldrecduplicate()) || (this.getOldrecduplicate() != null
                        && castOther.getOldrecduplicate() != null && this.getOldrecduplicate().equals(castOther.getOldrecduplicate())))
                && ((this.getRecduplicate() == castOther.getRecduplicate()) || (this.getRecduplicate() != null
                        && castOther.getRecduplicate() != null && this.getRecduplicate().equals(castOther.getRecduplicate())))
                && ((this.getFindduplicate() == castOther.getFindduplicate()) || (this.getFindduplicate() != null
                        && castOther.getFindduplicate() != null && this.getFindduplicate().equals(castOther.getFindduplicate())))
                && ((this.getOldrecstatus() == castOther.getOldrecstatus()) || (this.getOldrecstatus() != null
                        && castOther.getOldrecstatus() != null && this.getOldrecstatus().equals(castOther.getOldrecstatus())));
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + this.getRecordid();
        result = 37 * result + this.getChanid();
        result = 37 * result + (getStarttime() == null ? 0 : this.getStarttime().hashCode());
        result = 37 * result + this.getManualid();
        result = 37
                * result
                + (getOldrecduplicate() == null ? 0 : this.getOldrecduplicate().hashCode());
        result = 37 * result + (getRecduplicate() == null ? 0 : this.getRecduplicate().hashCode());
        result = 37
                * result
                + (getFindduplicate() == null ? 0 : this.getFindduplicate().hashCode());
        result = 37 * result + (getOldrecstatus() == null ? 0 : this.getOldrecstatus().hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RecordMatchId1272 [recordid=");
        builder.append(recordid);
        builder.append(", chanid=");
        builder.append(chanid);
        builder.append(", starttime=");
        builder.append(starttime);
        builder.append(", manualid=");
        builder.append(manualid);
        builder.append(", oldrecduplicate=");
        builder.append(oldrecduplicate);
        builder.append(", recduplicate=");
        builder.append(recduplicate);
        builder.append(", findduplicate=");
        builder.append(findduplicate);
        builder.append(", oldrecstatus=");
        builder.append(oldrecstatus);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public void setRecordid(Integer recordid)
    {
        if (recordid == null)
        {
            return;
        }

        setRecordid(recordid.intValue());
    }

    @Override
    public void setChanid(Integer chanid)
    {
        if (chanid == null)
        {
            return;
        }

        setChanid(chanid.intValue());
    }

    @Override
    public void setManualid(Integer manualid)
    {
        if (manualid == null)
        {
            return;
        }

        setManualid(manualid.intValue());
    }
}
