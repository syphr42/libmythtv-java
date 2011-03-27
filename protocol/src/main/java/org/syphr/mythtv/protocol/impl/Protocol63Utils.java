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

import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.syphr.mythtv.protocol.ProtocolException;
import org.syphr.mythtv.protocol.ProtocolException.Direction;
import org.syphr.mythtv.protocol.data.Channel;
import org.syphr.mythtv.protocol.data.DriveInfo;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.data.UpcomingRecordings;
import org.syphr.mythtv.protocol.types.RecordingStatus;
import org.syphr.mythtv.protocol.types.RecordingType;

public class Protocol63Utils
{
    private static final String DELIMITER = "[]:[]";

    private static final Translator TRANSLATOR = new Translator63();

    public static List<String> splitArguments(String value)
    {
        return new ArrayList<String>(Arrays.asList(value.split(Pattern.quote(DELIMITER))));
    }

    public static String combineArguments(String... args)
    {
        return combineArguments(new ArrayList<String>(Arrays.asList(args)));
    }

    public static String combineArguments(List<String> args)
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < args.size(); i++)
        {
            String arg = args.get(i);

            if (arg != null)
            {
                builder.append(arg);
            }

            /*
             * Don't append a delimiter to the end of the string.
             */
            if (i < args.size() - 1)
            {
                builder.append(DELIMITER);
            }
        }

        return builder.toString();
    }

    public static List<DriveInfo> parseDriveInfo(String value) throws ProtocolException
    {
        List<DriveInfo> drives = new ArrayList<DriveInfo>();

        try
        {
            List<String> args = splitArguments(value);

            for (int i = 0; i < args.size();)
            {
                String hostname = args.get(i++);
                File driveRoot = new File(args.get(i++));
                boolean local = "1".equals(args.get(i++));
                long driveNumber = Long.parseLong(args.get(i++));
                long storageGroupId = Long.parseLong(args.get(i++));
                long blockSize = Long.parseLong(args.get(i++));
                long totalSpace = ProtocolUtils.combineInts(args.get(i++), args.get(i++));
                long usedSpace = ProtocolUtils.combineInts(args.get(i++), args.get(i++));

                drives.add(new DriveInfo(hostname,
                                         driveRoot,
                                         local,
                                         driveNumber,
                                         storageGroupId,
                                         blockSize,
                                         totalSpace,
                                         usedSpace));
            }
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(value, Direction.RECEIVE, e);
        }

        return drives;
    }

    public static UpcomingRecordings parseUpcomingRecordings(String value) throws ProtocolException
    {
        List<String> args = Protocol63Utils.splitArguments(value);

        try
        {
            boolean conflicted = "1".equals(args.get(0));

            /*
             * Remove the conflict value and the next, which is the number of
             * programs returned.
             */
            args.remove(0);
            args.remove(0);

            List<ProgramInfo> programs = Protocol63Utils.parseProgramInfos(args);

            return new UpcomingRecordings(conflicted, programs);
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(value, Direction.RECEIVE, e);
        }
    }

    public static List<ProgramInfo> parseProgramInfos(List<String> args) throws ProtocolException
    {
        List<ProgramInfo> programs = new ArrayList<ProgramInfo>();
        DateFormat airDateFormat = getProgramInfoAirDateFormat();

        int i = 0;
        while (i < args.size())
        {
            try
            {
                programs.add(parseProgramInfo(args.subList(i, i += 41), airDateFormat));
            }
            catch (IndexOutOfBoundsException e)
            {
                throw new ProtocolException(args.toString(), Direction.RECEIVE, e);
            }
        }

        return programs;
    }

    public static ProgramInfo parseProgramInfo(List<String> args) throws ProtocolException
    {
        return parseProgramInfo(args, getProgramInfoAirDateFormat());
    }

    private static ProgramInfo parseProgramInfo(List<String> args,
                                                DateFormat airDateFormat) throws ProtocolException
    {
        try
        {
            int i = 0;

            String title = args.get(i++);
            String subtitle = args.get(i++);
            String description = args.get(i++);
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
            RecordingStatus recStatus = TRANSLATOR.toEnum(args.get(i++), RecordingStatus.class);
            int recordId = Integer.parseInt(args.get(i++));
            RecordingType recType = TRANSLATOR.toEnum(args.get(i++), RecordingType.class);
            int dupIn = Integer.parseInt(args.get(i++));
            int dupMethod = Integer.parseInt(args.get(i++));
            Date recStartTs = getDateTime(args.get(i++));
            Date recEndTs = getDateTime(args.get(i++));
            long programFlags = Long.parseLong(args.get(i++));
            String recGroup = args.get(i++);
            String outputFilters = args.get(i++);
            String seriesId = args.get(i++);
            String programId = args.get(i++);
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

            return new ProgramInfo(title,
                                   subtitle,
                                   description,
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

    public static Channel parseChannel(String value) throws ProtocolException
    {
        List<String> args = splitArguments(value);
        if (args.size() != 6)
        {
            throw new ProtocolException(value, Direction.RECEIVE);
        }

        try
        {
            int i = 0;

            int id = Integer.parseInt(args.get(i++));
            long sourceId = Long.parseLong(args.get(i++));
            String callsign = args.get(i++);
            String number = args.get(i++);
            String name = args.get(i++);
            String xmltvId = args.get(i++);

            return new Channel(id, sourceId, number, callsign, name, xmltvId);
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(value, Direction.RECEIVE, e);
        }
    }

    public static List<String> extractProgramInfo(ProgramInfo program) throws ProtocolException
    {
        List<String> extracted = new ArrayList<String>();

        Channel channel = program.getChannel();

        extracted.add(program.getTitle());
        extracted.add(program.getSubtitle());
        extracted.add(program.getDescription());
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
        extracted.add(TRANSLATOR.toString(program.getRecStatus()));
        extracted.add(String.valueOf(program.getRecordId()));
        extracted.add(TRANSLATOR.toString(program.getRecType()));
        extracted.add(String.valueOf(program.getDupIn()));
        extracted.add(String.valueOf(program.getDupMethod()));
        extracted.add(getDateTime(program.getRecStartTs()));
        extracted.add(getDateTime(program.getRecEndTs()));
        extracted.add(String.valueOf(program.getProgramFlags()));
        extracted.add(program.getRecGroup());
        extracted.add(program.getOutputFilters());
        extracted.add(program.getSeriesId());
        extracted.add(program.getProgramId());
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

    public static List<String> extractChannel(Channel channel)
    {
        List<String> extracted = new ArrayList<String>();

        extracted.add(String.valueOf(channel.getId()));
        extracted.add(String.valueOf(channel.getSourceId()));
        extracted.add(channel.getNumber());
        extracted.add(channel.getCallsign());
        extracted.add(channel.getName());

        return extracted;
    }

    private static DateFormat getProgramInfoAirDateFormat()
    {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    private static Date getDateTime(String seconds)
    {
        return new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(seconds)));
    }

    private static String getDateTime(Date date)
    {
        return date == null ? "" : String.valueOf(TimeUnit.MILLISECONDS.toSeconds(date.getTime()));
    }

    private static String valueOf(Object value)
    {
        if (value == null)
        {
            return "";
        }

        return value.toString();
    }

    public static Translator getTranslator()
    {
        return TRANSLATOR;
    }
}
