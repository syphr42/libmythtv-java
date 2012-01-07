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
import java.util.Date;

public interface TvChain extends Serializable
{
    public TvChainId getId();

    public void setId(TvChainId id);

    public String getChainid();

    public void setChainid(String chainid);

    public int getChainpos();

    public void setChainpos(int chainpos);

    public boolean isDiscontinuity();

    public void setDiscontinuity(boolean discontinuity);

    public int getWatching();

    public void setWatching(int watching);

    public String getHostprefix();

    public void setHostprefix(String hostprefix);

    public String getCardtype();

    public void setCardtype(String cardtype);

    public String getInput();

    public void setInput(String input);

    public String getChanname();

    public void setChanname(String channame);

    public Date getEndtime();

    public void setEndtime(Date endtime);
}