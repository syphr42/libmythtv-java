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
package org.syphr.mythtv.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.FileChannel;

import org.syphr.mythtv.proto.Protocol;
import org.syphr.mythtv.proto.ProtocolFactory;
import org.syphr.mythtv.proto.QueryFileTransfer;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.SocketManager.ReadWriteByteChannel;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.ProtocolVersion;
import org.syphr.prom.PropertiesManager;

public class Utils
{
    public static SocketManager connect(PropertiesManager<Settings> settings) throws IOException
    {
        SocketManager socketManager = new SocketManager();
        socketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                              settings.getIntegerProperty(Settings.BACKEND_PORT),
                              settings.getIntegerProperty(Settings.BACKEND_TIMEOUT));

        return socketManager;
    }

    public static Protocol announceMonitor(PropertiesManager<Settings> settings,
                                           SocketManager socketManager,
                                           EventLevel eventLevel) throws IOException
    {
        Protocol proto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                                                 ProtocolVersion.class),
                                                        socketManager);
        proto.mythProtoVersion();
        proto.ann(ConnectionType.MONITOR, InetAddress.getLocalHost()
                                                     .getHostName(), eventLevel);

        return proto;
    }

    public static void readToFile(PropertiesManager<Settings> settings,
                                  SocketManager socketManager,
                                  File file,
                                  QueryFileTransfer fileTransfer) throws IOException
    {
        readToFile(settings,
                   socketManager,
                   file,
                   fileTransfer,
                   fileTransfer.getSize());
    }

    public static void readToFile(PropertiesManager<Settings> settings,
                                  SocketManager socketManager,
                                  File file,
                                  QueryFileTransfer fileTransfer,
                                  long size) throws IOException
    {
        FileOutputStream outStream = new FileOutputStream(file);

        try
        {
            FileChannel out = outStream.getChannel();

            try
            {
                ReadWriteByteChannel in = socketManager.redirectChannel();

                try
                {
                    long buffer = settings.getLongProperty(Settings.BUFFER_SIZE);
                    int read = 0;
                    while (read < size)
                    {
                        long sendCount = fileTransfer.requestBlock(Math.min(buffer,
                                                                            size - read));
                        if (sendCount < 0)
                        {
                            throw new IOException();
                        }

                        read += sendCount;
                        while (sendCount > 0)
                        {
                            sendCount -= out.transferFrom(in, 0, sendCount);
                        }
                    }
                }
                finally
                {
                    in.close();
                }
            }
            finally
            {
                out.close();
            }
        }
        finally
        {
            outStream.close();
        }
    }

    public static void writeFromFile(PropertiesManager<Settings> settings,
                                     SocketManager socketManager,
                                     File file,
                                     QueryFileTransfer fileTransfer) throws IOException
    {
        FileInputStream inStream = new FileInputStream(file);

        try
        {
            FileChannel in = inStream.getChannel();

            try
            {
                ReadWriteByteChannel out = socketManager.redirectChannel();

                try
                {
                    long buffer = settings.getLongProperty(Settings.BUFFER_SIZE);
                    long size = file.length();
                    int written = 0;
                    while (written < size)
                    {
                        long sendCount = in.transferTo(written,
                                                       Math.min(buffer,
                                                                size - written),
                                                       out);

                        long received = fileTransfer.writeBlock(sendCount);
                        if (received < 0)
                        {
                            throw new IOException();
                        }

                        written += received;
                    }
                }
                finally
                {
                    out.close();
                }
            }
            finally
            {
                in.close();
            }
        }
        finally
        {
            inStream.close();
        }
    }
}
