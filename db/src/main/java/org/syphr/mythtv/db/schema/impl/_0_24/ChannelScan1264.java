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

import org.syphr.mythtv.db.schema.ChannelScan;

@Entity
@Table(name = "channelscan")
public class ChannelScan1264 implements ChannelScan
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "scanid", unique = true, nullable = false)
    private Integer scanid;

    @Column(name = "cardid", nullable = false)
    private int cardid;

    @Column(name = "sourceid", nullable = false)
    private int sourceid;

    @Column(name = "processed", nullable = false)
    private boolean processed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "scandate", nullable = false, length = 19)
    private Date scandate;

    @Override
    public Integer getScanid()
    {
        return this.scanid;
    }

    @Override
    public void setScanid(Integer scanid)
    {
        this.scanid = scanid;
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
    public boolean isProcessed()
    {
        return this.processed;
    }

    @Override
    public void setProcessed(boolean processed)
    {
        this.processed = processed;
    }

    @Override
    public Date getScandate()
    {
        return this.scandate;
    }

    @Override
    public void setScandate(Date scandate)
    {
        this.scandate = scandate;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelScan1264 [scanid=");
        builder.append(scanid);
        builder.append(", cardid=");
        builder.append(cardid);
        builder.append(", sourceid=");
        builder.append(sourceid);
        builder.append(", processed=");
        builder.append(processed);
        builder.append(", scandate=");
        builder.append(scandate);
        builder.append("]");
        return builder.toString();
    }
}
