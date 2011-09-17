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
package org.syphr.mythtv.util.socket;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.List;

/**
 * This interface describes the contract for an object that will handle formatting
 * outgoing data and sending it over the wire as well as reading incoming data and parsing
 * it. However, this formatting and parsing should not include specific logic about the
 * meaning of a message, but rather it should be limited to communication requirements
 * (such as adding/removing headers).<br>
 * <br>
 * Implementations of this interface should not retain state about any one communication
 * or the channel itself. A correct implementation must be thread-safe.
 *
 * @author Gregory P. Moyer
 */
public interface Packet
{
    /**
     * Read the next message(s) from the given channel. This method may or may
     * not block based on the channel configuration.
     * 
     * @param in
     *            the channel to read
     * @return the content of any messages after all available data has been
     *         completely read
     * @throws IOException
     *             if an error occurs or this thread is interrupted
     */
    public List<String> read(ReadableByteChannel in) throws IOException;

    /**
     * Write the given value to the specified channel. This method may or may not block
     * based on the channel configuration.
     *
     * @param out
     *            the channel to write
     * @param data
     *            the data to write
     * @throws IOException
     *             if an error occurs or this thread is interrupted
     */
    public void write(WritableByteChannel out, String data) throws IOException;
}
