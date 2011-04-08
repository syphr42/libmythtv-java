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
package org.syphr.mythtv.control.impl;

import java.io.IOException;

import org.syphr.mythtv.control.types.JumpPoint;
import org.syphr.mythtv.util.translate.Translator;

public class Control1 extends AbstractControl
{
    public Control1()
    {
        super(new ControlSocketManager());
    }

    @Override
    public void jump(JumpPoint jumpPoint) throws IOException
    {
        new Command1Jump(jumpPoint).send(getSocketManager());
    }

    @Override
    public void play() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void query() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void set() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void screenshot() throws IOException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void exit() throws IOException
    {
        new Command1Exit().send(getSocketManager());
        super.exit();
    }

    @Override
    protected Translator getTranslator()
    {
        return Control1Utils.getTranslator();
    }
}
