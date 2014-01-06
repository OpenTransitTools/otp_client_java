package com.opentransittools.client;

import java.net.URL;

/**
 * ParamParser will manage the set of http get parameters for OTP (with default values)
 * http://maps.trimet.org/prod?fromPlace=PDX::45.587647,-122.593173&toPlace=ZOO::45.509700,-122.716290&mode=&min=QUICK&maxWalkDistance=840&time=2:22%20pm&date=1/3/2014&arriveBy=false&itinID=1&wheelchair=&preferredRoutes=null&unpreferredRoutes=null
 */
public class ParamParser
{
    private String m_from;
    private String m_to;
    private String m_mode = "TRANSIT,WALK";
    private String m_min  = "QUICK";
    private String m_time = null;
    private String m_date = null; 
    private String m_maxWalkDistance = null;
    private String m_arriveBy        = null;

    public ParamParser()
    {
    }

    public URL makeOtpUrl() throws Exception
    {
        return new URL("http://maps.trimet.org/prod?fromPlace=PDX::45.587647,-122.593173&toPlace=ZOO::45.509700,-122.716290");
    }

    public String getFrom() {
        return m_from;
    }

    public void setFrom(String from) {
        m_from = from;
    }

    public String getTo() {
        return m_to;
    }

    public void setTo(String to) {
        m_to = to;
    }

    public String getMode() {
        return m_mode;
    }

    public void setMode(String mode) {
        m_mode = mode;
    }

    public String getMin() {
        return m_min;
    }

    public void setMin(String min) {
        m_min = min;
    }

    public String getTime() {
        return m_time;
    }

    public void setTime(String time) {
        m_time = time;
    }

    public String getDate() {
        return m_date;
    }

    public void setDate(String date) {
        m_date = date;
    }

    public String getMaxWalkDistance() {
        return m_maxWalkDistance;
    }

    public void setMaxWalkDistance(String maxWalkDistance) {
        m_maxWalkDistance = maxWalkDistance;
    }

    public String getArriveBy() {
        return m_arriveBy;
    }

    public void setArriveBy(String arriveBy) {
        m_arriveBy = arriveBy;
    }
}