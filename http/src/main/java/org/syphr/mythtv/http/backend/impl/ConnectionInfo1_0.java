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
package org.syphr.mythtv.http.backend.impl;

import java.io.StringReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.syphr.mythtv.http.backend.ConnectionInfo;
import org.syphr.mythtv.http.backend.ContentException;
import org.syphr.mythtv.http.backend.Database;
import org.syphr.mythtv.http.backend.Wol;

public class ConnectionInfo1_0 implements ConnectionInfo
{
    private static final String DATABASE_TAG = "Database";
    private static final String WOL_TAG = "WOL";

    private static final String TAG_DATABASE_HOST = "Host";
    private static final String TAG_DATABASE_PING = "Ping";
    private static final String TAG_DATABASE_PORT = "Port";
    private static final String TAG_DATABASE_USERNAME = "UserName";
    private static final String TAG_DATABASE_PASSWORD = "Password";
    private static final String TAG_DATABASE_NAME = "Name";
    private static final String TAG_DATABASE_TYPE = "Type";
    private static final String TAG_DATABASE_LOCALENABLED = "LocalEnabled";
    private static final String TAG_DATABASE_LOCALHOSTNAME = "LocalHostName";

    private static final String TAG_WOL_ENABLED = "Enabled";
    private static final String TAG_WOL_RECONNECT = "Reconnect";
    private static final String TAG_WOL_RETRY = "Retry";
    private static final String TAG_WOL_COMMAND = "Command";

    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

    private final Database database;
    private final Wol wol;

    private ConnectionInfo1_0(Database database, Wol wol)
    {
        this.database = database;
        this.wol = wol;
    }

    @Override
    public Database getDatabase()
    {
        return database;
    }

    @Override
    public Wol getWol()
    {
        return wol;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ConnectionInfo1_0 [database=");
        builder.append(database);
        builder.append(", wol=");
        builder.append(wol);
        builder.append("]");
        return builder.toString();
    }

    public static ConnectionInfo1_0 parse(String xml) throws ContentException
    {
        try
        {
            XMLEventReader parser = XML_INPUT_FACTORY.createXMLEventReader(new StringReader(xml));

            try
            {
                Database database = null;
                Wol wol = null;

                XMLEvent event = null;

                while (!(event = parser.nextEvent()).isEndDocument())
                {
                    if (!event.isStartElement())
                    {
                        continue;
                    }

                    if (DATABASE_TAG.equals(event.asStartElement()
                                                 .getName()
                                                 .getLocalPart()))
                    {
                        database = parseDatabase(parser);
                    }
                    else if (WOL_TAG.equals(event.asStartElement()
                                                 .getName()
                                                 .getLocalPart()))
                    {
                        wol = parseWol(parser);
                    }
                }

                return new ConnectionInfo1_0(database, wol);
            }
            finally
            {
                parser.close();
            }
        }
        catch (XMLStreamException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
    }

    private static Database parseDatabase(XMLEventReader parser) throws ContentException
    {
        String host = null;
        boolean ping = false;
        int port = 0;
        String userName = null;
        String password = null;
        String name = null;
        String type = null;
        boolean localEnabled = false;
        String localHostName = null;

        XMLEvent event = null;

        try
        {
            while (!(event = parser.nextEvent()).isEndDocument()
                   && !(event.isEndElement() && DATABASE_TAG.equals(event.asEndElement()
                                                                         .getName()
                                                                         .getLocalPart())))
            {
                if (!event.isStartElement())
                {
                    continue;
                }

                String tagName = event.asStartElement().getName().getLocalPart();
                String text = parser.getElementText();

                if (TAG_DATABASE_HOST.equals(tagName))
                {
                    host = text;
                }
                else if (TAG_DATABASE_PING.equals(tagName))
                {
                    ping = Boolean.parseBoolean(text);
                }
                else if (TAG_DATABASE_PORT.equals(tagName))
                {
                    port = Integer.parseInt(text);
                }
                else if (TAG_DATABASE_USERNAME.equals(tagName))
                {
                    userName = text;
                }
                else if (TAG_DATABASE_PASSWORD.equals(tagName))
                {
                    password = text;
                }
                else if (TAG_DATABASE_NAME.equals(tagName))
                {
                    name = text;
                }
                else if (TAG_DATABASE_TYPE.equals(tagName))
                {
                    type = text;
                }
                else if (TAG_DATABASE_LOCALENABLED.equals(tagName))
                {
                    localEnabled = Boolean.parseBoolean(text);
                }
                else if (TAG_DATABASE_LOCALHOSTNAME.equals(tagName))
                {
                    localHostName = text;
                }
                else
                {
                    throw new ContentException("Unknown tag: " + tagName);
                }
            }
        }
        catch (NumberFormatException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
        catch (XMLStreamException e)
        {
            throw new ContentException(e.getMessage(), e);
        }

        return new Database1_0(host,
                               ping,
                               port,
                               userName,
                               password,
                               name,
                               type,
                               localEnabled,
                               localHostName);
    }

    private static Wol parseWol(XMLEventReader parser) throws ContentException
    {
        boolean enabled = false;
        int reconnect = 0;
        int retry = 0;
        String command = null;

        XMLEvent event = null;

        try
        {
            while (!(event = parser.nextEvent()).isEndDocument()
                   && !(event.isEndElement() && WOL_TAG.equals(event.asEndElement()
                                                                    .getName()
                                                                    .getLocalPart())))
            {
                if (!event.isStartElement())
                {
                    continue;
                }

                String tagName = event.asStartElement().getName().getLocalPart();
                String text = parser.getElementText();

                if (TAG_WOL_ENABLED.equals(tagName))
                {
                    enabled = Boolean.parseBoolean(text);
                }
                else if (TAG_WOL_RECONNECT.equals(tagName))
                {
                    reconnect = Integer.parseInt(text);
                }
                else if (TAG_WOL_RETRY.equals(tagName))
                {
                    retry = Integer.parseInt(text);
                }
                else if (TAG_WOL_COMMAND.equals(tagName))
                {
                    command = text;
                }
                else
                {
                    throw new ContentException("Unknown tag: " + tagName);
                }
            }
        }
        catch (NumberFormatException e)
        {
            throw new ContentException(e.getMessage(), e);
        }
        catch (XMLStreamException e)
        {
            throw new ContentException(e.getMessage(), e);
        }

        return new Wol1_0(enabled, reconnect, retry, command);
    }
}
