package com.tss.awesomehotel.dto;

import java.io.Serializable;

public class TravelPathDTO implements Serializable
{
    /**
     * The Serializable serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private double travelPathID;

    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getTravelPathID()
    {
        return travelPathID;
    }

    public void setTravelPathID(double travelPathID)
    {
        this.travelPathID = travelPathID;
    }
}
