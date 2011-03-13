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
package org.syphr.mythtv.proto.impl;

import java.io.IOException;
import java.util.List;

import org.syphr.mythtv.proto.CommandException;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.types.RecordingCategory;

public class Protocol65 extends Protocol64
{
    public Protocol65(SocketManager socketManager)
    {
        super(socketManager);
    }

    @Override
    public void mythProtoVersion() throws IOException, CommandException
    {
        new Command63MythProtoVersion()
        {
            @Override
            protected String getVersion()
            {
                return "65";
            }

            @Override
            protected String getToken()
            {
                return "D2BB94C2";
            }
        }.send(getSocketManager());
    }

    @Override
    public List<ProgramInfo> queryRecordings(RecordingCategory recCategory) throws IOException
    {
        return new Command65QueryRecordings(recCategory).send(getSocketManager());
    }

    @Override
    protected Translator getTranslator()
    {
        return Protocol65Utils.getTranslator();
    }
}
