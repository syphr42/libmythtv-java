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
package org.syphr.mythtv.example;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.ResourceUnavailableEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.api.RecordingByteChannel;

public class PlayerPanel extends JPanel
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    private static final int BUFFER_SIZE = 1024 * 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerPanel.class);

    private final ExecutorService executor;

    private Future<Void> downloader;

    private Player player;

    private RecordingByteChannel channel;

    public PlayerPanel()
    {
        executor = Executors.newSingleThreadExecutor();
        initGui();
    }

    private void initGui()
    {
        setLayout(new BorderLayout());
    }

    public void start(RecordingByteChannel channel) throws NoPlayerException, IOException
    {
        stop();

        this.channel = channel;

        add(new JLabel("Initializing video player..."), BorderLayout.CENTER);
        revalidate();

        File file = startDownload();

        player = Manager.createPlayer(file.toURI().toURL());
        player.addControllerListener(new ControllerListener()
        {
            @Override
            public void controllerUpdate(ControllerEvent event)
            {
                if (event instanceof RealizeCompleteEvent)
                {
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            LOGGER.trace("player realized - showing video");

                            removeAll();
                            add(player.getVisualComponent(), BorderLayout.CENTER);
                            add(player.getControlPanelComponent(), BorderLayout.SOUTH);
                            revalidate();
                        }
                    });
                }
                else if (event instanceof ResourceUnavailableEvent)
                {
                    LOGGER.error("Resource unavailable: " + ((ResourceUnavailableEvent)event).getMessage());

                    try
                    {
                        stop();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    LOGGER.trace("Received player event: {}", event);
                }
            }
        });

        LOGGER.trace("Starting video player");
        player.start();
    }

    public void stop() throws IOException
    {
        removeAll();
        revalidate();

        if (player != null)
        {
            player.stop();
            player.close();
            player = null;
        }

        if (downloader != null)
        {
            downloader.cancel(true);
            downloader = null;
        }

        if (channel != null)
        {
            channel.close();
        }
    }

    private File startDownload() throws IOException
    {
        final File tmpFile = File.createTempFile("recording-", ".mpg");
        tmpFile.deleteOnExit();

        LOGGER.trace("Starting recording download to \"{}\"", tmpFile);

        downloader = executor.submit(new Callable<Void>()
        {
            @Override
            public Void call() throws IOException
            {
                FileOutputStream outStream = new FileOutputStream(tmpFile);

                try
                {
                    FileChannel out = outStream.getChannel();

                    try
                    {
                        try
                        {
                            long read = 0;
                            long size = channel.size();

                            while (read < size)
                            {
                                read += out.transferFrom(channel, read, Math.min(BUFFER_SIZE, size - read));
                            }
                        }
                        finally
                        {
                            channel.close();
                        }
                    }
                    finally
                    {
                        out.close();
                    }
                }
                finally
                {
                    outStream.close();
                }

                return null;
            }
        });

        return tmpFile;
    }
}
