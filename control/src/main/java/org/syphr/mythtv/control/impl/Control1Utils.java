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
package org.syphr.mythtv.control.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.DateUtils;
import org.syphr.mythtv.util.translate.Translator;

public class Control1Utils
{
    private static final Pattern PROGRAM_PATTERN = Pattern.compile("^(\\d+)\\s+(" + DateUtils.getIsoDatePattern() + ")\\s+([^-]+)(\\s+-\"(.*)\")?$");

    private static final Translator TRANSLATOR = new Translator1();

    public static List<Program> parsePrograms(String value) throws IOException
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
            Matcher matcher = PROGRAM_PATTERN.matcher(line);
            if (!matcher.find())
            {
                throw new ProtocolException(value, Direction.RECEIVE);
            }

            try
            {
                int channelId = Integer.parseInt(matcher.group(1));
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

    public static Translator getTranslator()
    {
        return TRANSLATOR;
    }
}
