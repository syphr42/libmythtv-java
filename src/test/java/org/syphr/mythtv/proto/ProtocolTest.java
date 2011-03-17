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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.DriveInfo;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.data.RecorderLocation;
import org.syphr.mythtv.protocol.data.RecordingsInProgress;
import org.syphr.mythtv.protocol.data.UpcomingRecordings;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.protocol.types.RecordingCategory;
import org.syphr.mythtv.protocol.types.RecordingStatus;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.test.Utils;
import org.syphr.prom.PropertiesManager;

public class ProtocolTest
{
    private static PropertiesManager<Settings> settings;
    private static SocketManager socketManager;
    private static Protocol proto;

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, CommandException
    {
        settings = Settings.createSettings();
        socketManager = Utils.connect(settings);
        proto = Utils.announceMonitor(settings, socketManager, EventLevel.NONE);
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
    public void testDownloadFile() throws IOException
    {
        // TODO
    }

    @Test
    public void testFillProgramInfo() throws IOException, URISyntaxException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
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
    public void testFreeTuner() throws IOException
    {
        // TODO
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
    public void testGetRecorderFromNumGood() throws IOException, CommandException
    {
        RecorderLocation good = proto.getRecorderFromNum(settings.getIntegerProperty(Settings.RECORDER));
        Assert.assertNotNull(good);
    }

    @Test(expected = CommandException.class)
    public void testGetRecorderFromNumBad() throws IOException, CommandException
    {
        proto.getRecorderFromNum(-1);
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
    public void testLockTuner() throws IOException
    {
        // TODO
    }

    @Test
    public void testMessageClearSettingsCache() throws IOException
    {
        proto.messageClearSettingsCache();
    }

    @Test
    public void testQueryBookmark() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
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
    public void testQueryCommBreak() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
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
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
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
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
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
    public void testQueryFileHash() throws IOException
    {
        // TODO
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
    public void testQueryGetConflicting() throws IOException
    {
        UpcomingRecordings upcoming = proto.queryGetAllPending();
        if (upcoming.isEmpty())
        {
            return;
        }

        ProgramInfo program = null;
        if (upcoming.isConflicted())
        {
            for (ProgramInfo pendingProg : upcoming)
            {
                if (RecordingStatus.CONFLICT.equals(pendingProg.getRecStatus()))
                {
                    program = pendingProg;
                    break;
                }
            }

            Assert.assertNotNull(program);
        }
        else
        {
            program = upcoming.get(0);
        }

        List<ProgramInfo> conflicted = proto.queryGetConflicting(program);
        System.out.println("Conflicted count: " + conflicted.size());
        printFirstFive(conflicted);
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

        Assert.assertEquals(recordings.size(), inProgress.getTotal());
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
    public void testQueryPixMapGetIfModified() throws IOException, CommandException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = recordings.get(0);
        System.out.println("Pix map for "
                           + program.getChannel()
                           + "/"
                           + program.getStartTime()
                           + ": "
                           + proto.queryPixMapGetIfModified(null, Integer.MAX_VALUE, program));
    }

    @Test
    public void testQueryPixMapLastModified() throws IOException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = recordings.get(0);
        System.out.println("Pix map last modified for "
                           + program.getChannel()
                           + "/"
                           + program.getStartTime()
                           + ": "
                           + proto.queryPixMapLastModified(program));
    }

    @Test
    public void testQueryRecordings() throws IOException
    {
        for (RecordingCategory category : proto.getAvailableTypes(RecordingCategory.class))
        {
            List<ProgramInfo> list = proto.queryRecordings(category);
            System.out.println(category.toString() + " count: " + list.size());
            printFirstFive(list);
        }
    }

    @Test
    public void testQueryRecordingBasename() throws IOException, CommandException
    {
        List<ProgramInfo> allRecordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (allRecordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = allRecordings.get(0);
        Assert.assertEquals(program,
                            proto.queryRecordingBasename(program.getFilename()
                                                                .getPath()));
    }

    @Test
    public void testQueryRecordingTimeslot() throws IOException, CommandException
    {
        List<ProgramInfo> allRecordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
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
    public void testQuerySgFileQuery() throws IOException, CommandException
    {
        // TODO
    }

    @Test
    public void testQuerySgGetFileList() throws IOException, CommandException
    {
        // TODO
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

    @Test
    public void testRefreshBackend() throws IOException
    {
        System.out.println("Refreshing backend config");
        proto.refreshBackend();
    }

    @Test
    public void testSetBookmark() throws IOException, CommandException
    {
        // TODO
    }

    @Test
    public void testSetChannelInfo() throws IOException, CommandException
    {
        // TODO
    }

    @Test
    public void testSetNextLiveTvDir() throws IOException, CommandException
    {
        // TODO
    }

    @Test
    public void testSetSetting() throws IOException
    {
        // TODO
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
//    public void testDeleteRecording() throws IOException, MythException
//    {
//        List<ProgramInfo> recorded = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
//        if (recorded.isEmpty())
//        {
//            return;
//        }
//
//        ProgramInfo delete = recorded.get(0);
//        System.out.println("Deleting \"" + delete.getTitle() + ": " + delete.getSubtitle() + "\"");
//        proto.deleteRecording(delete.getChannel(), delete.getRecStartTs(), false, false);
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
//    public void testGoToSleep() throws IOException
//    {
//        try
//        {
//            proto.goToSleep();
//            System.out.println("Backend OK with sleep command");
//        }
//        catch (CommandException e)
//        {
//            System.out.println("Backend refused sleep command: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testMessageSetVerbose() throws IOException, CommandException
//    {
//        proto.setVerbose(new ArrayList<Verbose>() {{ add(Verbose.ALL); add(Verbose.NOT_CHANNEL); }});
//        proto.setVerbose(new ArrayList<Verbose>() {{ add(Verbose.IMPORTANT); add(Verbose.GENERAL); }});
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
//    public void testQueryGenPixMap2() throws IOException, MythException
//    {
//        List<ProgramInfo> programs = proto.queryGetExpiring();
//        if (programs.isEmpty())
//        {
//            return;
//        }
//
//        proto.queryGenPixMap2(Protocol63Test.class.getName(), programs.get(0));
//    }
//
//    @Test
//    public void testRescheduleRecordings() throws IOException
//    {
//        int recorderId = settings.getIntegerProperty(Settings.RECORDER);
//        System.out.println("Requesting reschedule on recorder " + recorderId);
//        proto.rescheduleRecordings(recorderId);
//
//        System.out.println("Requesting full reschedule");
//        proto.rescheduleRecordings(-1);
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
