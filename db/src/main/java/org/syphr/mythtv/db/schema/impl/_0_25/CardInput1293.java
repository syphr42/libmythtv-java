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

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.syphr.mythtv.db.schema.CardInput;

@Entity
@Table(name = "cardinput")
public class CardInput1293 implements CardInput
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cardinputid", unique = true, nullable = false)
    private Integer cardinputid;

    @Column(name = "cardid", nullable = false)
    private int cardid;

    @Column(name = "sourceid", nullable = false)
    private int sourceid;

    @Column(name = "inputname", nullable = false, length = 32)
    private String inputname;

    @Column(name = "externalcommand", length = 128)
    private String externalcommand;

    @Column(name = "tunechan", length = 10)
    private String tunechan;

    @Column(name = "startchan", length = 10)
    private String startchan;

    @Column(name = "displayname", nullable = false, length = 64)
    private String displayname;

    @Column(name = "dishnet_eit", nullable = false)
    private boolean dishnetEit;

    @Column(name = "recpriority", nullable = false)
    private int recpriority;

    @Column(name = "quicktune", nullable = false)
    private byte quicktune;

    @Column(name = "schedorder", nullable = false)
    private int schedorder;

    @Column(name = "livetvorder", nullable = false)
    private int livetvorder;

    @Override
    public Integer getCardinputid()
    {
        return this.cardinputid;
    }

    @Override
    public void setCardinputid(Integer cardinputid)
    {
        this.cardinputid = cardinputid;
    }

    @Override
    public int getCardid()
    {
        return this.cardid;
    }

    @Override
    public void setCardid(int cardid)
    {
        this.cardid = cardid;
    }

    @Override
    public int getSourceid()
    {
        return this.sourceid;
    }

    @Override
    public void setSourceid(int sourceid)
    {
        this.sourceid = sourceid;
    }

    @Override
    public String getInputname()
    {
        return this.inputname;
    }

    @Override
    public void setInputname(String inputname)
    {
        this.inputname = inputname;
    }

    @Override
    public String getExternalcommand()
    {
        return this.externalcommand;
    }

    @Override
    public void setExternalcommand(String externalcommand)
    {
        this.externalcommand = externalcommand;
    }

    @Override
    public String getTunechan()
    {
        return this.tunechan;
    }

    @Override
    public void setTunechan(String tunechan)
    {
        this.tunechan = tunechan;
    }

    @Override
    public String getStartchan()
    {
        return this.startchan;
    }

    @Override
    public void setStartchan(String startchan)
    {
        this.startchan = startchan;
    }

    @Override
    public String getDisplayname()
    {
        return this.displayname;
    }

    @Override
    public void setDisplayname(String displayname)
    {
        this.displayname = displayname;
    }

    @Override
    public boolean isDishnetEit()
    {
        return this.dishnetEit;
    }

    @Override
    public void setDishnetEit(boolean dishnetEit)
    {
        this.dishnetEit = dishnetEit;
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
    public byte getQuicktune()
    {
        return this.quicktune;
    }

    @Override
    public void setQuicktune(byte quicktune)
    {
        this.quicktune = quicktune;
    }

    @Override
    public int getSchedorder()
    {
        return schedorder;
    }

    @Override
    public void setSchedorder(int schedorder)
    {
        this.schedorder = schedorder;
    }

    @Override
    public int getLivetvorder()
    {
        return livetvorder;
    }

    @Override
    public void setLivetvorder(int livetvorder)
    {
        this.livetvorder = livetvorder;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CardInput1293 [cardinputid=");
        builder.append(cardinputid);
        builder.append(", cardid=");
        builder.append(cardid);
        builder.append(", sourceid=");
        builder.append(sourceid);
        builder.append(", inputname=");
        builder.append(inputname);
        builder.append(", externalcommand=");
        builder.append(externalcommand);
        builder.append(", tunechan=");
        builder.append(tunechan);
        builder.append(", startchan=");
        builder.append(startchan);
        builder.append(", displayname=");
        builder.append(displayname);
        builder.append(", dishnetEit=");
        builder.append(dishnetEit);
        builder.append(", recpriority=");
        builder.append(recpriority);
        builder.append(", quicktune=");
        builder.append(quicktune);
        builder.append(", schedorder=");
        builder.append(schedorder);
        builder.append(", livetvorder=");
        builder.append(livetvorder);
        builder.append("]");
        return builder.toString();
    }
}
