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
import java.net.InetAddress;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.syphr.mythtv.data.Channel;
import org.syphr.mythtv.data.Program;
import org.syphr.mythtv.db.schema.Recorded;
import org.syphr.mythtv.db.schema.TvChain;
import org.syphr.mythtv.protocol.Protocol;
import org.syphr.mythtv.protocol.QueryRecorder;
import org.syphr.mythtv.protocol.QueryRemoteEncoder;
import org.syphr.mythtv.types.PictureAdjustType;
import org.syphr.mythtv.util.exception.CommandException;

public class Recorder
{
    private final Backend backend;

    private final int id;

    private final Protocol protocol;
    private final QueryRecorder queryRecorder;
    private final QueryRemoteEncoder queryRemoteEncoder;

    public Recorder(Backend backend, int id, Protocol protocol)
    {
        this.backend = backend;

        this.id = id;

        this.protocol = protocol;
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

    public boolean isBusyWithin(int duration, TimeUnit unit) throws IOException, CommandException
    {
        return queryRemoteEncoder.isBusy((int)unit.toSeconds(duration)).getLeft();
    }

    public RecordingByteChannel startLiveTv(Channel channel) throws IOException,
                                                            CommandException
    {
        if (channel.getNumber() == null)
        {
            channel = queryRecorder.getChannelInfo(channel.getId());
        }

        String chainId = "live-"
                         + InetAddress.getLocalHost().getHostName()
                         + "-"
                         + new Date().getTime();
        queryRecorder.spawnLiveTv(chainId, false, channel);

        EntityManager entityMan = backend.getDatabase().getEntityManager();
        entityMan.getTransaction().begin();

        try
        {
            TypedQuery<TvChain> tvChainsQuery = entityMan.createQuery("select t from "
                                                                              + TvChain.class.getName()
                                                                              + " as t where t.chainid = '"
                                                                              + chainId
                                                                              + "' order by chainpos desc",
                                                                      TvChain.class);
            List<TvChain> tvChains = tvChainsQuery.getResultList();
            if (tvChains.isEmpty())
            {
                throw new CommandException("Live TV did not start properly");
            }
            TvChain latestChain = tvChains.get(0);

            TypedQuery<Recorded> recordedQuery = entityMan.createQuery("select r from "
                                                                      + Recorded.class.getName()
                                                                      + " as r where r.id.chanid = '"
                                                                      + latestChain.getId().getChanid()
                                                                      + "' and r.id.starttime = '"
                                                                      + latestChain.getId().getStarttime()
                                                                      + "'",
                                                              Recorded.class);
            List<Recorded> recordedList = recordedQuery.getResultList();
            if (recordedList.size() != 1)
            {
                throw new CommandException("Live TV did not start properly");
            }
            Recorded liveTvRecorded = recordedList.get(0);

            Program program = new Program(channel,
                                                  URI.create(latestChain.getHostprefix() + liveTvRecorded.getBasename()),
                                                  latestChain.getId()
                                                             .getStarttime());

            // TODO backend is returning file size 0 for live tv - not sure why yet
            return new RecordingByteChannel(protocol, program, false, 3000);
        }
        finally
        {
            entityMan.getTransaction().commit();
            entityMan.close();
        }
    }

    public void stopLiveTv() throws IOException, CommandException
    {
        queryRecorder.stopLiveTv();
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
