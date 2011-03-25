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

import org.syphr.mythtv.protocol.types.ProtocolVersion;

public enum Version
{
    TWENTY_FOUR("0.24", ProtocolVersion.SIXTY_THREE),
    TWENTY_FIVE("0.25", ProtocolVersion.SIXTY_FIVE);

    private final String display;
    private final ProtocolVersion protocol;

    private Version(String display, ProtocolVersion protocol)
    {
        this.display = display;
        this.protocol = protocol;
    }

    public String getDisplay()
    {
        return display;
    }

    public ProtocolVersion getProtocol()
    {
        return protocol;
    }

    @Override
    public String toString()
    {
        return getDisplay();
    }
}
