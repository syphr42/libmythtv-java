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
package org.syphr.mythtv.control;

/**
 * This enum represents the implemented versions of the frontend network
 * control. These versions coincide with versions of MythTV since the network
 * control has no defined version scheme.
 *
 * @author Gregory P. Moyer
 */
public enum ControlVersion
{
    _0_24,
    _0_25,
    _0_26;
}
