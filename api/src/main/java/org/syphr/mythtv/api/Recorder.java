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

import org.syphr.mythtv.protocol.CommandException;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.protocol.types.PictureAdjustType;

public class Recorder
{
    private final int id;

    private final QueryRecorder queryRecorder;
    private final QueryRemoteEncoder queryRemoteEncoder;

    public Recorder(int id, Protocol protocol)
    {
        this.id = id;
        this.queryRecorder = protocol.queryRecorder(id);
        this.queryRemoteEncoder = protocol.queryRemoteEncoder(id);
    }

    public PictureControls getPictureControls() throws IOException, CommandException
    {
        if (queryRecorder.getBrightness() >= 0)
        {
            return new PictureControlsImpl(queryRecorder, PictureAdjustType.RECORDING);
        }

        return new PictureControlsNoOp();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Recorder [id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Recorder))
        {
            return false;
        }
        Recorder other = (Recorder)obj;
        if (id != other.id)
        {
            return false;
        }
        return true;
    }
}
