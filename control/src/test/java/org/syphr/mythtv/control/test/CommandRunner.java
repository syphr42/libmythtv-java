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
package org.syphr.mythtv.control.test;

import java.io.IOException;

import org.syphr.mythtv.control.impl.ControlSocketManager;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.util.socket.Interceptor;
import org.syphr.mythtv.util.socket.SocketManager;
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
        SocketManager socketManager = connect(settings);

        try
        {
            for (String command : args)
            {
                System.out.println(command + " :: " + socketManager.sendAndWait(command));
            }
        }
        finally
        {
            socketManager.disconnect();
        }
    }

    private static SocketManager connect(PropertiesManager<Settings> settings) throws IOException
    {
        SocketManager socketManager = new ControlSocketManager();

        socketManager.setInterceptor(new Interceptor()
        {
            @Override
            public boolean intercept(String response)
            {
                return response.startsWith("MythFrontend Network Control");
            }
        });

        socketManager.connect(settings.getProperty(Settings.FRONTEND_HOST),
                              settings.getIntegerProperty(Settings.FRONTEND_CONTROL_PORT),
                              settings.getIntegerProperty(Settings.FRONTEND_CONTROL_TIMEOUT));

        return socketManager;
    }
}
