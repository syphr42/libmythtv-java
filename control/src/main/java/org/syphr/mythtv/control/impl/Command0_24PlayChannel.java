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

import org.syphr.mythtv.util.translate.Translator;

/* default */class Command0_24PlayChannel extends AbstractCommand0_24Play
{
    private int id;
    private String number;

    public Command0_24PlayChannel(Translator translator, int id)
    {
        super(translator);

        this.id = id;
    }

    public Command0_24PlayChannel(Translator translator, String number)
    {
        super(translator);

        this.number = number;
    }

    @Override
    protected String getMessage()
    {
        String message = "play ";

        if (number != null)
        {
            message += "channel " + number;
        }
        else
        {
            message += "chanid " + id;
        }

        return message;
    }
}
