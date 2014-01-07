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
    private String m_itins = "3";
    private String m_mode = null;
    private String m_min  = null;
    private String m_time = null;
    private String m_date = null; 
    private String m_maxWalkDistance = null;
    private String m_arriveBy        = null;

    private String m_service;

    public ParamParser()
    {
        this.m_service = "http://maps.trimet.org/prod";
    }

    public URL makeOtpUrl() throws Exception
    {
        String url = String.format("%s?fromPlace=%s&toPlace=%s&numItins=%s", m_service, urlEncode(m_from), urlEncode(m_to), m_itins);
        if(m_mode  != null)    url = url + "&mode=" + m_mode;
        if(m_min   != null)    url = url + "&min="  + m_min;
        if(m_time  != null)    url = url + "&time=" + m_time;
        if(m_date  != null)    url = url + "&date=" + m_date;
        if(m_arriveBy != null) url = url + "&arriveBy=" + m_arriveBy;
        if(m_maxWalkDistance != null) url = url + "&maxWalkDistance=" + m_maxWalkDistance;
        System.out.print("URL: " + url);
        return new URL(url);
    }

    static public boolean hasGeoCode(String name, String ... etc)
    {
        return true;
    }

    static public String urlEncode(String str)
    {
        String ret_val = str;
        try
        {
            ret_val = ret_val.replace(" ", "%20"); // spaces to %20
        }
        catch(Exception e)
        {
            
        }
        return ret_val;
    }

    public String getFrom() {
        return m_from;
    }
    public void setFrom(String from) {
        m_from = from;
    }
    public String geoFrom(String from) {
        m_from = from;
        return "";
    }
    public void setFrom(String from, String lat_lon) {
        m_from = String.format("%s::%s", from, lat_lon);
    }
    public void setFrom(String from, String lat, String lon) {
        m_from = String.format("%s::%s,%s", from, lat, lon);
    }

    public String getTo() {
        return m_to;
    }
    public void setTo(String to) {
        m_to = to;
    }
    public String geoTo(String to) {
        m_to = to;
        return "";
    }
    public void setTo(String to, String lat_lon) {
        m_to = String.format("%s::%s", to, lat_lon);
    }
    public void setTo(String to, String lat, String lon) {
        m_to = String.format("%s::%s,%s", to, lat, lon);
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
        if(m_date == null)
            ; // add current date
    }

    public String getDate() {
        return m_date;
    }
    public void setDate(String date) {
        m_date = date;
        if(m_time  == null)
            ; // add current time
    }

    public String getMaxWalkDistance() {
        return m_maxWalkDistance;
    }
    public void setMaxWalkDistance(String maxWalkDistance) {
        m_maxWalkDistance = maxWalkDistance;
    }
    public void setMaxWalkDistance(Integer maxWalkDistance) {
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