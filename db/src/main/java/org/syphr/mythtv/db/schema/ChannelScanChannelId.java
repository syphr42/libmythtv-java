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

public interface ChannelScanChannelId extends Serializable
{
    public int getTransportid();

    public void setTransportid(int transportid);

    public int getScanid();

    public void setScanid(int scanid);

    public short getMplexId();

    public void setMplexId(short mplexId);

    public int getSourceId();

    public void setSourceId(int sourceId);

    public int getChannelId();

    public void setChannelId(int channelId);

    public String getCallsign();

    public void setCallsign(String callsign);

    public String getServiceName();

    public void setServiceName(String serviceName);

    public String getChanNum();

    public void setChanNum(String chanNum);

    public int getServiceId();

    public void setServiceId(int serviceId);

    public int getAtscMajorChannel();

    public void setAtscMajorChannel(int atscMajorChannel);

    public int getAtscMinorChannel();

    public void setAtscMinorChannel(int atscMinorChannel);

    public boolean isUseOnAirGuide();

    public void setUseOnAirGuide(boolean useOnAirGuide);

    public boolean isHidden();

    public void setHidden(boolean hidden);

    public boolean isHiddenInGuide();

    public void setHiddenInGuide(boolean hiddenInGuide);

    public String getFreqid();

    public void setFreqid(String freqid);

    public String getIcon();

    public void setIcon(String icon);

    public String getTvformat();

    public void setTvformat(String tvformat);

    public String getXmltvid();

    public void setXmltvid(String xmltvid);

    public int getPatTsid();

    public void setPatTsid(int patTsid);

    public int getVctTsid();

    public void setVctTsid(int vctTsid);

    public int getVctChanTsid();

    public void setVctChanTsid(int vctChanTsid);

    public int getSdtTsid();

    public void setSdtTsid(int sdtTsid);

    public int getOrigNetid();

    public void setOrigNetid(int origNetid);

    public int getNetid();

    public void setNetid(int netid);

    public String getSiStandard();

    public void setSiStandard(String siStandard);

    public boolean isInChannelsConf();

    public void setInChannelsConf(boolean inChannelsConf);

    public boolean isInPat();

    public void setInPat(boolean inPat);

    public boolean isInPmt();

    public void setInPmt(boolean inPmt);

    public boolean isInVct();

    public void setInVct(boolean inVct);

    public boolean isInNit();

    public void setInNit(boolean inNit);

    public boolean isInSdt();

    public void setInSdt(boolean inSdt);

    public boolean isIsEncrypted();

    public void setIsEncrypted(boolean isEncrypted);

    public boolean isIsDataService();

    public void setIsDataService(boolean isDataService);

    public boolean isIsAudioService();

    public void setIsAudioService(boolean isAudioService);

    public boolean isIsOpencable();

    public void setIsOpencable(boolean isOpencable);

    public boolean isCouldBeOpencable();

    public void setCouldBeOpencable(boolean couldBeOpencable);

    public short getDecryptionStatus();

    public void setDecryptionStatus(short decryptionStatus);

    public String getDefaultAuthority();

    public void setDefaultAuthority(String defaultAuthority);
}