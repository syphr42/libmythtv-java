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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolFactory;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.FileInfo;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.protocol.types.FileTransferType;
import org.syphr.mythtv.protocol.types.ProtocolVersion;
import org.syphr.mythtv.protocol.types.RecordingCategory;
import org.syphr.mythtv.protocol.types.SeekOrigin;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.test.Utils;
import org.syphr.prom.PropertiesManager;

import com.google.common.io.Files;
import com.google.common.io.Resources;

public class QueryFileTransferTest
{
    private static final File LOCAL_TEMP = new File("target/testing");

    private static final String TEST_URL = "http://www.syphr.org";
    private static final String TEST_URI = "/download-test.html";
    private static final String TEST_STORAGE_GROUP = "Default";

    private static final File EXPECTED_FILE = new File(LOCAL_TEMP, "transfer-test.data");

    private static PropertiesManager<Settings> settings;

    private static SocketManager commandSocketManager;
    private static Protocol commandProto;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException
    {
        if (!LOCAL_TEMP.exists() && !LOCAL_TEMP.mkdirs())
        {
            throw new IOException("Cannot create parent directory at: "
                                  + LOCAL_TEMP.getAbsolutePath());
        }

        /*
         * Download a file directly for comparison later.
         */
        OutputStream directDownloadOut = new BufferedOutputStream(new FileOutputStream(EXPECTED_FILE));
        Resources.copy(new URL(TEST_URL), directDownloadOut);
        directDownloadOut.close();

        settings = Settings.createSettings();

        commandSocketManager = Utils.connect(settings);
        commandProto = Utils.announceMonitor(settings,
                                             commandSocketManager,
                                             EventLevel.NONE);
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        commandProto.done();
        commandSocketManager.disconnect();

        Files.deleteRecursively(LOCAL_TEMP);
    }

    @Test
    public void testDownloadReadDeleteFile() throws IOException, URISyntaxException, CommandException
    {
        URI dest = new URI(TEST_URI);

        URI result = commandProto.downloadFileNow(new URL(TEST_URL), TEST_STORAGE_GROUP, dest);
        Assert.assertNotNull(result);
        Assert.assertEquals(dest.getPath().replace("/", ""),
                            result.getPath().replace("/", ""));
        Assert.assertEquals(TEST_STORAGE_GROUP, result.getUserInfo());

        SocketManager fileSocketManager = Utils.connect(settings);
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
                                                                   TEST_STORAGE_GROUP,
                                                                   commandSocketManager);

        File actualFile = new File(LOCAL_TEMP, "actual.data");
        Utils.readToFile(settings, fileSocketManager, actualFile, fileTransfer);

        fileTransfer.done();

        fileProto.done();
        fileSocketManager.disconnect();

        Checksum crc32 = new CRC32();
        Assert.assertEquals(Files.getChecksum(EXPECTED_FILE, crc32),
                            Files.getChecksum(actualFile, crc32));

        Assert.assertNotNull(commandProto.queryFileExists(dest, TEST_STORAGE_GROUP));
        Assert.assertTrue(commandProto.deleteFile(dest, TEST_STORAGE_GROUP));
        Assert.assertNull(commandProto.queryFileExists(dest, TEST_STORAGE_GROUP));
    }

    @Test
    public void testReadSeekRecording() throws IOException, CommandException
    {
        List<ProgramInfo> programs = commandProto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (programs.isEmpty())
        {
            return;
        }

        SocketManager fileSocketManager = Utils.connect(settings);
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

        long buffer = settings.getLongProperty(Settings.BUFFER_SIZE);
        long totalSize = fileTransfer.getSize();
        long chunk = buffer * 100;
        long seekPosition = totalSize - chunk;

        if (chunk >= totalSize)
        {
            Assert.fail("Unable to test read/seek - file too small ("
                        + totalSize
                        + "B)");
        }

        File tempFile = new File(LOCAL_TEMP, program.getBasename().toString());

        Utils.readToFile(settings,
                         fileSocketManager,
                         tempFile,
                         fileTransfer,
                         chunk,
                         false);

        long newPosition = fileTransfer.seek(seekPosition, SeekOrigin.BEGINNING, chunk);
        Assert.assertEquals(seekPosition, newPosition);

        Utils.readToFile(settings,
                         fileSocketManager,
                         tempFile,
                         fileTransfer,
                         chunk,
                         true);

        fileTransfer.done();

        fileProto.done();
        fileSocketManager.disconnect();
    }

    @Test
    public void testWriteDelete() throws IOException, URISyntaxException, CommandException
    {
        URI dest = new URI(TEST_URI);

        SocketManager fileSocketManager = Utils.connect(settings);
        Protocol fileProto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                                                     ProtocolVersion.class),
                                                            fileSocketManager);
        fileProto.mythProtoVersion();

        QueryFileTransfer fileTransfer = fileProto.annFileTransfer(InetAddress.getLocalHost()
                                                                              .getHostName(),
                                                                   FileTransferType.WRITE,
                                                                   false,
                                                                   0L,
                                                                   dest,
                                                                   TEST_STORAGE_GROUP,
                                                                   commandSocketManager);

        Utils.writeFromFile(settings, fileSocketManager, EXPECTED_FILE, fileTransfer);

        fileTransfer.done();

        fileProto.done();
        fileSocketManager.disconnect();

        FileInfo info = commandProto.queryFileExists(dest, TEST_STORAGE_GROUP);
        Assert.assertNotNull(info);
        Assert.assertEquals(EXPECTED_FILE.length(), info.getSize());
        Assert.assertTrue(commandProto.deleteFile(dest, TEST_STORAGE_GROUP));
        Assert.assertNull(commandProto.queryFileExists(dest, TEST_STORAGE_GROUP));

    }
}
