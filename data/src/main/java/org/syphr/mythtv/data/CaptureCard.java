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

public class CaptureCard
{
    private Long cardId;
    private String videoDevice;
    private String audioDevice;
    private String vbiDevice;
    private String cardType;
    private Long audioRateLimit;
    private String hostName;
    private Long dvbswFilter;
    private Long dvbSatType;
    private Boolean dvbWaitForSeqStart;
    private Boolean skipBTAudio;
    private Boolean dvbOnDemand;
    private Long dvbDiSEqCType;
    private Long firewireSpeed;
    private String firewireModel;
    private Long firewireConnection;
    private Long signalTimeout;
    private Long channelTimeout;
    private Long dvbTuningDelay;
    private Long contrast;
    private Long brightness;
    private Long colour;
    private Long hue;
    private Long diSEqCId;
    private Boolean dvbeitScan;

    public Long getCardId()
    {
        return cardId;
    }

    public void setCardId(Long cardId)
    {
        this.cardId = cardId;
    }

    public String getVideoDevice()
    {
        return videoDevice;
    }

    public void setVideoDevice(String videoDevice)
    {
        this.videoDevice = videoDevice;
    }

    public String getAudioDevice()
    {
        return audioDevice;
    }

    public void setAudioDevice(String audioDevice)
    {
        this.audioDevice = audioDevice;
    }

    public String getVbiDevice()
    {
        return vbiDevice;
    }

    public void setVbiDevice(String vbiDevice)
    {
        this.vbiDevice = vbiDevice;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public Long getAudioRateLimit()
    {
        return audioRateLimit;
    }

    public void setAudioRateLimit(Long audioRateLimit)
    {
        this.audioRateLimit = audioRateLimit;
    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public Long getDvbswFilter()
    {
        return dvbswFilter;
    }

    public void setDvbswFilter(Long dvbswFilter)
    {
        this.dvbswFilter = dvbswFilter;
    }

    public Long getDvbSatType()
    {
        return dvbSatType;
    }

    public void setDvbSatType(Long dvbSatType)
    {
        this.dvbSatType = dvbSatType;
    }

    public Boolean getDvbWaitForSeqStart()
    {
        return dvbWaitForSeqStart;
    }

    public void setDvbWaitForSeqStart(Boolean dvbWaitForSeqStart)
    {
        this.dvbWaitForSeqStart = dvbWaitForSeqStart;
    }

    public Boolean getSkipBTAudio()
    {
        return skipBTAudio;
    }

    public void setSkipBTAudio(Boolean skipBTAudio)
    {
        this.skipBTAudio = skipBTAudio;
    }

    public Boolean getDvbOnDemand()
    {
        return dvbOnDemand;
    }

    public void setDvbOnDemand(Boolean dvbOnDemand)
    {
        this.dvbOnDemand = dvbOnDemand;
    }

    public Long getDvbDiSEqCType()
    {
        return dvbDiSEqCType;
    }

    public void setDvbDiSEqCType(Long dvbDiSEqCType)
    {
        this.dvbDiSEqCType = dvbDiSEqCType;
    }

    public Long getFirewireSpeed()
    {
        return firewireSpeed;
    }

    public void setFirewireSpeed(Long firewireSpeed)
    {
        this.firewireSpeed = firewireSpeed;
    }

    public String getFirewireModel()
    {
        return firewireModel;
    }

    public void setFirewireModel(String firewireModel)
    {
        this.firewireModel = firewireModel;
    }

    public Long getFirewireConnection()
    {
        return firewireConnection;
    }

    public void setFirewireConnection(Long firewireConnection)
    {
        this.firewireConnection = firewireConnection;
    }

    public Long getSignalTimeout()
    {
        return signalTimeout;
    }

    public void setSignalTimeout(Long signalTimeout)
    {
        this.signalTimeout = signalTimeout;
    }

    public Long getChannelTimeout()
    {
        return channelTimeout;
    }

    public void setChannelTimeout(Long channelTimeout)
    {
        this.channelTimeout = channelTimeout;
    }

    public Long getDvbTuningDelay()
    {
        return dvbTuningDelay;
    }

    public void setDvbTuningDelay(Long dvbTuningDelay)
    {
        this.dvbTuningDelay = dvbTuningDelay;
    }

    public Long getContrast()
    {
        return contrast;
    }

    public void setContrast(Long contrast)
    {
        this.contrast = contrast;
    }

    public Long getBrightness()
    {
        return brightness;
    }

    public void setBrightness(Long brightness)
    {
        this.brightness = brightness;
    }

    public Long getColour()
    {
        return colour;
    }

    public void setColour(Long colour)
    {
        this.colour = colour;
    }

    public Long getHue()
    {
        return hue;
    }

    public void setHue(Long hue)
    {
        this.hue = hue;
    }

    public Long getDiSEqCId()
    {
        return diSEqCId;
    }

    public void setDiSEqCId(Long diSEqCId)
    {
        this.diSEqCId = diSEqCId;
    }

    public Boolean getDvbeitScan()
    {
        return dvbeitScan;
    }

    public void setDvbeitScan(Boolean dvbeitScan)
    {
        this.dvbeitScan = dvbeitScan;
    }
}
