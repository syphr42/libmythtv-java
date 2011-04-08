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
package org.syphr.mythtv.control.impl;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

import org.syphr.mythtv.util.socket.Packet;

/**
 * This class represents the lowest level socket communication between the client and a
 * MythTV frontend instance.
 *
 * @author Gregory P. Moyer
 */
/* default */class ControlPacket implements Packet
{
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final String READ_MESSAGE_TERMINATOR = "\r\n# ";
    private static final String WRITE_MESSAGE_TERMINATOR = "\r\n";

    private static final int PAYLOAD_BUFFER_SIZE = 8192;

    @Override
    public String read(ReadableByteChannel in) throws IOException
    {
        byte[] payloadBytes = new byte[PAYLOAD_BUFFER_SIZE];
        ByteBuffer payloadBuffer = ByteBuffer.wrap(payloadBytes);

        StringBuilder builder = new StringBuilder();
        int read = 0;

        while (true)
        {
            if (Thread.interrupted())
            {
                InterruptedIOException e = new InterruptedIOException();
                e.bytesTransferred = read;
                throw e;
            }

            payloadBuffer.clear();

            int justRead = in.read(payloadBuffer);
            if (justRead <= 0)
            {
                break;
            }

            read += justRead;
            builder.append(new String(payloadBytes, 0, justRead, UTF8));
        }

        String response = builder.toString();
        if (response.endsWith(READ_MESSAGE_TERMINATOR))
        {
            response = response.substring(0, response.length() - READ_MESSAGE_TERMINATOR.length());
        }

        return response;
    }

    @Override
    public void write(WritableByteChannel out, String data) throws IOException
    {
        byte[] payloadBytes = data.concat(WRITE_MESSAGE_TERMINATOR).getBytes(UTF8);
        ByteBuffer buffer = ByteBuffer.allocate(payloadBytes.length);
        buffer.put(payloadBytes);

        buffer.flip();

        while (buffer.hasRemaining())
        {
            if (Thread.interrupted())
            {
                InterruptedIOException e = new InterruptedIOException();
                e.bytesTransferred = buffer.position();
                throw e;
            }

            out.write(buffer);
        }
    }
}
