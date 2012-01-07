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
package org.syphr.mythtv.data;

public class VersionInfo
{
    private final String mythLongVersion;
    private final String mythShortVersion;

    private final String scmPath;

    private final String binaryVersion;

    private final String protocolVersion;

    private final String qtVersion;

    private final String schemaVersion;

    public VersionInfo(String mythLongVersion,
                       String mythShortVersion,
                       String scmPath,
                       String binaryVersion,
                       String protocolVersion,
                       String qtVersion,
                       String schemaVersion)
    {
        this.mythLongVersion = mythLongVersion;
        this.mythShortVersion = mythShortVersion;
        this.scmPath = scmPath;
        this.binaryVersion = binaryVersion;
        this.protocolVersion = protocolVersion;
        this.qtVersion = qtVersion;
        this.schemaVersion = schemaVersion;
    }

    public String getMythLongVersion()
    {
        return mythLongVersion;
    }

    public String getMythShortVersion()
    {
        return mythShortVersion;
    }

    public String getScmPath()
    {
        return scmPath;
    }

    public String getBinaryVersion()
    {
        return binaryVersion;
    }

    public String getProtocolVersion()
    {
        return protocolVersion;
    }

    public String getQtVersion()
    {
        return qtVersion;
    }

    public String getSchemaVersion()
    {
        return schemaVersion;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("VersionInfo [mythLongVersion=");
        builder.append(mythLongVersion);
        builder.append(", mythShortVersion=");
        builder.append(mythShortVersion);
        builder.append(", scmPath=");
        builder.append(scmPath);
        builder.append(", binaryVersion=");
        builder.append(binaryVersion);
        builder.append(", protocolVersion=");
        builder.append(protocolVersion);
        builder.append(", qtVersion=");
        builder.append(qtVersion);
        builder.append(", schemaVersion=");
        builder.append(schemaVersion);
        builder.append("]");
        return builder.toString();
    }
}
