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

public interface CaptureCard extends Serializable
{
    public Integer getCardid();

    public void setCardid(Integer cardid);

    public String getVideodevice();

    public void setVideodevice(String videodevice);

    public String getAudiodevice();

    public void setAudiodevice(String audiodevice);

    public String getVbidevice();

    public void setVbidevice(String vbidevice);

    public String getCardtype();

    public void setCardtype(String cardtype);

    public String getDefaultinput();

    public void setDefaultinput(String defaultinput);

    public Integer getAudioratelimit();

    public void setAudioratelimit(Integer audioratelimit);

    public String getHostname();

    public void setHostname(String hostname);

    public Integer getDvbSwfilter();

    public void setDvbSwfilter(Integer dvbSwfilter);

    public int getDvbSatType();

    public void setDvbSatType(int dvbSatType);

    public int getDvbWaitForSeqstart();

    public void setDvbWaitForSeqstart(int dvbWaitForSeqstart);

    public Boolean getSkipbtaudio();

    public void setSkipbtaudio(Boolean skipbtaudio);

    public byte getDvbOnDemand();

    public void setDvbOnDemand(byte dvbOnDemand);

    public Short getDvbDiseqcType();

    public void setDvbDiseqcType(Short dvbDiseqcType);

    public int getFirewireSpeed();

    public void setFirewireSpeed(int firewireSpeed);

    public String getFirewireModel();

    public void setFirewireModel(String firewireModel);

    public int getFirewireConnection();

    public void setFirewireConnection(int firewireConnection);

    public int getSignalTimeout();

    public void setSignalTimeout(int signalTimeout);

    public int getChannelTimeout();

    public void setChannelTimeout(int channelTimeout);

    public int getDvbTuningDelay();

    public void setDvbTuningDelay(int dvbTuningDelay);

    public int getContrast();

    public void setContrast(int contrast);

    public int getBrightness();

    public void setBrightness(int brightness);

    public int getColour();

    public void setColour(int colour);

    public int getHue();

    public void setHue(int hue);

    public Integer getDiseqcid();

    public void setDiseqcid(Integer diseqcid);

    public boolean isDvbEitscan();

    public void setDvbEitscan(boolean dvbEitscan);
}