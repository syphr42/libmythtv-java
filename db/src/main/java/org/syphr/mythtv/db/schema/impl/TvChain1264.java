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
package org.syphr.mythtv.db.schema.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.syphr.mythtv.db.schema.TvChain;
import org.syphr.mythtv.db.schema.TvChainId;

@Entity
@Table(name = "tvchain")
public class TvChain1264 implements TvChain
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TvChainId1264 id;

    @Column(nullable = false, length = 128)
    private String chainid;

    @Column(nullable = false)
    private int chainpos;

    @Column(nullable = false)
    private boolean discontinuity;

    @Column(nullable = false)
    private int watching;

    @Column(nullable = false, length = 128)
    private String hostprefix;

    @Column(nullable = false, length = 32)
    private String cardtype;

    @Column(nullable = false, length = 32)
    private String input;

    @Column(nullable = false, length = 32)
    private String channame;

    @Column(nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;

    public TvChain1264()
    {
        super();
    }

    public TvChain1264(TvChainId1264 id,
                   String chainid,
                   int chainpos,
                   boolean discontinuity,
                   int watching,
                   String hostprefix,
                   String cardtype,
                   String input,
                   String channame,
                   Date endtime)
    {
        this.id = id;
        this.chainid = chainid;
        this.chainpos = chainpos;
        this.discontinuity = discontinuity;
        this.watching = watching;
        this.hostprefix = hostprefix;
        this.cardtype = cardtype;
        this.input = input;
        this.channame = channame;
        this.endtime = endtime;
    }

    @Override
    public TvChainId1264 getId()
    {
        return this.id;
    }

    @Override
    public void setId(TvChainId id)
    {
        if (id != null && !(id instanceof TvChainId1264))
        {
            throw new IllegalArgumentException("Invalid ID type: " + id.getClass().getName());
        }

        this.id = (TvChainId1264)id;
    }

    @Override
    public String getChainid()
    {
        return this.chainid;
    }

    @Override
    public void setChainid(String chainid)
    {
        this.chainid = chainid;
    }

    @Override
    public int getChainpos()
    {
        return this.chainpos;
    }

    @Override
    public void setChainpos(int chainpos)
    {
        this.chainpos = chainpos;
    }

    @Override
    public boolean isDiscontinuity()
    {
        return this.discontinuity;
    }

    @Override
    public void setDiscontinuity(boolean discontinuity)
    {
        this.discontinuity = discontinuity;
    }

    @Override
    public int getWatching()
    {
        return this.watching;
    }

    @Override
    public void setWatching(int watching)
    {
        this.watching = watching;
    }

    @Override
    public String getHostprefix()
    {
        return this.hostprefix;
    }

    @Override
    public void setHostprefix(String hostprefix)
    {
        this.hostprefix = hostprefix;
    }

    @Override
    public String getCardtype()
    {
        return this.cardtype;
    }

    @Override
    public void setCardtype(String cardtype)
    {
        this.cardtype = cardtype;
    }

    @Override
    public String getInput()
    {
        return this.input;
    }

    @Override
    public void setInput(String input)
    {
        this.input = input;
    }

    @Override
    public String getChanname()
    {
        return this.channame;
    }

    @Override
    public void setChanname(String channame)
    {
        this.channame = channame;
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
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TvChain1264 [id=");
        builder.append(id);
        builder.append(", chainid=");
        builder.append(chainid);
        builder.append(", chainpos=");
        builder.append(chainpos);
        builder.append(", discontinuity=");
        builder.append(discontinuity);
        builder.append(", watching=");
        builder.append(watching);
        builder.append(", hostprefix=");
        builder.append(hostprefix);
        builder.append(", cardtype=");
        builder.append(cardtype);
        builder.append(", input=");
        builder.append(input);
        builder.append(", channame=");
        builder.append(channame);
        builder.append(", endtime=");
        builder.append(endtime);
        builder.append("]");
        return builder.toString();
    }
}
