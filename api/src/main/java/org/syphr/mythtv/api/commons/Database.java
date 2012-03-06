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
package org.syphr.mythtv.api.commons;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.syphr.mythtv.api.commons.MythConfig.DatabaseInfo;
import org.syphr.mythtv.db.DatabaseException;
import org.syphr.mythtv.db.DbUtils;
import org.syphr.mythtv.db.SchemaVersion;

public class Database
{
    private final SchemaVersion version;

    private EntityManagerFactory factory;

    public Database(SchemaVersion version)
    {
        this.version = version;
    }

    public void load(DatabaseInfo dbInfo) throws DatabaseException, IOException
    {
        load(dbInfo.getHost(),
             dbInfo.getPort(),
             dbInfo.getDatabase(),
             dbInfo.getUser(),
             dbInfo.getPassword());
    }

    public void load(String host, int port, String database, String user, String password) throws DatabaseException
    {
        if (isLoaded())
        {
            unload();
        }

        factory = DbUtils.createEntityManagerFactory(version, host, port, database, user, password);
    }

    public boolean isLoaded()
    {
        return factory != null && factory.isOpen();
    }

    public void unload()
    {
        if (!isLoaded())
        {
            return;
        }

        factory.close();
        factory = null;
    }

    public EntityManager createEntityManager() throws IllegalStateException
    {
        if (!isLoaded())
        {
            throw new IllegalStateException("database not loaded");
        }

        return factory.createEntityManager();
    }
}
