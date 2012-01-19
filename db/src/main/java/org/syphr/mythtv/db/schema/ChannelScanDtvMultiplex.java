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
package org.syphr.mythtv.db.schema;

import java.io.Serializable;

public interface ChannelScanDtvMultiplex extends Serializable
{
    public Integer getTransportid();

    public void setTransportid(Integer transportid);

    public int getScanid();

    public void setScanid(int scanid);

    public short getMplexid();

    public void setMplexid(short mplexid);

    public long getFrequency();

    public void setFrequency(long frequency);

    public char getInversion();

    public void setInversion(char inversion);

    public long getSymbolrate();

    public void setSymbolrate(long symbolrate);

    public String getFec();

    public void setFec(String fec);

    public char getPolarity();

    public void setPolarity(char polarity);

    public String getHpCodeRate();

    public void setHpCodeRate(String hpCodeRate);

    public String getModSys();

    public void setModSys(String modSys);

    public String getRolloff();

    public void setRolloff(String rolloff);

    public String getLpCodeRate();

    public void setLpCodeRate(String lpCodeRate);

    public String getModulation();

    public void setModulation(String modulation);

    public char getTransmissionMode();

    public void setTransmissionMode(char transmissionMode);

    public String getGuardInterval();

    public void setGuardInterval(String guardInterval);

    public String getHierarchy();

    public void setHierarchy(String hierarchy);

    public char getBandwidth();

    public void setBandwidth(char bandwidth);

    public String getSistandard();

    public void setSistandard(String sistandard);

    public short getTunerType();

    public void setTunerType(short tunerType);

    public String getDefaultAuthority();

    public void setDefaultAuthority(String defaultAuthority);
}