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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.syphr.mythtv.db.schema.ChannelGroup;

@Entity
@Table(name = "channelgroup")
public class ChannelGroup1264 implements ChannelGroup
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "chanid", nullable = false)
    private int chanid;

    @Column(name = "grpid", nullable = false)
    private int grpid;

    @Override
    public Integer getId()
    {
        return this.id;
    }

    @Override
    public void setId(Integer id)
    {
        this.id = id;
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
    public int getGrpid()
    {
        return this.grpid;
    }

    @Override
    public void setGrpid(int grpid)
    {
        this.grpid = grpid;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelGroup1264 [id=");
        builder.append(id);
        builder.append(", chanid=");
        builder.append(chanid);
        builder.append(", grpid=");
        builder.append(grpid);
        builder.append("]");
        return builder.toString();
    }
}
