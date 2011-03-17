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

import java.io.IOException;

public class ProtocolException extends IOException
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    private final Direction direction;

    public ProtocolException(String message, Direction direction)
    {
        this(message, direction, null);
    }

    public ProtocolException(String message, Direction direction, Throwable cause)
    {
        super(message, cause);
        this.direction = direction;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public static enum Direction
    {
        SEND, RECEIVE
    }
}
