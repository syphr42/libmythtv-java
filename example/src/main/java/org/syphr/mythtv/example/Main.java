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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main
{
    public static void main(String[] args)
    {
        setLookAndFeel();

        JFrame mainWindow = new MainWindow();

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centerAndSize(mainWindow, 0.8f);

        mainWindow.setVisible(true);
    }

    private static void setLookAndFeel()
    {
        try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void centerAndSize(JFrame frame, float size)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)(screenSize.getWidth() * size), (int)(screenSize.getHeight() * size));

        frame.setLocationRelativeTo(null);
    }
}
