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
package org.syphr.mythtv.protocol.impl;

import java.util.Date;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command73RescheduleRecordingsMatch extends
                                                      AbstractCommand73RescheduleRecordings
{
    private final int recorderId;
    private final int sourceId;
    private final int mplexId;
    private final Date maxStartTime;

    public Command73RescheduleRecordingsMatch(Translator translator,
                                              Parser parser,
                                              int recorderId,
                                              int sourceId,
                                              int mplexId,
                                              Date maxStartTime,
                                              String reason)
    {
        super(translator, parser, reason);

        this.recorderId = recorderId;
        this.sourceId = sourceId;
        this.mplexId = mplexId;
        this.maxStartTime = maxStartTime;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MATCH ");
        builder.append(recorderId).append(' ');
        builder.append(sourceId).append(' ');
        builder.append(mplexId).append(' ');

        /*
         * The string "-" will be sent here if the value is null, which is OK.
         * To not use the max start time restriction, any invalid date can be
         * sent.
         */
        builder.append(maxStartTime == null
                ? '-'
                : DateUtils.getIsoDateFormat().format(maxStartTime)).append(' ');

        builder.append(getReason());

        return builder.toString();
    }
}
