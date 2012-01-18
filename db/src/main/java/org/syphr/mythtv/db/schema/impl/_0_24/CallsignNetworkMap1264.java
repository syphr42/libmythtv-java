package org.syphr.mythtv.db.schema.impl._0_24;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.syphr.mythtv.db.schema.CallsignNetworkMap;

@Entity
@Table(name = "callsignnetworkmap", uniqueConstraints = @UniqueConstraint(columnNames = "callsign"))
public class CallsignNetworkMap1264 implements CallsignNetworkMap
{
    /**
     * Serialization ID
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "callsign", unique = true, nullable = false, length = 20)
    private String callsign;

    @Column(name = "network", nullable = false, length = 20)
    private String network;

    @Override
    public Integer getId()
    {
        return this.id;
    }

    @Override
    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public String getCallsign()
    {
        return this.callsign;
    }

    @Override
    public void setCallsign(String callsign)
    {
        this.callsign = callsign;
    }

    @Override
    public String getNetwork()
    {
        return this.network;
    }

    @Override
    public void setNetwork(String network)
    {
        this.network = network;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Callsignnetworkmap1264 [id=");
        builder.append(id);
        builder.append(", callsign=");
        builder.append(callsign);
        builder.append(", network=");
        builder.append(network);
        builder.append("]");
        return builder.toString();
    }
}
