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
package org.syphr.mythtv.db.schema.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.syphr.mythtv.db.schema.Program;

@Entity
@Table(name = "program")
public class Program1264 implements java.io.Serializable, Program
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProgramId1264 id;

    @Column(nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;

    @Column(nullable = false, length = 128)
    private String title;

    @Column(nullable = false, length = 128)
    private String subtitle;

    @Column(nullable = false, length = 16000)
    private String description;

    @Column(nullable = false, length = 64)
    private String category;

    @Column(name = "category_type", nullable = false, length = 64)
    private String categoryType;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date airdate;

    @Column(nullable = false, precision = 12, scale = 0)
    private float stars;

    @Column(nullable = false)
    private byte previouslyshown;

    @Column(name = "title_pronounce", nullable = false, length = 128)
    private String titlePronounce;

    @Column(nullable = false)
    private boolean stereo;

    @Column(nullable = false)
    private boolean subtitled;

    @Column(nullable = false)
    private boolean hdtv;

    @Column(nullable = false)
    private boolean closecaptioned;

    @Column(nullable = false)
    private int partnumber;

    @Column(nullable = false)
    private int parttotal;

    @Column(nullable = false, length = 64)
    private String seriesid;

    @Column(nullable = false, length = 10)
    @Temporal(TemporalType.DATE)
    private Date originalairdate;

    @Column(nullable = false, length = 30)
    private String showtype;

    @Column(nullable = false, length = 20)
    private String colorcode;

    @Column(nullable = false, length = 20)
    private String syndicatedepisodenumber;

    @Column(nullable = false, length = 64)
    private String programid;

    private Boolean generic;

    @Column(nullable = false)
    private int listingsource;

    @Column(nullable = false)
    private boolean first;

    @Column(nullable = false)
    private boolean last;

    @Column(nullable = false, length = 48)
    private String audioprop;

    @Column(nullable = false, length = 31)
    private String subtitletypes;

    @Column(nullable = false, length = 19)
    private String videoprop;

    public Program1264()
    {
        super();
    }

    public Program1264(ProgramId1264 id,
                   Date endtime,
                   String title,
                   String subtitle,
                   String description,
                   String category,
                   String categoryType,
                   Date airdate,
                   float stars,
                   byte previouslyshown,
                   String titlePronounce,
                   boolean stereo,
                   boolean subtitled,
                   boolean hdtv,
                   boolean closecaptioned,
                   int partnumber,
                   int parttotal,
                   String seriesid,
                   String showtype,
                   String colorcode,
                   String syndicatedepisodenumber,
                   String programid,
                   int listingsource,
                   boolean first,
                   boolean last,
                   String audioprop,
                   String subtitletypes,
                   String videoprop)
    {
        this.id = id;
        this.endtime = endtime;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.categoryType = categoryType;
        this.airdate = airdate;
        this.stars = stars;
        this.previouslyshown = previouslyshown;
        this.titlePronounce = titlePronounce;
        this.stereo = stereo;
        this.subtitled = subtitled;
        this.hdtv = hdtv;
        this.closecaptioned = closecaptioned;
        this.partnumber = partnumber;
        this.parttotal = parttotal;
        this.seriesid = seriesid;
        this.showtype = showtype;
        this.colorcode = colorcode;
        this.syndicatedepisodenumber = syndicatedepisodenumber;
        this.programid = programid;
        this.listingsource = listingsource;
        this.first = first;
        this.last = last;
        this.audioprop = audioprop;
        this.subtitletypes = subtitletypes;
        this.videoprop = videoprop;
    }

    public Program1264(ProgramId1264 id,
                   Date endtime,
                   String title,
                   String subtitle,
                   String description,
                   String category,
                   String categoryType,
                   Date airdate,
                   float stars,
                   byte previouslyshown,
                   String titlePronounce,
                   boolean stereo,
                   boolean subtitled,
                   boolean hdtv,
                   boolean closecaptioned,
                   int partnumber,
                   int parttotal,
                   String seriesid,
                   Date originalairdate,
                   String showtype,
                   String colorcode,
                   String syndicatedepisodenumber,
                   String programid,
                   Boolean generic,
                   int listingsource,
                   boolean first,
                   boolean last,
                   String audioprop,
                   String subtitletypes,
                   String videoprop)
    {
        this.id = id;
        this.endtime = endtime;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.category = category;
        this.categoryType = categoryType;
        this.airdate = airdate;
        this.stars = stars;
        this.previouslyshown = previouslyshown;
        this.titlePronounce = titlePronounce;
        this.stereo = stereo;
        this.subtitled = subtitled;
        this.hdtv = hdtv;
        this.closecaptioned = closecaptioned;
        this.partnumber = partnumber;
        this.parttotal = parttotal;
        this.seriesid = seriesid;
        this.originalairdate = originalairdate;
        this.showtype = showtype;
        this.colorcode = colorcode;
        this.syndicatedepisodenumber = syndicatedepisodenumber;
        this.programid = programid;
        this.generic = generic;
        this.listingsource = listingsource;
        this.first = first;
        this.last = last;
        this.audioprop = audioprop;
        this.subtitletypes = subtitletypes;
        this.videoprop = videoprop;
    }

    @Override
    public ProgramId1264 getId()
    {
        return this.id;
    }

    @Override
    public void setId(ProgramId1264 id)
    {
        this.id = id;
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
        builder.append("Program1264 [id=");
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
