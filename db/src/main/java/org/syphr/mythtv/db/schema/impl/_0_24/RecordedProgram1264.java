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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.syphr.mythtv.db.schema.RecordedProgram;
import org.syphr.mythtv.db.schema.RecordedProgramId;

@Entity
@Table(name = "recordedprogram")
public class RecordedProgram1264 implements RecordedProgram
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "chanid", column = @Column(name = "chanid",
                                                                               nullable = false)),
                         @AttributeOverride(name = "starttime",
                                            column = @Column(name = "starttime", nullable = false,
                                                             length = 19)),
                         @AttributeOverride(name = "manualid", column = @Column(name = "manualid",
                                                                                nullable = false)) })
    private RecordedProgramId1264 id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endtime", nullable = false, length = 19)
    private Date endtime;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "subtitle", nullable = false, length = 128)
    private String subtitle;

    @Column(name = "description", nullable = false, length = 16000)
    private String description;

    @Column(name = "category", nullable = false, length = 64)
    private String category;

    @Column(name = "category_type", nullable = false, length = 64)
    private String categoryType;

    @Temporal(TemporalType.DATE)
    @Column(name = "airdate", nullable = false, length = 0)
    private Date airdate;

    @Column(name = "stars", nullable = false, precision = 12, scale = 0)
    private float stars;

    @Column(name = "previouslyshown", nullable = false)
    private byte previouslyshown;

    @Column(name = "title_pronounce", nullable = false, length = 128)
    private String titlePronounce;

    @Column(name = "stereo", nullable = false)
    private boolean stereo;

    @Column(name = "subtitled", nullable = false)
    private boolean subtitled;

    @Column(name = "hdtv", nullable = false)
    private boolean hdtv;

    @Column(name = "closecaptioned", nullable = false)
    private boolean closecaptioned;

    @Column(name = "partnumber", nullable = false)
    private int partnumber;

    @Column(name = "parttotal", nullable = false)
    private int parttotal;

    @Column(name = "seriesid", nullable = false, length = 40)
    private String seriesid;

    @Temporal(TemporalType.DATE)
    @Column(name = "originalairdate", length = 10)
    private Date originalairdate;

    @Column(name = "showtype", nullable = false, length = 30)
    private String showtype;

    @Column(name = "colorcode", nullable = false, length = 20)
    private String colorcode;

    @Column(name = "syndicatedepisodenumber", nullable = false, length = 20)
    private String syndicatedepisodenumber;

    @Column(name = "programid", nullable = false, length = 40)
    private String programid;

    @Column(name = "generic")
    private Boolean generic;

    @Column(name = "listingsource", nullable = false)
    private int listingsource;

    @Column(name = "first", nullable = false)
    private boolean first;

    @Column(name = "last", nullable = false)
    private boolean last;

    @Column(name = "audioprop", nullable = false, length = 48)
    private String audioprop;

    @Column(name = "subtitletypes", nullable = false, length = 31)
    private String subtitletypes;

    @Column(name = "videoprop", nullable = false, length = 28)
    private String videoprop;

    @Override
    public RecordedProgramId getId()
    {
        return this.id;
    }

    @Override
    public void setId(RecordedProgramId id)
    {
        if (id != null && !(id instanceof RecordedProgramId1264))
        {
            throw new IllegalArgumentException("Invalid ID type: " + id.getClass().getName());
        }

        this.id = (RecordedProgramId1264)id;
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
    public String getCategoryType()
    {
        return this.categoryType;
    }

    @Override
    public void setCategoryType(String categoryType)
    {
        this.categoryType = categoryType;
    }

    @Override
    public Date getAirdate()
    {
        return this.airdate;
    }

    @Override
    public void setAirdate(Date airdate)
    {
        this.airdate = airdate;
    }

    @Override
    public float getStars()
    {
        return this.stars;
    }

    @Override
    public void setStars(float stars)
    {
        this.stars = stars;
    }

    @Override
    public byte getPreviouslyshown()
    {
        return this.previouslyshown;
    }

    @Override
    public void setPreviouslyshown(byte previouslyshown)
    {
        this.previouslyshown = previouslyshown;
    }

    @Override
    public String getTitlePronounce()
    {
        return this.titlePronounce;
    }

    @Override
    public void setTitlePronounce(String titlePronounce)
    {
        this.titlePronounce = titlePronounce;
    }

    @Override
    public boolean isStereo()
    {
        return this.stereo;
    }

    @Override
    public void setStereo(boolean stereo)
    {
        this.stereo = stereo;
    }

    @Override
    public boolean isSubtitled()
    {
        return this.subtitled;
    }

    @Override
    public void setSubtitled(boolean subtitled)
    {
        this.subtitled = subtitled;
    }

    @Override
    public boolean isHdtv()
    {
        return this.hdtv;
    }

    @Override
    public void setHdtv(boolean hdtv)
    {
        this.hdtv = hdtv;
    }

    @Override
    public boolean isClosecaptioned()
    {
        return this.closecaptioned;
    }

    @Override
    public void setClosecaptioned(boolean closecaptioned)
    {
        this.closecaptioned = closecaptioned;
    }

    @Override
    public int getPartnumber()
    {
        return this.partnumber;
    }

    @Override
    public void setPartnumber(int partnumber)
    {
        this.partnumber = partnumber;
    }

    @Override
    public int getParttotal()
    {
        return this.parttotal;
    }

    @Override
    public void setParttotal(int parttotal)
    {
        this.parttotal = parttotal;
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
    public Date getOriginalairdate()
    {
        return this.originalairdate;
    }

    @Override
    public void setOriginalairdate(Date originalairdate)
    {
        this.originalairdate = originalairdate;
    }

    @Override
    public String getShowtype()
    {
        return this.showtype;
    }

    @Override
    public void setShowtype(String showtype)
    {
        this.showtype = showtype;
    }

    @Override
    public String getColorcode()
    {
        return this.colorcode;
    }

    @Override
    public void setColorcode(String colorcode)
    {
        this.colorcode = colorcode;
    }

    @Override
    public String getSyndicatedepisodenumber()
    {
        return this.syndicatedepisodenumber;
    }

    @Override
    public void setSyndicatedepisodenumber(String syndicatedepisodenumber)
    {
        this.syndicatedepisodenumber = syndicatedepisodenumber;
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
    public Boolean getGeneric()
    {
        return this.generic;
    }

    @Override
    public void setGeneric(Boolean generic)
    {
        this.generic = generic;
    }

    @Override
    public int getListingsource()
    {
        return this.listingsource;
    }

    @Override
    public void setListingsource(int listingsource)
    {
        this.listingsource = listingsource;
    }

    @Override
    public boolean isFirst()
    {
        return this.first;
    }

    @Override
    public void setFirst(boolean first)
    {
        this.first = first;
    }

    @Override
    public boolean isLast()
    {
        return this.last;
    }

    @Override
    public void setLast(boolean last)
    {
        this.last = last;
    }

    @Override
    public String getAudioprop()
    {
        return this.audioprop;
    }

    @Override
    public void setAudioprop(String audioprop)
    {
        this.audioprop = audioprop;
    }

    @Override
    public String getSubtitletypes()
    {
        return this.subtitletypes;
    }

    @Override
    public void setSubtitletypes(String subtitletypes)
    {
        this.subtitletypes = subtitletypes;
    }

    @Override
    public String getVideoprop()
    {
        return this.videoprop;
    }

    @Override
    public void setVideoprop(String videoprop)
    {
        this.videoprop = videoprop;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RecordedProgram1264 [id=");
        builder.append(id);
        builder.append(", endtime=");
        builder.append(endtime);
        builder.append(", title=");
        builder.append(title);
        builder.append(", subtitle=");
        builder.append(subtitle);
        builder.append(", description=");
        builder.append(description);
        builder.append(", category=");
        builder.append(category);
        builder.append(", categoryType=");
        builder.append(categoryType);
        builder.append(", airdate=");
        builder.append(airdate);
        builder.append(", stars=");
        builder.append(stars);
        builder.append(", previouslyshown=");
        builder.append(previouslyshown);
        builder.append(", titlePronounce=");
        builder.append(titlePronounce);
        builder.append(", stereo=");
        builder.append(stereo);
        builder.append(", subtitled=");
        builder.append(subtitled);
        builder.append(", hdtv=");
        builder.append(hdtv);
        builder.append(", closecaptioned=");
        builder.append(closecaptioned);
        builder.append(", partnumber=");
        builder.append(partnumber);
        builder.append(", parttotal=");
        builder.append(parttotal);
        builder.append(", seriesid=");
        builder.append(seriesid);
        builder.append(", originalairdate=");
        builder.append(originalairdate);
        builder.append(", showtype=");
        builder.append(showtype);
        builder.append(", colorcode=");
        builder.append(colorcode);
        builder.append(", syndicatedepisodenumber=");
        builder.append(syndicatedepisodenumber);
        builder.append(", programid=");
        builder.append(programid);
        builder.append(", generic=");
        builder.append(generic);
        builder.append(", listingsource=");
        builder.append(listingsource);
        builder.append(", first=");
        builder.append(first);
        builder.append(", last=");
        builder.append(last);
        builder.append(", audioprop=");
        builder.append(audioprop);
        builder.append(", subtitletypes=");
        builder.append(subtitletypes);
        builder.append(", videoprop=");
        builder.append(videoprop);
        builder.append("]");
        return builder.toString();
    }
}
