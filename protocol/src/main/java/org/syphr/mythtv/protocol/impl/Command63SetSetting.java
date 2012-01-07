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
package org.syphr.mythtv.protocol.impl;

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command63SetSetting extends AbstractCommandOkResponse
{
    private final String host;
    private final String name;
    private final String value;

    public Command63SetSetting(Translator translator,
                               Parser parser,
                               String host,
                               String name,
                               String value)
    {
        super(translator, parser);

        this.host = host;
        this.name = name;
        this.value = value;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        StringBuilder builder = new StringBuilder();
        builder.append("SET_SETTING ");
        builder.append(host);
        builder.append(' ');
        builder.append(name);
        builder.append(' ');
        builder.append(value);

        return builder.toString();
    }
}
