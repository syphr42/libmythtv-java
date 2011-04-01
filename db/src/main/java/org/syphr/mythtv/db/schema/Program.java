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

import org.syphr.mythtv.db.schema.impl.ProgramId1264;

public interface Program extends Serializable
{
    public ProgramId1264 getId();

    public void setId(ProgramId1264 id);

    public Date getEndtime();

    public void setEndtime(Date endtime);

    public String getTitle();

    public void setTitle(String title);

    public String getSubtitle();

    public void setSubtitle(String subtitle);

    public String getDescription();

    public void setDescription(String description);

    public String getCategory();

    public void setCategory(String category);

    public String getCategoryType();

    public void setCategoryType(String categoryType);

    public Date getAirdate();

    public void setAirdate(Date airdate);

    public float getStars();

    public void setStars(float stars);

    public byte getPreviouslyshown();

    public void setPreviouslyshown(byte previouslyshown);

    public String getTitlePronounce();

    public void setTitlePronounce(String titlePronounce);

    public boolean isStereo();

    public void setStereo(boolean stereo);

    public boolean isSubtitled();

    public void setSubtitled(boolean subtitled);

    public boolean isHdtv();

    public void setHdtv(boolean hdtv);

    public boolean isClosecaptioned();

    public void setClosecaptioned(boolean closecaptioned);

    public int getPartnumber();

    public void setPartnumber(int partnumber);

    public int getParttotal();

    public void setParttotal(int parttotal);

    public String getSeriesid();

    public void setSeriesid(String seriesid);

    public Date getOriginalairdate();

    public void setOriginalairdate(Date originalairdate);

    public String getShowtype();

    public void setShowtype(String showtype);

    public String getColorcode();

    public void setColorcode(String colorcode);

    public String getSyndicatedepisodenumber();

    public void setSyndicatedepisodenumber(String syndicatedepisodenumber);

    public String getProgramid();

    public void setProgramid(String programid);

    public Boolean getGeneric();

    public void setGeneric(Boolean generic);

    public int getListingsource();

    public void setListingsource(int listingsource);

    public boolean isFirst();

    public void setFirst(boolean first);

    public boolean isLast();

    public void setLast(boolean last);

    public String getAudioprop();

    public void setAudioprop(String audioprop);

    public String getSubtitletypes();

    public void setSubtitletypes(String subtitletypes);

    public String getVideoprop();

    public void setVideoprop(String videoprop);
}