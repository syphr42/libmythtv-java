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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

    private static final String PROP_URL = "javax.persistence.jdbc.url";
    private static final String PROP_USER = "javax.persistence.jdbc.user";
    private static final String PROP_PASSWORD = "javax.persistence.jdbc.password";

    private static final File DEFAULT_CONFIG_FILE = new File(System.getProperty("user.home")
                                                             + File.separator
                                                             + ".mythtv"
                                                             + File.separator
                                                             + "config.xml");

    private static final int DEFAULT_PORT = 3306;

    private static final String TAG_DB_HOST = "DBHostName";
    private static final String TAG_DB_PORT = "DBPort";
    private static final String TAG_DB_USERNAME = "DBUserName";
    private static final String TAG_DB_PASSWORD = "DBPassword";
    private static final String TAG_DB_NAME = "DBName";

    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

    private static final ConcurrentMap<SchemaVersion, EntityManagerFactory> factories = new ConcurrentHashMap<SchemaVersion, EntityManagerFactory>();

    public static boolean hasDefaultConfig()
    {
        return DEFAULT_CONFIG_FILE.canRead();
    }

    public static EntityManagerFactory getEntityManagerFactory(SchemaVersion version) throws DatabaseException,
                                                                                     IOException
    {
        return getEntityManagerFactory(version, DEFAULT_CONFIG_FILE);
    }

    public static EntityManagerFactory getEntityManagerFactory(SchemaVersion version,
                                                               File config) throws DatabaseException,
                                                                           IOException
    {
        return getEntityManagerFactory(version, readFile(config));
    }

    public static EntityManagerFactory getEntityManagerFactory(SchemaVersion version,
                                                               String host,
                                                               int port,
                                                               String database,
                                                               String user,
                                                               String password) throws DatabaseException
    {
        return getEntityManagerFactory(version, new ConnectionInfo(host,
                                                                   port,
                                                                   database,
                                                                   user,
                                                                   password));
    }

    private static EntityManagerFactory getEntityManagerFactory(SchemaVersion version,
                                                                ConnectionInfo info) throws DatabaseException
    {
        EntityManagerFactory factory = factories.get(version);

        if (factory == null)
        {
            if (info.port <= 0)
            {
                info.port = DEFAULT_PORT;
            }

            Properties props = new Properties();
            props.put(PROP_URL, "jdbc:mysql://"
                                + info.host
                                + ":"
                                + info.port
                                + "/"
                                + info.database
                                + "?zeroDateTimeBehavior=convertToNull");
            props.put(PROP_USER, info.user);
            props.put(PROP_PASSWORD, info.password);

            try
            {
                LOGGER.trace("Attempting to connect using schema version {}", version);

                final EntityManagerFactory newFactory = Persistence.createEntityManagerFactory(version.getPersistenceUnitName(),
                                                                                               props);

                factory = factories.putIfAbsent(version, newFactory);
                if (factory == null)
                {
                    factory = newFactory;
                    Runtime.getRuntime().addShutdownHook(new Thread()
                    {
                        @Override
                        public void run()
                        {
                            newFactory.close();
                        }
                    });
                }
            }
            catch (PersistenceException e)
            {
                throw new DatabaseException("Unable to access \""
                                            + info.database
                                            + "\" at "
                                            + info.host
                                            + ":"
                                            + info.port, e);
            }
        }

        return factory;
    }

    private static ConnectionInfo readFile(File file) throws IOException
    {
        Reader reader = new BufferedReader(new FileReader(file));
        try
        {
            XMLEventReader parser = XML_INPUT_FACTORY.createXMLEventReader(reader);

            try
            {
                ConnectionInfo info = new ConnectionInfo();
                XMLEvent event = null;

                while (!(event = parser.nextEvent()).isEndDocument())
                {
                    if (!event.isStartElement())
                    {
                        continue;
                    }

                    String tagName = event.asStartElement()
                                          .getName()
                                          .getLocalPart();

                    if (TAG_DB_HOST.equals(tagName))
                    {
                        info.host = parser.getElementText();
                    }
                    else if (TAG_DB_PORT.equals(tagName))
                    {
                        try
                        {
                            info.port = Integer.parseInt(parser.getElementText());
                        }
                        catch (NumberFormatException e)
                        {
                            LOGGER.warn("Invalid port found in config file, using default instead", e);
                            info.port = DEFAULT_PORT;
                        }
                    }
                    else if (TAG_DB_USERNAME.equals(tagName))
                    {
                        info.user = parser.getElementText();
                    }
                    else if (TAG_DB_PASSWORD.equals(tagName))
                    {
                        info.password = parser.getElementText();
                    }
                    else if (TAG_DB_NAME.equals(tagName))
                    {
                        info.database = parser.getElementText();
                    }
                }

                return info;
            }
            finally
            {
                parser.close();
            }
        }
        catch (XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
        finally
        {
            reader.close();
        }
    }

    private static class ConnectionInfo
    {
        public String host;
        public int port;
        public String database;
        public String user;
        public String password;

        public ConnectionInfo()
        {
            super();
        }

        public ConnectionInfo(String host,
                              int port,
                              String database,
                              String user,
                              String password)
        {
            this.host = host;
            this.port = port;
            this.database = database;
            this.user = user;
            this.password = password;
        }
    }
}
