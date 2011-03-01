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
package org.syphr.mythtv.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.StringUtils;

public class ConsoleFormatter extends Formatter
{
    private static final String NEWLINE = System.getProperty("line.separator");

    @Override
    public String format(LogRecord record)
    {
        StringBuilder formattedRecord = new StringBuilder(StringUtils.rightPad(record.getSourceClassName(),
                                                                               50,
                                                                               '.'));
        formattedRecord.append(" | ");

        String message = record.getMessage();
        formattedRecord.append(message != null ? message : " no message provided");
        formattedRecord.append(NEWLINE);

        Throwable thrown = record.getThrown();
        if (thrown != null)
        {
            StringWriter sw = new StringWriter();
            thrown.printStackTrace(new PrintWriter(sw));
            formattedRecord.append(sw);
        }

        return formattedRecord.toString();
    }
}
