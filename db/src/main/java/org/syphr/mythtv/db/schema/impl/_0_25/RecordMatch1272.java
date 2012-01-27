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

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.syphr.mythtv.db.schema.RecordMatch;
import org.syphr.mythtv.db.schema.RecordMatchId;

@Entity
@Table(name = "recordmatch")
public class RecordMatch1272 implements RecordMatch
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "recordid", column = @Column(name = "recordid",
                                                                                 nullable = false)),
                         @AttributeOverride(name = "chanid", column = @Column(name = "chanid",
                                                                              nullable = false)),
                         @AttributeOverride(name = "starttime",
                                            column = @Column(name = "starttime", nullable = false,
                                                             length = 19)),
                         @AttributeOverride(name = "manualid", column = @Column(name = "manualid",
                                                                                nullable = false)),
                         @AttributeOverride(name = "oldrecduplicate",
                                            column = @Column(name = "oldrecduplicate")),
                         @AttributeOverride(name = "recduplicate",
                                            column = @Column(name = "recduplicate")),
                         @AttributeOverride(name = "findduplicate",
                                            column = @Column(name = "findduplicate")),
                         @AttributeOverride(name = "oldrecstatus",
                                            column = @Column(name = "oldrecstatus")) })
    private RecordMatchId1272 id;

    @Override
    public RecordMatchId getId()
    {
        return this.id;
    }

    @Override
    public void setId(RecordMatchId id)
    {
        if (id != null && !(id instanceof RecordMatchId1272))
        {
            throw new IllegalArgumentException("Invalid ID type: " + id.getClass().getName());
        }

        this.id = (RecordMatchId1272)id;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RecordMatch1272 [id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }
}
