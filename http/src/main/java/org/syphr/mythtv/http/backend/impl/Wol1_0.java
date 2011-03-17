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
package org.syphr.mythtv.http.backend.impl;

import org.syphr.mythtv.http.backend.Wol;

public class Wol1_0 implements Wol
{
    private final boolean enabled;
    private final int reconnect;
    private final int retry;
    private final String command;

    public Wol1_0(boolean enabled, int reconnect, int retry, String command)
    {
        this.enabled = enabled;
        this.reconnect = reconnect;
        this.retry = retry;
        this.command = command;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public int getReconnect()
    {
        return reconnect;
    }

    @Override
    public int getRetry()
    {
        return retry;
    }

    @Override
    public String getCommand()
    {
        return command;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Wol1_0 [enabled=");
        builder.append(enabled);
        builder.append(", reconnect=");
        builder.append(reconnect);
        builder.append(", retry=");
        builder.append(retry);
        builder.append(", command=");
        builder.append(command);
        builder.append("]");
        return builder.toString();
    }
}
