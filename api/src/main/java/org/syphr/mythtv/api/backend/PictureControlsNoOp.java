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

public class PictureControlsNoOp implements PictureControls
{
    @Override
    public boolean isAvailable()
    {
        return false;
    }

    @Override
    public int getBrightness()
    {
        return -1;
    }

    @Override
    public int getColor()
    {
        return -1;
    }

    @Override
    public int getContrast()
    {
        return -1;
    }

    @Override
    public int getHue()
    {
        return -1;
    }

    @Override
    public int getIncreaseBrightness()
    {
        return -1;
    }

    @Override
    public int getDecreaseBrightness()
    {
        return -1;
    }

    @Override
    public int getIncreaseColor()
    {
        return -1;
    }

    @Override
    public int getDecreaseColor()
    {
        return -1;
    }

    @Override
    public int getIncreaseContrast()
    {
        return -1;
    }

    @Override
    public int getDecreaseContrast()
    {
        return -1;
    }

    @Override
    public int getIncreaseHue()
    {
        return -1;
    }

    @Override
    public int getDecreaseHue()
    {
        return -1;
    }
}
