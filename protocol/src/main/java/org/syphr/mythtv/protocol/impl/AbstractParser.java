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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.syphr.mythtv.util.translate.Translator;

public abstract class AbstractParser implements Parser
{
    private static final String DELIMITER = "[]:[]";

    private final Translator translator;

    public AbstractParser(Translator translator)
    {
        this.translator = translator;
    }

    protected String getDelimiter()
    {
        return DELIMITER;
    }

    protected Translator getTranslator()
    {
        return translator;
    }

    protected DateFormat getProgramInfoAirDateFormat()
    {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    protected Date getDateTime(String seconds)
    {
        return new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(seconds)));
    }

    protected String getDateTime(Date date)
    {
        return date == null ? "" : String.valueOf(TimeUnit.MILLISECONDS.toSeconds(date.getTime()));
    }

    protected String valueOf(Object value)
    {
        if (value == null)
        {
            return "";
        }

        return value.toString();
    }

    @Override
    public List<String> splitArguments(String value)
    {
        return new ArrayList<String>(Arrays.asList(value.split(Pattern.quote(getDelimiter()))));
    }

    @Override
    public String combineArguments(String... args)
    {
        return combineArguments(new ArrayList<String>(Arrays.asList(args)));
    }

    @Override
    public String combineArguments(List<String> args)
    {
        return StringUtils.join(args, getDelimiter());
    }
}
