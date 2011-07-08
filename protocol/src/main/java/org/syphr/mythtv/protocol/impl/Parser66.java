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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.util.exception.ProtocolException;
import org.syphr.mythtv.util.exception.ProtocolException.Direction;
import org.syphr.mythtv.util.translate.Translator;

public class Parser66 extends Parser63
{
    public Parser66(Translator translator)
    {
        super(translator);
    }

    @Override
    public List<DriveInfo> parseDriveInfo(String value) throws ProtocolException
    {
        List<DriveInfo> drives = new ArrayList<DriveInfo>();

        try
        {
            List<String> args = splitArguments(value);

            for (int i = 0; i < args.size();)
            {
                String hostname = args.get(i++);
                File driveRoot = new File(args.get(i++));
                boolean local = "1".equals(args.get(i++));
                long driveNumber = Long.parseLong(args.get(i++));
                long storageGroupId = Long.parseLong(args.get(i++));
                long blockSize = Long.parseLong(args.get(i++));
                long totalSpace = Long.parseLong(args.get(i++));
                long usedSpace = Long.parseLong(args.get(i++));

                drives.add(new DriveInfo(hostname,
                                         driveRoot,
                                         local,
                                         driveNumber,
                                         storageGroupId,
                                         blockSize,
                                         totalSpace,
                                         usedSpace));
            }
        }
        catch (RuntimeException e)
        {
            throw new ProtocolException(value, Direction.RECEIVE, e);
        }

        return drives;
    }
}
