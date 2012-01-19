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
package org.syphr.mythtv.db.schema.impl._0_24;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.syphr.mythtv.db.schema.ChannelScanDtvMultiplex;

@Entity
@Table(name = "channelscan_dtv_multiplex")
public class ChannelScanDtvMultiplex1264 implements ChannelScanDtvMultiplex
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "transportid", unique = true, nullable = false)
    private Integer transportid;

    @Column(name = "scanid", nullable = false)
    private int scanid;

    @Column(name = "mplexid", nullable = false)
    private short mplexid;

    @Column(name = "frequency", nullable = false)
    private long frequency;

    @Column(name = "inversion", nullable = false, length = 1)
    private char inversion;

    @Column(name = "symbolrate", nullable = false)
    private long symbolrate;

    @Column(name = "fec", nullable = false, length = 10)
    private String fec;

    @Column(name = "polarity", nullable = false, length = 1)
    private char polarity;

    @Column(name = "hp_code_rate", nullable = false, length = 10)
    private String hpCodeRate;

    @Column(name = "mod_sys", length = 10)
    private String modSys;

    @Column(name = "rolloff", length = 4)
    private String rolloff;

    @Column(name = "lp_code_rate", nullable = false, length = 10)
    private String lpCodeRate;

    @Column(name = "modulation", nullable = false, length = 10)
    private String modulation;

    @Column(name = "transmission_mode", nullable = false, length = 1)
    private char transmissionMode;

    @Column(name = "guard_interval", nullable = false, length = 10)
    private String guardInterval;

    @Column(name = "hierarchy", nullable = false, length = 10)
    private String hierarchy;

    @Column(name = "bandwidth", nullable = false, length = 1)
    private char bandwidth;

    @Column(name = "sistandard", nullable = false, length = 10)
    private String sistandard;

    @Column(name = "tuner_type", nullable = false)
    private short tunerType;

    @Column(name = "default_authority", nullable = false, length = 32)
    private String defaultAuthority;

    @Override
    public Integer getTransportid()
    {
        return this.transportid;
    }

    @Override
    public void setTransportid(Integer transportid)
    {
        this.transportid = transportid;
    }

    @Override
    public int getScanid()
    {
        return this.scanid;
    }

    @Override
    public void setScanid(int scanid)
    {
        this.scanid = scanid;
    }

    @Override
    public short getMplexid()
    {
        return this.mplexid;
    }

    @Override
    public void setMplexid(short mplexid)
    {
        this.mplexid = mplexid;
    }

    @Override
    public long getFrequency()
    {
        return this.frequency;
    }

    @Override
    public void setFrequency(long frequency)
    {
        this.frequency = frequency;
    }

    @Override
    public char getInversion()
    {
        return this.inversion;
    }

    @Override
    public void setInversion(char inversion)
    {
        this.inversion = inversion;
    }

    @Override
    public long getSymbolrate()
    {
        return this.symbolrate;
    }

    @Override
    public void setSymbolrate(long symbolrate)
    {
        this.symbolrate = symbolrate;
    }

    @Override
    public String getFec()
    {
        return this.fec;
    }

    @Override
    public void setFec(String fec)
    {
        this.fec = fec;
    }

    @Override
    public char getPolarity()
    {
        return this.polarity;
    }

    @Override
    public void setPolarity(char polarity)
    {
        this.polarity = polarity;
    }

    @Override
    public String getHpCodeRate()
    {
        return this.hpCodeRate;
    }

    @Override
    public void setHpCodeRate(String hpCodeRate)
    {
        this.hpCodeRate = hpCodeRate;
    }

    @Override
    public String getModSys()
    {
        return this.modSys;
    }

    @Override
    public void setModSys(String modSys)
    {
        this.modSys = modSys;
    }

    @Override
    public String getRolloff()
    {
        return this.rolloff;
    }

    @Override
    public void setRolloff(String rolloff)
    {
        this.rolloff = rolloff;
    }

    @Override
    public String getLpCodeRate()
    {
        return this.lpCodeRate;
    }

    @Override
    public void setLpCodeRate(String lpCodeRate)
    {
        this.lpCodeRate = lpCodeRate;
    }

    @Override
    public String getModulation()
    {
        return this.modulation;
    }

    @Override
    public void setModulation(String modulation)
    {
        this.modulation = modulation;
    }

    @Override
    public char getTransmissionMode()
    {
        return this.transmissionMode;
    }

    @Override
    public void setTransmissionMode(char transmissionMode)
    {
        this.transmissionMode = transmissionMode;
    }

    @Override
    public String getGuardInterval()
    {
        return this.guardInterval;
    }

    @Override
    public void setGuardInterval(String guardInterval)
    {
        this.guardInterval = guardInterval;
    }

    @Override
    public String getHierarchy()
    {
        return this.hierarchy;
    }

    @Override
    public void setHierarchy(String hierarchy)
    {
        this.hierarchy = hierarchy;
    }

    @Override
    public char getBandwidth()
    {
        return this.bandwidth;
    }

    @Override
    public void setBandwidth(char bandwidth)
    {
        this.bandwidth = bandwidth;
    }

    @Override
    public String getSistandard()
    {
        return this.sistandard;
    }

    @Override
    public void setSistandard(String sistandard)
    {
        this.sistandard = sistandard;
    }

    @Override
    public short getTunerType()
    {
        return this.tunerType;
    }

    @Override
    public void setTunerType(short tunerType)
    {
        this.tunerType = tunerType;
    }

    @Override
    public String getDefaultAuthority()
    {
        return this.defaultAuthority;
    }

    @Override
    public void setDefaultAuthority(String defaultAuthority)
    {
        this.defaultAuthority = defaultAuthority;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelScanDtvMultiplex1264 [transportid=");
        builder.append(transportid);
        builder.append(", scanid=");
        builder.append(scanid);
        builder.append(", mplexid=");
        builder.append(mplexid);
        builder.append(", frequency=");
        builder.append(frequency);
        builder.append(", inversion=");
        builder.append(inversion);
        builder.append(", symbolrate=");
        builder.append(symbolrate);
        builder.append(", fec=");
        builder.append(fec);
        builder.append(", polarity=");
        builder.append(polarity);
        builder.append(", hpCodeRate=");
        builder.append(hpCodeRate);
        builder.append(", modSys=");
        builder.append(modSys);
        builder.append(", rolloff=");
        builder.append(rolloff);
        builder.append(", lpCodeRate=");
        builder.append(lpCodeRate);
        builder.append(", modulation=");
        builder.append(modulation);
        builder.append(", transmissionMode=");
        builder.append(transmissionMode);
        builder.append(", guardInterval=");
        builder.append(guardInterval);
        builder.append(", hierarchy=");
        builder.append(hierarchy);
        builder.append(", bandwidth=");
        builder.append(bandwidth);
        builder.append(", sistandard=");
        builder.append(sistandard);
        builder.append(", tunerType=");
        builder.append(tunerType);
        builder.append(", defaultAuthority=");
        builder.append(defaultAuthority);
        builder.append("]");
        return builder.toString();
    }
}
