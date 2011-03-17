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
package org.syphr.mythtv.protocol;

/**
 * This exception indicates that an error occurred while processing a protocol request.
 * This is usually caused by a condition that the backend has no way to handle - such as
 * requesting information about a recorder that does not exist.<br>
 * <br>
 * This exception will not be thrown for protocol/communication errors. See
 * {@link ProtocolException} instead.
 *
 * @author Gregory P. Moyer
 */
public class CommandException extends Exception
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public CommandException()
    {
        super();
    }

    public CommandException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CommandException(String message)
    {
        super(message);
    }

    public CommandException(Throwable cause)
    {
        super(cause);
    }
}
