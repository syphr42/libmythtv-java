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

public interface OldRecorded extends Serializable
{
    public OldRecordedId getId();

    public void setId(OldRecordedId id);

    public int getChanid();

    public void setChanid(int chanid);

    public Date getEndtime();

    public void setEndtime(Date endtime);

    public String getSubtitle();

    public void setSubtitle(String subtitle);

    public String getDescription();

    public void setDescription(String description);

    public short getSeason();

    public void setSeason(short season);

    public short getEpisode();

    public void setEpisode(short episode);

    public String getCategory();

    public void setCategory(String category);

    public String getSeriesid();

    public void setSeriesid(String seriesid);

    public String getProgramid();

    public void setProgramid(String programid);

    public String getInetref();

    public void setInetref(String inetref);

    public int getFindid();

    public void setFindid(int findid);

    public int getRecordid();

    public void setRecordid(int recordid);

    public int getRectype();

    public void setRectype(int rectype);

    public boolean isDuplicate();

    public void setDuplicate(boolean duplicate);

    public int getRecstatus();

    public void setRecstatus(int recstatus);

    public short getReactivate();

    public void setReactivate(short reactivate);

    public boolean isGeneric();

    public void setGeneric(boolean generic);

    public boolean isFuture();

    public void setFuture(boolean future);

    public Boolean getGeneric();

    public void setGeneric(Boolean generic);
}