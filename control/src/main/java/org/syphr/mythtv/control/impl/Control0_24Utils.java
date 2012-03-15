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
package org.syphr.mythtv.control.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.commons.translate.DateUtils;
import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;

public class Control0_24Utils
{
    private static final Pattern RECORDING_PATTERN = Pattern.compile("^(\\d+)\\s+(" + DateUtils.getIsoDatePattern() + ")\\s+(.+?)(\\s+-\"(.*)\")?$");

    private static final Pattern LIVE_TV_PATTERN = Pattern.compile("^\\s*(\\d+)\\s+(" + DateUtils.getIsoDatePattern() + ")\\s+(" + DateUtils.getIsoDatePattern() + ")\\s+(.+?)(\\s+-\"(.*)\")?$");

    private static final Pattern CHANNEL_PATTERN = Pattern.compile("^\\d+:(\\d+)\\s+(\\d+)\\s+\"(.*)\"\\s+\"(.*)\"$");

    public static List<Program> parseRecordings(String value) throws IOException
    {
        if (value.isEmpty())
        {
            return Collections.emptyList();
        }

        List<Program> programs = new ArrayList<Program>();

        BufferedReader reader = new BufferedReader(new StringReader(value));

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            Matcher matcher = RECORDING_PATTERN.matcher(line);
            if (!matcher.find())
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }

            try
            {
                long channelId = Long.parseLong(matcher.group(1));
                Date recStartTs = DateUtils.getIsoDateFormat().parse(matcher.group(2));
                String title = matcher.group(3);
                String subtitle = matcher.groupCount() == 5 ? matcher.group(5) : null;

                programs.add(new Program(title, subtitle, new Channel(channelId), recStartTs));
            }
            catch (NumberFormatException e)
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }
            catch (ParseException e)
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }
        }

        return programs;
    }

    public static List<Program> parseLiveTv(String value) throws IOException
    {
        if (value.isEmpty())
        {
            return Collections.emptyList();
        }

        List<Program> programs = new ArrayList<Program>();

        BufferedReader reader = new BufferedReader(new StringReader(value));

        String line = null;
        while ((line = reader.readLine()) != null)
        {
            Matcher matcher = LIVE_TV_PATTERN.matcher(line);
            if (!matcher.find())
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }

            try
            {
                long channelId = Long.parseLong(matcher.group(1));
                Date startTime = DateUtils.getIsoDateFormat().parse(matcher.group(2));
                Date endTime = DateUtils.getIsoDateFormat().parse(matcher.group(3));
                String title = matcher.group(4);
                String subtitle = matcher.groupCount() == 6 ? matcher.group(6) : null;

                programs.add(new Program(title, subtitle, new Channel(channelId), startTime, endTime));
            }
            catch (NumberFormatException e)
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }
            catch (ParseException e)
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }
        }

        return programs;
    }

    public static List<Channel> parseChannels(String value) throws IOException
    {
        List<Channel> channels = new ArrayList<Channel>();

        BufferedReader reader = new BufferedReader(new StringReader(value));

        boolean checkedCount = false;
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            Matcher matcher = CHANNEL_PATTERN.matcher(line);
            if (!matcher.find())
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }

            try
            {
                /*
                 * The first time through the loop, check to make sure the count
                 * is above 0. If there are no channels, the server will send
                 * back a line that matches the regular expression, but contains
                 * an invalid channel.
                 */
                if (!checkedCount)
                {
                    int count = Integer.parseInt(matcher.group(1));
                    if (count == 0)
                    {
                        break;
                    }

                    checkedCount = true;
                }

                long channelId = Long.parseLong(matcher.group(2));
                String callsign = matcher.group(3);
                String channelName = matcher.group(4);

                channels.add(new Channel(channelId, callsign, channelName));
            }
            catch (NumberFormatException e)
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }
        }

        return channels;
    }

    /**
     * Send a message and wait for a short period for a response. If no response comes in
     * that time, it is assumed that there will be no response from the frontend. This is
     * useful for commands that get a response when there is data available and silence
     * otherwise.
     *
     * @param socketManager
     *            the socket manager to use for communicating with the frontend
     * @param message
     *            the message to send
     * @return the response if there was one; otherwise an empty string
     * @throws IOException
     *             if there is a communication or protocol error
     */
    public static String getResponseMaybe(SocketManager socketManager, String message) throws IOException
    {
        String response = socketManager.sendAndWait(message, 5, TimeUnit.SECONDS);

        /*
         * If there is no response (therefore the timeout was hit in the
         * previous send-and-wait), then the socket manager will be expecting
         * the next message that arrives to be an orphan connected to this
         * command that didn't come back in time. To get things back in sync, a
         * throwaway command (help) will be sent.
         */
        if (response.isEmpty())
        {
            socketManager.send("help");
        }

        return response;
    }
}
