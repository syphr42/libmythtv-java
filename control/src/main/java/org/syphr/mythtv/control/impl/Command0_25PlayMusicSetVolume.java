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

import org.syphr.mythtv.commons.exception.ProtocolException;
import org.syphr.mythtv.commons.exception.ProtocolException.Direction;
import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command0_25PlayMusicSetVolume extends AbstractCommandOkResponse
{
    private final int percent;

    public Command0_25PlayMusicSetVolume(Translator translator, int percent) throws ProtocolException
    {
        super(translator);

        if (percent < 0 || percent > 100)
        {
            throw new ProtocolException("Volume percent must be 0 - 100, inclusive", Direction.SEND);
        }

        this.percent = percent;
    }

    @Override
    protected String getMessage()
    {
        return "play music setvolume " + percent;
    }
}
