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
package org.syphr.mythtv.protocol.test;

import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class CommandRunner
{
    public static void main(String[] args) throws Exception
    {
        if (args.length == 0)
        {
            System.out.println("Usage: java "
                               + CommandRunner.class.getName()
                               + " COMMAND1 [COMMAND2 [COMMAND3 ...  ]]");
            System.exit(1);
        }

        PropertiesManager<Settings> settings = Settings.createSettings();

        SocketManager socketManager = Utils.connect(settings);
        Protocol proto = Utils.announceMonitor(settings, socketManager, EventLevel.NONE);

        try
        {
            for (String command : args)
            {
                System.out.println(command + " :: " + socketManager.sendAndWait(command));
            }
        }
        finally
        {
            proto.done();
            socketManager.disconnect();
        }
    }
}
