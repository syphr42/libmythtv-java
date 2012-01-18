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

public interface CardInput extends Serializable
{
    public Integer getCardinputid();

    public void setCardinputid(Integer cardinputid);

    public int getCardid();

    public void setCardid(int cardid);

    public int getSourceid();

    public void setSourceid(int sourceid);

    public String getInputname();

    public void setInputname(String inputname);

    public String getExternalcommand();

    public void setExternalcommand(String externalcommand);

    public String getTunechan();

    public void setTunechan(String tunechan);

    public String getStartchan();

    public void setStartchan(String startchan);

    public String getDisplayname();

    public void setDisplayname(String displayname);

    public boolean isDishnetEit();

    public void setDishnetEit(boolean dishnetEit);

    public int getRecpriority();

    public void setRecpriority(int recpriority);

    public byte getQuicktune();

    public void setQuicktune(byte quicktune);

    public int getSchedorder();

    public void setSchedorder(int schedorder);

    public int getLivetvorder();

    public void setLivetvorder(int livetvorder);
}