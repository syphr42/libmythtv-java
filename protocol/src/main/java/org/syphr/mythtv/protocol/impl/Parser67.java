/*
 * Copyright 2011 Gregory P. Moyer
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

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.types.RecordingDupIn;
import org.syphr.mythtv.types.RecordingDupMethod;
import org.syphr.mythtv.types.RecordingStatus;
import org.syphr.mythtv.types.RecordingType;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.Translator;

public class Parser67 extends Parser66
{
    public Parser67(Translator translator)
    {
        super(translator);
    }

    @Override
    protected int getProgramInfoArgsCount()
    {
        return 44;
    }

    @Override
    protected Program parseProgramInfo(List<String> args, DateFormat airDateFormat) throws ProtocolException
    {
        try
        {
            int i = 0;

            String title = args.get(i++);
            String subtitle = args.get(i++);
            String description = args.get(i++);
            int season = Integer.parseInt(args.get(i++));
            int episode = Integer.parseInt(args.get(i++));
            String category = args.get(i++);
            int chanId = Integer.parseInt(args.get(i++));
            String chanNum = args.get(i++);
            String callsign = args.get(i++);
            String chanName = args.get(i++);
            URI filename = URI.create(args.get(i++));
            long fileSize = Long.parseLong(args.get(i++));
            Date startTime = getDateTime(args.get(i++));
            Date endTime = getDateTime(args.get(i++));
            int findId = Integer.parseInt(args.get(i++));
            String hostname = args.get(i++);
            long sourceId = Long.parseLong(args.get(i++));
            int cardId = Integer.parseInt(args.get(i++));
            int inputId = Integer.parseInt(args.get(i++));
            int recPriority = Integer.parseInt(args.get(i++));
            RecordingStatus recStatus = getTranslator().toEnum(args.get(i++), RecordingStatus.class);
            int recordId = Integer.parseInt(args.get(i++));
            RecordingType recType = getTranslator().toEnum(args.get(i++), RecordingType.class);
            Set<RecordingDupIn> dupIn = getTranslator().toEnums(args.get(i++), RecordingDupIn.class);
            RecordingDupMethod dupMethod = getTranslator().toEnum(args.get(i++),
                                                                  RecordingDupMethod.class);
            Date recStartTs = getDateTime(args.get(i++));
            Date recEndTs = getDateTime(args.get(i++));
            long programFlags = Long.parseLong(args.get(i++));
            String recGroup = args.get(i++);
            String outputFilters = args.get(i++);
            String seriesId = args.get(i++);
            String programId = args.get(i++);
            String inetref = args.get(i++);
            Date lastModified = getDateTime(args.get(i++));
            float stars = Float.parseFloat(args.get(i++));
            Date airDate;
            String arg = args.get(i++);
            if (arg.isEmpty())
            {
                airDate = null;
            }
            else
            {
                airDate = airDateFormat.parse(arg);
            }
            String playGroup = args.get(i++);
            int recPriority2 = Integer.parseInt(args.get(i++));
            int parentId = Integer.parseInt(args.get(i++));
            String storageGroup = args.get(i++);
            int audioProps = Integer.parseInt(args.get(i++));
            int videoProps = Integer.parseInt(args.get(i++));
            int subtitleType = Integer.parseInt(args.get(i++));
            int year = Integer.parseInt(args.get(i++));

            return new Program(title,
                               subtitle,
                               description,
                               season,
                               episode,
                               category,
                               new Channel(chanId, sourceId, chanNum, callsign, chanName),
                               filename,
                               fileSize,
                               startTime,
                               endTime,
                               findId,
                               hostname,
                               cardId,
                               inputId,
                               recPriority,
                               recStatus,
                               recordId,
                               recType,
                               dupIn,
                               dupMethod,
                               recStartTs,
                               recEndTs,
                               programFlags,
                               recGroup,
                               outputFilters,
                               seriesId,
                               programId,
                               inetref,
                               lastModified,
                               stars,
                               airDate,
                               playGroup,
                               recPriority2,
                               parentId,
                               storageGroup,
                               audioProps,
                               videoProps,
                               subtitleType,
                               year);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(args.toString(), Direction.RECEIVE, e);
        }
        catch (IndexOutOfBoundsException e)
        {
            throw new ProtocolException(args.toString(), Direction.RECEIVE, e);
        }
        catch (ParseException e)
        {
            throw new ProtocolException(args.toString(), Direction.RECEIVE, e);
        }
    }

    @Override
    public List<String> extractProgramInfo(Program program) throws ProtocolException
    {
        List<String> extracted = new ArrayList<String>();

        Channel channel = program.getChannel();

        extracted.add(program.getTitle());
        extracted.add(program.getSubtitle());
        extracted.add(program.getDescription());
        extracted.add(String.valueOf(program.getSeason()));
        extracted.add(String.valueOf(program.getEpisode()));
        extracted.add(program.getCategory());
        extracted.add(String.valueOf(channel.getId()));
        extracted.add(channel.getNumber());
        extracted.add(channel.getCallsign());
        extracted.add(channel.getName());
        extracted.add(valueOf(program.getFilename()));
        extracted.add(String.valueOf(program.getFileSize()));
        extracted.add(getDateTime(program.getStartTime()));
        extracted.add(getDateTime(program.getEndTime()));
        extracted.add(String.valueOf(program.getFindId()));
        extracted.add(program.getHostname());
        extracted.add(String.valueOf(channel.getSourceId()));
        extracted.add(String.valueOf(program.getCardId()));
        extracted.add(String.valueOf(program.getInputId()));
        extracted.add(String.valueOf(program.getRecPriority()));
        extracted.add(getTranslator().toString(program.getRecStatus()));
        extracted.add(String.valueOf(program.getRecordId()));
        extracted.add(getTranslator().toString(program.getRecType()));
        extracted.add(String.valueOf(program.getDupIn()));
        extracted.add(String.valueOf(program.getDupMethod()));
        extracted.add(getDateTime(program.getRecStartTs()));
        extracted.add(getDateTime(program.getRecEndTs()));
        extracted.add(String.valueOf(program.getProgramFlags()));
        extracted.add(program.getRecGroup());
        extracted.add(program.getOutputFilters());
        extracted.add(program.getSeriesId());
        extracted.add(program.getProgramId());
        extracted.add(program.getInetref());
        extracted.add(getDateTime(program.getLastModified()));
        extracted.add(String.valueOf(program.getStars()));
        extracted.add(program.getAirDate() == null
                ? ""
                : getProgramInfoAirDateFormat().format(program.getAirDate()));
        extracted.add(program.getPlayGroup());
        extracted.add(String.valueOf(program.getRecPriority2()));
        extracted.add(String.valueOf(program.getParentId()));
        extracted.add(program.getStorageGroup());
        extracted.add(String.valueOf(program.getAudioProps()));
        extracted.add(String.valueOf(program.getVideoProps()));
        extracted.add(String.valueOf(program.getSubtitleType()));
        extracted.add(String.valueOf(program.getYear()));

        return extracted;
    }
}
