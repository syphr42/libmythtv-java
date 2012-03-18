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
package org.syphr.mythtv.ws.backend.impl;

import java.util.Calendar;
import java.util.List;

import org.syphr.mythtv.ws.backend.impl._0_25.dvr.Encoder;
import org.syphr.mythtv.ws.backend.impl._0_25.dvr.Program;
import org.syphr.mythtv.ws.backend.impl._0_25.dvr.RecRule;

public class DvrService0_24 extends AbstractDvrService
{
    @Override
    public Integer addRecordSchedule(Integer chanId,
                                     Calendar startTime,
                                     Integer parentId,
                                     Boolean inactive,
                                     Long season,
                                     Long episode,
                                     String inetref,
                                     Integer findId,
                                     String type,
                                     String searchType,
                                     Integer recPriority,
                                     Long preferredInput,
                                     Integer startOffset,
                                     Integer endOffset,
                                     String dupMethod,
                                     String dupIn,
                                     Long filter,
                                     String recProfile,
                                     String recGroup,
                                     String storageGroup,
                                     String playGroup,
                                     Boolean autoExpire,
                                     Integer maxEpisodes,
                                     Boolean maxNewest,
                                     Boolean autoCommflag,
                                     Boolean autoTranscode,
                                     Boolean autoMetaLookup,
                                     Boolean autoUserJob1,
                                     Boolean autoUserJob2,
                                     Boolean autoUserJob3,
                                     Boolean autoUserJob4,
                                     Integer transcoder)
    {
        handleUnsupported("add record schedule");
        return null;
    }

    @Override
    public boolean disableRecordSchedule(Long recordId)
    {
        handleUnsupported("disable record schedule");
        return false;
    }

    @Override
    public boolean enableRecordSchedule(Long recordId)
    {
        handleUnsupported("enable record schedule");
        return false;
    }

    @Override
    public List<Program> getConflictList(Integer startIndex, Integer count)
    {
        handleUnsupported("get conflict list");
        return null;
    }

    @Override
    public List<Encoder> getEncoderList()
    {
        handleUnsupported("get encoder list");
        return null;
    }

    @Override
    public List<Program> getExpiringList(Integer startIndex, Integer count)
    {
        handleUnsupported("get expiring list");
        return null;
    }

    @Override
    public List<Program> getFilteredRecordedList(Boolean descending,
                                                 Integer startIndex,
                                                 Integer count,
                                                 String titleRegEx,
                                                 String recGroup,
                                                 String storageGroup)
    {
        handleUnsupported("get filtered recorded list");
        return null;
    }

    @Override
    public RecRule getRecordSchedule(Long recordId)
    {
        handleUnsupported("get record schedule");
        return null;
    }

    @Override
    public List<RecRule> getRecordScheduleList(Integer startIndex, Integer count)
    {
        handleUnsupported("get record schedule list");
        return null;
    }

    @Override
    public Program getRecorded(Integer chanId, Calendar startTime)
    {
        handleUnsupported("get recorded");
        return null;
    }

    @Override
    public List<Program> getRecordedList(Boolean descending, Integer startIndex, Integer count)
    {
        handleUnsupported("get recorded list");
        return null;
    }

    @Override
    public List<Program> getUpcomingList(Integer startIndex, Integer count, Boolean showAll)
    {
        handleUnsupported("get upcoming list");
        return null;
    }

    @Override
    public boolean removeRecordSchedule(Long recordId)
    {
        handleUnsupported("remove record schedule");
        return false;
    }

    @Override
    public boolean removeRecorded(Integer chanId, Calendar startTime)
    {
        handleUnsupported("remove recorded");
        return false;
    }

    @Override
    protected String getVersion()
    {
        return null;
    }
}