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

import java.io.IOException;

import org.syphr.mythtv.http.backend.ConnectionInfo;
import org.syphr.mythtv.http.backend.ConnectionManager;
import org.syphr.mythtv.http.backend.Myth;

public class Myth1_0 implements Myth
{
    private final ConnectionManager connMan;

    public Myth1_0(ConnectionManager connMan)
    {
        this.connMan = connMan;
    }

    @Override
    public ConnectionInfo getConnectionInfo() throws IOException
    {
        String xml = connMan.getXml("GetConnectionInfo");
        return ConnectionInfo1_0.parse(xml);
    }
}
