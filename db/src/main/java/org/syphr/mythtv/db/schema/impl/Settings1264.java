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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.syphr.mythtv.db.schema.Settings;
import org.syphr.mythtv.db.schema.SettingsId;

@Entity
@Table(name = "settings")
public class Settings1264 implements Settings
{
    @EmbeddedId
    private SettingsId1264 id;

    @Column(nullable = false, length = 16000)
    private String data;

    public Settings1264()
    {
        super();
    }

    public Settings1264(SettingsId1264 id, String data)
    {
        this.id = id;
        this.data = data;
    }

    @Override
    public SettingsId1264 getId()
    {
        return this.id;
    }

    @Override
    public void setId(SettingsId id)
    {
        if (id != null && !(id instanceof SettingsId1264))
        {
            throw new IllegalArgumentException("Invalid ID type: " + id.getClass().getName());
        }

        this.id = (SettingsId1264)id;
    }

    @Override
    public String getData()
    {
        return this.data;
    }

    @Override
    public void setData(String data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Settings1264 [id=");
        builder.append(id);
        builder.append(", data=");
        builder.append(data);
        builder.append("]");
        return builder.toString();
    }
}
