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

import org.syphr.mythtv.util.socket.AbstractCommand;
import org.syphr.mythtv.util.translate.Translator;

public abstract class AbstractProtocolCommand<T> extends AbstractCommand<T>
{
    private final Translator translator;

    private final Parser parser;

    public AbstractProtocolCommand(Translator translator, Parser parser)
    {
        this.translator = translator;
        this.parser = parser;
    }

    protected Translator getTranslator()
    {
        return translator;
    }

    protected Parser getParser()
    {
        return parser;
    }
}
