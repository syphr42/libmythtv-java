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
package org.syphr.mythtv.db.schema;

import java.io.Serializable;
import java.util.Date;

public interface Record extends Serializable
{
    public Integer getRecordid();

    public void setRecordid(Integer recordid);

    public int getType();

    public void setType(int type);

    public Integer getChanid();

    public void setChanid(Integer chanid);

    public Date getStarttime();

    public void setStarttime(Date starttime);

    public Date getStartdate();

    public void setStartdate(Date startdate);

    public Date getEndtime();

    public void setEndtime(Date endtime);

    public Date getEnddate();

    public void setEnddate(Date enddate);

    public String getTitle();

    public void setTitle(String title);

    public String getSubtitle();

    public void setSubtitle(String subtitle);

    public String getDescription();

    public void setDescription(String description);

    public String getCategory();

    public void setCategory(String category);

    public String getProfile();

    public void setProfile(String profile);

    public int getRecpriority();

    public void setRecpriority(int recpriority);

    public int getAutoexpire();

    public void setAutoexpire(int autoexpire);

    public int getMaxepisodes();

    public void setMaxepisodes(int maxepisodes);

    public int getMaxnewest();

    public void setMaxnewest(int maxnewest);

    public int getStartoffset();

    public void setStartoffset(int startoffset);

    public int getEndoffset();

    public void setEndoffset(int endoffset);

    public String getRecgroup();

    public void setRecgroup(String recgroup);

    public int getDupmethod();

    public void setDupmethod(int dupmethod);

    public int getDupin();

    public void setDupin(int dupin);

    public String getStation();

    public void setStation(String station);

    public String getSeriesid();

    public void setSeriesid(String seriesid);

    public String getProgramid();

    public void setProgramid(String programid);

    public int getSearch();

    public void setSearch(int search);

    public boolean isAutotranscode();

    public void setAutotranscode(boolean autotranscode);

    public boolean isAutocommflag();

    public void setAutocommflag(boolean autocommflag);

    public boolean isAutouserjob1();

    public void setAutouserjob1(boolean autouserjob1);

    public boolean isAutouserjob2();

    public void setAutouserjob2(boolean autouserjob2);

    public boolean isAutouserjob3();

    public void setAutouserjob3(boolean autouserjob3);

    public boolean isAutouserjob4();

    public void setAutouserjob4(boolean autouserjob4);

    public byte getFindday();

    public void setFindday(byte findday);

    public Date getFindtime();

    public void setFindtime(Date findtime);

    public int getFindid();

    public void setFindid(int findid);

    public boolean isInactive();

    public void setInactive(boolean inactive);

    public int getParentid();

    public void setParentid(int parentid);

    public int getTranscoder();

    public void setTranscoder(int transcoder);

    public String getPlaygroup();

    public void setPlaygroup(String playgroup);

    public int getPrefinput();

    public void setPrefinput(int prefinput);

    public Date getNextRecord();

    public void setNextRecord(Date nextRecord);

    public Date getLastRecord();

    public void setLastRecord(Date lastRecord);

    public Date getLastDelete();

    public void setLastDelete(Date lastDelete);

    public String getStoragegroup();

    public void setStoragegroup(String storagegroup);

    public int getAvgDelay();

    public void setAvgDelay(int avgDelay);

    public short getSeason();

    public void setSeason(short season);

    public short getEpisode();

    public void setEpisode(short episode);

    public String getInetref();

    public void setInetref(String inetref);

    public boolean isAutometadata();

    public void setAutometadata(boolean autometadata);

    public int getFilter();

    public void setFilter(int filter);
}