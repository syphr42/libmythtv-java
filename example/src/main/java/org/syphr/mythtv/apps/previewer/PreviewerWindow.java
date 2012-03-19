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
package org.syphr.mythtv.apps.previewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.types.RecordingCategory;

/**
 * This class provides a quick way to examine preview images for existing
 * recordings. It is not meant to be re-used in application code as it takes
 * many shortcuts around the proper Swing way, including making network calls
 * directly inside UI event handlers! If this little app proves useful, I will
 * clean it up.
 * 
 * @author Gregory P. Moyer
 */
public class PreviewerWindow extends JFrame
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Standard logging facility.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PreviewerWindow.class);

    private final Protocol protocol;

    public PreviewerWindow(Protocol protocol)
    {
        this.protocol = protocol;
        initGUI();
    }

    private void initGUI()
    {
        setTitle("Preview Image Viewer");

        setLayout(new BorderLayout());

        final ViewerPanel viewerPanel = new ViewerPanel(protocol);
        viewerPanel.setBorder(BorderFactory.createTitledBorder("Preview Image"));
        add(viewerPanel, BorderLayout.CENTER);

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
                JLabel label = (JLabel)super.getListCellRendererComponent(list,
                                                                          value,
                                                                          index,
                                                                          isSelected,
                                                                          cellHasFocus);

                if (value instanceof Program)
                {
                    Program program = (Program)value;
                    label.setText(program.getTitle() + " - " + program.getSubtitle());
                }
                else
                {
                    label.setText("<No program selected>");
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

                if (item instanceof Program)
                {
                    viewerPanel.setProgram((Program)item);
                }
                else
                {
                    viewerPanel.setProgram(null);
                }
            }
        });

        List<RecordingCategory> categories = protocol.getAvailableTypes(RecordingCategory.class);
        JComboBox categoriesComboBox = new JComboBox(categories.toArray(new RecordingCategory[categories.size()]));
        categoriesComboBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent event)
            {
                if (ItemEvent.SELECTED != event.getStateChange())
                {
                    return;
                }

                List<Program> programs = new ArrayList<Program>();
                programs.add(null);

                RecordingCategory category = (RecordingCategory)event.getItem();
                try
                {
                    programs.addAll(protocol.queryRecordings(category));
                }
                catch (IOException e)
                {
                    LOGGER.error("Unable to retrieve programs of type " + category, e);
                }

                programsComboBox.setModel(new DefaultComboBoxModel(programs.toArray(new Program[programs.size()])));
                programsComboBox.setSelectedItem(null);
            }
        });

        categoriesComboBox.setSelectedItem(null);
        categoriesComboBox.setSelectedIndex(0);

        GridBagLayout selectionLayout = new GridBagLayout();
        selectionLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0 };
        selectionLayout.rowWeights = new double[] { 1.0 };

        JPanel selectionPanel = new JPanel(selectionLayout);
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selection"));
        selectionPanel.add(new JLabel("Category:"), new GridBagConstraints(0,
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
        selectionPanel.add(categoriesComboBox, new GridBagConstraints(1,
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
        selectionPanel.add(new JLabel("Program:"), new GridBagConstraints(2,
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
        selectionPanel.add(programsComboBox, new GridBagConstraints(3,
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

        add(selectionPanel, BorderLayout.NORTH);
    }
}
