/*
 * Copyright 2011-2012 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 import java.util.Date;

import org.syphr.mythtv.db.schema.TvChainId;
 You may obtain a copy of the License at
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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.syphr.mythtv.db.schema.TvChainId;

@Embeddable
public class TvChainId1264 implements TvChainId
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private int chanid;

    @Column(nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;

    public TvChainId1264()
    {
        super();
    }

    public TvChainId1264(int chanid, Date starttime)
    {
        this.chanid = chanid;
        this.starttime = starttime;
    }

    @Override
    public int getChanid()
    {
        return this.chanid;
    }

    @Override
    public void setChanid(int chanid)
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
    public boolean equals(Object other)
    {
        if ((this == other))
        {
            return true;
        }
        if ((other == null))
        {
            return false;
        }
        if (!(other instanceof TvChainId1264))
        {
            return false;
        }
        TvChainId1264 castOther = (TvChainId1264) other;

        return (this.getChanid() == castOther.getChanid())
               && ((this.getStarttime() == castOther.getStarttime()) || (this.getStarttime() != null
                                                                         && castOther.getStarttime() != null && this.getStarttime()
                                                                                                                    .equals(castOther.getStarttime())));
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + this.getChanid();
        result = 37
                 * result
                 + (getStarttime() == null ? 0 : this.getStarttime().hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("TvChainId1264 [chanid=");
        builder.append(chanid);
        builder.append(", starttime=");
        builder.append(starttime);
        builder.append("]");
        return builder.toString();
    }
}
