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

import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.ProtocolException.Direction;
import org.syphr.mythtv.proto.data.FileEntry;
import org.syphr.mythtv.proto.types.FileEntryType;

/* default */class Command63QuerySgGetFileList extends AbstractCommand63QuerySg<List<FileEntry>>
{
    public Command63QuerySgGetFileList(String host, String storageGroup, String path)
    {
        super(host, storageGroup, path);
    }

    @Override
    protected String getCommand()
    {
        return "QUERY_SG_GETFILELIST";
    }

    @Override
    protected List<FileEntry> parseResponse(String response, List<String> args) throws ProtocolException
    {
        List<FileEntry> list = new ArrayList<FileEntry>();

        for (String fileEntryStr : args)
        {
            String[] pair = fileEntryStr.split("::");
            if (pair.length != 2)
            {
                throw new ProtocolException(response, Direction.RECEIVE);
            }

            FileEntryType type;
            if ("dir".equals(pair[0]))
            {
                type = FileEntryType.DIRECTORY;
            }
            else if ("file".equals(pair[0]))
            {
                type = FileEntryType.FILE;
            }
            else
            {
                throw new ProtocolException(response, Direction.RECEIVE);
            }

            list.add(new FileEntry(type, pair[1]));
        }

        return list;
    }
}
