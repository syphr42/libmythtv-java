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
package org.syphr.mythtv.data;

public class Channel
{
    private long id = -1;
    private long sourceId = -1;
    private String number;
    private String callsign;
    private String name;
    private String xmltvId;
    private String iconPath;
    private Long mplexId;
    private Long transportId;
    private Long serviceId;
    private Long networkId;
    private Long atscMajorChan;
    private Long atscMinorChan;
    private String format;
    private String modulation;
    private Long frequency;
    private String frequencyId;
    private String frequencyTable;
    private Integer fineTune;
    private String siStandard;
    private String chanFilters;
    private Integer inputId;
    private Integer commFree;
    private boolean useEIT;
    private boolean visible;
    private String defaultAuth;

    public Channel()
    {
        super();
    }

    public Channel(long id)
    {
        this(id, null, null, null);
    }

    public Channel(long id, String number, String callsign, String iconPath)
    {
        this(id, -1, number, callsign, null, null, iconPath);
    }

    public Channel(long id, String callsign, String name)
    {
        this(id, -1, null, callsign, name);
    }

    public Channel(long id, long sourceId, String number, String callsign, String name)
    {
        this(id, sourceId, number, callsign, name, null);
    }

    public Channel(long id,
                   long sourceId,
                   String number,
                   String callsign,
                   String name,
                   String xmltvId)
    {
        this(id, sourceId, number, callsign, name, xmltvId, null);
    }

    public Channel(long id,
                   long sourceId,
                   String number,
                   String callsign,
                   String name,
                   String xmltvId,
                   String iconPath)
    {
        this.id = id;
        this.sourceId = sourceId;
        this.number = number;
        this.callsign = callsign;
        this.name = name;
        this.xmltvId = xmltvId;
        this.iconPath = iconPath;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(long sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getCallsign()
    {
        return callsign;
    }

    public void setCallsign(String callsign)
    {
        this.callsign = callsign;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getXmltvId()
    {
        return xmltvId;
    }

    public void setXmltvId(String xmltvId)
    {
        this.xmltvId = xmltvId;
    }

    public String getIconPath()
    {
        return iconPath;
    }

    public void setIconPath(String iconPath)
    {
        this.iconPath = iconPath;
    }

    public Long getMplexId()
    {
        return mplexId;
    }

    public void setMplexId(Long mplexId)
    {
        this.mplexId = mplexId;
    }

    public Long getTransportId()
    {
        return transportId;
    }

    public void setTransportId(Long transportId)
    {
        this.transportId = transportId;
    }

    public Long getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(Long serviceId)
    {
        this.serviceId = serviceId;
    }

    public Long getNetworkId()
    {
        return networkId;
    }

    public void setNetworkId(Long networkId)
    {
        this.networkId = networkId;
    }

    public Long getAtscMajorChan()
    {
        return atscMajorChan;
    }

    public void setAtscMajorChan(Long atscMajorChan)
    {
        this.atscMajorChan = atscMajorChan;
    }

    public Long getAtscMinorChan()
    {
        return atscMinorChan;
    }

    public void setAtscMinorChan(Long atscMinorChan)
    {
        this.atscMinorChan = atscMinorChan;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getModulation()
    {
        return modulation;
    }

    public void setModulation(String modulation)
    {
        this.modulation = modulation;
    }

    public Long getFrequency()
    {
        return frequency;
    }

    public void setFrequency(Long frequency)
    {
        this.frequency = frequency;
    }

    public String getFrequencyId()
    {
        return frequencyId;
    }

    public void setFrequencyId(String frequencyId)
    {
        this.frequencyId = frequencyId;
    }

    public String getFrequencyTable()
    {
        return frequencyTable;
    }

    public void setFrequencyTable(String frequencyTable)
    {
        this.frequencyTable = frequencyTable;
    }

    public Integer getFineTune()
    {
        return fineTune;
    }

    public void setFineTune(Integer fineTune)
    {
        this.fineTune = fineTune;
    }

    public String getSiStandard()
    {
        return siStandard;
    }

    public void setSiStandard(String siStandard)
    {
        this.siStandard = siStandard;
    }

    public String getChanFilters()
    {
        return chanFilters;
    }

    public void setChanFilters(String chanFilters)
    {
        this.chanFilters = chanFilters;
    }

    public Integer getInputId()
    {
        return inputId;
    }

    public void setInputId(Integer inputId)
    {
        this.inputId = inputId;
    }

    public Integer getCommFree()
    {
        return commFree;
    }

    public void setCommFree(Integer commFree)
    {
        this.commFree = commFree;
    }

    public boolean isUseEIT()
    {
        return useEIT;
    }

    public void setUseEIT(boolean useEIT)
    {
        this.useEIT = useEIT;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public String getDefaultAuth()
    {
        return defaultAuth;
    }

    public void setDefaultAuth(String defaultAuth)
    {
        this.defaultAuth = defaultAuth;
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
        builder.append(", iconPath=");
        builder.append(iconPath);
        builder.append(", mplexId=");
        builder.append(mplexId);
        builder.append(", transportId=");
        builder.append(transportId);
        builder.append(", serviceId=");
        builder.append(serviceId);
        builder.append(", networkId=");
        builder.append(networkId);
        builder.append(", atscMajorChan=");
        builder.append(atscMajorChan);
        builder.append(", atscMinorChan=");
        builder.append(atscMinorChan);
        builder.append(", format=");
        builder.append(format);
        builder.append(", modulation=");
        builder.append(modulation);
        builder.append(", frequency=");
        builder.append(frequency);
        builder.append(", frequencyId=");
        builder.append(frequencyId);
        builder.append(", frequencyTable=");
        builder.append(frequencyTable);
        builder.append(", fineTune=");
        builder.append(fineTune);
        builder.append(", siStandard=");
        builder.append(siStandard);
        builder.append(", chanFilters=");
        builder.append(chanFilters);
        builder.append(", inputId=");
        builder.append(inputId);
        builder.append(", commFree=");
        builder.append(commFree);
        builder.append(", useEIT=");
        builder.append(useEIT);
        builder.append(", visible=");
        builder.append(visible);
        builder.append(", defaultAuth=");
        builder.append(defaultAuth);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)(id ^ (id >>> 32));
        result = prime * result + (int)(sourceId ^ (sourceId >>> 32));
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
