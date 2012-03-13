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
package org.syphr.mythtv.apps.previewer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.syphr.mythtv.api.backend.AutomaticProtocol;
import org.syphr.mythtv.protocol.ConnectionType;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.protocol.ProtocolVersionException;

public class Main
{
    public static void main(String[] args)
    {
        CommandLineParser parser = new PosixParser();
        CommandLine cl;
        try
        {
            cl = parser.parse(PreviewerOption.getOptions(), args);

            if (PreviewerOption.HELP.hasOption(cl))
            {
                dumpUsage(null);
            }

            try
            {
                final AutomaticProtocol protocol = connect(cl);

                protocol.mythProtoVersion();
                protocol.ann(ConnectionType.PLAYBACK,
                             InetAddress.getLocalHost().getHostName(),
                             EventLevel.NONE);

                setLookAndFeel();

                JFrame frame = new PreviewerWindow(protocol);

                frame.addWindowListener(new WindowAdapter()
                {
                    @Override
                    public void windowClosed(WindowEvent event)
                    {
                        try
                        {
                            protocol.done();
                        }
                        catch (IOException e)
                        {
                            // ignore failure on disconnect
                        }
                    }
                });

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                centerAndSize(frame, 0.8f);

                frame.setVisible(true);
            }
            catch (ProtocolVersionException e)
            {
                System.out.println("Host speaks unsupported protocol version "
                        + e.getSupportedVersionStr());
                System.exit(1);
            }
            catch (IOException e)
            {
                System.out.println("Connection with remote host failed.");
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        catch (ParseException e)
        {
            dumpUsage("Failed to parse command line: " + e.getMessage());
        }
    }

    private static AutomaticProtocol connect(CommandLine cl) throws IOException, ParseException
    {
        AutomaticProtocol protocol = new AutomaticProtocol();

        int port = PreviewerOption.PORT.hasOption(cl)
                ? PreviewerOption.PORT.getNumberValue(cl).intValue()
                : 6543;
        long timeout = PreviewerOption.TIMEOUT.hasOption(cl)
                ? PreviewerOption.TIMEOUT.getNumberValue(cl).longValue()
                : TimeUnit.SECONDS.toMillis(10);

        protocol.connect(PreviewerOption.HOST.getValue(cl), port, timeout);

        return protocol;
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
                                      PreviewerOption.getOptions());

        System.exit(error ? 1 : 0);
    }

    private static void setLookAndFeel()
    {
        try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void centerAndSize(JFrame frame, float size)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)(screenSize.getWidth() * size), (int)(screenSize.getHeight() * size));

        frame.setLocationRelativeTo(null);
    }
}
