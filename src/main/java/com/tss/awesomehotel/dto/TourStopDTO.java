package com.tss.awesomehotel.dto;

import com.tss.awesomehotel.model.travel.TourStop;

import java.io.Serializable;

/**
 * The DTO object for {@link TourStop}
 */
public class TourStopDTO implements Serializable
{
    /**
     * The Serializable serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the stop
     */
    private String name;

    /**
     * the id of the stop
     */
    private double ID;

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

    public double getID()
    {
        return ID;
    }

    public void setID(double ID)
    {
        this.ID = ID;
    }
}
