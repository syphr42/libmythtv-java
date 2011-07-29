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
package org.syphr.mythtv.db;

public enum SchemaVersion
{
    _1264(1264),
    _1280(1280);

    private final int value;

    private SchemaVersion(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public String getPersistenceUnitName()
    {
        return "org.syphr.mythtv.db.schema.impl." + getValue();
    }

    public static SchemaVersion valueOf(int value)
    {
        for (SchemaVersion version : values())
        {
            if (value == version.getValue())
            {
                return version;
            }
        }

        throw new IllegalArgumentException("Unable to locate schema version " + value);
    }
}
