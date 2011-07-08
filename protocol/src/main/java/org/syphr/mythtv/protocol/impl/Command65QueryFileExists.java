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

import java.net.URI;

import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.translate.Translator;

/* default */class Command65QueryFileExists extends Command63QueryFileExists
{
    public Command65QueryFileExists(Translator translator,
                                    Parser parser,
                                    URI filename,
                                    String storageGroup)
    {
        super(translator, parser, filename, storageGroup);
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        String message = getParser().combineArguments("QUERY_FILE_EXISTS",
                                                      getFilename().getPath());

        String storageGroup = getStorageGroup();
        if (storageGroup != null)
        {
            message = getParser().combineArguments(message, storageGroup);
        }

        return message;
    }
}
