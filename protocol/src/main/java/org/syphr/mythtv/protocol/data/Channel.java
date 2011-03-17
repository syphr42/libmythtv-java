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
package org.syphr.mythtv.protocol.data;

public class Channel
{
    private final int id;

    private int sourceId;

    private String number;
    private String callsign;
    private String name;

    private String xmltvId;

    public Channel(int id)
    {
        this(id, -1, null, null, null);
    }

    public Channel(int id, int sourceId, String number, String callsign, String name)
    {
        this(id, sourceId, number, callsign, name, null);
    }

    public Channel(int id, int sourceId, String number, String callsign, String name, String xmltvId)
    {
        this.id = id;
        this.sourceId = sourceId;
        this.number = number;
        this.callsign = callsign;
        this.name = name;
        this.xmltvId = xmltvId;
    }

    public int getId()
    {
        return id;
    }

    public int getSourceId()
    {
        return sourceId;
    }

    public String getNumber()
    {
        return number;
    }

    public String getCallsign()
    {
        return callsign;
    }

    public String getName()
    {
        return name;
    }

    public String getXmltvId()
    {
        return xmltvId;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Channel [id=");
        builder.append(id);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", number=");
        builder.append(number);
        builder.append(", callsign=");
        builder.append(callsign);
        builder.append(", name=");
        builder.append(name);
        builder.append(", xmltvId=");
        builder.append(xmltvId);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + sourceId;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Channel))
        {
            return false;
        }
        Channel other = (Channel)obj;
        if (id != other.id)
        {
            return false;
        }
        if (sourceId != other.sourceId)
        {
            return false;
        }
        return true;
    }
}
