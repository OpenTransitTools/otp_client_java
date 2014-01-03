package com.opentransittools.client;

import java.io.File;

import com.opentransittools.client.TripPlan;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * OtpClient does three things:
 * 1)  provides a set of http get parameters for OTP (with default values)
 * 2a) calls OTP via http
 * 2b) marshal's the OTP response into the com.opentransittools.client.TripPlan client POJO.
 * 
 *     http://maps.trimet.org/prod?fromPlace=PDX::45.587647,-122.593173&toPlace=ZOO::45.509700,-122.716290&mode=TRANSIT,WALK&min=QUICK&maxWalkDistance=840&time=2:22%20pm&date=1/3/2014&arriveBy=false&itinID=1&wheelchair=&preferredRoutes=null&unpreferredRoutes=null
 */
public class OtpClient
{
    private String m_from;
    private String m_to;
    private ObjectMapper m_mapper;

    public OtpClient(String from, String to)
    {
        this.m_from = from;
        this.m_to   = to;
        this.m_mapper = new ObjectMapper();
    }

    public OtpClient(String from, String to, String ... params)
    {
        this(from, to);
        
    }

    public String makeOtpUrl()
    {
        return "http://maps.trimet.org/prod?fromPlace=PDX::45.587647,-122.593173&toPlace=ZOO::45.509700,-122.716290";
    }

    public TripPlan call() 
    {
        TripPlan ret_val = null;
        try
        {
            ret_val = m_mapper.readValue(this.makeOtpUrl(), TripPlan.class);
        }
        catch(Exception e)
        {
        }
        return ret_val;
    }
    public static void main(String[] args) throws Exception
    {
        String from = "PDX";
        String to   = "ZOO";
        if(args.length >= 1) from = args[0];
        if(args.length >= 2) to   = args[1];

        OtpClient c = new OtpClient(from, to, args);
        TripPlan p = c.call();
        System.out.print(p.plan.from.name);
    }
}