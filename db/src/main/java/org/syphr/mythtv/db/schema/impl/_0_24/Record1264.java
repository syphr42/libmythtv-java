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

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.syphr.mythtv.db.schema.Record;

@Entity
@Table(name = "record")
public class Record1264 implements Record
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "recordid", unique = true, nullable = false)
    private Integer recordid;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "chanid")
    private Integer chanid;

    @Temporal(TemporalType.TIME)
    @Column(name = "starttime", nullable = false, length = 8)
    private Date starttime;

    @Temporal(TemporalType.DATE)
    @Column(name = "startdate", nullable = false, length = 10)
    private Date startdate;

    @Temporal(TemporalType.TIME)
    @Column(name = "endtime", nullable = false, length = 8)
    private Date endtime;

    @Temporal(TemporalType.DATE)
    @Column(name = "enddate", nullable = false, length = 10)
    private Date enddate;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "subtitle", nullable = false, length = 128)
    private String subtitle;

    @Column(name = "description", nullable = false, length = 16000)
    private String description;

    @Column(name = "category", nullable = false, length = 64)
    private String category;

    @Column(name = "profile", nullable = false, length = 128)
    private String profile;

    @Column(name = "recpriority", nullable = false)
    private int recpriority;

    @Column(name = "autoexpire", nullable = false)
    private int autoexpire;

    @Column(name = "maxepisodes", nullable = false)
    private int maxepisodes;

    @Column(name = "maxnewest", nullable = false)
    private int maxnewest;

    @Column(name = "startoffset", nullable = false)
    private int startoffset;

    @Column(name = "endoffset", nullable = false)
    private int endoffset;

    @Column(name = "recgroup", nullable = false, length = 32)
    private String recgroup;

    @Column(name = "dupmethod", nullable = false)
    private int dupmethod;

    @Column(name = "dupin", nullable = false)
    private int dupin;

    @Column(name = "station", nullable = false, length = 20)
    private String station;

    @Column(name = "seriesid", nullable = false, length = 40)
    private String seriesid;

    @Column(name = "programid", nullable = false, length = 40)
    private String programid;

    @Column(name = "search", nullable = false)
    private int search;

    @Column(name = "autotranscode", nullable = false)
    private boolean autotranscode;

    @Column(name = "autocommflag", nullable = false)
    private boolean autocommflag;

    @Column(name = "autouserjob1", nullable = false)
    private boolean autouserjob1;

    @Column(name = "autouserjob2", nullable = false)
    private boolean autouserjob2;

    @Column(name = "autouserjob3", nullable = false)
    private boolean autouserjob3;

    @Column(name = "autouserjob4", nullable = false)
    private boolean autouserjob4;

    @Column(name = "findday", nullable = false)
    private byte findday;

    @Temporal(TemporalType.TIME)
    @Column(name = "findtime", nullable = false, length = 8)
    private Date findtime;

    @Column(name = "findid", nullable = false)
    private int findid;

    @Column(name = "inactive", nullable = false)
    private boolean inactive;

    @Column(name = "parentid", nullable = false)
    private int parentid;

    @Column(name = "transcoder", nullable = false)
    private int transcoder;

    @Column(name = "playgroup", nullable = false, length = 32)
    private String playgroup;

    @Column(name = "prefinput", nullable = false)
    private int prefinput;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_record", nullable = false, length = 19)
    private Date nextRecord;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_record", nullable = false, length = 19)
    private Date lastRecord;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_delete", nullable = false, length = 19)
    private Date lastDelete;

    @Column(name = "storagegroup", nullable = false, length = 32)
    private String storagegroup;

    @Column(name = "avg_delay", nullable = false)
    private int avgDelay;

    @Override
    public Integer getRecordid()
    {
        return this.recordid;
    }

    @Override
    public void setRecordid(Integer recordid)
    {
        this.recordid = recordid;
    }

    @Override
    public int getType()
    {
        return this.type;
    }

    @Override
    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public Integer getChanid()
    {
        return this.chanid;
    }

    @Override
    public void setChanid(Integer chanid)
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
    public Date getStartdate()
    {
        return this.startdate;
    }

    @Override
    public void setStartdate(Date startdate)
    {
        this.startdate = startdate;
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
    public Date getEnddate()
    {
        return this.enddate;
    }

    @Override
    public void setEnddate(Date enddate)
    {
        this.enddate = enddate;
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
    public String getProfile()
    {
        return this.profile;
    }

    @Override
    public void setProfile(String profile)
    {
        this.profile = profile;
    }

    @Override
    public int getRecpriority()
    {
        return this.recpriority;
    }

    @Override
    public void setRecpriority(int recpriority)
    {
        this.recpriority = recpriority;
    }

    @Override
    public int getAutoexpire()
    {
        return this.autoexpire;
    }

    @Override
    public void setAutoexpire(int autoexpire)
    {
        this.autoexpire = autoexpire;
    }

    @Override
    public int getMaxepisodes()
    {
        return this.maxepisodes;
    }

    @Override
    public void setMaxepisodes(int maxepisodes)
    {
        this.maxepisodes = maxepisodes;
    }

    @Override
    public int getMaxnewest()
    {
        return this.maxnewest;
    }

    @Override
    public void setMaxnewest(int maxnewest)
    {
        this.maxnewest = maxnewest;
    }

    @Override
    public int getStartoffset()
    {
        return this.startoffset;
    }

    @Override
    public void setStartoffset(int startoffset)
    {
        this.startoffset = startoffset;
    }

    @Override
    public int getEndoffset()
    {
        return this.endoffset;
    }

    @Override
    public void setEndoffset(int endoffset)
    {
        this.endoffset = endoffset;
    }

    @Override
    public String getRecgroup()
    {
        return this.recgroup;
    }

    @Override
    public void setRecgroup(String recgroup)
    {
        this.recgroup = recgroup;
    }

    @Override
    public int getDupmethod()
    {
        return this.dupmethod;
    }

    @Override
    public void setDupmethod(int dupmethod)
    {
        this.dupmethod = dupmethod;
    }

    @Override
    public int getDupin()
    {
        return this.dupin;
    }

    @Override
    public void setDupin(int dupin)
    {
        this.dupin = dupin;
    }

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
    public int getSearch()
    {
        return this.search;
    }

    @Override
    public void setSearch(int search)
    {
        this.search = search;
    }

    @Override
    public boolean isAutotranscode()
    {
        return this.autotranscode;
    }

    @Override
    public void setAutotranscode(boolean autotranscode)
    {
        this.autotranscode = autotranscode;
    }

    @Override
    public boolean isAutocommflag()
    {
        return this.autocommflag;
    }

    @Override
    public void setAutocommflag(boolean autocommflag)
    {
        this.autocommflag = autocommflag;
    }

    @Override
    public boolean isAutouserjob1()
    {
        return this.autouserjob1;
    }

    @Override
    public void setAutouserjob1(boolean autouserjob1)
    {
        this.autouserjob1 = autouserjob1;
    }

    @Override
    public boolean isAutouserjob2()
    {
        return this.autouserjob2;
    }

    @Override
    public void setAutouserjob2(boolean autouserjob2)
    {
        this.autouserjob2 = autouserjob2;
    }

    @Override
    public boolean isAutouserjob3()
    {
        return this.autouserjob3;
    }

    @Override
    public void setAutouserjob3(boolean autouserjob3)
    {
        this.autouserjob3 = autouserjob3;
    }

    @Override
    public boolean isAutouserjob4()
    {
        return this.autouserjob4;
    }

    @Override
    public void setAutouserjob4(boolean autouserjob4)
    {
        this.autouserjob4 = autouserjob4;
    }

    @Override
    public byte getFindday()
    {
        return this.findday;
    }

    @Override
    public void setFindday(byte findday)
    {
        this.findday = findday;
    }

    @Override
    public Date getFindtime()
    {
        return this.findtime;
    }

    @Override
    public void setFindtime(Date findtime)
    {
        this.findtime = findtime;
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
    public boolean isInactive()
    {
        return this.inactive;
    }

    @Override
    public void setInactive(boolean inactive)
    {
        this.inactive = inactive;
    }

    @Override
    public int getParentid()
    {
        return this.parentid;
    }

    @Override
    public void setParentid(int parentid)
    {
        this.parentid = parentid;
    }

    @Override
    public int getTranscoder()
    {
        return this.transcoder;
    }

    @Override
    public void setTranscoder(int transcoder)
    {
        this.transcoder = transcoder;
    }

    @Override
    public String getPlaygroup()
    {
        return this.playgroup;
    }

    @Override
    public void setPlaygroup(String playgroup)
    {
        this.playgroup = playgroup;
    }

    @Override
    public int getPrefinput()
    {
        return this.prefinput;
    }

    @Override
    public void setPrefinput(int prefinput)
    {
        this.prefinput = prefinput;
    }

    @Override
    public Date getNextRecord()
    {
        return this.nextRecord;
    }

    @Override
    public void setNextRecord(Date nextRecord)
    {
        this.nextRecord = nextRecord;
    }

    @Override
    public Date getLastRecord()
    {
        return this.lastRecord;
    }

    @Override
    public void setLastRecord(Date lastRecord)
    {
        this.lastRecord = lastRecord;
    }

    @Override
    public Date getLastDelete()
    {
        return this.lastDelete;
    }

    @Override
    public void setLastDelete(Date lastDelete)
    {
        this.lastDelete = lastDelete;
    }

    @Override
    public String getStoragegroup()
    {
        return this.storagegroup;
    }

    @Override
    public void setStoragegroup(String storagegroup)
    {
        this.storagegroup = storagegroup;
    }

    @Override
    public int getAvgDelay()
    {
        return this.avgDelay;
    }

    @Override
    public void setAvgDelay(int avgDelay)
    {
        this.avgDelay = avgDelay;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Record1264 [recordid=");
        builder.append(recordid);
        builder.append(", type=");
        builder.append(type);
        builder.append(", chanid=");
        builder.append(chanid);
        builder.append(", starttime=");
        builder.append(starttime);
        builder.append(", startdate=");
        builder.append(startdate);
        builder.append(", endtime=");
        builder.append(endtime);
        builder.append(", enddate=");
        builder.append(enddate);
        builder.append(", title=");
        builder.append(title);
        builder.append(", subtitle=");
        builder.append(subtitle);
        builder.append(", description=");
        builder.append(description);
        builder.append(", category=");
        builder.append(category);
        builder.append(", profile=");
        builder.append(profile);
        builder.append(", recpriority=");
        builder.append(recpriority);
        builder.append(", autoexpire=");
        builder.append(autoexpire);
        builder.append(", maxepisodes=");
        builder.append(maxepisodes);
        builder.append(", maxnewest=");
        builder.append(maxnewest);
        builder.append(", startoffset=");
        builder.append(startoffset);
        builder.append(", endoffset=");
        builder.append(endoffset);
        builder.append(", recgroup=");
        builder.append(recgroup);
        builder.append(", dupmethod=");
        builder.append(dupmethod);
        builder.append(", dupin=");
        builder.append(dupin);
        builder.append(", station=");
        builder.append(station);
        builder.append(", seriesid=");
        builder.append(seriesid);
        builder.append(", programid=");
        builder.append(programid);
        builder.append(", search=");
        builder.append(search);
        builder.append(", autotranscode=");
        builder.append(autotranscode);
        builder.append(", autocommflag=");
        builder.append(autocommflag);
        builder.append(", autouserjob1=");
        builder.append(autouserjob1);
        builder.append(", autouserjob2=");
        builder.append(autouserjob2);
        builder.append(", autouserjob3=");
        builder.append(autouserjob3);
        builder.append(", autouserjob4=");
        builder.append(autouserjob4);
        builder.append(", findday=");
        builder.append(findday);
        builder.append(", findtime=");
        builder.append(findtime);
        builder.append(", findid=");
        builder.append(findid);
        builder.append(", inactive=");
        builder.append(inactive);
        builder.append(", parentid=");
        builder.append(parentid);
        builder.append(", transcoder=");
        builder.append(transcoder);
        builder.append(", playgroup=");
        builder.append(playgroup);
        builder.append(", prefinput=");
        builder.append(prefinput);
        builder.append(", nextRecord=");
        builder.append(nextRecord);
        builder.append(", lastRecord=");
        builder.append(lastRecord);
        builder.append(", lastDelete=");
        builder.append(lastDelete);
        builder.append(", storagegroup=");
        builder.append(storagegroup);
        builder.append(", avgDelay=");
        builder.append(avgDelay);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public short getSeason()
    {
        return 0;
    }

    @Override
    public void setSeason(short season)
    {
        // NOOP
    }

    @Override
    public short getEpisode()
    {
        return 0;
    }

    @Override
    public void setEpisode(short episode)
    {
        // NOOP
    }

    @Override
    public String getInetref()
    {
        return null;
    }

    @Override
    public void setInetref(String inetref)
    {
        // NOOP
    }

    @Override
    public boolean isAutometadata()
    {
        return false;
    }

    @Override
    public void setAutometadata(boolean autometadata)
    {
        // NOOP
    }

    @Override
    public int getFilter()
    {
        return 0;
    }

    @Override
    public void setFilter(int filter)
    {
        // NOOP
    }
}
