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

import org.syphr.mythtv.http.backend.Database;

public class Database1_0 implements Database
{
    private final String host;
    private final boolean ping;
    private final int port;
    private final String userName;
    private final String password;
    private final String name;
    private final String type;
    private final boolean localEnabled;
    private final String localHostName;

    public Database1_0(String host,
                       boolean ping,
                       int port,
                       String userName,
                       String password,
                       String name,
                       String type,
                       boolean localEnabled,
                       String localHostName)
    {
        this.host = host;
        this.ping = ping;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.type = type;
        this.localEnabled = localEnabled;
        this.localHostName = localHostName;
    }

    @Override
    public String getHost()
    {
        return host;
    }

    @Override
    public boolean isPing()
    {
        return ping;
    }

    @Override
    public int getPort()
    {
        return port;
    }

    @Override
    public String getUserName()
    {
        return userName;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getType()
    {
        return type;
    }

    @Override
    public boolean isLocalEnabled()
    {
        return localEnabled;
    }

    @Override
    public String getLocalHostName()
    {
        return localHostName;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Database1_0 [host=");
        builder.append(host);
        builder.append(", ping=");
        builder.append(ping);
        builder.append(", port=");
        builder.append(port);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", password=");
        builder.append(password);
        builder.append(", name=");
        builder.append(name);
        builder.append(", type=");
        builder.append(type);
        builder.append(", localEnabled=");
        builder.append(localEnabled);
        builder.append(", localHostName=");
        builder.append(localHostName);
        builder.append("]");
        return builder.toString();
    }
}
