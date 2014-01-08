package com.opentransittools.client;

import java.net.URL;

/**
 * used to handle the different set of parameters seen in TriMets old trip web service 
 * fromPlace=Sylvania
 * toPlace=Acquired%20GPS%20Location&toCoord=-122.685896,45.403310
 * Date=01-07-14
 * Time=02:00%20PM
 * Arr=D
 * Walk=0.500000
 * Mode=A
 * Min=T
 * &Format=XML&MaxItineraries=6
 */
public class  ParamParserLegacyTM extends ParamParser
{
    public ParamParserLegacyTM()
    {
        super();
    }

    public void setFrom(String from, String lat_lon) {
        m_from = String.format("%s::%s", from, lat_lon);
    }

    public void setTo(String to, String lat_lon) {
        m_to = String.format("%s::%s", to, lat_lon);
    }

    public void setMode(String mode) {
        if(mode.equals("A"))       this.m_mode = "TRANSIT,WALK";
        else if(mode.equals("B"))  this.m_mode = "BUSISH,WALK";
        else if(mode.equals("T"))  this.m_mode = "TRAINISH,WALK";
        else                       this.m_mode = "TRANSIT,WALK";
    }

    public void setMin(String min) {
        if(min.equals("T"))       this.m_min  = "QUICK";
        else if(min.equals("X"))  this.m_min  = "TRANSFERS";
        else                      this.m_min  = "QUICK";
    }

    public void setTime(String time) {
        m_time = time;
        if(m_date == null)
            ; // add current date
    }

    public void setDate(String date) {
        m_date = date;
        if(m_time  == null)
            ; // add current time
    }

    /** 
     * convert from % of a mile (e.g., 1.0 = 1 mile -- 0.50 = 1/2 mile, 
     */
    public void setMaxWalkDistance(Double maxWalkDistance)
    {
        if(maxWalkDistance < 30)
            maxWalkDistance = maxWalkDistance * 1609.5;
        if(maxWalkDistance < 10)
            maxWalkDistance = 1609.0;

        Long m = Math.round(maxWalkDistance);
        m_maxWalkDistance = m.toString();
    }
    public void setMaxWalkDistance(String maxWalkDistance)
    {
        try
        {
            this.setMaxWalkDistance(new Double(maxWalkDistance));
        }
        catch(Exception e)
        {
            this.m_maxWalkDistance = "1610"; // default to 1 mile
        }
    }
    public void setMaxWalkDistance(Integer maxWalkDistance)
    {
        this.setMaxWalkDistance(new Double(maxWalkDistance));
    }
}