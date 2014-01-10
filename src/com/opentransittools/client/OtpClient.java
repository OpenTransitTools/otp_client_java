package com.opentransittools.client;

import java.net.URL;

import com.opentransittools.client.TripPlan;
import com.opentransittools.client.Geocoder;
import com.opentransittools.client.ParamParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * OtpClient does three things:
 * 1)  provides a set of http get parameters for OTP (with default values)
 * 2a) calls OTP via http
 * 2b) marshal's the OTP response into the com.opentransittools.client.TripPlan client POJO.
 */
public class OtpClient
{
    private ObjectMapper m_mapper;
    private ParamParser  m_params;

    public OtpClient(ParamParser p)
    {
        this.m_mapper = new ObjectMapper();
        this.m_params = p;
    }

    public TripPlan call() 
    {
        TripPlan ret_val = null;
        try
        {
            ret_val = m_mapper.readValue(this.m_params.makeOtpUrl(), TripPlan.class);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return ret_val;
    }

    public Geocoder geocode(String geo)
    {
        Geocoder ret_val = null;
        try
        {
            ret_val = m_mapper.readValue(this.m_params.makeGeoUrl(geo), Geocoder.class);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return ret_val;
    }

    public static void main(String[] args) throws Exception
    {
        String from = "PDX";
        String to   = "ZOO";
        if(args.length >= 1) from = args[0];
        if(args.length >= 2) to   = args[1];

        ParamParser p = new ParamParser();
        OtpClient c = new OtpClient(p);

        Geocoder g = c.geocode(from);
        System.out.println(g.results[0].description);

        p.setFrom(from);
        p.setTo(to);

        TripPlan t = c.call();
        System.out.print(t.plan.from.name);
    }
}
