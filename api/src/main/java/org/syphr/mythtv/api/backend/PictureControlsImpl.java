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
package org.syphr.mythtv.api.backend;

import java.io.IOException;

import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.types.PictureAdjustType;
import org.syphr.mythtv.util.exception.CommandException;

public class PictureControlsImpl implements PictureControls
{
    private final QueryRecorder queryRecorder;
    private final PictureAdjustType type;

    public PictureControlsImpl(QueryRecorder queryRecorder, PictureAdjustType type)
    {
        this.queryRecorder = queryRecorder;
        this.type = type;
    }

    @Override
    public boolean isAvailable()
    {
        return true;
    }

    @Override
    public int getBrightness() throws IOException, CommandException
    {
        return queryRecorder.getBrightness();
    }

    @Override
    public int getColor() throws IOException, CommandException
    {
        return queryRecorder.getColour();
    }

    @Override
    public int getContrast() throws IOException, CommandException
    {
        return queryRecorder.getContrast();
    }

    @Override
    public int getHue() throws IOException, CommandException
    {
        return queryRecorder.getHue();
    }

    @Override
    public int getIncreaseBrightness() throws IOException, CommandException
    {
        return queryRecorder.changeBrightness(type, true);
    }

    @Override
    public int getDecreaseBrightness() throws IOException, CommandException
    {
        return queryRecorder.changeBrightness(type, false);
    }

    @Override
    public int getIncreaseColor() throws IOException, CommandException
    {
        return queryRecorder.changeColour(type, true);
    }

    @Override
    public int getDecreaseColor() throws IOException, CommandException
    {
        return queryRecorder.changeColour(type, false);
    }

    @Override
    public int getIncreaseContrast() throws IOException, CommandException
    {
        return queryRecorder.changeContrast(type, true);
    }

    @Override
    public int getDecreaseContrast() throws IOException, CommandException
    {
        return queryRecorder.changeContrast(type, false);
    }

    @Override
    public int getIncreaseHue() throws IOException, CommandException
    {
        return queryRecorder.changeHue(type, true);
    }

    @Override
    public int getDecreaseHue() throws IOException, CommandException
    {
        return queryRecorder.changeHue(type, false);
    }
}
