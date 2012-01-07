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
package org.syphr.mythtv.protocol.test.previews;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    private Image image;

    public void setImage(Image image)
    {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (image != null)
        {
            int imageWidth = image.getWidth(this);
            int imageHeight = image.getHeight(this);

            int centerWidth = getWidth() / 2;
            int centerHeight = getHeight() / 2;

            g.drawImage(image, centerWidth - imageWidth / 2, centerHeight - imageHeight / 2, this);
        }
    }
}