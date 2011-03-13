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
package org.syphr.mythtv.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.proto.CommandException;
import org.syphr.mythtv.proto.Protocol;
import org.syphr.mythtv.proto.SocketManager;
import org.syphr.mythtv.proto.data.PixMap;
import org.syphr.mythtv.proto.data.ProgramInfo;
import org.syphr.mythtv.proto.types.EventLevel;
import org.syphr.mythtv.proto.types.RecordingCategory;
import org.syphr.prom.PropertiesManager;

/**
 * This class provides a quick way to examine preview images for existing
 * recordings. It is not meant to be re-used in application code as it takes
 * many shortcuts around the proper Swing way.
 *
 * @author Gregory P. Moyer
 */
public class PreviewImageViewer
{
    public static void main(String[] args) throws IOException, CommandException
    {
        final Logger logger = LoggerFactory.getLogger(PreviewImageViewer.class);

        PropertiesManager<Settings> settings = Settings.createSettings();

        final SocketManager socketManager = Utils.connect(settings);
        final Protocol proto = Utils.announceMonitor(settings, socketManager, EventLevel.NONE);

        List<ProgramInfo> recordings = proto.queryRecordings(RecordingCategory.PLAY);
        recordings.add(0, null);

        final ViewerPanel viewerPanel = new ViewerPanel(proto);

        JComboBox recordingsComboBox = new JComboBox(recordings.toArray());
        recordingsComboBox.setRenderer(new DefaultListCellRenderer()
        {
            /**
             * Serialization ID
             */
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus)
            {
                JLabel label = (JLabel) super.getListCellRendererComponent(list,
                                                                           value,
                                                                           index,
                                                                           isSelected,
                                                                           cellHasFocus);

                if (value == null)
                {
                    label.setText("<No program selected>");
                }
                else
                {
                    ProgramInfo program = (ProgramInfo) value;
                    label.setText(program.getTitle()
                                  + " - "
                                  + program.getSubtitle());
                }

                return label;
            }
        });
        recordingsComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (ItemEvent.SELECTED != e.getStateChange())
                {
                    return;
                }

                ProgramInfo program = (ProgramInfo)e.getItem();
                viewerPanel.setProgram(program);
            }
        });
        recordingsComboBox.setSelectedIndex(0);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(recordingsComboBox, BorderLayout.NORTH);
        mainPanel.add(viewerPanel, BorderLayout.CENTER);

        JFrame frame = new JFrame("Preview Image Viewer");
        frame.setContentPane(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent event)
            {
                try
                {
                    proto.done();
                }
                catch (IOException e)
                {
                    logger.error("Failed to close connection gracefully", e);
                }

                socketManager.disconnect();
            }
        });

        frame.setVisible(true);
    }

    private static class ViewerPanel extends JPanel
    {
        /**
         * Serialization ID
         */
        private static final long serialVersionUID = 1L;

        private static final String SELECTED_PROGRAM = "Selected Program: ";

        private final Protocol proto;

        private final JLabel selectedProgram = new JLabel(SELECTED_PROGRAM);
        private final DisplayPanel displayPanel = new DisplayPanel();

        public ViewerPanel(Protocol proto)
        {
            this.proto = proto;
            initGUI();
        }

        private void initGUI()
        {
            setLayout(new BorderLayout());

            add(selectedProgram, BorderLayout.NORTH);
            add(displayPanel, BorderLayout.CENTER);
        }

        public void setProgram(ProgramInfo program)
        {
            displayPanel.setImage(null);

            if (program == null)
            {
                selectedProgram.setText(SELECTED_PROGRAM + "none");
                return;
            }

            selectedProgram.setText(SELECTED_PROGRAM
                                    + program.getTitle()
                                    + " / "
                                    + program.getSubtitle()
                                    + " ( "
                                    + program.getStartTime()
                                    + " )");

            try
            {
                PixMap pixMap = proto.queryPixMapGetIfModified(null,
                                                               Integer.MAX_VALUE,
                                                               program);

                byte[] data = pixMap.getData();
                if (data == null)
                {
                    selectedProgram.setText(selectedProgram.getText() + " - No preview image found!");
                    return;
                }

                final BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
                displayPanel.setImage(image);
            }
            catch (IOException e)
            {
                selectedProgram.setText(selectedProgram.getText() + " - Error downloading preview image!");
                return;
            }
            catch (CommandException e)
            {
                selectedProgram.setText(selectedProgram.getText() + " - Error finding preview image!");
                return;
            }
        }
    }

    private static class DisplayPanel extends JPanel
    {
        /**
         * Serialization ID
         */
        private static final long serialVersionUID = 1L;

        private Image image;

        public void setImage(Image image)
        {
            this.image = image;

            if (image != null)
            {
                setSize(image.getWidth(this), image.getHeight(this));
            }

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if (image != null)
            {
                g.drawImage(image, 0, 0, this);
            }
        }
    }
}
