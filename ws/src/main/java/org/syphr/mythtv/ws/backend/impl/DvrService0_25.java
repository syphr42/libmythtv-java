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

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.syphr.mythtv.ws.ServiceVersionException;
import org.syphr.mythtv.ws.backend.DvrService;
import org.syphr.mythtv.ws.backend.impl._0_25.dvr.Dvr;
import org.syphr.mythtv.ws.backend.impl._0_25.dvr.DvrServices;
import org.syphr.mythtv.ws.backend.impl._0_25.dvr.Encoder;
import org.syphr.mythtv.ws.backend.impl._0_25.dvr.Program;
import org.syphr.mythtv.ws.backend.impl._0_25.dvr.RecRule;
import org.syphr.mythtv.ws.impl.AbstractService;
import org.syphr.mythtv.ws.impl.ServiceUtils;

public class DvrService0_25 extends AbstractService implements DvrService
{
    private static final String NAME = "Dvr";

    private static final String VERSION = "1.4";

    private final Dvr service;

    public DvrService0_25(String host, int port) throws ServiceVersionException, IOException
    {
        DvrServices locator = new DvrServices();
        service = locator.getBasicHttpBindingDvr();

        configureAndVerify(host, port, (BindingProvider)service);
    }

    @Override
    protected String getName()
    {
        return NAME;
    }

    @Override
    protected String getVersion()
    {
        return VERSION;
    }

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
        return service.addRecordSchedule(chanId,
                                         startTime,
                                         parentId,
                                         inactive,
                                         season,
                                         episode,
                                         inetref,
                                         findId,
                                         type,
                                         searchType,
                                         recPriority,
                                         preferredInput,
                                         startOffset,
                                         endOffset,
                                         dupMethod,
                                         dupIn,
                                         filter,
                                         recProfile,
                                         recGroup,
                                         storageGroup,
                                         playGroup,
                                         autoExpire,
                                         maxEpisodes,
                                         maxNewest,
                                         autoCommflag,
                                         autoTranscode,
                                         autoMetaLookup,
                                         autoUserJob1,
                                         autoUserJob2,
                                         autoUserJob3,
                                         autoUserJob4,
                                         transcoder);
    }

    @Override
    public boolean disableRecordSchedule(Long recordId)
    {
        return ServiceUtils.toPrimitive(service.disableRecordSchedule(recordId));
    }

    @Override
    public boolean enableRecordSchedule(Long recordId)
    {
        return ServiceUtils.toPrimitive(service.enableRecordSchedule(recordId));
    }

    @Override
    public List<Program> getConflictList(Integer startIndex, Integer count)
    {
        // TODO
        return null;//service.getConflictList(startIndex, count);
    }

    @Override
    public List<Encoder> getEncoderList()
    {
        // TODO
        return null;//service.getEncoderList();
    }

    @Override
    public List<Program> getExpiringList(Integer startIndex, Integer count)
    {
        // TODO
        return null;//service.getExpiringList(startIndex, count);
    }

    @Override
    public List<Program> getFilteredRecordedList(Boolean descending,
                                                 Integer startIndex,
                                                 Integer count,
                                                 String titleRegEx,
                                                 String recGroup,
                                                 String storageGroup)
    {
        // TODO
        return null;/*
                     * service.getFilteredRecordedList(descending, startIndex,
                     * count, titleRegEx, recGroup, storageGroup);
                     */
    }

    @Override
    public RecRule getRecordSchedule(Long recordId)
    {
        return service.getRecordSchedule(recordId);
    }

    @Override
    public List<RecRule> getRecordScheduleList(Integer startIndex, Integer count)
    {
        // TODO
        return null;//service.getRecordScheduleList(startIndex, count);
    }

    @Override
    public Program getRecorded(Integer chanId, Calendar startTime)
    {
        return service.getRecorded(chanId, startTime);
    }

    @Override
    public List<Program> getRecordedList(Boolean descending, Integer startIndex, Integer count)
    {
        // TODO
        return null;//service.getRecordedList(descending, startIndex, count);
    }

    @Override
    public List<Program> getUpcomingList(Integer startIndex, Integer count, Boolean showAll)
    {
        // TODO
        return null;//service.getUpcomingList(startIndex, count, showAll);
    }

    @Override
    public boolean removeRecordSchedule(Long recordId)
    {
        return ServiceUtils.toPrimitive(service.removeRecordSchedule(recordId));
    }

    @Override
    public boolean removeRecorded(Integer chanId, Calendar startTime)
    {
        return ServiceUtils.toPrimitive(service.removeRecorded(chanId, startTime));
    }
}
