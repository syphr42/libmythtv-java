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
package org.syphr.mythtv.apps.commander;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Main
{
    public static void main(String[] args)
    {
        CommandLineParser parser = new PosixParser();
        CommandLine cl;
        try
        {
            cl = parser.parse(CommanderOption.getOptions(), args);

            if (CommanderOption.HELP.hasOption(cl))
            {
                dumpUsage(null);
            }

            try
            {
                Connection connection = connect(cl);
                try
                {
                    for (String command : CommanderOption.COMMANDS.getValues(cl))
                    {
                        System.out.println(command);
                        System.out.println(connection.send(command));
                        System.out.println();
                    }
                }
                finally
                {
                    connection.disconnect();
                }
            }
            catch (IOException e)
            {
                System.out.println("Connection with remote host failed.");
                System.out.println(e.getMessage());
            }
        }
        catch (ParseException e)
        {
            dumpUsage("Failed to parse command line: " + e.getMessage());
        }
    }

    private static void dumpUsage(String errorMsg)
    {
        boolean error = errorMsg != null;

        if (error)
        {
            System.out.println(errorMsg);
            System.out.println();
        }

        new HelpFormatter().printHelp(Main.class.getName() + " [OPTIONS]",
                                      CommanderOption.getOptions());

        System.exit(error ? 1 : 0);
    }

    private static Connection connect(CommandLine cl) throws IOException, ParseException
    {
        Connection connection = getConnection(cl);

        int port = CommanderOption.PORT.hasOption(cl)
                ? CommanderOption.PORT.getNumberValue(cl).intValue()
                : getDefaultPort(cl);
        long timeout = CommanderOption.TIMEOUT.hasOption(cl)
                ? CommanderOption.TIMEOUT.getNumberValue(cl).longValue()
                : TimeUnit.SECONDS.toMillis(10);

        connection.connect(CommanderOption.HOST.getValue(cl), port, timeout);

        return connection;
    }

    private static Connection getConnection(CommandLine cl)
    {
        if (CommanderOption.FRONTEND.hasOption(cl))
        {
            return new FrontendConnection();
        }

        if (CommanderOption.BACKEND.hasOption(cl))
        {
            return new BackendConnection();
        }

        throw new IllegalArgumentException("No connection type was specified");
    }

    private static int getDefaultPort(CommandLine cl)
    {
        if (CommanderOption.FRONTEND.hasOption(cl))
        {
            return 6546;
        }

        if (CommanderOption.BACKEND.hasOption(cl))
        {
            return 6543;
        }

        throw new IllegalArgumentException("No connection type was specified");
    }
}
