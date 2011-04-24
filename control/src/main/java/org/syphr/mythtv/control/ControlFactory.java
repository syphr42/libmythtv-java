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

import org.syphr.mythtv.control.impl.Control0_24;
import org.syphr.mythtv.control.impl.Control0_25;

/**
 * This class acts as a bridge to the various frontend network control
 * implementations.
 *
 * @author Gregory P. Moyer
 */
public class ControlFactory
{
    /**
     * Create a new instance of the frontend network control for the desired
     * version.
     *
     * @param version
     *            the desired network control version
     * @return a new network control instance
     */
    public static Control createInstance(ControlVersion version)
    {
        switch (version)
        {
            case _0_24:
                return new Control0_24();

            case _0_25:
                return new Control0_25();

            default:
                throw new IllegalArgumentException("Unknown control version: " + version);
        }
    }
}
