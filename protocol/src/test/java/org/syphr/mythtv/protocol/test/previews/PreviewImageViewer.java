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
package org.syphr.mythtv.protocol.test.previews;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.data.ProgramInfo;
import org.syphr.mythtv.protocol.EventLevel;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.test.Utils;
import org.syphr.mythtv.test.Settings;
import org.syphr.mythtv.types.RecordingCategory;
import org.syphr.mythtv.util.exception.CommandException;
import org.syphr.mythtv.util.socket.SocketManager;
import org.syphr.prom.PropertiesManager;

/**
 * This class provides a quick way to examine preview images for existing
 * recordings. It is not meant to be re-used in application code as it takes
 * many shortcuts around the proper Swing way and does no caching of images.
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

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            logger.warn("Unable to set system look & feel", e);
        }

        final ViewerPanel viewerPanel = new ViewerPanel(proto);
        viewerPanel.setBorder(BorderFactory.createTitledBorder("Preview Image"));

        final JComboBox programsComboBox = new JComboBox();
        programsComboBox.setRenderer(new DefaultListCellRenderer()
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

                if (value instanceof ProgramInfo)
                {
                    ProgramInfo program = (ProgramInfo) value;
                    label.setText(program.getTitle()
                                  + " - "
                                  + program.getSubtitle());
                }
                else
                {
                    label.setText(value.toString());
                }

                return label;
            }
        });
        programsComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (ItemEvent.SELECTED != e.getStateChange())
                {
                    return;
                }

                Object item = e.getItem();

                if (item instanceof ProgramInfo)
                {
                    viewerPanel.setProgram((ProgramInfo)item);
                }
                else
                {
                    viewerPanel.setProgram(null);
                }
            }
        });

        JComboBox categoriesComboBox = new JComboBox(proto.getAvailableTypes(RecordingCategory.class).toArray());
        categoriesComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (ItemEvent.SELECTED != e.getStateChange())
                {
                    return;
                }

                List<Object> programs = new ArrayList<Object>();
                programs.add("<No program selected>");

                RecordingCategory category = (RecordingCategory)e.getItem();
                try
                {
                    programs.addAll(proto.queryRecordings(category));
                }
                catch (IOException e1)
                {
                    logger.error("Unable to retrieve programs of type " + category);
                }

                programsComboBox.setModel(new DefaultComboBoxModel(programs.toArray()));
                programsComboBox.setSelectedItem(null);
                programsComboBox.setSelectedIndex(0);
            }
        });

        categoriesComboBox.setSelectedItem(null);
        categoriesComboBox.setSelectedIndex(0);

        GridBagLayout selectionLayout = new GridBagLayout();
        selectionLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0 };
        selectionLayout.rowWeights = new double[] { 1.0 };

        JPanel selectionPanel = new JPanel(selectionLayout);
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selection"));
        selectionPanel.add(new JLabel("Category:"),
                           new GridBagConstraints(0,
                                                  0,
                                                  1,
                                                  1,
                                                  0.0,
                                                  0.0,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 3, 0, 0),
                                                  0,
                                                  0));
        selectionPanel.add(categoriesComboBox,
                           new GridBagConstraints(1,
                                                  0,
                                                  1,
                                                  1,
                                                  0.0,
                                                  0.0,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 3, 0, 3),
                                                  0,
                                                  0));
        selectionPanel.add(new JLabel("Program:"),
                           new GridBagConstraints(2,
                                                  0,
                                                  1,
                                                  1,
                                                  0.0,
                                                  0.0,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 3, 0, 0),
                                                  0,
                                                  0));
        selectionPanel.add(programsComboBox,
                           new GridBagConstraints(3,
                                                  0,
                                                  1,
                                                  1,
                                                  0.0,
                                                  0.0,
                                                  GridBagConstraints.WEST,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(0, 3, 0, 3),
                                                  0,
                                                  0));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(selectionPanel, BorderLayout.NORTH);
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
}
