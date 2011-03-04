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

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.syphr.mythtv.proto.ProtocolException;
import org.syphr.mythtv.proto.data.FileInfo;

/* default */class Command63QuerySgFileQuery extends AbstractCommand63QuerySg<FileInfo>
{
    public Command63QuerySgFileQuery(String host, String storageGroup, String path)
    {
        super(host, storageGroup, path);
    }

    @Override
    protected String getCommand()
    {
        return "QUERY_SG_FILEQUERY";
    }

    @Override
    protected FileInfo parseResponse(String response, List<String> args) throws ProtocolException
    {
        if (args.size() != 3)
        {
            throw new ProtocolException(response);
        }

        try
        {
            return new FileInfo(new Date(TimeUnit.SECONDS.toMillis(Long.parseLong(args.get(1)))),
                                Long.parseLong(args.get(2)));
        }
        catch (NumberFormatException e)
        {
            throw new ProtocolException(response, e);
        }
    }
}
