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
package org.syphr.mythtv.api;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ReadableByteChannel;

import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.QueryFileTransfer;
import org.syphr.mythtv.protocol.data.ProgramInfo;
import org.syphr.mythtv.protocol.types.FileTransferType;
import org.syphr.mythtv.protocol.types.SeekOrigin;

public class RecordingByteChannel implements ReadableByteChannel
{
    private final ProgramInfo program;

    private final Protocol transferProtocol;
    private final QueryFileTransfer fileTransfer;
    private final ReadableByteChannel channel;

    private boolean open;

    public RecordingByteChannel(Protocol protocol,
                                ProgramInfo program,
                                boolean readAhead,
                                long timeout) throws IOException, CommandException
    {
        this.program = program;

        // TODO what if some of this code succeeds and then an exception is thrown? need to guarantee cleanup
        transferProtocol = protocol.newProtocol();
        transferProtocol.mythProtoVersion();

        fileTransfer = transferProtocol.annFileTransfer(InetAddress.getLocalHost().getHostName(),
                                                        FileTransferType.READ,
                                                        readAhead,
                                                        timeout,
                                                        program.getBasename(),
                                                        program.getStorageGroup(),
                                                        protocol);

        channel = transferProtocol.getChannel();

        open = true;
    }

    public ProgramInfo getProgram()
    {
        return program;
    }

    @Override
    public int read(ByteBuffer dst) throws IOException
    {
        if (!isOpen())
        {
            throw new ClosedChannelException();
        }

        long sent = fileTransfer.requestBlock(dst.remaining());
        if (sent < 0)
        {
            throw new IOException("Unable to transfer data");
        }

        int read = 0;
        while (read < sent)
        {
            int justRead = channel.read(dst);
            if (justRead < 0)
            {
                throw new IOException("Unexpected end of file");
            }

            read += justRead;
        }

        return read;
    }

    public long seek(long position, SeekOrigin origin, long curPosition) throws IOException
    {
        if (!isOpen())
        {
            throw new ClosedChannelException();
        }

        return fileTransfer.seek(position, origin, curPosition);
    }

    public long size() throws IOException
    {
        if (!isOpen())
        {
            throw new ClosedChannelException();
        }

        return fileTransfer.getSize();
    }

    @Override
    public boolean isOpen()
    {
        return open;
    }

    @Override
    public void close() throws IOException
    {
        if (!isOpen())
        {
            return;
        }

        open = false;

        // TODO what if close throws an exception - need to guarantee clean up
        channel.close();
        fileTransfer.done();
        transferProtocol.done();
    }
}
