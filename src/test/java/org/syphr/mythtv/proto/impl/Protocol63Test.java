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
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.mythtv.proto.Protocol;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.Channel;
import org.syphr.mythtv.proto.data.DriveInfo;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.data.UpcomingRecordings;
import org.syphr.mythtv.proto.impl.Protocol63;
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
                            proto.queryRecordingTimeslot(new Channel(program.getChanId()),
                                                         program.getStartTime()));
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
