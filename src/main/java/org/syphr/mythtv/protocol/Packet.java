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
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/* default */class Packet
{
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final int HEADER_SIZE = 8;
    private static final int PAYLOAD_BUFFER_SIZE = 8192;

    public static String read(ReadableByteChannel in) throws IOException
    {
        byte[] headerBytes = new byte[HEADER_SIZE];
        ByteBuffer headerBuffer = ByteBuffer.wrap(headerBytes);

        int read = 0;

        /*
         * Read the header, which indicates the size of the payload.
         */
        while (headerBuffer.hasRemaining())
        {
            if (Thread.interrupted())
            {
                InterruptedIOException e = new InterruptedIOException();
                e.bytesTransferred = read;
                throw e;
            }

            read += in.read(headerBuffer);
        }

        int size = Integer.parseInt(new String(headerBytes,
                                               0,
                                               headerBytes.length,
                                               UTF8).trim());

        /*
         * Read the payload.
         */
        byte[] payloadBytes = new byte[PAYLOAD_BUFFER_SIZE];
        ByteBuffer payloadBuffer = ByteBuffer.wrap(payloadBytes);

        StringBuilder builder = new StringBuilder();
        read = 0;

        while (read < size)
        {
            if (Thread.interrupted())
            {
                InterruptedIOException e = new InterruptedIOException();
                e.bytesTransferred = read + HEADER_SIZE;
                throw e;
            }

            payloadBuffer.clear();
            payloadBuffer.limit(Math.min(PAYLOAD_BUFFER_SIZE, size - read));

            int justRead = 0;
            while (payloadBuffer.hasRemaining())
            {
                justRead += in.read(payloadBuffer);
            }

            read += justRead;
            builder.append(new String(payloadBytes, 0, justRead, UTF8));
        }

        return builder.toString();
    }

    public static void write(WritableByteChannel out, String value) throws IOException
    {
        byte[] payloadBytes = value.getBytes(UTF8);

        String size = String.format("%-" + HEADER_SIZE + "d", payloadBytes.length);
        byte[] headerBytes = size.getBytes(UTF8);

        ByteBuffer buffer = ByteBuffer.allocate(headerBytes.length + payloadBytes.length);
        buffer.put(headerBytes);
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
