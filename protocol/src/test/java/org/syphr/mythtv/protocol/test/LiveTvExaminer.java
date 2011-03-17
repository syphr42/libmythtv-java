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
package org.syphr.mythtv.protocol.test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.ProtocolFactory;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.SocketManager;
import org.syphr.mythtv.protocol.data.Channel;
import org.syphr.mythtv.protocol.data.RecorderLocation;
import org.syphr.mythtv.protocol.types.ConnectionType;
import org.syphr.mythtv.protocol.types.EventLevel;
import org.syphr.mythtv.protocol.types.ProtocolVersion;
import org.syphr.mythtv.test.Settings;
import org.syphr.prom.PropertiesManager;

public class LiveTvExaminer
{
    public static void main(String[] args) throws IOException, InterruptedException, CommandException
    {
        if (args.length == 0)
        {
            System.out.println("Usage: java " + LiveTvExaminer.class.getName()
                               + " CHANNEL [RECORDER]");
            System.exit(1);
        }

        PropertiesManager<Settings> settings = Settings.createSettings();

        SocketManager socketManager = new SocketManager();
        socketManager.connect(settings.getProperty(Settings.BACKEND_HOST),
                              settings.getIntegerProperty(Settings.BACKEND_PROTOCOL_PORT),
                              settings.getIntegerProperty(Settings.BACKEND_PROTOCOL_TIMEOUT));

        Protocol proto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.BACKEND_PROTOCOL_VERSION,
                                                                                 ProtocolVersion.class),
                                                        socketManager);
        proto.mythProtoVersion();
        proto.ann(ConnectionType.MONITOR, InetAddress.getLocalHost().getHostName(), EventLevel.NONE);

        int recorderId;
        if (args.length > 1)
        {
            recorderId = Integer.parseInt(args[1]);
        }
        else
        {
            RecorderLocation recorderLoc = proto.getFreeRecorder();
            if (recorderLoc == null)
            {
                System.out.println("Cannot find a free recorder");
                return;
            }

            recorderId = recorderLoc.getId();
        }

        try
        {
            QueryRecorder queryRecorder = proto.queryRecorder(recorderId);
            queryRecorder.spawnLiveTv("live-livetv-tester-" + new Date().getTime(),
                                      false,
                                      new Channel(-1, -1, args[0], null, null));

            try
            {
                Thread.sleep(30000);
            }
            finally
            {
                queryRecorder.stopLiveTv();
            }
        }
        finally
        {
            proto.done();
            socketManager.disconnect();
        }
    }
}
