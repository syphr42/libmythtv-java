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
package org.syphr.mythtv.util.exception;

import java.io.IOException;

/**
 * This exception indicates that the client has given up on waiting for a
 * response from the server due to a timeout being reached.
 * 
 * @author Gregory P. Moyer
 */
public class ResponseTimeoutException extends IOException
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public ResponseTimeoutException()
    {
        super();
    }

    public ResponseTimeoutException(String message)
    {
        super(message);
    }

    public ResponseTimeoutException(Throwable cause)
    {
        super(cause);
    }

    public ResponseTimeoutException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
