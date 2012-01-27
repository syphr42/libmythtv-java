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

public interface RecordMatchId extends Serializable
{
    public Integer getRecordid();

    public void setRecordid(Integer recordid);

    public Integer getChanid();

    public void setChanid(Integer chanid);

    public Date getStarttime();

    public void setStarttime(Date starttime);

    public Integer getManualid();

    public void setManualid(Integer manualid);

    public Boolean getOldrecduplicate();

    public void setOldrecduplicate(Boolean oldrecduplicate);

    public Boolean getRecduplicate();

    public void setRecduplicate(Boolean recduplicate);

    public Boolean getFindduplicate();

    public void setFindduplicate(Boolean findduplicate);

    public Integer getOldrecstatus();

    public void setOldrecstatus(Integer oldrecstatus);

    public void setRecordid(int recordid);

    public void setChanid(int chanid);

    public void setManualid(int manualid);
}