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
package org.syphr.mythtv.db.schema;

import java.io.Serializable;
import java.util.Date;

public interface Recorded extends Serializable
{
    public RecordedId getId();

    public void setId(RecordedId id);

    public Date getEndtime();

    public void setEndtime(Date endtime);

    public String getTitle();

    public void setTitle(String title);

    public String getSubtitle();

    public void setSubtitle(String subtitle);

    public String getDescription();

    public void setDescription(String description);

    public int getSeason();

    public void setSeason(int season);

    public int getEpisode();

    public void setEpisode(int episode);

    public String getCategory();

    public void setCategory(String category);

    public String getHostname();

    public void setHostname(String hostname);

    public boolean isBookmark();

    public void setBookmark(boolean bookmark);

    public int getEditing();

    public void setEditing(int editing);

    public boolean isCutlist();

    public void setCutlist(boolean cutlist);

    public int getAutoexpire();

    public void setAutoexpire(int autoexpire);

    public int getCommflagged();

    public void setCommflagged(int commflagged);

    public String getRecgroup();

    public void setRecgroup(String recgroup);

    public Integer getRecordid();

    public void setRecordid(Integer recordid);

    public String getSeriesid();

    public void setSeriesid(String seriesid);

    public String getProgramid();

    public void setProgramid(String programid);

    public String getInetref();

    public void setInetref(String inetref);

    public Date getLastmodified();

    public void setLastmodified(Date lastmodified);

    public long getFilesize();

    public void setFilesize(long filesize);

    public float getStars();

    public void setStars(float stars);

    public Boolean getPreviouslyshown();

    public void setPreviouslyshown(Boolean previouslyshown);

    public Date getOriginalairdate();

    public void setOriginalairdate(Date originalairdate);

    public boolean isPreserve();

    public void setPreserve(boolean preserve);

    public int getFindid();

    public void setFindid(int findid);

    public boolean isDeletepending();

    public void setDeletepending(boolean deletepending);

    public int getTranscoder();

    public void setTranscoder(int transcoder);

    public float getTimestretch();

    public void setTimestretch(float timestretch);

    public int getRecpriority();

    public void setRecpriority(int recpriority);

    public String getBasename();

    public void setBasename(String basename);

    public Date getProgstart();

    public void setProgstart(Date progstart);

    public Date getProgend();

    public void setProgend(Date progend);

    public String getPlaygroup();

    public void setPlaygroup(String playgroup);

    public String getProfile();

    public void setProfile(String profile);

    public boolean isDuplicate();

    public void setDuplicate(boolean duplicate);

    public boolean isTranscoded();

    public void setTranscoded(boolean transcoded);

    public byte getWatched();

    public void setWatched(byte watched);

    public String getStoragegroup();

    public void setStoragegroup(String storagegroup);

    public Date getBookmarkupdate();

    public void setBookmarkupdate(Date bookmarkupdate);
}