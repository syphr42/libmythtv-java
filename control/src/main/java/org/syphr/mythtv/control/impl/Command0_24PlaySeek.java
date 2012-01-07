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

import org.syphr.mythtv.types.SeekTarget;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command0_24PlaySeek extends AbstractCommand0_24Play
{
    private SeekTarget seekTarget;

    private int hour;
    private int minute;
    private int second;

    public Command0_24PlaySeek(Translator translator, SeekTarget seekTarget)
    {
        super(translator);

        this.seekTarget = seekTarget;
    }

    public Command0_24PlaySeek(Translator translator,
                               int hour,
                               int minute,
                               int second)
    {
        super(translator);

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        String message = "play seek ";

        if (seekTarget != null)
        {
            message += getTranslator().toString(seekTarget);
        }
        else
        {
            message += String.format("%02d:%02d:%02d", hour, minute, second);
        }

        return message;
    }
}
