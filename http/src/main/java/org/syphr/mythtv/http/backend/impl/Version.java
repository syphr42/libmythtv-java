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
package org.syphr.mythtv.http.backend.impl;

import java.io.StringReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

public class Version
{
    private static final String TAG_STRING = "QString";
    private static final String TAG_VERSION = "version";

    private static final XMLInputFactory XML_INPUT_FACTORY = XMLInputFactory.newInstance();

    private final String value;

    private Version(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static Version parse(String xml) throws XMLStreamException
    {
        XMLEventReader parser = XML_INPUT_FACTORY.createXMLEventReader(new StringReader(xml));

        try
        {
            XMLEvent event = null;

            while (!(event = parser.nextEvent()).isEndDocument())
            {
                if ((event.isStartElement() && TAG_STRING.equals(event.asStartElement()
                                                                      .getName()
                                                                      .getLocalPart()))
                    || (event.isAttribute() && TAG_VERSION.equals(((Attribute) event).getName()
                                                                                     .getLocalPart())))
                {
                    return new Version(parser.getElementText());
                }
            }

            throw new IllegalArgumentException("Unknown version XML: " + xml);
        }
        finally
        {
            parser.close();
        }
    }
}
