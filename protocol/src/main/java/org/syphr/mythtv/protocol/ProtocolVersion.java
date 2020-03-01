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
package org.syphr.mythtv.protocol;

import java.util.regex.Pattern;

/**
 * This enum provides the list of supported protocol versions.
 * 
 * @author Gregory P. Moyer
 */
public enum ProtocolVersion
{
    _63,
    _72,
    _73,
    _74,
    _75;
    
    /**
     * The pattern used to determine if a potential version string needs a
     * prefix to be a valid enum name.
     */
    private static final Pattern NEEDS_PREFIX_PATTERN = Pattern.compile("^\\d.*$");

    /**
     * Convert a version string to a valid {@link ProtocolVersion}.
     * 
     * @param version
     *            the version string
     * @return the corresponding {@link ProtocolVersion}
     * @throws IllegalArgumentException
     *             if there is no matching {@link ProtocolVersion}
     */
    public static ProtocolVersion convert(String version)
    {
        boolean needsPrefix = NEEDS_PREFIX_PATTERN.matcher(version).matches();
        return needsPrefix
                ? ProtocolVersion.valueOf("_" + version)
                : ProtocolVersion.valueOf(version);
    }
    
    @Override
    public String toString()
    {
        return name().replace("_", "");
    }
}
