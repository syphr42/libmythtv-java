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

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.translate.Translator;
import org.syphr.mythtv.types.RecordingStatus;

/* default */class Command73RescheduleRecordingsCheck extends
                                                      AbstractCommand73RescheduleRecordings
{
    private final int recorderId;
    private final int findId;
    private final String title;
    private final String subtitle;
    private final String description;
    private final String programId;
    private final RecordingStatus recStatus;

    public Command73RescheduleRecordingsCheck(Translator translator,
                                              Parser parser,
                                              String title,
                                              RecordingStatus recStatus,
                                              String reason)
    {
        this(translator, parser, 0, 0, title, null, null, "**any**", recStatus, reason);
    }

    public Command73RescheduleRecordingsCheck(Translator translator,
                                              Parser parser,
                                              int recorderId,
                                              int findId,
                                              String title,
                                              String subtitle,
                                              String description,
                                              String programId,
                                              RecordingStatus recStatus,
                                              String reason)
    {
        super(translator, parser, reason);

        this.recorderId = recorderId;
        this.findId = findId;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.programId = programId;
        this.recStatus = recStatus;
    }

    @Override
    protected String getSubCommand() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CHECK ");
        builder.append(getTranslator().toString(recStatus)).append(' ');
        builder.append(recorderId).append(' ');
        builder.append(findId).append(' ');
        builder.append(getReason());

        return getParser().combineArguments(builder.toString(),
                                            title,
                                            subtitle,
                                            description,
                                            programId);
    }
}
