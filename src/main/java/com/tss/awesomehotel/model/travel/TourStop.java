package com.tss.awesomehotel.model.travel;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Document(collection = "paths")
public class TourStop
{
    @Id
    protected int travelPathID;

    @JsonProperty("name")
    protected String name;

    @JsonProperty("connections")
    protected List<HashMap<String,Double>> connections;


    @PersistenceConstructor
    public TourStop(int travelPathID, String name, List<HashMap<String, Double>> connections)
    {
        this.travelPathID = travelPathID;
        this.name = name;
        this.connections = connections;
    }

    public int getTravelPathID()
    {
        return travelPathID;
    }

    public void setTravelPathID(int travelPathID)
    {
        this.travelPathID = travelPathID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<HashMap<String, Double>> getConnections()
    {
        return connections;
    }

    public void setConnections(List<HashMap<String, Double>> connections)
    {
        this.connections = connections;
    }

    @Override
    public boolean equals(Object o)
    {
        boolean equals = false;
        if (this == o) equals = true;
        else if (o != null)
        {
            if (o instanceof TourStop)
            {
                TourStop that = (TourStop) o;
                equals = Double.compare(that.travelPathID, travelPathID) == 0;
            }
            else if (o instanceof  Double)
            {
                equals = Double.compare(this.travelPathID, (Double) o) == 0;
            }
        }

        return equals;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(travelPathID);
    }
}
