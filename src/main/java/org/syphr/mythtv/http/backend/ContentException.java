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
package org.syphr.mythtv.http.backend;

import java.io.IOException;

/**
 * This exception indicates that an unexpected response (or lack of one) was received from
 * the backend or an attempt was made to send data to a backend that does not understand
 * it. The likely cause of this is that the backend is implementing a version of the data
 * that this library does not support.
 *
 * @author Gregory P. Moyer
 */
public class ContentException extends IOException
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    public ContentException()
    {
        super();
    }

    public ContentException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ContentException(String message)
    {
        super(message);
    }

    public ContentException(Throwable cause)
    {
        super(cause);
    }
}
