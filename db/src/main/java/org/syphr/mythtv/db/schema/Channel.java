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
package org.syphr.mythtv.db.schema;

import java.io.Serializable;
import java.util.Date;

public interface Channel extends Serializable
{
    public int getChanid();

    public void setChanid(int chanid);

    public String getChannum();

    public void setChannum(String channum);

    public String getFreqid();

    public void setFreqid(String freqid);

    public Integer getSourceid();

    public void setSourceid(Integer sourceid);

    public String getCallsign();

    public void setCallsign(String callsign);

    public String getName();

    public void setName(String name);

    public String getIcon();

    public void setIcon(String icon);

    public Integer getFinetune();

    public void setFinetune(Integer finetune);

    public String getVideofilters();

    public void setVideofilters(String videofilters);

    public String getXmltvid();

    public void setXmltvid(String xmltvid);

    public int getRecpriority();

    public void setRecpriority(int recpriority);

    public Integer getContrast();

    public void setContrast(Integer contrast);

    public Integer getBrightness();

    public void setBrightness(Integer brightness);

    public Integer getColour();

    public void setColour(Integer colour);

    public Integer getHue();

    public void setHue(Integer hue);

    public String getTvformat();

    public void setTvformat(String tvformat);

    public boolean isVisible();

    public void setVisible(boolean visible);

    public String getOutputfilters();

    public void setOutputfilters(String outputfilters);

    public Boolean getUseonairguide();

    public void setUseonairguide(Boolean useonairguide);

    public Short getMplexid();

    public void setMplexid(Short mplexid);

    public Integer getServiceid();

    public void setServiceid(Integer serviceid);

    public int getTmoffset();

    public void setTmoffset(int tmoffset);

    public int getAtscMajorChan();

    public void setAtscMajorChan(int atscMajorChan);

    public int getAtscMinorChan();

    public void setAtscMinorChan(int atscMinorChan);

    public Date getLastRecord();

    public void setLastRecord(Date lastRecord);

    public String getDefaultAuthority();

    public void setDefaultAuthority(String defaultAuthority);

    public int getCommmethod();

    public void setCommmethod(int commmethod);
}