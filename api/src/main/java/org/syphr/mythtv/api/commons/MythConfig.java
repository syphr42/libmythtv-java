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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MythConfig
{
    /**
     * Standard logging facility.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MythConfig.class);

    private static final String TAG_DB_HOST = "DBHostName";
    private static final String TAG_DB_PORT = "DBPort";
    private static final String TAG_DB_USERNAME = "DBUserName";
    private static final String TAG_DB_PASSWORD = "DBPassword";
    private static final String TAG_DB_NAME = "DBName";

    private static final File DEFAULT_CONFIG_FILE = new File(System.getProperty("user.home")
            + File.separator
            + ".mythtv"
            + File.separator
            + "config.xml");

    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

    public static boolean hasDefaultConfiguration()
    {
        return DEFAULT_CONFIG_FILE.canRead();
    }

    private File file = DEFAULT_CONFIG_FILE;

    private DatabaseInfo databaseInfo;

    public void setFile(File file)
    {
        this.file = file;
    }

    public File getFile()
    {
        return file;
    }

    public DatabaseInfo getDatabaseInfo()
    {
        return databaseInfo;
    }

    public void parse() throws IOException
    {
        Reader reader = new BufferedReader(new FileReader(file));
        try
        {
            XMLEventReader parser = XML_INPUT_FACTORY.createXMLEventReader(reader);

            try
            {
                databaseInfo = new DatabaseInfo();
                XMLEvent event = null;

                while (!(event = parser.nextEvent()).isEndDocument())
                {
                    if (!event.isStartElement())
                    {
                        continue;
                    }

                    String tagName = event.asStartElement().getName().getLocalPart();

                    if (TAG_DB_HOST.equals(tagName))
                    {
                        databaseInfo.host = parser.getElementText();
                    }
                    else if (TAG_DB_PORT.equals(tagName))
                    {
                        try
                        {
                            databaseInfo.port = Integer.parseInt(parser.getElementText());
                        }
                        catch (NumberFormatException e)
                        {
                            LOGGER.warn("Invalid port found in config file, using default instead",
                                        e);
                            databaseInfo.port = 0;
                        }
                    }
                    else if (TAG_DB_USERNAME.equals(tagName))
                    {
                        databaseInfo.user = parser.getElementText();
                    }
                    else if (TAG_DB_PASSWORD.equals(tagName))
                    {
                        databaseInfo.password = parser.getElementText();
                    }
                    else if (TAG_DB_NAME.equals(tagName))
                    {
                        databaseInfo.database = parser.getElementText();
                    }
                }
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

    public static class DatabaseInfo
    {
        private String host;
        private int port;
        private String database;
        private String user;
        private String password;

        public String getHost()
        {
            return host;
        }

        public int getPort()
        {
            return port;
        }

        public String getDatabase()
        {
            return database;
        }

        public String getUser()
        {
            return user;
        }

        public String getPassword()
        {
            return password;
        }
    }
}
