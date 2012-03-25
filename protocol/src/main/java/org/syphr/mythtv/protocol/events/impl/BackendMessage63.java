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
package org.syphr.mythtv.protocol.events.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackendMessage63 implements BackendMessage
{
    private final String command;
    private final List<String> args;
    private final List<String> data;

    public BackendMessage63(List<String> message)
    {
        String[] commandBreakdown = message.get(0).split("\\s+");
        command = commandBreakdown[0];

        args = new ArrayList<String>();
        for (int i = 1; i < commandBreakdown.length; i++)
        {
            args.add(commandBreakdown[i]);
        }

        data = new ArrayList<String>(message.subList(1, message.size()));
    }

    @Override
    public String getCommand()
    {
        return command;
    }

    @Override
    public List<String> getArgs()
    {
        return Collections.unmodifiableList(args);
    }

    @Override
    public List<String> getData()
    {
        return Collections.unmodifiableList(data);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Message63 [command=");
        builder.append(command);
        builder.append(", args=");
        builder.append(args);
        builder.append(", data=");
        builder.append(data);
        builder.append("]");
        return builder.toString();
    }
}
