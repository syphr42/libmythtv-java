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

import java.util.Date;

public class VideoMultiplex
{
    private Integer mplexId;
    private Integer sourceId;
    private Integer transportId;
    private Integer networkId;
    private Long frequency;
    private String inversion;
    private Long symbolRate;
    private String fec;
    private String polarity;
    private String modulation;
    private String bandwidth;
    private String lpCodeRate;
    private String hpCodeRate;
    private String transmissionMode;
    private String guardInterval;
    private boolean visible;
    private String constellation;
    private String hierarchy;
    private String modulationSystem;
    private String rollOff;
    private String siStandard;
    private Integer serviceVersion;
    private Date updateTimeStamp;
    private String defaultAuthority;

    public Integer getMplexId()
    {
        return mplexId;
    }

    public void setMplexId(Integer mplexId)
    {
        this.mplexId = mplexId;
    }

    public Integer getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(Integer sourceId)
    {
        this.sourceId = sourceId;
    }

    public Integer getTransportId()
    {
        return transportId;
    }

    public void setTransportId(Integer transportId)
    {
        this.transportId = transportId;
    }

    public Integer getNetworkId()
    {
        return networkId;
    }

    public void setNetworkId(Integer networkId)
    {
        this.networkId = networkId;
    }

    public Long getFrequency()
    {
        return frequency;
    }

    public void setFrequency(Long frequency)
    {
        this.frequency = frequency;
    }

    public String getInversion()
    {
        return inversion;
    }

    public void setInversion(String inversion)
    {
        this.inversion = inversion;
    }

    public Long getSymbolRate()
    {
        return symbolRate;
    }

    public void setSymbolRate(Long symbolRate)
    {
        this.symbolRate = symbolRate;
    }

    public String getFec()
    {
        return fec;
    }

    public void setFec(String fec)
    {
        this.fec = fec;
    }

    public String getPolarity()
    {
        return polarity;
    }

    public void setPolarity(String polarity)
    {
        this.polarity = polarity;
    }

    public String getModulation()
    {
        return modulation;
    }

    public void setModulation(String modulation)
    {
        this.modulation = modulation;
    }

    public String getBandwidth()
    {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth)
    {
        this.bandwidth = bandwidth;
    }

    public String getLpCodeRate()
    {
        return lpCodeRate;
    }

    public void setLpCodeRate(String lpCodeRate)
    {
        this.lpCodeRate = lpCodeRate;
    }

    public String getHpCodeRate()
    {
        return hpCodeRate;
    }

    public void setHpCodeRate(String hpCodeRate)
    {
        this.hpCodeRate = hpCodeRate;
    }

    public String getTransmissionMode()
    {
        return transmissionMode;
    }

    public void setTransmissionMode(String transmissionMode)
    {
        this.transmissionMode = transmissionMode;
    }

    public String getGuardInterval()
    {
        return guardInterval;
    }

    public void setGuardInterval(String guardInterval)
    {
        this.guardInterval = guardInterval;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public String getConstellation()
    {
        return constellation;
    }

    public void setConstellation(String constellation)
    {
        this.constellation = constellation;
    }

    public String getHierarchy()
    {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy)
    {
        this.hierarchy = hierarchy;
    }

    public String getModulationSystem()
    {
        return modulationSystem;
    }

    public void setModulationSystem(String modulationSystem)
    {
        this.modulationSystem = modulationSystem;
    }

    public String getRollOff()
    {
        return rollOff;
    }

    public void setRollOff(String rollOff)
    {
        this.rollOff = rollOff;
    }

    public String getSiStandard()
    {
        return siStandard;
    }

    public void setSiStandard(String siStandard)
    {
        this.siStandard = siStandard;
    }

    public Integer getServiceVersion()
    {
        return serviceVersion;
    }

    public void setServiceVersion(Integer serviceVersion)
    {
        this.serviceVersion = serviceVersion;
    }

    public Date getUpdateTimeStamp()
    {
        return updateTimeStamp;
    }

    public void setUpdateTimeStamp(Date updateTimeStamp)
    {
        this.updateTimeStamp = updateTimeStamp;
    }

    public String getDefaultAuthority()
    {
        return defaultAuthority;
    }

    public void setDefaultAuthority(String defaultAuthority)
    {
        this.defaultAuthority = defaultAuthority;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("VideoMultiplex [mplexId=");
        builder.append(mplexId);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", transportId=");
        builder.append(transportId);
        builder.append(", networkId=");
        builder.append(networkId);
        builder.append(", frequency=");
        builder.append(frequency);
        builder.append(", inversion=");
        builder.append(inversion);
        builder.append(", symbolRate=");
        builder.append(symbolRate);
        builder.append(", fec=");
        builder.append(fec);
        builder.append(", polarity=");
        builder.append(polarity);
        builder.append(", modulation=");
        builder.append(modulation);
        builder.append(", bandwidth=");
        builder.append(bandwidth);
        builder.append(", lpCodeRate=");
        builder.append(lpCodeRate);
        builder.append(", hpCodeRate=");
        builder.append(hpCodeRate);
        builder.append(", transmissionMode=");
        builder.append(transmissionMode);
        builder.append(", guardInterval=");
        builder.append(guardInterval);
        builder.append(", visible=");
        builder.append(visible);
        builder.append(", constellation=");
        builder.append(constellation);
        builder.append(", hierarchy=");
        builder.append(hierarchy);
        builder.append(", modulationSystem=");
        builder.append(modulationSystem);
        builder.append(", rollOff=");
        builder.append(rollOff);
        builder.append(", siStandard=");
        builder.append(siStandard);
        builder.append(", serviceVersion=");
        builder.append(serviceVersion);
        builder.append(", updateTimeStamp=");
        builder.append(updateTimeStamp);
        builder.append(", defaultAuthority=");
        builder.append(defaultAuthority);
        builder.append("]");
        return builder.toString();
    }
}
