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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syphr.mythtv.commons.exception.CommandException;

/**
 * This exception indicates that an attempt was made to connect to a backend
 * using an invalid protocol version.
 * 
 * @author Gregory P. Moyer
 */
public class InvalidProtocolVersionException extends CommandException
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Standard logging facility.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidProtocolVersionException.class);

    /**
     * The exact attempted version as sent to the remote server.
     */
    private final String attemptedVersionStr;

    /**
     * The local representation of the attempted version sent to the remote
     * server.
     */
    private final ProtocolVersion attemptedVersion;

    /**
     * The exact supported version as announced by the remote server.
     */
    private final String supportedVersionStr;

    /**
     * The local representation of the supported version announced by the remote
     * server.
     */
    private ProtocolVersion supportedVersion;

    /**
     * Construct a new exception with the given version information.
     * 
     * @param attemptedVersionStr
     *            the exact version used to attempt a connection to the remote
     *            server
     * @param supportedVersionStr
     *            the exact supported version as announced by the remote server
     */
    public InvalidProtocolVersionException(String attemptedVersionStr, String supportedVersionStr)
    {
        super("Attempted version "
                + attemptedVersionStr
                + ", backend supports "
                + supportedVersionStr);

        this.attemptedVersionStr = attemptedVersionStr;
        attemptedVersion = ProtocolVersion.convert(attemptedVersionStr);

        this.supportedVersionStr = supportedVersionStr;
        try
        {
            supportedVersion = ProtocolVersion.convert(supportedVersionStr);
        }
        catch (IllegalArgumentException e)
        {
            LOGGER.warn("Unknown protocol version announced by remote server: {}",
                        supportedVersionStr);
            supportedVersion = null;
        }
    }

    /**
     * Retrieve the exact attempted version as sent to the remote server.
     * 
     * @return the attempted version
     */
    public String getAttemptedVersionStr()
    {
        return attemptedVersionStr;
    }

    /**
     * Retrieve the local representation of the attempted version sent to the
     * remote server.
     * 
     * @return the attempted version
     */
    public ProtocolVersion getAttemptedVersion()
    {
        return attemptedVersion;
    }

    /**
     * Retrieve the exact supported version as announced by the remote server.
     * 
     * @return the supported version
     */
    public String getSupportedVersionStr()
    {
        return supportedVersionStr;
    }

    /**
     * Retrieve the local representation of the supported version announced by
     * the remote server.
     * 
     * @return the supported version or <code>null</code> if the supported
     *         version is not implemented by this framework
     */
    public ProtocolVersion getSupportedVersion()
    {
        return supportedVersion;
    }
}
