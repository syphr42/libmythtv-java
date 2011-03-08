package org.syphr.mythtv.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import org.syphr.mythtv.proto.Protocol;
import org.syphr.mythtv.proto.ProtocolFactory;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.Channel;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.data.TunerStatus;
import org.syphr.mythtv.proto.events.BackendEventListener;
import org.syphr.mythtv.proto.events.BackendEventListener63;
import org.syphr.mythtv.proto.events.SystemEvent;
import org.syphr.mythtv.proto.events.SystemEventData;
import org.syphr.mythtv.proto.types.ConnectionType;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.ProtocolVersion;
import org.syphr.prom.PropertiesManager;

public class EventMonitor
{
    public static void main(String[] args) throws IOException
    {
        if (args.length == 0)
        {
            System.out.println("Usage: java "
                               + EventMonitor.class.getName()
                               + " <event level>");
            System.exit(1);
        }

        PropertiesManager<Settings> settings = Settings.createSettings();

        SocketManager socketManager = Utils.connect(settings);
        Protocol proto = ProtocolFactory.createInstance(settings.getEnumProperty(Settings.PROTOCOL_VERSION,
                                                                                 ProtocolVersion.class),
                                                        socketManager);
        proto.addBackendEventListener(eventListener63);

        EventLevel level = EventLevel.valueOf(args[0].toUpperCase());
        System.out.println("Listening for backend events at level " + level);
        System.out.println("Hit [ENTER] to quit.");
        System.out.println();

        try
        {
            proto.mythProtoVersion();
            proto.ann(ConnectionType.MONITOR, InetAddress.getLocalHost().getHostName(), level);

            try
            {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                input.readLine();
            }
            finally
            {
                proto.done();
            }
        }
        finally
        {
            socketManager.disconnect();
        }
    }

    private static BackendEventListener eventListener63 = new BackendEventListener63()
    {
        @Override
        public void clearSettingsCache()
        {
            System.out.println("Cleared settings cache");
        }

        @Override
        public void commFlagRequest(Channel channel, Date startTime)
        {
            System.out.println("Commercial flagging request: "
                               + channel
                               + ", "
                               + startTime);
        }

        @Override
        public void doneRecording(int recorder, long seconds, long frames)
        {
            System.out.println("Done recording: "
                               + recorder
                               + ", "
                               + seconds
                               + " secs, "
                               + frames
                               + " frames");
        }

        @Override
        public void downloadFileUpdate(URL remoteUrl,
                                       URI localUri,
                                       long bytesReceived,
                                       long bytesTotal)
        {
            System.out.println("Download file update: "
                               + remoteUrl
                               + ", "
                               + localUri
                               + ", "
                               + bytesReceived
                               + "B, "
                               + bytesTotal
                               + "B");
        }

        @Override
        public void downloadFileFinshed(URL remoteUrl,
                                        URI localUri,
                                        long bytesTotal,
                                        String errorText,
                                        int errorCode)
        {
            System.out.println("Download file finished: "
                               + remoteUrl
                               + ", "
                               + localUri
                               + ", "
                               + bytesTotal
                               + "B, "
                               + errorText
                               + ", "
                               + errorCode);
        }

        @Override
        public void generatedPixmap(Channel channel,
                                    Date timestamp,
                                    String comment,
                                    Date timestamp2,
                                    long num1,
                                    long num2,
                                    byte[] bytes)
        {
            System.out.println("Generated pixmap: "
                               + channel
                               + ", "
                               + timestamp
                               + ", "
                               + comment
                               + ", "
                               + timestamp2
                               + ", "
                               + num1
                               + ", "
                               + num2);
        }

        @Override
        public void liveTvChainUpdate(String chainId)
        {
            System.out.println("Live TV chain update: " + chainId);
        }

        @Override
        public void liveTvWatch(int recorder, boolean recordingIsActive)
        {
            System.out.println("Live TV watch: recorder " + recorder + ", active " + recordingIsActive);
        }

        @Override
        public void masterUpdateProgInfo(Channel channel, Date startTime)
        {
            System.out.println("Master update program info: "
                               + channel
                               + ", "
                               + startTime);
        }

        @Override
        public void recordingListChangeAdd(Channel channel, Date startTime)
        {
            System.out.println("Recording list change - ADD: "
                               + channel
                               + ", "
                               + startTime);
        }

        @Override
        public void recordingListChangeUpdate(ProgramInfo program)
        {
            System.out.println("Recording list change - UPDATE: " + program);
        }

        @Override
        public void recordingListChangeDelete(Channel channel, Date startTime)
        {
            System.out.println("Recording list change - DELETE: "
                               + channel
                               + ", "
                               + startTime);
        }

        @Override
        public void scheduleChange()
        {
            System.out.println("Schedule changed");
        }

        @Override
        public void signalMessage(int recorder, String message)
        {
            System.out.println("Signal: recorder " + recorder + ", " + message);
        }

        @Override
        public void signalTunerStatus(int recorder, TunerStatus tunerStatus)
        {
            System.out.println("Signal: recorder " + recorder + ", " + tunerStatus);
        }

        @Override
        public void systemEvent(SystemEvent event,
                                Map<SystemEventData, String> data)
        {
            System.out.println("System event: " + event + ", " + data);
        }

        @Override
        public void updateFileSize(Channel channel, Date startTime, long size)
        {
            System.out.println("Update file size: "
                               + channel
                               + ", "
                               + startTime
                               + ", "
                               + size);
        }

        @Override
        public void videoListChange()
        {
            System.out.println("Video list changed");
        }
    };
}
