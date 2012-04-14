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

import java.io.IOException;
import java.util.Date;

import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.protocol.ProtocolVersionException;
import org.syphr.mythtv.types.RecordingStatus;

public class Protocol73 extends Protocol72
{
    public Protocol73(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, ProtocolVersionException
    {
        new Command63MythProtoVersion(getTranslator(), getParser())
        {
            @Override
            protected String getVersion()
            {
                return "73";
            }

            @Override
            protected String getToken()
            {
                return "D7FE8D6F";
            }
        }.send(getSocketManager());
    }

    @Override
    public void rescheduleRecordings(int recorderId) throws IOException
    {
        handleUnsupported("deprecated reschedule recordings");

        try
        {
            if (recorderId == 0)
            {
                rescheduleRecordingsCheck(null, RecordingStatus.UNKNOWN, "Full reschedule");
            }
            else
            {
                if (recorderId == -1)
                {
                    recorderId = 0;
                }

                rescheduleRecordingsMatch(recorderId, 0, 0, null, "Full reschedule on recorder ID "
                        + recorderId);
            }
        }
        catch (CommandException e)
        {
            throw new IOException(e);
        }
    }

    @Override
    public void rescheduleRecordingsCheck(int recorderId,
                                          int findId,
                                          String title,
                                          String subtitle,
                                          String description,
                                          String programId,
                                          RecordingStatus recStatus,
                                          String reason) throws IOException, CommandException
    {
        new Command73RescheduleRecordingsCheck(getTranslator(),
                                               getParser(),
                                               recorderId,
                                               findId,
                                               title,
                                               subtitle,
                                               description,
                                               programId,
                                               recStatus,
                                               reason).send(getSocketManager());
    }

    @Override
    public void rescheduleRecordingsCheck(String title, RecordingStatus recStatus, String reason) throws IOException,
                                                                                                 CommandException
    {
        new Command73RescheduleRecordingsCheck(getTranslator(),
                                               getParser(),
                                               title,
                                               recStatus,
                                               reason).send(getSocketManager());
    }

    @Override
    public void rescheduleRecordingsMatch(int recorderId,
                                          int sourceId,
                                          int mplexId,
                                          Date maxStartTime,
                                          String reason) throws IOException, CommandException
    {
        new Command73RescheduleRecordingsMatch(getTranslator(),
                                               getParser(),
                                               recorderId,
                                               sourceId,
                                               mplexId,
                                               maxStartTime,
                                               reason).send(getSocketManager());
    }

    @Override
    public void rescheduleRecordingsPlace(String reason) throws IOException, CommandException
    {
        new Command73RescheduleRecordingsPlace(getTranslator(), getParser(), reason).send(getSocketManager());
    }
}
