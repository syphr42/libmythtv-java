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

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.proto.Protocol;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.DriveInfo;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.data.RecorderLocation;
import org.syphr.mythtv.proto.data.RecordingsInProgress;
import org.syphr.mythtv.proto.data.UpcomingRecordings;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.RecordingCategory;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class Protocol63Test
{
    private static PropertiesManager<Settings> settings;
    private static SocketManager socketManager;
    private static Protocol proto;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException
    {
        settings = Settings.createSettings();

        socketManager = new SocketManager();
        socketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                              settings.getIntegerProperty(Settings.BACKEND_PORT),
                              settings.getIntegerProperty(Settings.BACKEND_TIMEOUT));

        proto = new Protocol63(socketManager);
        proto.mythProtoVersion();
        proto.ann(ConnectionType.MONITOR,
                  InetAddress.getLocalHost().getHostName(),
                  EventLevel.NONE);
    }

    @AfterClass
    public static void tearDownAfterClass() throws IOException
    {
        proto.done();
        socketManager.disconnect();
    }

    @Test
    public void testCheckRecording() throws IOException
    {
        List<ProgramInfo> expiringPrograms = proto.queryGetExpiring();
        if (!expiringPrograms.isEmpty())
        {
            Assert.assertEquals(0, proto.checkRecording(expiringPrograms.get(0)));
        }

        List<ProgramInfo> recPrograms = proto.queryRecordings(RecordingCategory.RECORDING);
        if (!recPrograms.isEmpty())
        {
            Assert.assertNotSame(0, proto.checkRecording(recPrograms.get(0)));
        }
    }

    @Test
    public void testFillProgramInfo() throws IOException, URISyntaxException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.PLAY);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo fullProgram = recordings.get(0);
        ProgramInfo partialProgram = new ProgramInfo(fullProgram.getTitle(),
                                                     fullProgram.getSubtitle(),
                                                     fullProgram.getDescription(),
                                                     fullProgram.getCategory(),
                                                     fullProgram.getChannel(),
                                                     fullProgram.getFilename(),
                                                     0,
                                                     fullProgram.getStartTime(),
                                                     fullProgram.getEndTime(),
                                                     fullProgram.getFindId(),
                                                     fullProgram.getHostname(),
                                                     fullProgram.getCardId(),
                                                     fullProgram.getInputId(),
                                                     fullProgram.getRecPriority(),
                                                     fullProgram.getRecStatus(),
                                                     fullProgram.getRecordId(),
                                                     fullProgram.getRecType(),
                                                     fullProgram.getDupIn(),
                                                     fullProgram.getDupMethod(),
                                                     fullProgram.getRecStartTs(),
                                                     fullProgram.getRecEndTs(),
                                                     fullProgram.getProgramFlags(),
                                                     fullProgram.getRecGroup(),
                                                     fullProgram.getOutputFilters(),
                                                     fullProgram.getSeriesId(),
                                                     fullProgram.getProgramId(),
                                                     fullProgram.getLastModified(),
                                                     fullProgram.getStars(),
                                                     fullProgram.getAirDate(),
                                                     fullProgram.getPlayGroup(),
                                                     fullProgram.getRecPriority2(),
                                                     fullProgram.getParentId(),
                                                     fullProgram.getStorageGroup(),
                                                     fullProgram.getAudioProps(),
                                                     fullProgram.getVideoProps(),
                                                     fullProgram.getSubtitleType(),
                                                     fullProgram.getYear());

        ProgramInfo fillInProgram = proto.fillProgramInfo(settings.getProperty(Settings.FRONTEND_HOST),
                                                          partialProgram);

        System.out.println("File info for " + fillInProgram.getChannel() + "/"
                           + fillInProgram.getRecStartTs() + ": " + fillInProgram.getFilename()
                           + " / " + fillInProgram.getFileSize());
    }

    @Test
    public void testGetFreeRecorderInfo() throws IOException
    {
        int count = proto.getFreeRecorderCount();
        if (count < 0)
        {
            Assert.fail();
        }

        List<Integer> freeRecorders = proto.getFreeRecorderList();

        Assert.assertEquals(count, freeRecorders.size());

        System.out.println("Free recorder count: " + count);
        System.out.println("Free recorders: " + freeRecorders);

        RecorderLocation freeRecorder = proto.getFreeRecorder();
        if (count > 0)
        {
            Assert.assertNotNull(freeRecorder);
        }
        else
        {
            Assert.assertNull(freeRecorder);
        }

        System.out.println("Next free recorder after "
                           + freeRecorder.getId()
                           + ": "
                           + proto.getNextFreeRecorder(freeRecorder));
    }

    @Test
    public void testGetRecorderFromNum() throws IOException
    {
        RecorderLocation good = proto.getRecorderFromNum(settings.getIntegerProperty(Settings.RECORDER));
        Assert.assertNotNull(good);

        RecorderLocation bad = proto.getRecorderFromNum(-1);
        Assert.assertNull(bad);
    }

    @Test
    public void testGetRecorderNum() throws IOException
    {
        List<ProgramInfo> recording = proto.queryRecordings(RecordingCategory.RECORDING);
        if (!recording.isEmpty())
        {
            Assert.assertNotNull(proto.getRecorderNum(recording.get(0)));
        }

        List<ProgramInfo> expipring = proto.queryGetExpiring();
        if (!expipring.isEmpty())
        {
            Assert.assertNull(proto.getRecorderNum(expipring.get(0)));
        }
    }

    @Test
    public void testQueryCommBreak() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.PLAY);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = recordings.get(0);
        System.out.println("Commercial breaks for "
                           + program.getChannel()
                           + "/"
                           + program.getStartTime()
                           + ": "
                           + proto.queryCommBreak(program.getChannel(),
                                                  program.getRecStartTs()));
    }

    @Test
    public void testQueryCutList() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.PLAY);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = recordings.get(0);
        System.out.println("Cut list marks for "
                           + program.getChannel()
                           + "/"
                           + program.getStartTime()
                           + ": "
                           + proto.queryCutList(program.getChannel(),
                                                program.getRecStartTs()));
    }

    @Test
    public void testQueryCheckFile() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.PLAY);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = recordings.get(0);
        System.out.println("URI for "
                           + program.getChannel()
                           + "/"
                           + program.getRecStartTs()
                           + ": "
                           + proto.queryCheckFile(true, program));
    }

    @Test
    public void testQueryBookmark() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.PLAY);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = recordings.get(0);
        System.out.println("Bookmark for "
                           + program.getChannel()
                           + "/"
                           + program.getStartTime()
                           + ": "
                           + proto.queryBookmark(program.getChannel(),
                                                 program.getRecStartTs()));
    }

    @Test
    public void testQueryFreeSpace() throws IOException
    {
        for (DriveInfo drive : proto.queryFreeSpace())
        {
            System.out.println(drive);
        }
    }

    @Test
    public void testQueryFreeSpaceSummary() throws IOException
    {
        System.out.println(proto.queryFreeSpaceSummary());
    }

    @Test
    public void testQueryGetAllPending() throws IOException
    {
        UpcomingRecordings upcoming = proto.queryGetAllPending();

        System.out.println("Conflicts? " + upcoming.isConflicted());
        System.out.println("Upcoming count: " + upcoming.size());
        printFirstFive(upcoming);
    }

    @Test
    public void testQueryGetAllScheduled() throws IOException
    {
        List<ProgramInfo> scheduled = proto.queryGetAllScheduled();
        System.out.println("Scheduled count: " + scheduled.size());
        printFirstFive(scheduled);
    }

    @Test
    public void testQueryGetExpiring() throws IOException
    {
        List<ProgramInfo> expiring = proto.queryGetExpiring();
        System.out.println("Expiring count: " + expiring.size());
        printFirstFive(expiring);
    }

    @Test
    public void testQueryGuideDataThrough() throws IOException
    {
        System.out.println("Guide data through: " + proto.queryGuideDataThrough());
    }

    @Test
    public void testQueryHostname() throws IOException
    {
        System.out.println("Hostname: " + proto.queryHostname());
    }

    @Test
    public void testQueryIsActiveBackend() throws IOException
    {
        Assert.assertTrue(proto.queryIsActiveBackend(proto.queryHostname()));
        Assert.assertFalse(proto.queryIsActiveBackend("thishostnamedoesnotexist"));
    }

    @Test
    public void testQueryIsRecording() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDING);
        RecordingsInProgress inProgress = proto.queryIsRecording();

        System.out.println(inProgress);

        Assert.assertSame(recordings.size(), inProgress.getTotal());
    }

    @Test
    public void testQueryLoad() throws IOException
    {
        System.out.println(proto.queryLoad());
    }

    @Test
    public void testQueryMemStats() throws IOException
    {
        System.out.println(proto.queryMemStats());
    }

    @Test
    public void testQueryRecordingsRecording() throws IOException
    {
        List<ProgramInfo> recording = proto.queryRecordings(RecordingCategory.RECORDING);
        System.out.println("Recording count: " + recording.size());
        printFirstFive(recording);
    }

    @Test
    public void testQueryRecordingsDelete() throws IOException
    {
        // FIXME this provides the same response as PLAY, seems broken
        List<ProgramInfo> delete = proto.queryRecordings(RecordingCategory.DELETE);
        System.out.println("Delete count: " + delete.size());
        printFirstFive(delete);
    }

    @Test
    public void testQueryRecordingsPlay() throws IOException
    {
        List<ProgramInfo> play = proto.queryRecordings(RecordingCategory.PLAY);
        System.out.println("Play count: " + play.size());
        printFirstFive(play);
    }

    @Test
    public void testQueryRecordingTimeslot() throws IOException
    {
        List<ProgramInfo> allRecordings = proto.queryRecordings(RecordingCategory.PLAY);
        if (allRecordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = allRecordings.get(0);
        Assert.assertEquals(program,
                            proto.queryRecordingTimeslot(program.getChannel(),
                                                         program.getRecStartTs()));
    }

    @Test
    public void testQuerySetting() throws IOException
    {
        String invalidHost = "!!!!!!!";
        String validHost = settings.getProperty(Settings.FRONTEND_HOST);

        String invalidKey = "@@@@@@@";
        String validKey = "Theme";

        Assert.assertNull(proto.querySetting(invalidHost, validKey));
        Assert.assertNull(proto.querySetting(validHost, invalidKey));
        Assert.assertNull(proto.querySetting(invalidHost, invalidKey));

        Assert.assertNotNull(proto.querySetting(validHost, validKey));
    }

    @Test
    public void testQueryTimeZone() throws IOException
    {
        System.out.println(proto.queryTimeZone());
    }

    @Test
    public void testQueryUptime() throws IOException
    {
        System.out.println("Uptime: " + proto.queryUptime() + " secs");
    }

    /*
     * ----------------------------------------------------------------
     * The following unit tests can cause side effects. Use with care.
     */
//    @Test
//    public void testAllowShutdown() throws IOException
//    {
//        proto.allowShutdown();
//    }
//
//    @Test
//    public void testBlockShutdown() throws IOException
//    {
//        proto.blockShutdown();
//    }
//
//    @Test
//    public void testDeleteRecording() throws IOException
//    {
//        List<ProgramInfo> recorded = proto.queryRecordings(RecordingCategory.PLAY);
//        if (recorded.isEmpty())
//        {
//            return;
//        }
//
//        ProgramInfo delete = recorded.get(0);
//        System.out.println("Deleting \"" + delete.getTitle() + ": " + delete.getSubtitle() + "\"");
//        Assert.assertTrue(proto.deleteRecording(delete.getChannel(),
//                                                delete.getRecStartTs(),
//                                                false,
//                                                false));
//    }
//
//    @Test
//    public void testForgetRecording() throws IOException
//    {
//        List<ProgramInfo> expiring = proto.queryGetExpiring();
//        if (expiring.isEmpty())
//        {
//            return;
//        }
//
//        ProgramInfo forget = expiring.get(0);
//        System.out.println("Forgetting \"" + forget.getTitle() + ": " + forget.getSubtitle() + "\"");
//        proto.forgetRecording(forget);
//    }
//
//    @Test
//    public void testShutdownNow() throws IOException
//    {
//        String backendHost = settings.getProperty(Settings.BACKEND_HOST);
//        System.out.println("Attempting to shut down " + backendHost);
//        proto.shutdownNow("halt");
//    }
//
//    @Test
//    public void testStopRecording() throws IOException
//    {
//        List<ProgramInfo> recording = proto.queryRecordings(RecordingCategory.RECORDING);
//        if (recording.isEmpty())
//        {
//            return;
//        }
//
//        ProgramInfo stop = recording.get(0);
//        System.out.println("Stopping \"" + stop.getTitle() + ": " + stop.getSubtitle() + "\"");
//        Assert.assertNotSame(-1, proto.stopRecording(stop));
//    }
//
//    @Test
//    public void testUndeleteRecording() throws IOException
//    {
//        List<ProgramInfo> expiring = proto.queryGetExpiring();
//        if (expiring.isEmpty())
//        {
//            return;
//        }
//
//        ProgramInfo undelete = expiring.get(0);
//        System.out.println("Undeleting \"" + undelete.getTitle() + ": " + undelete.getSubtitle() + "\"");
//        Assert.assertTrue(proto.undeleteRecording(undelete));
//    }
//
//    @Test
//    public void testQueryGenPixMap2() throws IOException
//    {
//        List<ProgramInfo> programs = proto.queryGetExpiring();
//        if (programs.isEmpty())
//        {
//            return;
//        }
//
//        GenPixMapResponse response = proto.queryGenPixMap2(Protocol63Test.class.getName(),
//                                                           programs.get(0));
//        Assert.assertEquals(GenPixMapResponse.OK, response);
//    }

    /*
     * ----------------------------------------------------------------
     * Utility methods.
     */
    private <T> void printFirstFive(Iterable<T> iterable)
    {
        int counter = 0;
        Iterator<T> iter = iterable.iterator();

        if (!iter.hasNext())
        {
            return;
        }

        System.out.println("(Up to) the first five:");

        while (iter.hasNext() && counter < 5)
        {
            System.out.println(iter.next());
            counter++;
        }
    }
}
