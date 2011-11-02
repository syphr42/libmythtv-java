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
package org.syphr.mythtv.api.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.syphr.mythtv.data.PixMap;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.data.RecorderLocation;
import org.syphr.mythtv.data.VideoEditInfo;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.util.exception.CommandException;

public class Recording
{
    private final Program program;

    private final Protocol protocol;

    public Recording(Program program, Protocol protocol)
    {
        this.program = program;
        this.protocol = protocol;
    }

    public Program getProgram()
    {
        return program;
    }

    public boolean isCurrentlyRecording() throws IOException
    {
        return protocol.checkRecording(program) > 0;
    }

    public boolean stopRecording() throws IOException
    {
        return protocol.stopRecording(program) > 0;
    }

    public void delete(boolean force, boolean forget) throws IOException, CommandException
    {
        protocol.deleteRecording(program.getChannel(), program.getRecStartTs(), force, forget);
    }

    public boolean undelete() throws IOException
    {
        return protocol.undeleteRecording(program);
    }

    public void forget() throws IOException
    {
        protocol.forgetRecording(program);
    }

    public RecorderLocation getRecorder() throws IOException
    {
        return protocol.getRecorderNum(program);
    }

    public long getBookmark() throws IOException
    {
        return protocol.queryBookmark(program.getChannel(), program.getRecStartTs());
    }

    public boolean setBookmark(long location) throws IOException, CommandException
    {
        return protocol.setBookmark(program.getChannel(), program.getRecStartTs(), location);
    }

    public List<VideoEditInfo> getCommercialBreaks() throws IOException
    {
        return protocol.queryCommBreak(program.getChannel(), program.getRecStartTs());
    }

    public List<VideoEditInfo> getCutList() throws IOException
    {
        return protocol.queryCutList(program.getChannel(), program.getRecStartTs());
    }

    public void generatePreviewImage(String id) throws IOException, CommandException
    {
        protocol.queryGenPixMap2(id, program);
    }

    public PixMap getPreviewImage(Date since, int maxSize) throws IOException, CommandException
    {
        return protocol.queryPixMapGetIfModified(since, maxSize, program);
    }

    public static List<Recording> getRecordings(Iterable<Program> programs, Protocol protocol)
    {
        List<Recording> recordings = new ArrayList<Recording>();

        for (Program program : programs)
        {
            recordings.add(new Recording(program, protocol));
        }

        return recordings;
    }
}
