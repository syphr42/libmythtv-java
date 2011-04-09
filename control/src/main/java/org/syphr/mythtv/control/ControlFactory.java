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
package org.syphr.mythtv.control;

import org.syphr.mythtv.control.impl.Control1;
import org.syphr.mythtv.control.impl.Control2;
import org.syphr.mythtv.control.types.ControlVersion;

/**
 * This class acts as a bridge to the various frontend control implementations.
 *
 * @author Gregory P. Moyer
 */
public class ControlFactory
{
    public static Control createInstance(ControlVersion version)
    {
        switch (version)
        {
            case _1:
                return new Control1();

            case _2:
                return new Control2();

            default:
                throw new IllegalArgumentException("Unknown control version: " + version);
        }
    }
}
