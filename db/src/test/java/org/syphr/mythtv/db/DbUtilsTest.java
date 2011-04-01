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

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.db.schema.Channel;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.test.Utils;
import org.syphr.prom.PropertiesManager;

public class DbUtilsTest
{
    private static PropertiesManager<Settings> settings;
    private static Logger logger;

    @BeforeClass
    public static void setupBeforeClass() throws IOException
    {
        settings = Settings.createSettings();
        logger = LoggerFactory.getLogger(DbUtilsTest.class);
    }

    @Test
    public void testGetEntityManagerFactory() throws DatabaseException
    {
        EntityManagerFactory factory = DbUtils.getEntityManagerFactory(settings.getEnumProperty(Settings.DB_SCHEMA,
                                                                                                SchemaVersion.class),
                                                                       settings.getProperty(Settings.DB_HOST),
                                                                       settings.getIntegerProperty(Settings.DB_PORT),
                                                                       settings.getProperty(Settings.DB_INSTANCE),
                                                                       settings.getProperty(Settings.DB_USER),
                                                                       settings.getProperty(Settings.DB_PASSWORD));

        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Channel> channels = entityManager.createQuery("from "
                                                                   + Channel.class.getName(),
                                                           Channel.class)
                                              .getResultList();
        Utils.printFirstFive(channels, logger);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
