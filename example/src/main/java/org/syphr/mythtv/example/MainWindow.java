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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame
{
    /**
     * Seriualization ID
     */
    private static final long serialVersionUID = 1L;

    private final Action actionExit = new AbstractAction()
    {
        /**
         * Serialization ID
         */
        private static final long serialVersionUID = 1L;

        @Override
        public Object getValue(String key)
        {
            if (Action.NAME.equals(key))
            {
                return "Exit";
            }

            return super.getValue(key);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            dispose();
            System.exit(0);
        }
    };

    private final Action actionConnect = new AbstractAction()
    {
        /**
         * Serialization ID
         */
        private static final long serialVersionUID = 1L;

        @Override
        public Object getValue(String key)
        {
            if (Action.NAME.equals(key))
            {
                return "Connect";
            }

            return super.getValue(key);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            // TODO
        }
    };

    public MainWindow()
    {
        super("MythTV Java Example");
        initGui();
    }

    private void initGui()
    {
        setJMenuBar(createMenu());

        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[] { 1.0 };
        layout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        setLayout(layout);

        JPanel panelConnection = createConnectionPanel();
        add(panelConnection, new GridBagConstraints(0,
                                                    0,
                                                    1,
                                                    1,
                                                    1.0,
                                                    1.0,
                                                    GridBagConstraints.NORTH,
                                                    GridBagConstraints.HORIZONTAL,
                                                    new Insets(3, 3, 0, 3),
                                                    0,
                                                    0));

        JPanel panelSelection = createSelectionPanel();
        add(panelSelection, new GridBagConstraints(0,
                                                   1,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.NORTH,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(3, 3, 0, 3),
                                                   0,
                                                   0));

        JPanel panelPlayer = createPlayerPanel();
        add(panelPlayer, new GridBagConstraints(0,
                                                2,
                                                1,
                                                1,
                                                1.0,
                                                1.0,
                                                GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(3, 3, 0, 3),
                                                0,
                                                0));

        JPanel panelStatus = createStatusPanel();
        add(panelStatus, new GridBagConstraints(0,
                                                3,
                                                1,
                                                1,
                                                1.0,
                                                1.0,
                                                GridBagConstraints.SOUTH,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 3, 0, 0),
                                                0,
                                                0));
    }

    private JMenuBar createMenu()
    {
        JMenuBar menu = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menuFile.add(actionExit);
        menu.add(menuFile);

        return menu;
    }

    private JPanel createConnectionPanel()
    {
        JPanel panelConnection = new JPanel();

        panelConnection.setBorder(BorderFactory.createTitledBorder("Connection"));

        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        layout.rowWeights = new double[] { 1.0 };
        setLayout(layout);

        JLabel labelHost = new JLabel("Host:");
        panelConnection.add(labelHost,
                            new GridBagConstraints(0,
                                                   0,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 3, 0, 0),
                                                   0,
                                                   0));

        JTextField textHost = new JTextField("localhost");
        panelConnection.add(textHost,
                            new GridBagConstraints(1,
                                                   0,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 3, 0, 0),
                                                   0,
                                                   0));

        JLabel labelPort = new JLabel("Port:");
        panelConnection.add(labelPort,
                            new GridBagConstraints(2,
                                                   0,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 3, 0, 0),
                                                   0,
                                                   0));

        JTextField textPort = new JTextField("6543");
        panelConnection.add(textPort,
                            new GridBagConstraints(3,
                                                   0,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 3, 0, 0),
                                                   0,
                                                   0));

        JLabel labelVersion = new JLabel("Version:");
        panelConnection.add(labelVersion,
                            new GridBagConstraints(4,
                                                   0,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 3, 0, 0),
                                                   0,
                                                   0));

        JComboBox comboVersion = new JComboBox(Version.values());
        panelConnection.add(comboVersion,
                            new GridBagConstraints(5,
                                                   0,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.HORIZONTAL,
                                                   new Insets(0, 3, 0, 0),
                                                   0,
                                                   0));

        JButton buttonConnect = new JButton(actionConnect);
        panelConnection.add(buttonConnect,
                            new GridBagConstraints(6,
                                                   0,
                                                   1,
                                                   1,
                                                   1.0,
                                                   1.0,
                                                   GridBagConstraints.WEST,
                                                   GridBagConstraints.NONE,
                                                   new Insets(0, 3, 0, 0),
                                                   0,
                                                   0));

        return panelConnection;
    }

    private JPanel createSelectionPanel()
    {
        JPanel panelSelection = new JPanel();

        panelSelection.setBorder(BorderFactory.createTitledBorder("Video Selection"));

        // TODO

        return panelSelection;
    }

    private JPanel createPlayerPanel()
    {
        JPanel panelPlayer = new JPanel();

        panelPlayer.setBorder(BorderFactory.createTitledBorder("Player"));

        // TODO

        return panelPlayer;
    }

    private JPanel createStatusPanel()
    {
        JPanel panelStatus = new JPanel();

        panelStatus.setBorder(BorderFactory.createEtchedBorder());

        // TODO

        return panelStatus;
    }
}
