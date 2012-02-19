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

import org.syphr.mythtv.commons.translate.Translator;

/* default */class Command0_24PlaySavePreview extends AbstractCommand0_24Play
{
    private final String filename;
    private final int width;
    private final int height;

    public Command0_24PlaySavePreview(Translator translator,
                                      String filename,
                                      int width,
                                      int height)
    {
        super(translator);

        this.filename = filename;
        this.width = width;
        this.height = height;
    }

    @Override
    protected String getMessage()
    {
        StringBuilder builder = new StringBuilder("play save preview");

        if (filename != null)
        {
            builder.append(' ');
            builder.append(filename);

            if (width > 0 && height > 0)
            {
                builder.append(' ');
                builder.append(width);
                builder.append('x');
                builder.append(height);
            }
        }

        return builder.toString();
    }
}
