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
package org.syphr.mythtv.ws.backend;

import java.util.Calendar;
import java.util.List;

import org.syphr.mythtv.http.backend.impl._0_25.dvr.Encoder;
import org.syphr.mythtv.http.backend.impl._0_25.dvr.Program;
import org.syphr.mythtv.http.backend.impl._0_25.dvr.RecRule;

public interface DvrService
{
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
                                     Integer transcoder);

    public boolean disableRecordSchedule(Long recordId);

    public boolean enableRecordSchedule(Long recordId);

    public List<Program> getConflictList(Integer startIndex, Integer count);

    public List<Encoder> getEncoderList();

    public List<Program> getExpiringList(Integer startIndex, Integer count);

    public List<Program> getFilteredRecordedList(Boolean descending,
                                                 Integer startIndex,
                                                 Integer count,
                                                 String titleRegEx,
                                                 String recGroup,
                                                 String storageGroup);

    public RecRule getRecordSchedule(Long recordId);

    public List<RecRule> getRecordScheduleList(Integer startIndex, Integer count);

    public Program getRecorded(Integer chanId, Calendar startTime);

    public List<Program> getRecordedList(Boolean descending, Integer startIndex, Integer count);

    public List<Program> getUpcomingList(Integer startIndex, Integer count, Boolean showAll);

    public boolean removeRecordSchedule(Long recordId);

    public boolean removeRecorded(Integer chanId, Calendar startTime);
}