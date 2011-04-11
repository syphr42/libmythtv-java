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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.syphr.mythtv.data.PixMap;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.util.exception.CommandException;

public class ViewerPanel extends JPanel
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    private static final String SELECTED_PROGRAM = "Selected Program: ";

    private final Protocol proto;

    private final JLabel selectedProgram = new JLabel(SELECTED_PROGRAM);
    private final ImagePanel imagePanel = new ImagePanel();

    public ViewerPanel(Protocol proto)
    {
        this.proto = proto;
        initGUI();
    }

    private void initGUI()
    {
        setLayout(new BorderLayout());

        add(selectedProgram, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
    }

    public void setProgram(Program program)
    {
        imagePanel.setImage(null);

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
            imagePanel.setImage(image);
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