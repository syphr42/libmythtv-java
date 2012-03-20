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
package org.syphr.mythtv.protocol;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.commons.exception.CommandException;
import org.syphr.mythtv.commons.socket.SocketManager;
import org.syphr.mythtv.data.DriveInfo;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.RecorderLocation;
import org.syphr.mythtv.data.RecordingsInProgress;
import org.syphr.mythtv.data.UpcomingRecordings;
import org.syphr.mythtv.protocol.test.Utils;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.types.RecordingStatus;
import org.syphr.mythtv.types.Verbose;
import org.syphr.prom.PropertiesManager;

public class ProtocolIT
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolIT.class);

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
        List<Program> expiringPrograms = proto.queryGetExpiring();
        if (!expiringPrograms.isEmpty())
        {
            Assert.assertEquals(0, proto.checkRecording(expiringPrograms.get(0)));
        }

        List<Program> recPrograms = proto.queryRecordings(RecordingCategory.RECORDING);
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
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        Program fullProgram = recordings.get(0);
        Program partialProgram = new Program(fullProgram.getTitle(),
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

        Program fillInProgram = proto.fillProgramInfo(settings.getProperty(Settings.FRONTEND_HOST),
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
        List<Program> recording = proto.queryRecordings(RecordingCategory.RECORDING);
        if (!recording.isEmpty())
        {
            Assert.assertNotNull(proto.getRecorderNum(recording.get(0)));
        }

        List<Program> expipring = proto.queryGetExpiring();
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
    public void testMessageResetIdleTime() throws IOException
    {
        proto.messageResetIdleTime();
    }

    @Test
    @SuppressWarnings("serial")
    public void testSetVerbose() throws IOException, CommandException
    {
        proto.messageSetVerbose(new ArrayList<Verbose>()
        {
            {
                add(Verbose.ALL);
            }
        });
        proto.messageSetVerbose(new ArrayList<Verbose>()
        {
            {
                add(Verbose.DEFAULT);
                add(Verbose.NETWORK);
            }
        });
    }

    @Test
    public void testQueryBookmark() throws IOException
    {
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        Program program = recordings.get(0);
        LOGGER.debug("Bookmark for "
                + program.getChannel()
                + "/"
                + program.getStartTime()
                + ": "
                + proto.queryBookmark(program.getChannel(), program.getRecStartTs()));
    }

    @Test
    public void testQueryCommBreak() throws IOException
    {
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        Program program = recordings.get(0);
        LOGGER.debug("Commercial breaks for "
                + program.getChannel()
                + "/"
                + program.getStartTime()
                + ": "
                + proto.queryCommBreak(program.getChannel(), program.getRecStartTs()));
    }

    @Test
    public void testQueryCutList() throws IOException
    {
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        Program program = recordings.get(0);
        LOGGER.debug("Cut list marks for "
                + program.getChannel()
                + "/"
                + program.getStartTime()
                + ": "
                + proto.queryCutList(program.getChannel(), program.getRecStartTs()));
    }

    @Test
    public void testQueryCheckFile() throws IOException
    {
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        Program program = recordings.get(0);
        LOGGER.debug("URI for "
                + program.getChannel()
                + "/"
                + program.getRecStartTs()
                + ": "
                + proto.queryCheckFile(true, program));
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
    public void testQueryGenPixMap2() throws IOException, CommandException
    {
        List<Program> programs = proto.queryGetExpiring();
        if (programs.isEmpty())
        {
            return;
        }

        proto.queryGenPixMap2(getClass().getName(), programs.get(0));
    }

    @Test
    public void testQueryGetAllPending() throws IOException
    {
        UpcomingRecordings upcoming = proto.queryGetAllPending();

        LOGGER.debug("Conflicts? {}", upcoming.isConflicted());
        LOGGER.debug("Upcoming count: {}", upcoming.size());
        org.syphr.mythtv.test.Utils.printFirstFive(upcoming);
    }

    @Test
    public void testQueryGetAllScheduled() throws IOException
    {
        List<Program> scheduled = proto.queryGetAllScheduled();
        LOGGER.debug("Scheduled count: {}", scheduled.size());
        org.syphr.mythtv.test.Utils.printFirstFive(scheduled);
    }

    @Test
    public void testQueryGetConflicting() throws IOException
    {
        UpcomingRecordings upcoming = proto.queryGetAllPending();
        if (upcoming.isEmpty())
        {
            return;
        }

        Program program = null;
        if (upcoming.isConflicted())
        {
            for (Program pendingProg : upcoming)
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

        List<Program> conflicted = proto.queryGetConflicting(program);
        LOGGER.debug("Conflicted count: {}", conflicted.size());
        org.syphr.mythtv.test.Utils.printFirstFive(conflicted);
    }

    @Test
    public void testQueryGetExpiring() throws IOException
    {
        List<Program> expiring = proto.queryGetExpiring();
        LOGGER.debug("Expiring count: {}", expiring.size());
        org.syphr.mythtv.test.Utils.printFirstFive(expiring);
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
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDING);
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
    public void testQueryPixMapGetIfModified() throws IOException, CommandException
    {
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        for (Program recording : recordings)
        {
            /*
             * Live TV will not have pix maps and will generate an exception
             * here.
             */
            if ("LiveTV".equals(recording.getStorageGroup()))
            {
                continue;
            }

            LOGGER.debug("Pix map for "
                    + recording.getChannel()
                    + "/"
                    + recording.getStartTime()
                    + ": "
                    + proto.queryPixMapGetIfModified(null, Integer.MAX_VALUE, recording));
        }
    }

    @Test
    public void testQueryPixMapLastModified() throws IOException
    {
        List<Program> recordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (recordings.isEmpty())
        {
            return;
        }

        Program program = recordings.get(0);
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
            List<Program> list = proto.queryRecordings(category);
            LOGGER.debug("{} count: {}", category.toString(), list.size());
            org.syphr.mythtv.test.Utils.printFirstFive(list);
        }
    }

    @Test
    public void testQueryRecordingBasename() throws IOException, CommandException
    {
        List<Program> allRecordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (allRecordings.isEmpty())
        {
            return;
        }

        Program program = allRecordings.get(0);
        Assert.assertEquals(program, proto.queryRecordingBasename(program.getFilename().getPath()));
    }

    @Test
    public void testQueryRecordingTimeslot() throws IOException, CommandException
    {
        List<Program> allRecordings = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
        if (allRecordings.isEmpty())
        {
            return;
        }

        Program program = allRecordings.get(0);
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

    @Test
    public void testScanVideos() throws IOException
    {
        proto.scanVideos();
    }

    @Test
    public void testQueryActiveBackends() throws IOException
    {
        proto.queryActiveBackends();
    }

    /*
     * ---------------------------------------------------------------- The
     * following unit tests can cause side effects. Use with care.
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
    //        List<Program> recorded = proto.queryRecordings(RecordingCategory.RECORDED_UNSORTED);
    //        if (recorded.isEmpty())
    //        {
    //            return;
    //        }
    //
    //        Program delete = recorded.get(0);
    //        LOGGER.debug("Deleting \"{}\" : \"{}\"", delete.getTitle(), delete.getSubtitle());
    //        proto.deleteRecording(delete.getChannel(), delete.getRecStartTs(), false, false);
    //    }
    //
    //    @Test
    //    public void testForgetRecording() throws IOException
    //    {
    //        List<Program> expiring = proto.queryGetExpiring();
    //        if (expiring.isEmpty())
    //        {
    //            return;
    //        }
    //
    //        Program forget = expiring.get(0);
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
    //        List<Program> recording = proto.queryRecordings(RecordingCategory.RECORDING);
    //        if (recording.isEmpty())
    //        {
    //            return;
    //        }
    //
    //        Program stop = recording.get(0);
    //
    //        LOGGER.debug("Stopping \"{}\" : \"{}\"", stop.getTitle(), stop.getSubtitle());
    //        Assert.assertNotSame(-1, proto.stopRecording(stop));
    //    }
    //
    //    @Test
    //    public void testUndeleteRecording() throws IOException
    //    {
    //        List<Program> expiring = proto.queryGetExpiring();
    //        if (expiring.isEmpty())
    //        {
    //            return;
    //        }
    //
    //        Program undelete = expiring.get(0);
    //        LOGGER.debug("Undeleting \"{}\" : \"{}\"", undelete.getTitle(), undelete.getSubtitle());
    //        Assert.assertTrue(proto.undeleteRecording(undelete));
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
