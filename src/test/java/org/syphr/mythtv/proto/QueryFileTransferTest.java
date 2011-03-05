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
package org.syphr.mythtv.proto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.proto.SocketManager.ReadWriteByteChannel;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.FileTransferType;
import org.syphr.mythtv.proto.types.ProtocolVersion;
import org.syphr.mythtv.proto.types.RecordingCategory;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class QueryFileTransferTest
{
    private static PropertiesManager<Settings> settings;

    private static SocketManager commandSocketManager;
    private static Protocol commandProto;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();

        commandSocketManager = new SocketManager();
        commandSocketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                              settings.getIntegerProperty(Settings.BACKEND_PORT),
                              settings.getIntegerProperty(Settings.BACKEND_TIMEOUT));

        commandProto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                                               ProtocolVersion.class),
                                                      commandSocketManager);
        commandProto.mythProtoVersion();
        commandProto.ann(ConnectionType.MONITOR,
                         InetAddress.getLocalHost().getHostName(),
                         EventLevel.NONE);
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        commandProto.done();
        commandSocketManager.disconnect();
    }

    @Test
    public void testReadFile() throws IOException
    {
        List<ProgramInfo> programs = commandProto.queryRecordings(RecordingCategory.PLAY);
        if (programs.isEmpty())
        {
            return;
        }

        SocketManager fileSocketManager = new SocketManager();
        fileSocketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                                  settings.getIntegerProperty(Settings.BACKEND_PORT),
                                  settings.getIntegerProperty(Settings.BACKEND_TIMEOUT));

        Protocol fileProto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                                                     ProtocolVersion.class),
                                                            fileSocketManager);
        fileProto.mythProtoVersion();

        ProgramInfo program = programs.get(0);
        QueryFileTransfer fileTransfer = fileProto.annFileTransfer(InetAddress.getLocalHost()
                                                                              .getHostName(),
                                                                   FileTransferType.READ,
                                                                   false,
                                                                   0L,
                                                                   program.getBasename(),
                                                                   program.getStorageGroup(),
                                                                   commandSocketManager);

        Assert.assertTrue(fileTransfer.isOpen());

        fileTransfer.setTimeout(false);
        fileTransfer.setTimeout(true);

        ByteBuffer buffer = ByteBuffer.allocate(settings.getIntegerProperty(Settings.BUFFER_SIZE));

        File tempFile = new File("target/testing/" + program.getBasename().toString());
        File parentDir = tempFile.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs())
        {
            throw new IOException("Cannot create parent directory at: " + parentDir.getAbsolutePath());
        }
        tempFile.deleteOnExit();

        FileOutputStream outStream = new FileOutputStream(tempFile);
        FileChannel out = outStream.getChannel();

        ReadWriteByteChannel in = fileSocketManager.redirectChannel();

        /*
         * If the test case transfered the entire file it would take too long,
         * so just grab an amount that does not fit nicely into a set of
         * buffers.
         *
         * To get the whole file, switch the size lines below.
         */
        // long size = fileTransfer.getSize();
        long size = (long)(buffer.capacity() * 2.5);
        int read = 0;
        while (read < size)
        {
            buffer.clear();

            int sendCount = fileTransfer.requestBlock((int)Math.min(buffer.capacity(), size - read));
            buffer.limit(sendCount);

            while (buffer.hasRemaining())
            {
                read += in.read(buffer);
            }

            buffer.flip();

            while (buffer.hasRemaining())
            {
                out.write(buffer);
            }
        }

        in.close();
        out.close();
        outStream.close();

        fileTransfer.done();

        fileProto.done();
        fileSocketManager.disconnect();
    }
}
