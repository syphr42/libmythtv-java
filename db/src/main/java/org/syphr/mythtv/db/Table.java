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
package org.syphr.mythtv.db;

import org.syphr.mythtv.db.schema.Channel;
import org.syphr.mythtv.db.schema.Program;
import org.syphr.mythtv.db.schema.Recorded;
import org.syphr.mythtv.db.schema.Settings;
import org.syphr.mythtv.db.schema.TvChain;

public class Table
{
    private static final String CHANNEL = Channel.class.getName();
    private static final String PROGRAM = Program.class.getName();
    private static final String RECORDED = Recorded.class.getName();
    private static final String SETTINGS = Settings.class.getName();
    private static final String TV_CHAIN = TvChain.class.getName();

    private Table()
    {
        /*
         * Static constants
         */
    }
}
