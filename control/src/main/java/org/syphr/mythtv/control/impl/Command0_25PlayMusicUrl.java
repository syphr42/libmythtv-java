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

import java.net.URL;

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command0_25PlayMusicUrl extends AbstractCommandOkResponse
{
    private final URL url;

    public Command0_25PlayMusicUrl(Translator translator, URL url) throws ProtocolException
    {
        super(translator);

        if (url == null)
        {
            throw new ProtocolException("URL must not be null", Direction.SEND);
        }

        this.url = url;
    }

    @Override
    protected String getMessage()
    {
        return "play music url " + url;
    }
}
