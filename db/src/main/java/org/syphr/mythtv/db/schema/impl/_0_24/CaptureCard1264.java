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

import org.syphr.mythtv.db.schema.CaptureCard;

@Entity
@Table(name = "capturecard")
public class CaptureCard1264 implements CaptureCard
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cardid", unique = true, nullable = false)
    private Integer cardid;

    @Column(name = "videodevice", length = 128)
    private String videodevice;

    @Column(name = "audiodevice", length = 128)
    private String audiodevice;

    @Column(name = "vbidevice", length = 128)
    private String vbidevice;

    @Column(name = "cardtype", length = 32)
    private String cardtype;

    @Column(name = "defaultinput", length = 32)
    private String defaultinput;

    @Column(name = "audioratelimit")
    private Integer audioratelimit;

    @Column(name = "hostname", length = 64)
    private String hostname;

    @Column(name = "dvb_swfilter")
    private Integer dvbSwfilter;

    @Column(name = "dvb_sat_type", nullable = false)
    private int dvbSatType;

    @Column(name = "dvb_wait_for_seqstart", nullable = false)
    private int dvbWaitForSeqstart;

    @Column(name = "skipbtaudio")
    private Boolean skipbtaudio;

    @Column(name = "dvb_on_demand", nullable = false)
    private byte dvbOnDemand;

    @Column(name = "dvb_diseqc_type")
    private Short dvbDiseqcType;

    @Column(name = "firewire_speed", nullable = false)
    private int firewireSpeed;

    @Column(name = "firewire_model", length = 32)
    private String firewireModel;

    @Column(name = "firewire_connection", nullable = false)
    private int firewireConnection;

    @Column(name = "signal_timeout", nullable = false)
    private int signalTimeout;

    @Column(name = "channel_timeout", nullable = false)
    private int channelTimeout;

    @Column(name = "dvb_tuning_delay", nullable = false)
    private int dvbTuningDelay;

    @Column(name = "contrast", nullable = false)
    private int contrast;

    @Column(name = "brightness", nullable = false)
    private int brightness;

    @Column(name = "colour", nullable = false)
    private int colour;

    @Column(name = "hue", nullable = false)
    private int hue;

    @Column(name = "diseqcid")
    private Integer diseqcid;

    @Column(name = "dvb_eitscan", nullable = false)
    private boolean dvbEitscan;

    @Override
    public Integer getCardid()
    {
        return this.cardid;
    }

    @Override
    public void setCardid(Integer cardid)
    {
        this.cardid = cardid;
    }

    @Override
    public String getVideodevice()
    {
        return this.videodevice;
    }

    @Override
    public void setVideodevice(String videodevice)
    {
        this.videodevice = videodevice;
    }

    @Override
    public String getAudiodevice()
    {
        return this.audiodevice;
    }

    @Override
    public void setAudiodevice(String audiodevice)
    {
        this.audiodevice = audiodevice;
    }

    @Override
    public String getVbidevice()
    {
        return this.vbidevice;
    }

    @Override
    public void setVbidevice(String vbidevice)
    {
        this.vbidevice = vbidevice;
    }

    @Override
    public String getCardtype()
    {
        return this.cardtype;
    }

    @Override
    public void setCardtype(String cardtype)
    {
        this.cardtype = cardtype;
    }

    @Override
    public String getDefaultinput()
    {
        return this.defaultinput;
    }

    @Override
    public void setDefaultinput(String defaultinput)
    {
        this.defaultinput = defaultinput;
    }

    @Override
    public Integer getAudioratelimit()
    {
        return this.audioratelimit;
    }

    @Override
    public void setAudioratelimit(Integer audioratelimit)
    {
        this.audioratelimit = audioratelimit;
    }

    @Override
    public String getHostname()
    {
        return this.hostname;
    }

    @Override
    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    @Override
    public Integer getDvbSwfilter()
    {
        return this.dvbSwfilter;
    }

    @Override
    public void setDvbSwfilter(Integer dvbSwfilter)
    {
        this.dvbSwfilter = dvbSwfilter;
    }

    @Override
    public int getDvbSatType()
    {
        return this.dvbSatType;
    }

    @Override
    public void setDvbSatType(int dvbSatType)
    {
        this.dvbSatType = dvbSatType;
    }

    @Override
    public int getDvbWaitForSeqstart()
    {
        return this.dvbWaitForSeqstart;
    }

    @Override
    public void setDvbWaitForSeqstart(int dvbWaitForSeqstart)
    {
        this.dvbWaitForSeqstart = dvbWaitForSeqstart;
    }

    @Override
    public Boolean getSkipbtaudio()
    {
        return this.skipbtaudio;
    }

    @Override
    public void setSkipbtaudio(Boolean skipbtaudio)
    {
        this.skipbtaudio = skipbtaudio;
    }

    @Override
    public byte getDvbOnDemand()
    {
        return this.dvbOnDemand;
    }

    @Override
    public void setDvbOnDemand(byte dvbOnDemand)
    {
        this.dvbOnDemand = dvbOnDemand;
    }

    @Override
    public Short getDvbDiseqcType()
    {
        return this.dvbDiseqcType;
    }

    @Override
    public void setDvbDiseqcType(Short dvbDiseqcType)
    {
        this.dvbDiseqcType = dvbDiseqcType;
    }

    @Override
    public int getFirewireSpeed()
    {
        return this.firewireSpeed;
    }

    @Override
    public void setFirewireSpeed(int firewireSpeed)
    {
        this.firewireSpeed = firewireSpeed;
    }

    @Override
    public String getFirewireModel()
    {
        return this.firewireModel;
    }

    @Override
    public void setFirewireModel(String firewireModel)
    {
        this.firewireModel = firewireModel;
    }

    @Override
    public int getFirewireConnection()
    {
        return this.firewireConnection;
    }

    @Override
    public void setFirewireConnection(int firewireConnection)
    {
        this.firewireConnection = firewireConnection;
    }

    @Override
    public int getSignalTimeout()
    {
        return this.signalTimeout;
    }

    @Override
    public void setSignalTimeout(int signalTimeout)
    {
        this.signalTimeout = signalTimeout;
    }

    @Override
    public int getChannelTimeout()
    {
        return this.channelTimeout;
    }

    @Override
    public void setChannelTimeout(int channelTimeout)
    {
        this.channelTimeout = channelTimeout;
    }

    @Override
    public int getDvbTuningDelay()
    {
        return this.dvbTuningDelay;
    }

    @Override
    public void setDvbTuningDelay(int dvbTuningDelay)
    {
        this.dvbTuningDelay = dvbTuningDelay;
    }

    @Override
    public int getContrast()
    {
        return this.contrast;
    }

    @Override
    public void setContrast(int contrast)
    {
        this.contrast = contrast;
    }

    @Override
    public int getBrightness()
    {
        return this.brightness;
    }

    @Override
    public void setBrightness(int brightness)
    {
        this.brightness = brightness;
    }

    @Override
    public int getColour()
    {
        return this.colour;
    }

    @Override
    public void setColour(int colour)
    {
        this.colour = colour;
    }

    @Override
    public int getHue()
    {
        return this.hue;
    }

    @Override
    public void setHue(int hue)
    {
        this.hue = hue;
    }

    @Override
    public Integer getDiseqcid()
    {
        return this.diseqcid;
    }

    @Override
    public void setDiseqcid(Integer diseqcid)
    {
        this.diseqcid = diseqcid;
    }

    @Override
    public boolean isDvbEitscan()
    {
        return this.dvbEitscan;
    }

    @Override
    public void setDvbEitscan(boolean dvbEitscan)
    {
        this.dvbEitscan = dvbEitscan;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Capturecard1264 [cardid=");
        builder.append(cardid);
        builder.append(", videodevice=");
        builder.append(videodevice);
        builder.append(", audiodevice=");
        builder.append(audiodevice);
        builder.append(", vbidevice=");
        builder.append(vbidevice);
        builder.append(", cardtype=");
        builder.append(cardtype);
        builder.append(", defaultinput=");
        builder.append(defaultinput);
        builder.append(", audioratelimit=");
        builder.append(audioratelimit);
        builder.append(", hostname=");
        builder.append(hostname);
        builder.append(", dvbSwfilter=");
        builder.append(dvbSwfilter);
        builder.append(", dvbSatType=");
        builder.append(dvbSatType);
        builder.append(", dvbWaitForSeqstart=");
        builder.append(dvbWaitForSeqstart);
        builder.append(", skipbtaudio=");
        builder.append(skipbtaudio);
        builder.append(", dvbOnDemand=");
        builder.append(dvbOnDemand);
        builder.append(", dvbDiseqcType=");
        builder.append(dvbDiseqcType);
        builder.append(", firewireSpeed=");
        builder.append(firewireSpeed);
        builder.append(", firewireModel=");
        builder.append(firewireModel);
        builder.append(", firewireConnection=");
        builder.append(firewireConnection);
        builder.append(", signalTimeout=");
        builder.append(signalTimeout);
        builder.append(", channelTimeout=");
        builder.append(channelTimeout);
        builder.append(", dvbTuningDelay=");
        builder.append(dvbTuningDelay);
        builder.append(", contrast=");
        builder.append(contrast);
        builder.append(", brightness=");
        builder.append(brightness);
        builder.append(", colour=");
        builder.append(colour);
        builder.append(", hue=");
        builder.append(hue);
        builder.append(", diseqcid=");
        builder.append(diseqcid);
        builder.append(", dvbEitscan=");
        builder.append(dvbEitscan);
        builder.append("]");
        return builder.toString();
    }
}
