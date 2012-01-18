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

public interface ChannelScan extends Serializable
{
    public Integer getScanid();

    public void setScanid(Integer scanid);

    public int getCardid();

    public void setCardid(int cardid);

    public int getSourceid();

    public void setSourceid(int sourceid);

    public boolean isProcessed();

    public void setProcessed(boolean processed);

    public Date getScandate();

    public void setScandate(Date scandate);
}