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

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.db.schema.CallsignNetworkMap;
import org.syphr.mythtv.db.schema.CaptureCard;
import org.syphr.mythtv.db.schema.CardInput;
import org.syphr.mythtv.db.schema.Channel;
import org.syphr.mythtv.db.schema.ChannelGroup;
import org.syphr.mythtv.db.schema.ChannelGroupNames;
import org.syphr.mythtv.db.schema.ChannelScan;
import org.syphr.mythtv.db.schema.ChannelScanChannel;
import org.syphr.mythtv.db.schema.ChannelScanDtvMultiplex;
import org.syphr.mythtv.db.schema.OldRecorded;
import org.syphr.mythtv.db.schema.Program;
import org.syphr.mythtv.db.schema.Recorded;
import org.syphr.mythtv.db.schema.Settings;
import org.syphr.mythtv.db.schema.TvChain;
import org.syphr.mythtv.test.Utils;
import org.syphr.prom.PropertiesManager;

public class DbSchemaIT
{
    private static PropertiesManager<org.syphr.mythtv.test.Settings> settings;
    private static Logger logger;

    private static EntityManagerFactory factory;

    private EntityManager manager;

    @BeforeClass
    public static void setupBeforeClass() throws IOException, DatabaseException
    {
        settings = org.syphr.mythtv.test.Settings.createSettings();
        logger = LoggerFactory.getLogger(DbSchemaIT.class);

        factory = DbUtils.getEntityManagerFactory(settings.getEnumProperty(org.syphr.mythtv.test.Settings.DB_SCHEMA,
                                                                           SchemaVersion.class),
                                                  settings.getProperty(org.syphr.mythtv.test.Settings.DB_HOST),
                                                  settings.getIntegerProperty(org.syphr.mythtv.test.Settings.DB_PORT),
                                                  settings.getProperty(org.syphr.mythtv.test.Settings.DB_INSTANCE),
                                                  settings.getProperty(org.syphr.mythtv.test.Settings.DB_USER),
                                                  settings.getProperty(org.syphr.mythtv.test.Settings.DB_PASSWORD));
    }

    @Before
    public void before()
    {
        manager = factory.createEntityManager();
        manager.getTransaction().begin();
    }

    @After
    public void after()
    {
        manager.getTransaction().commit();
        manager.close();
    }

    @Test
    public void testCallsignNetworkMap()
    {
        printFirstFive(CallsignNetworkMap.class);
    }

    @Test
    public void testCaptureCard()
    {
        printFirstFive(CaptureCard.class);
    }

    @Test
    public void testCardInput()
    {
        printFirstFive(CardInput.class);
    }

    @Test
    public void testChannel()
    {
        printFirstFive(Channel.class);
    }

    @Test
    public void testChannelGroup()
    {
        printFirstFive(ChannelGroup.class);
    }

    @Test
    public void testChannelGroupNames()
    {
        printFirstFive(ChannelGroupNames.class);
    }

    @Test
    public void testChannelScan()
    {
        printFirstFive(ChannelScan.class);
    }

    @Test
    public void testChannelScanChannel()
    {
        printFirstFive(ChannelScanChannel.class);
    }

    @Test
    public void testChannelScanDtvMultiplex()
    {
        printFirstFive(ChannelScanDtvMultiplex.class);
    }

    @Test
    public void testOldRecorded()
    {
        printFirstFive(OldRecorded.class);
    }

    @Test
    public void testProgram()
    {
        printFirstFive(Program.class);
    }

    @Test
    public void testRecorded()
    {
        printFirstFive(Recorded.class);
    }

    @Test
    public void testSettings()
    {
        printFirstFive(Settings.class);
    }

    @Test
    public void testTvChain()
    {
        printFirstFive(TvChain.class);
    }

    private <T> void printFirstFive(Class<T> entityType)
    {
        logger.debug("Retrieving records from {} table", entityType.getSimpleName());

        TypedQuery<T> query = manager.createQuery("select x from " + entityType.getName() + " as x",
                                                  entityType);
        query.setMaxResults(5);

        Utils.printFirstFive(query.getResultList(), logger);
    }
}
