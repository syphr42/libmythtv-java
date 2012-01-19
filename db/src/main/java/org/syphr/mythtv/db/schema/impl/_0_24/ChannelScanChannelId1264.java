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

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.syphr.mythtv.db.schema.ChannelScanChannelId;

@Embeddable
public class ChannelScanChannelId1264 implements ChannelScanChannelId
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "transportid", nullable = false)
    private int transportid;

    @Column(name = "scanid", nullable = false)
    private int scanid;

    @Column(name = "mplex_id", nullable = false)
    private short mplexId;

    @Column(name = "source_id", nullable = false)
    private int sourceId;

    @Column(name = "channel_id", nullable = false)
    private int channelId;

    @Column(name = "callsign", nullable = false, length = 20)
    private String callsign;

    @Column(name = "service_name", nullable = false, length = 64)
    private String serviceName;

    @Column(name = "chan_num", nullable = false, length = 10)
    private String chanNum;

    @Column(name = "service_id", nullable = false)
    private int serviceId;

    @Column(name = "atsc_major_channel", nullable = false)
    private int atscMajorChannel;

    @Column(name = "atsc_minor_channel", nullable = false)
    private int atscMinorChannel;

    @Column(name = "use_on_air_guide", nullable = false)
    private boolean useOnAirGuide;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    @Column(name = "hidden_in_guide", nullable = false)
    private boolean hiddenInGuide;

    @Column(name = "freqid", nullable = false, length = 10)
    private String freqid;

    @Column(name = "icon", nullable = false)
    private String icon;

    @Column(name = "tvformat", nullable = false, length = 10)
    private String tvformat;

    @Column(name = "xmltvid", nullable = false, length = 64)
    private String xmltvid;

    @Column(name = "pat_tsid", nullable = false)
    private int patTsid;

    @Column(name = "vct_tsid", nullable = false)
    private int vctTsid;

    @Column(name = "vct_chan_tsid", nullable = false)
    private int vctChanTsid;

    @Column(name = "sdt_tsid", nullable = false)
    private int sdtTsid;

    @Column(name = "orig_netid", nullable = false)
    private int origNetid;

    @Column(name = "netid", nullable = false)
    private int netid;

    @Column(name = "si_standard", nullable = false, length = 10)
    private String siStandard;

    @Column(name = "in_channels_conf", nullable = false)
    private boolean inChannelsConf;

    @Column(name = "in_pat", nullable = false)
    private boolean inPat;

    @Column(name = "in_pmt", nullable = false)
    private boolean inPmt;

    @Column(name = "in_vct", nullable = false)
    private boolean inVct;

    @Column(name = "in_nit", nullable = false)
    private boolean inNit;

    @Column(name = "in_sdt", nullable = false)
    private boolean inSdt;

    @Column(name = "is_encrypted", nullable = false)
    private boolean isEncrypted;

    @Column(name = "is_data_service", nullable = false)
    private boolean isDataService;

    @Column(name = "is_audio_service", nullable = false)
    private boolean isAudioService;

    @Column(name = "is_opencable", nullable = false)
    private boolean isOpencable;

    @Column(name = "could_be_opencable", nullable = false)
    private boolean couldBeOpencable;

    @Column(name = "decryption_status", nullable = false)
    private short decryptionStatus;

    @Column(name = "default_authority", nullable = false, length = 32)
    private String defaultAuthority;

    @Override
    public int getTransportid()
    {
        return this.transportid;
    }

    @Override
    public void setTransportid(int transportid)
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
    public short getMplexId()
    {
        return this.mplexId;
    }

    @Override
    public void setMplexId(short mplexId)
    {
        this.mplexId = mplexId;
    }

    @Override
    public int getSourceId()
    {
        return this.sourceId;
    }

    @Override
    public void setSourceId(int sourceId)
    {
        this.sourceId = sourceId;
    }

    @Override
    public int getChannelId()
    {
        return this.channelId;
    }

    @Override
    public void setChannelId(int channelId)
    {
        this.channelId = channelId;
    }

    @Override
    public String getCallsign()
    {
        return this.callsign;
    }

    @Override
    public void setCallsign(String callsign)
    {
        this.callsign = callsign;
    }

    @Override
    public String getServiceName()
    {
        return this.serviceName;
    }

    @Override
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    @Override
    public String getChanNum()
    {
        return this.chanNum;
    }

    @Override
    public void setChanNum(String chanNum)
    {
        this.chanNum = chanNum;
    }

    @Override
    public int getServiceId()
    {
        return this.serviceId;
    }

    @Override
    public void setServiceId(int serviceId)
    {
        this.serviceId = serviceId;
    }

    @Override
    public int getAtscMajorChannel()
    {
        return this.atscMajorChannel;
    }

    @Override
    public void setAtscMajorChannel(int atscMajorChannel)
    {
        this.atscMajorChannel = atscMajorChannel;
    }

    @Override
    public int getAtscMinorChannel()
    {
        return this.atscMinorChannel;
    }

    @Override
    public void setAtscMinorChannel(int atscMinorChannel)
    {
        this.atscMinorChannel = atscMinorChannel;
    }

    @Override
    public boolean isUseOnAirGuide()
    {
        return this.useOnAirGuide;
    }

    @Override
    public void setUseOnAirGuide(boolean useOnAirGuide)
    {
        this.useOnAirGuide = useOnAirGuide;
    }

    @Override
    public boolean isHidden()
    {
        return this.hidden;
    }

    @Override
    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }

    @Override
    public boolean isHiddenInGuide()
    {
        return this.hiddenInGuide;
    }

    @Override
    public void setHiddenInGuide(boolean hiddenInGuide)
    {
        this.hiddenInGuide = hiddenInGuide;
    }

    @Override
    public String getFreqid()
    {
        return this.freqid;
    }

    @Override
    public void setFreqid(String freqid)
    {
        this.freqid = freqid;
    }

    @Override
    public String getIcon()
    {
        return this.icon;
    }

    @Override
    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    @Override
    public String getTvformat()
    {
        return this.tvformat;
    }

    @Override
    public void setTvformat(String tvformat)
    {
        this.tvformat = tvformat;
    }

    @Override
    public String getXmltvid()
    {
        return this.xmltvid;
    }

    @Override
    public void setXmltvid(String xmltvid)
    {
        this.xmltvid = xmltvid;
    }

    @Override
    public int getPatTsid()
    {
        return this.patTsid;
    }

    @Override
    public void setPatTsid(int patTsid)
    {
        this.patTsid = patTsid;
    }

    @Override
    public int getVctTsid()
    {
        return this.vctTsid;
    }

    @Override
    public void setVctTsid(int vctTsid)
    {
        this.vctTsid = vctTsid;
    }

    @Override
    public int getVctChanTsid()
    {
        return this.vctChanTsid;
    }

    @Override
    public void setVctChanTsid(int vctChanTsid)
    {
        this.vctChanTsid = vctChanTsid;
    }

    @Override
    public int getSdtTsid()
    {
        return this.sdtTsid;
    }

    @Override
    public void setSdtTsid(int sdtTsid)
    {
        this.sdtTsid = sdtTsid;
    }

    @Override
    public int getOrigNetid()
    {
        return this.origNetid;
    }

    @Override
    public void setOrigNetid(int origNetid)
    {
        this.origNetid = origNetid;
    }

    @Override
    public int getNetid()
    {
        return this.netid;
    }

    @Override
    public void setNetid(int netid)
    {
        this.netid = netid;
    }

    @Override
    public String getSiStandard()
    {
        return this.siStandard;
    }

    @Override
    public void setSiStandard(String siStandard)
    {
        this.siStandard = siStandard;
    }

    @Override
    public boolean isInChannelsConf()
    {
        return this.inChannelsConf;
    }

    @Override
    public void setInChannelsConf(boolean inChannelsConf)
    {
        this.inChannelsConf = inChannelsConf;
    }

    @Override
    public boolean isInPat()
    {
        return this.inPat;
    }

    @Override
    public void setInPat(boolean inPat)
    {
        this.inPat = inPat;
    }

    @Override
    public boolean isInPmt()
    {
        return this.inPmt;
    }

    @Override
    public void setInPmt(boolean inPmt)
    {
        this.inPmt = inPmt;
    }

    @Override
    public boolean isInVct()
    {
        return this.inVct;
    }

    @Override
    public void setInVct(boolean inVct)
    {
        this.inVct = inVct;
    }

    @Override
    public boolean isInNit()
    {
        return this.inNit;
    }

    @Override
    public void setInNit(boolean inNit)
    {
        this.inNit = inNit;
    }

    @Override
    public boolean isInSdt()
    {
        return this.inSdt;
    }

    @Override
    public void setInSdt(boolean inSdt)
    {
        this.inSdt = inSdt;
    }

    @Override
    public boolean isIsEncrypted()
    {
        return this.isEncrypted;
    }

    @Override
    public void setIsEncrypted(boolean isEncrypted)
    {
        this.isEncrypted = isEncrypted;
    }

    @Override
    public boolean isIsDataService()
    {
        return this.isDataService;
    }

    @Override
    public void setIsDataService(boolean isDataService)
    {
        this.isDataService = isDataService;
    }

    @Override
    public boolean isIsAudioService()
    {
        return this.isAudioService;
    }

    @Override
    public void setIsAudioService(boolean isAudioService)
    {
        this.isAudioService = isAudioService;
    }

    @Override
    public boolean isIsOpencable()
    {
        return this.isOpencable;
    }

    @Override
    public void setIsOpencable(boolean isOpencable)
    {
        this.isOpencable = isOpencable;
    }

    @Override
    public boolean isCouldBeOpencable()
    {
        return this.couldBeOpencable;
    }

    @Override
    public void setCouldBeOpencable(boolean couldBeOpencable)
    {
        this.couldBeOpencable = couldBeOpencable;
    }

    @Override
    public short getDecryptionStatus()
    {
        return this.decryptionStatus;
    }

    @Override
    public void setDecryptionStatus(short decryptionStatus)
    {
        this.decryptionStatus = decryptionStatus;
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
    public boolean equals(Object other)
    {
        if ((this == other))
        {
            return true;
        }
        if ((other == null))
        {
            return false;
        }
        if (!(other instanceof ChannelScanChannelId1264))
        {
            return false;
        }
        ChannelScanChannelId castOther = (ChannelScanChannelId)other;

        return (this.getTransportid() == castOther.getTransportid())
                && (this.getScanid() == castOther.getScanid())
                && (this.getMplexId() == castOther.getMplexId())
                && (this.getSourceId() == castOther.getSourceId())
                && (this.getChannelId() == castOther.getChannelId())
                && ((this.getCallsign() == castOther.getCallsign()) || (this.getCallsign() != null
                        && castOther.getCallsign() != null && this.getCallsign().equals(castOther.getCallsign())))
                && ((this.getServiceName() == castOther.getServiceName()) || (this.getServiceName() != null
                        && castOther.getServiceName() != null && this.getServiceName().equals(castOther.getServiceName())))
                && ((this.getChanNum() == castOther.getChanNum()) || (this.getChanNum() != null
                        && castOther.getChanNum() != null && this.getChanNum().equals(castOther.getChanNum())))
                && (this.getServiceId() == castOther.getServiceId())
                && (this.getAtscMajorChannel() == castOther.getAtscMajorChannel())
                && (this.getAtscMinorChannel() == castOther.getAtscMinorChannel())
                && (this.isUseOnAirGuide() == castOther.isUseOnAirGuide())
                && (this.isHidden() == castOther.isHidden())
                && (this.isHiddenInGuide() == castOther.isHiddenInGuide())
                && ((this.getFreqid() == castOther.getFreqid()) || (this.getFreqid() != null
                        && castOther.getFreqid() != null && this.getFreqid().equals(castOther.getFreqid())))
                && ((this.getIcon() == castOther.getIcon()) || (this.getIcon() != null
                        && castOther.getIcon() != null && this.getIcon().equals(castOther.getIcon())))
                && ((this.getTvformat() == castOther.getTvformat()) || (this.getTvformat() != null
                        && castOther.getTvformat() != null && this.getTvformat().equals(castOther.getTvformat())))
                && ((this.getXmltvid() == castOther.getXmltvid()) || (this.getXmltvid() != null
                        && castOther.getXmltvid() != null && this.getXmltvid().equals(castOther.getXmltvid())))
                && (this.getPatTsid() == castOther.getPatTsid())
                && (this.getVctTsid() == castOther.getVctTsid())
                && (this.getVctChanTsid() == castOther.getVctChanTsid())
                && (this.getSdtTsid() == castOther.getSdtTsid())
                && (this.getOrigNetid() == castOther.getOrigNetid())
                && (this.getNetid() == castOther.getNetid())
                && ((this.getSiStandard() == castOther.getSiStandard()) || (this.getSiStandard() != null
                        && castOther.getSiStandard() != null && this.getSiStandard().equals(castOther.getSiStandard())))
                && (this.isInChannelsConf() == castOther.isInChannelsConf())
                && (this.isInPat() == castOther.isInPat())
                && (this.isInPmt() == castOther.isInPmt())
                && (this.isInVct() == castOther.isInVct())
                && (this.isInNit() == castOther.isInNit())
                && (this.isInSdt() == castOther.isInSdt())
                && (this.isIsEncrypted() == castOther.isIsEncrypted())
                && (this.isIsDataService() == castOther.isIsDataService())
                && (this.isIsAudioService() == castOther.isIsAudioService())
                && (this.isIsOpencable() == castOther.isIsOpencable())
                && (this.isCouldBeOpencable() == castOther.isCouldBeOpencable())
                && (this.getDecryptionStatus() == castOther.getDecryptionStatus())
                && ((this.getDefaultAuthority() == castOther.getDefaultAuthority()) || (this.getDefaultAuthority() != null
                        && castOther.getDefaultAuthority() != null && this.getDefaultAuthority().equals(castOther.getDefaultAuthority())));
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + this.getTransportid();
        result = 37 * result + this.getScanid();
        result = 37 * result + this.getMplexId();
        result = 37 * result + this.getSourceId();
        result = 37 * result + this.getChannelId();
        result = 37 * result + (getCallsign() == null ? 0 : this.getCallsign().hashCode());
        result = 37 * result + (getServiceName() == null ? 0 : this.getServiceName().hashCode());
        result = 37 * result + (getChanNum() == null ? 0 : this.getChanNum().hashCode());
        result = 37 * result + this.getServiceId();
        result = 37 * result + this.getAtscMajorChannel();
        result = 37 * result + this.getAtscMinorChannel();
        result = 37 * result + (this.isUseOnAirGuide() ? 1 : 0);
        result = 37 * result + (this.isHidden() ? 1 : 0);
        result = 37 * result + (this.isHiddenInGuide() ? 1 : 0);
        result = 37 * result + (getFreqid() == null ? 0 : this.getFreqid().hashCode());
        result = 37 * result + (getIcon() == null ? 0 : this.getIcon().hashCode());
        result = 37 * result + (getTvformat() == null ? 0 : this.getTvformat().hashCode());
        result = 37 * result + (getXmltvid() == null ? 0 : this.getXmltvid().hashCode());
        result = 37 * result + this.getPatTsid();
        result = 37 * result + this.getVctTsid();
        result = 37 * result + this.getVctChanTsid();
        result = 37 * result + this.getSdtTsid();
        result = 37 * result + this.getOrigNetid();
        result = 37 * result + this.getNetid();
        result = 37 * result + (getSiStandard() == null ? 0 : this.getSiStandard().hashCode());
        result = 37 * result + (this.isInChannelsConf() ? 1 : 0);
        result = 37 * result + (this.isInPat() ? 1 : 0);
        result = 37 * result + (this.isInPmt() ? 1 : 0);
        result = 37 * result + (this.isInVct() ? 1 : 0);
        result = 37 * result + (this.isInNit() ? 1 : 0);
        result = 37 * result + (this.isInSdt() ? 1 : 0);
        result = 37 * result + (this.isIsEncrypted() ? 1 : 0);
        result = 37 * result + (this.isIsDataService() ? 1 : 0);
        result = 37 * result + (this.isIsAudioService() ? 1 : 0);
        result = 37 * result + (this.isIsOpencable() ? 1 : 0);
        result = 37 * result + (this.isCouldBeOpencable() ? 1 : 0);
        result = 37 * result + this.getDecryptionStatus();
        result = 37
                * result
                + (getDefaultAuthority() == null ? 0 : this.getDefaultAuthority().hashCode());
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelScanChannelId1264 [transportid=");
        builder.append(transportid);
        builder.append(", scanid=");
        builder.append(scanid);
        builder.append(", mplexId=");
        builder.append(mplexId);
        builder.append(", sourceId=");
        builder.append(sourceId);
        builder.append(", channelId=");
        builder.append(channelId);
        builder.append(", callsign=");
        builder.append(callsign);
        builder.append(", serviceName=");
        builder.append(serviceName);
        builder.append(", chanNum=");
        builder.append(chanNum);
        builder.append(", serviceId=");
        builder.append(serviceId);
        builder.append(", atscMajorChannel=");
        builder.append(atscMajorChannel);
        builder.append(", atscMinorChannel=");
        builder.append(atscMinorChannel);
        builder.append(", useOnAirGuide=");
        builder.append(useOnAirGuide);
        builder.append(", hidden=");
        builder.append(hidden);
        builder.append(", hiddenInGuide=");
        builder.append(hiddenInGuide);
        builder.append(", freqid=");
        builder.append(freqid);
        builder.append(", icon=");
        builder.append(icon);
        builder.append(", tvformat=");
        builder.append(tvformat);
        builder.append(", xmltvid=");
        builder.append(xmltvid);
        builder.append(", patTsid=");
        builder.append(patTsid);
        builder.append(", vctTsid=");
        builder.append(vctTsid);
        builder.append(", vctChanTsid=");
        builder.append(vctChanTsid);
        builder.append(", sdtTsid=");
        builder.append(sdtTsid);
        builder.append(", origNetid=");
        builder.append(origNetid);
        builder.append(", netid=");
        builder.append(netid);
        builder.append(", siStandard=");
        builder.append(siStandard);
        builder.append(", inChannelsConf=");
        builder.append(inChannelsConf);
        builder.append(", inPat=");
        builder.append(inPat);
        builder.append(", inPmt=");
        builder.append(inPmt);
        builder.append(", inVct=");
        builder.append(inVct);
        builder.append(", inNit=");
        builder.append(inNit);
        builder.append(", inSdt=");
        builder.append(inSdt);
        builder.append(", isEncrypted=");
        builder.append(isEncrypted);
        builder.append(", isDataService=");
        builder.append(isDataService);
        builder.append(", isAudioService=");
        builder.append(isAudioService);
        builder.append(", isOpencable=");
        builder.append(isOpencable);
        builder.append(", couldBeOpencable=");
        builder.append(couldBeOpencable);
        builder.append(", decryptionStatus=");
        builder.append(decryptionStatus);
        builder.append(", defaultAuthority=");
        builder.append(defaultAuthority);
        builder.append("]");
        return builder.toString();
    }
}
