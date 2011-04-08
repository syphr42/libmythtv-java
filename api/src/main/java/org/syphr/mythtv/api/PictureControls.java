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
package org.syphr.mythtv.api;

import java.io.IOException;

import org.syphr.mythtv.util.exception.CommandException;

public interface PictureControls
{
    public boolean isAvailable();

    public int getBrightness() throws IOException, CommandException;

    public int getColor() throws IOException, CommandException;

    public int getContrast() throws IOException, CommandException;

    public int getHue() throws IOException, CommandException;

    public int getIncreaseBrightness() throws IOException, CommandException;

    public int getDecreaseBrightness() throws IOException, CommandException;

    public int getIncreaseColor() throws IOException, CommandException;

    public int getDecreaseColor() throws IOException, CommandException;

    public int getIncreaseContrast() throws IOException, CommandException;

    public int getDecreaseContrast() throws IOException, CommandException;

    public int getIncreaseHue() throws IOException, CommandException;

    public int getDecreaseHue() throws IOException, CommandException;
}
