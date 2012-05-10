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
package org.syphr.mythtv.commons.socket;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class DefaultPacket implements Packet
{
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final int DEFAULT_PAYLOAD_BUFFER_SIZE = 8192;

    private static final String DEFAULT_MESSAGE_TERMINATOR_LITERAL = "\n";

    private Charset charset = DEFAULT_CHARSET;

    private int bufferSize = DEFAULT_PAYLOAD_BUFFER_SIZE;

    private String messageTerminator = DEFAULT_MESSAGE_TERMINATOR_LITERAL;

    public void setCharset(Charset charset)
    {
        this.charset = charset;
    }

    public Charset getCharset()
    {
        return charset;
    }

    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    public int getBufferSize()
    {
        return bufferSize;
    }

    /**
     * Set the string literal boundary between two messages.
     * 
     * @param messageTerminator
     *            the terminator to set
     */
    public void setMessageTerminator(String messageTerminator)
    {
        this.messageTerminator = messageTerminator;
    }

    /**
     * Retrieve the string literal boundary between two messages.
     * 
     * @return the terminator
     */
    public String getMessageTerminator()
    {
        return messageTerminator;
    }

    @Override
    public List<String> read(ReadableByteChannel in) throws IOException
    {
        byte[] payloadBytes = new byte[getBufferSize()];
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
            builder.append(new String(payloadBytes, 0, justRead, getCharset()));
        }

        String received = builder.toString();
        int messageCount = StringUtils.countMatches(received, getMessageTerminator());

        List<String> messages = new ArrayList<String>(Arrays.asList(builder.toString().split(Pattern.quote(getMessageTerminator()))));

        /*
         * String.split(String) trims empty matches from the end of the array
         * before returning. This will cause disruption for a synchronous
         * protocol, so override this behavior and put the empty strings back.
         */
        for (int i = messages.size(); i < messageCount; i++)
        {
            messages.add("");
        }

        return messages;
    }

    @Override
    public void write(WritableByteChannel out, String data) throws IOException
    {
        byte[] payloadBytes = buildMessage(data).getBytes(getCharset());
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

    protected String buildMessage(String data)
    {
        return data.concat(DEFAULT_MESSAGE_TERMINATOR_LITERAL);
    }
}
