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
package org.syphr.mythtv.db;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

    private static final String PROP_URL = "javax.persistence.jdbc.url";
    private static final String PROP_USER = "javax.persistence.jdbc.user";
    private static final String PROP_PASSWORD = "javax.persistence.jdbc.password";

    private static final int DEFAULT_PORT = 3306;

    public static EntityManagerFactory createEntityManagerFactory(SchemaVersion version,
                                                                  String host,
                                                                  int port,
                                                                  String database,
                                                                  String user,
                                                                  String password) throws DatabaseException
    {
        if (port <= 0)
        {
            port = DEFAULT_PORT;
        }

        Properties props = new Properties();
        props.put(PROP_URL, "jdbc:mysql://"
                + host
                + ":"
                + port
                + "/"
                + database
                + "?zeroDateTimeBehavior=convertToNull");
        props.put(PROP_USER, user);
        props.put(PROP_PASSWORD, password);

        try
        {
            LOGGER.trace("Attempting to connect using schema version {}", version);

            final EntityManagerFactory factory = Persistence.createEntityManagerFactory(version.getPersistenceUnitName(),
                                                                                        props);

            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                @Override
                public void run()
                {
                    if (factory.isOpen())
                    {
                        factory.close();
                    }
                }
            });

            return factory;
        }
        catch (PersistenceException e)
        {
            throw new DatabaseException("Unable to access \""
                    + database
                    + "\" at "
                    + host
                    + ":"
                    + port, e);
        }
    }
}
