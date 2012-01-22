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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.syphr.mythtv.db.schema.OldRecorded;
import org.syphr.mythtv.db.schema.OldRecordedId;
import org.syphr.mythtv.db.schema.impl._0_24.OldRecordedId1264;

@Entity
@Table(name = "oldrecorded")
public class OldRecorded1281 implements OldRecorded
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "station", column = @Column(name = "station",
                                                                                nullable = false,
                                                                                length = 20)),
                         @AttributeOverride(name = "starttime",
                                            column = @Column(name = "starttime", nullable = false,
                                                             length = 19)),
                         @AttributeOverride(name = "title", column = @Column(name = "title",
                                                                             nullable = false,
                                                                             length = 128)) })
    private OldRecordedId1264 id;

    @Column(name = "chanid", nullable = false)
    private int chanid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endtime", nullable = false, length = 19)
    private Date endtime;

    @Column(name = "subtitle", nullable = false, length = 128)
    private String subtitle;

    @Column(name = "description", nullable = false, length = 16000)
    private String description;

    @Column(name = "season", nullable = false)
    private short season;

    @Column(name = "episode", nullable = false)
    private short episode;

    @Column(name = "category", nullable = false, length = 64)
    private String category;

    @Column(name = "seriesid", nullable = false, length = 40)
    private String seriesid;

    @Column(name = "programid", nullable = false, length = 40)
    private String programid;

    @Column(name = "inetref", nullable = false, length = 40)
    private String inetref;

    @Column(name = "findid", nullable = false)
    private int findid;

    @Column(name = "recordid", nullable = false)
    private int recordid;

    @Column(name = "rectype", nullable = false)
    private int rectype;

    @Column(name = "duplicate", nullable = false)
    private boolean duplicate;

    @Column(name = "recstatus", nullable = false)
    private int recstatus;

    @Column(name = "reactivate", nullable = false)
    private short reactivate;

    @Column(name = "generic", nullable = false)
    private boolean generic;

    @Column(name = "future", nullable = false)
    private boolean future;

    @Override
    public OldRecordedId getId()
    {
        return this.id;
    }

    @Override
    public void setId(OldRecordedId id)
    {
        if (id != null && !(id instanceof OldRecordedId1264))
        {
            throw new IllegalArgumentException("Invalid ID type: " + id.getClass().getName());
        }

        this.id = (OldRecordedId1264)id;
    }

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
    public Date getEndtime()
    {
        return this.endtime;
    }

    @Override
    public void setEndtime(Date endtime)
    {
        this.endtime = endtime;
    }

    @Override
    public String getSubtitle()
    {
        return this.subtitle;
    }

    @Override
    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    @Override
    public String getDescription()
    {
        return this.description;
    }

    @Override
    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public short getSeason()
    {
        return this.season;
    }

    @Override
    public void setSeason(short season)
    {
        this.season = season;
    }

    @Override
    public short getEpisode()
    {
        return this.episode;
    }

    @Override
    public void setEpisode(short episode)
    {
        this.episode = episode;
    }

    @Override
    public String getCategory()
    {
        return this.category;
    }

    @Override
    public void setCategory(String category)
    {
        this.category = category;
    }

    @Override
    public String getSeriesid()
    {
        return this.seriesid;
    }

    @Override
    public void setSeriesid(String seriesid)
    {
        this.seriesid = seriesid;
    }

    @Override
    public String getProgramid()
    {
        return this.programid;
    }

    @Override
    public void setProgramid(String programid)
    {
        this.programid = programid;
    }

    @Override
    public String getInetref()
    {
        return this.inetref;
    }

    @Override
    public void setInetref(String inetref)
    {
        this.inetref = inetref;
    }

    @Override
    public int getFindid()
    {
        return this.findid;
    }

    @Override
    public void setFindid(int findid)
    {
        this.findid = findid;
    }

    @Override
    public int getRecordid()
    {
        return this.recordid;
    }

    @Override
    public void setRecordid(int recordid)
    {
        this.recordid = recordid;
    }

    @Override
    public int getRectype()
    {
        return this.rectype;
    }

    @Override
    public void setRectype(int rectype)
    {
        this.rectype = rectype;
    }

    @Override
    public boolean isDuplicate()
    {
        return this.duplicate;
    }

    @Override
    public void setDuplicate(boolean duplicate)
    {
        this.duplicate = duplicate;
    }

    @Override
    public int getRecstatus()
    {
        return this.recstatus;
    }

    @Override
    public void setRecstatus(int recstatus)
    {
        this.recstatus = recstatus;
    }

    @Override
    public short getReactivate()
    {
        return this.reactivate;
    }

    @Override
    public void setReactivate(short reactivate)
    {
        this.reactivate = reactivate;
    }

    @Override
    public boolean isGeneric()
    {
        return this.generic;
    }

    @Override
    public void setGeneric(boolean generic)
    {
        this.generic = generic;
    }

    @Override
    public boolean isFuture()
    {
        return this.future;
    }

    @Override
    public void setFuture(boolean future)
    {
        this.future = future;
    }

    @Override
    public Boolean getGeneric()
    {
        return isGeneric();
    }

    @Override
    public void setGeneric(Boolean generic)
    {
        if (generic == null)
        {
            return;
        }

        setGeneric(generic.booleanValue());
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        OldRecorded1281 other = (OldRecorded1281)obj;
        if (id == null)
        {
            if (other.id != null)
            {
                return false;
            }
        }
        else if (!id.equals(other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("OldRecorded1281 [id=");
        builder.append(id);
        builder.append(", chanid=");
        builder.append(chanid);
        builder.append(", endtime=");
        builder.append(endtime);
        builder.append(", subtitle=");
        builder.append(subtitle);
        builder.append(", description=");
        builder.append(description);
        builder.append(", season=");
        builder.append(season);
        builder.append(", episode=");
        builder.append(episode);
        builder.append(", category=");
        builder.append(category);
        builder.append(", seriesid=");
        builder.append(seriesid);
        builder.append(", programid=");
        builder.append(programid);
        builder.append(", inetref=");
        builder.append(inetref);
        builder.append(", findid=");
        builder.append(findid);
        builder.append(", recordid=");
        builder.append(recordid);
        builder.append(", rectype=");
        builder.append(rectype);
        builder.append(", duplicate=");
        builder.append(duplicate);
        builder.append(", recstatus=");
        builder.append(recstatus);
        builder.append(", reactivate=");
        builder.append(reactivate);
        builder.append(", generic=");
        builder.append(generic);
        builder.append(", future=");
        builder.append(future);
        builder.append("]");
        return builder.toString();
    }
}
