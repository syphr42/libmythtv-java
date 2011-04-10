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
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.ProgramInfo;
import org.syphr.mythtv.data.RecorderLocation;
import org.syphr.mythtv.data.RecordingsInProgress;
import org.syphr.mythtv.data.UpcomingRecordings;
import org.syphr.mythtv.protocol.test.Utils;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.RecordingStatus;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.prom.PropertiesManager;

public class ProtocolTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolTest.class);

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
            Assert.assertEquals(0,
                                proto.checkRecording(expiringPrograms.get(0)));
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

        LOGGER.debug("File info for "
                     + fillInProgram.getChannel()
                     + "/"
                     + fillInProgram.getRecStartTs()
                     + ": "
                     + fillInProgram.getFilename()
                     + " / "
                     + fillInProgram.getFileSize());
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

        LOGGER.debug("Free recorder count: {}", count);
        LOGGER.debug("Free recorders: {}", freeRecorders);

        RecorderLocation freeRecorder = proto.getFreeRecorder();
        if (count > 0)
        {
            Assert.assertNotNull(freeRecorder);
        }
        else
        {
            Assert.assertNull(freeRecorder);
        }

        LOGGER.debug("Next free recorder after {}: {}",
                     freeRecorder.getId(),
                     proto.getNextFreeRecorder(freeRecorder));
    }

    @Test
    public void testGetRecorderFromNumGood() throws IOException,
                                            CommandException
    {
        RecorderLocation good = proto.getRecorderFromNum(settings.getIntegerProperty(Settings.RECORDER));
        Assert.assertNotNull(good);
    }

    @Test(expected = CommandException.class)
    public void testGetRecorderFromNumBad() throws IOException,
                                           CommandException
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
        LOGGER.debug("Bookmark for "
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
        LOGGER.debug("Commercial breaks for "
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
        LOGGER.debug("Cut list marks for "
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
        LOGGER.debug("URI for "
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
            LOGGER.debug(drive.toString());
        }
    }

    @Test
    public void testQueryFreeSpaceSummary() throws IOException
    {
        LOGGER.debug(proto.queryFreeSpaceSummary().toString());
    }

    @Test
    public void testQueryGetAllPending() throws IOException
    {
        UpcomingRecordings upcoming = proto.queryGetAllPending();

        LOGGER.debug("Conflicts? {}", upcoming.isConflicted());
        LOGGER.debug("Upcoming count: {}", upcoming.size());
        org.syphr.mythtv.test.Utils.printFirstFive(upcoming, LOGGER);
    }

    @Test
    public void testQueryGetAllScheduled() throws IOException
    {
        List<ProgramInfo> scheduled = proto.queryGetAllScheduled();
        LOGGER.debug("Scheduled count: {}", scheduled.size());
        org.syphr.mythtv.test.Utils.printFirstFive(scheduled, LOGGER);
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
        LOGGER.debug("Conflicted count: {}", conflicted.size());
        org.syphr.mythtv.test.Utils.printFirstFive(conflicted, LOGGER);
    }

    @Test
    public void testQueryGetExpiring() throws IOException
    {
        List<ProgramInfo> expiring = proto.queryGetExpiring();
        LOGGER.debug("Expiring count: {}", expiring.size());
        org.syphr.mythtv.test.Utils.printFirstFive(expiring, LOGGER);
    }

    @Test
    public void testQueryGuideDataThrough() throws IOException
    {
        LOGGER.debug("Guide data through: {}", proto.queryGuideDataThrough());
    }

    @Test
    public void testQueryHostname() throws IOException
    {
        LOGGER.debug("Hostname: {}", proto.queryHostname());
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

        LOGGER.debug(inProgress.toString());

        Assert.assertEquals(recordings.size(), inProgress.getTotal());
    }

    @Test
    public void testQueryLoad() throws IOException
    {
        LOGGER.debug(proto.queryLoad().toString());
    }

    @Test
    public void testQueryMemStats() throws IOException
    {
        LOGGER.debug(proto.queryMemStats().toString());
    }

    @Test
    public void testQueryPixMapGetIfModified() throws IOException,
                                              CommandException
    {
        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        ProgramInfo program = recordings.get(0);
        LOGGER.debug("Pix map for "
                     + program.getChannel()
                     + "/"
                     + program.getStartTime()
                     + ": "
                     + proto.queryPixMapGetIfModified(null,
                                                      Integer.MAX_VALUE,
                                                      program));
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
        LOGGER.debug("Pix map last modified for "
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
            LOGGER.debug("{} count: {}", category.toString(), list.size());
            org.syphr.mythtv.test.Utils.printFirstFive(list, LOGGER);
        }
    }

    @Test
    public void testQueryRecordingBasename() throws IOException,
                                            CommandException
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
    public void testQueryRecordingTimeslot() throws IOException,
                                            CommandException
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
        LOGGER.debug(proto.queryTimeZone().toString());
    }

    @Test
    public void testQueryUptime() throws IOException
    {
        LOGGER.debug("Uptime: {} secs", proto.queryUptime());
    }

    @Test
    public void testRefreshBackend() throws IOException
    {
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
//    public void testDeleteRecording() throws IOException, CommandException
//    {
//        List<ProgramInfo> recorded = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
//        if (recorded.isEmpty())
//        {
//            return;
//        }
//
//        ProgramInfo delete = recorded.get(0);
//        LOGGER.debug("Deleting \"{}\" : \"{}\"", delete.getTitle(), delete.getSubtitle());
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
//        LOGGER.debug("Forgetting \"{}\" : \"{}\"", forget.getTitle(), forget.getSubtitle());
//        proto.forgetRecording(forget);
//    }
//
//    @Test
//    public void testGoToSleep() throws IOException
//    {
//        try
//        {
//            proto.goToSleep();
//            LOGGER.debug("Backend OK with sleep command");
//        }
//        catch (CommandException e)
//        {
//            LOGGER.debug("Backend refused sleep command: " + e.getMessage());
//        }
//    }
//
//    @Test
//    public void testMessageSetVerbose() throws IOException, CommandException
//    {
//        proto.messageSetVerbose(new ArrayList<Verbose>() {{ add(Verbose.ALL); add(Verbose.NOT_CHANNEL); }});
//        proto.messageSetVerbose(new ArrayList<Verbose>() {{ add(Verbose.IMPORTANT); add(Verbose.GENERAL); }});
//    }
//
//    @Test
//    public void testShutdownNow() throws IOException
//    {
//        String backendHost = settings.getProperty(Settings.BACKEND_HOST);
//        LOGGER.debug("Attempting to shut down {}", backendHost);
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
//
//        LOGGER.debug("Stopping \"{}\" : \"{}\"", stop.getTitle(), stop.getSubtitle());
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
//        LOGGER.debug("Undeleting \"{}\" : \"{}\"", undelete.getTitle(), undelete.getSubtitle());
//        Assert.assertTrue(proto.undeleteRecording(undelete));
//    }
//
//    @Test
//    public void testQueryGenPixMap2() throws IOException, CommandException
//    {
//        List<ProgramInfo> programs = proto.queryGetExpiring();
//        if (programs.isEmpty())
//        {
//            return;
//        }
//
//        proto.queryGenPixMap2(getClass().getName(), programs.get(0));
//    }
//
//    @Test
//    public void testRescheduleRecordings() throws IOException
//    {
//        int recorderId = settings.getIntegerProperty(Settings.RECORDER);
//        LOGGER.debug("Requesting reschedule on recorder {}", recorderId);
//        proto.rescheduleRecordings(recorderId);
//
//        LOGGER.debug("Requesting full reschedule");
//        proto.rescheduleRecordings(-1);
//    }
}
