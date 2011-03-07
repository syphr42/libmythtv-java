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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
    private static final File LOCAL_TEMP = new File("target/testing");

    private static PropertiesManager<Settings> settings;

    private static SocketManager commandSocketManager;
    private static Protocol commandProto;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        if (!LOCAL_TEMP.exists() && !LOCAL_TEMP.mkdirs())
        {
            throw new IOException("Cannot create parent directory at: "
                                  + LOCAL_TEMP.getAbsolutePath());
        }

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

        FileUtils.deleteDirectory(LOCAL_TEMP);
    }

    @Test
    public void testDownloadReadDeleteFile() throws IOException, URISyntaxException
    {
        URL source = new URL("http://www.syphr.org");
        URI dest = new URI("/download-test.html");
        String storageGroup = "Default";

        /*
         * Download the file directly for comparison.
         */
        File expectedFile = new File(LOCAL_TEMP, "expected.html");
        OutputStream directDownloadOut = new BufferedOutputStream(new FileOutputStream(expectedFile));
        InputStream directDownloadIn = new BufferedInputStream(source.openStream());
        IOUtils.copy(directDownloadIn, directDownloadOut);
        directDownloadOut.close();
        directDownloadIn.close();

        URI result = commandProto.downloadFileNow(source, storageGroup, dest);
        Assert.assertNotNull(result);
        // remove slashes so that repeated slashes in one and not the other will still work
        Assert.assertEquals(dest.getPath().replace("/", ""), result.getPath().replace("/", ""));
        Assert.assertEquals(storageGroup, result.getUserInfo());

        SocketManager fileSocketManager = new SocketManager();
        fileSocketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                                  settings.getIntegerProperty(Settings.BACKEND_PORT),
                                  settings.getIntegerProperty(Settings.BACKEND_TIMEOUT));

        Protocol fileProto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                                                     ProtocolVersion.class),
                                                            fileSocketManager);
        fileProto.mythProtoVersion();

        QueryFileTransfer fileTransfer = fileProto.annFileTransfer(InetAddress.getLocalHost()
                                                                              .getHostName(),
                                                                   FileTransferType.READ,
                                                                   false,
                                                                   0L,
                                                                   dest,
                                                                   storageGroup,
                                                                   commandSocketManager);

        ByteBuffer buffer = ByteBuffer.allocate(settings.getIntegerProperty(Settings.BUFFER_SIZE));

        File actualFile = new File(LOCAL_TEMP, "actual.html");
        FileOutputStream outStream = new FileOutputStream(actualFile);
        FileChannel out = outStream.getChannel();

        ReadWriteByteChannel in = fileSocketManager.redirectChannel();

        long size = fileTransfer.getSize();
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

        Assert.assertEquals(FileUtils.checksumCRC32(expectedFile),
                            FileUtils.checksumCRC32(actualFile));

        Assert.assertNotNull(commandProto.queryFileExists(dest, storageGroup));
        Assert.assertTrue(commandProto.deleteFile(dest, storageGroup));
        Assert.assertNull(commandProto.queryFileExists(dest, storageGroup));
    }

    @Test
    public void testReadRecording() throws IOException
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

        File tempFile = new File(LOCAL_TEMP, program.getBasename().toString());
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
