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

import org.syphr.mythtv.types.Key;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.socket.AbstractCommandOkResponse;

/* default */class Command0_24Key extends AbstractCommandOkResponse
{
    private char c;
    private Key key;

    public Command0_24Key(char c)
    {
        this.c = c;
    }

    public Command0_24Key(Key key)
    {
        this.key = key;
    }

    @Override
    protected String getMessage() throws ProtocolException
    {
        String keyCode;

        if (key != null)
        {
            keyCode = Control0_24Utils.getTranslator().toString(key);
        }
        else
        {
            switch (c)
            {
                case ' ':
                    keyCode = Control0_24Utils.getTranslator().toString(Key.SPACE);
                    break;

                case '\t':
                    keyCode = Control0_24Utils.getTranslator().toString(Key.TAB);
                    break;

                case '\n':
                    keyCode = Control0_24Utils.getTranslator().toString(Key.ENTER);
                    break;

                default:
                    keyCode = String.valueOf(c);
                    break;
            }
        }

        return "key " + keyCode;
    }
}
