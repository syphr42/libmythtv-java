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
package org.syphr.mythtv.db.schema.impl._0_24;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.syphr.mythtv.db.schema.ChannelScanChannel;
import org.syphr.mythtv.db.schema.ChannelScanChannelId;

@Entity
@Table(name = "channelscan_channel")
public class ChannelScanChannel1264 implements ChannelScanChannel
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "transportid",
                                             column = @Column(name = "transportid",
                                                              nullable = false)),
                         @AttributeOverride(name = "scanid", column = @Column(name = "scanid",
                                                                              nullable = false)),
                         @AttributeOverride(name = "mplexId", column = @Column(name = "mplex_id",
                                                                               nullable = false)),
                         @AttributeOverride(name = "sourceId", column = @Column(name = "source_id",
                                                                                nullable = false)),
                         @AttributeOverride(name = "channelId",
                                            column = @Column(name = "channel_id", nullable = false)),
                         @AttributeOverride(name = "callsign", column = @Column(name = "callsign",
                                                                                nullable = false,
                                                                                length = 20)),
                         @AttributeOverride(name = "serviceName",
                                            column = @Column(name = "service_name",
                                                             nullable = false, length = 64)),
                         @AttributeOverride(name = "chanNum", column = @Column(name = "chan_num",
                                                                               nullable = false,
                                                                               length = 10)),
                         @AttributeOverride(name = "serviceId",
                                            column = @Column(name = "service_id", nullable = false)),
                         @AttributeOverride(name = "atscMajorChannel",
                                            column = @Column(name = "atsc_major_channel",
                                                             nullable = false)),
                         @AttributeOverride(name = "atscMinorChannel",
                                            column = @Column(name = "atsc_minor_channel",
                                                             nullable = false)),
                         @AttributeOverride(name = "useOnAirGuide",
                                            column = @Column(name = "use_on_air_guide",
                                                             nullable = false)),
                         @AttributeOverride(name = "hidden", column = @Column(name = "hidden",
                                                                              nullable = false)),
                         @AttributeOverride(name = "hiddenInGuide",
                                            column = @Column(name = "hidden_in_guide",
                                                             nullable = false)),
                         @AttributeOverride(name = "freqid", column = @Column(name = "freqid",
                                                                              nullable = false,
                                                                              length = 10)),
                         @AttributeOverride(name = "icon", column = @Column(name = "icon",
                                                                            nullable = false)),
                         @AttributeOverride(name = "tvformat", column = @Column(name = "tvformat",
                                                                                nullable = false,
                                                                                length = 10)),
                         @AttributeOverride(name = "xmltvid", column = @Column(name = "xmltvid",
                                                                               nullable = false,
                                                                               length = 64)),
                         @AttributeOverride(name = "patTsid", column = @Column(name = "pat_tsid",
                                                                               nullable = false)),
                         @AttributeOverride(name = "vctTsid", column = @Column(name = "vct_tsid",
                                                                               nullable = false)),
                         @AttributeOverride(name = "vctChanTsid",
                                            column = @Column(name = "vct_chan_tsid",
                                                             nullable = false)),
                         @AttributeOverride(name = "sdtTsid", column = @Column(name = "sdt_tsid",
                                                                               nullable = false)),
                         @AttributeOverride(name = "origNetid",
                                            column = @Column(name = "orig_netid", nullable = false)),
                         @AttributeOverride(name = "netid", column = @Column(name = "netid",
                                                                             nullable = false)),
                         @AttributeOverride(name = "siStandard",
                                            column = @Column(name = "si_standard",
                                                             nullable = false, length = 10)),
                         @AttributeOverride(name = "inChannelsConf",
                                            column = @Column(name = "in_channels_conf",
                                                             nullable = false)),
                         @AttributeOverride(name = "inPat", column = @Column(name = "in_pat",
                                                                             nullable = false)),
                         @AttributeOverride(name = "inPmt", column = @Column(name = "in_pmt",
                                                                             nullable = false)),
                         @AttributeOverride(name = "inVct", column = @Column(name = "in_vct",
                                                                             nullable = false)),
                         @AttributeOverride(name = "inNit", column = @Column(name = "in_nit",
                                                                             nullable = false)),
                         @AttributeOverride(name = "inSdt", column = @Column(name = "in_sdt",
                                                                             nullable = false)),
                         @AttributeOverride(name = "isEncrypted",
                                            column = @Column(name = "is_encrypted",
                                                             nullable = false)),
                         @AttributeOverride(name = "isDataService",
                                            column = @Column(name = "is_data_service",
                                                             nullable = false)),
                         @AttributeOverride(name = "isAudioService",
                                            column = @Column(name = "is_audio_service",
                                                             nullable = false)),
                         @AttributeOverride(name = "isOpencable",
                                            column = @Column(name = "is_opencable",
                                                             nullable = false)),
                         @AttributeOverride(name = "couldBeOpencable",
                                            column = @Column(name = "could_be_opencable",
                                                             nullable = false)),
                         @AttributeOverride(name = "decryptionStatus",
                                            column = @Column(name = "decryption_status",
                                                             nullable = false)),
                         @AttributeOverride(name = "defaultAuthority",
                                            column = @Column(name = "default_authority",
                                                             nullable = false, length = 32)) })
    private ChannelScanChannelId1264 id;

    @Override
    public ChannelScanChannelId getId()
    {
        return this.id;
    }

    @Override
    public void setId(ChannelScanChannelId id)
    {
        if (id != null && !(id instanceof ChannelScanChannelId1264))
        {
            throw new IllegalArgumentException("Invalid ID type: " + id.getClass().getName());
        }

        this.id = (ChannelScanChannelId1264)id;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ChannelScanChannel1264 [id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }
}
