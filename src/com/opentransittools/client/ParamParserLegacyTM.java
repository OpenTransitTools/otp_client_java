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
        this.m_service = "http://maps.trimet.org/prod";
    }

    public void setFrom(String from, String lat_lon) {
        m_from = String.format("%s::%s", from, lat_lon);
    }

    public void setTo(String to, String lat_lon) {
        m_to = String.format("%s::%s", to, lat_lon);
    }

    public void setMode(String mode) {
        if(mode.equals("A"))       this.m_mode = ""; 
        else if(mode.equals("A"))  this.m_mode = ""; 
        else if(mode.equals("A"))  this.m_mode = ""; 
        else                       this.m_mode = ""; 
    }

    public void setMin(String min) {
        if(min.equals("T"))       this.m_min  = "";
        else if(min.equals("X"))  this.m_min  = "";
        else                      this.m_min  = "";
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

    public void setMaxWalkDistance(String maxWalkDistance) {
        try
        {
            this.setMaxWalkDistance(new Double(maxWalkDistance));
        }
        catch(Exception e)
        {
            
        }
    }
    /** 
     * convert from % of a mile (e.g., 1.0 = 1 mile -- 0.50 = 1/2 mile, 
     */
    public void setMaxWalkDistance(Double maxWalkDistance)
    {
        if(maxWalkDistance < 30)
            maxWalkDistance = maxWalkDistance * 1800;
        m_maxWalkDistance = maxWalkDistance.toString();
    }

    public String getArriveBy() {
        return m_arriveBy;
    }
    public void setArriveBy(String arriveBy) {
        m_arriveBy = arriveBy;
    }
    public void setArriveBy(Boolean arriveBy) {
        m_arriveBy = arriveBy.toString();
    }
}