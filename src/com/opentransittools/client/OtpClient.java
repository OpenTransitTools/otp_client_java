package com.opentransittools.client;

import java.net.URL;

import com.opentransittools.client.TripPlan;
import com.opentransittools.client.OtpGeocoder;
import com.opentransittools.client.SolrGeocoder;
import com.opentransittools.client.ParamParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


/**
 * OtpClient does three things:
 * 1)  provides a set of http get parameters for OTP (with default values)
 * 2a) calls OTP via http
 * 2b) marshal's the OTP response into the com.opentransittools.client.TripPlan client POJO.
 */
public class OtpClient
{
    private ObjectMapper m_json;
    private XmlMapper    m_xml;
    private ParamParser  m_params;

    public OtpClient(ParamParser p)
    {
        this.m_json = new ObjectMapper();
        this.m_xml  = new XmlMapper();
        this.m_params = p;
    }

    public TripPlan planner() 
    {
        TripPlan ret_val = null;
        try
        {
            ret_val = m_json.readValue(this.m_params.makeOtpUrl(), TripPlan.class);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return ret_val;
    }

    public OtpGeocoder otp_geocode(String geo)
    {
        OtpGeocoder ret_val = null;
        try
        {
            ret_val = m_xml.readValue(this.m_params.makeOtpGeoUrl(geo), OtpGeocoder.class);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return ret_val;
    }

    public SolrGeocoder solr_geocode(String geo)
    {
        SolrGeocoder ret_val = null;
        try
        {
            URL url = this.m_params.makeSolrGeoUrl(geo);
            ret_val = m_json.readValue(url, SolrGeocoder.class);
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

        SolrGeocoder f = c.solr_geocode(from);
        SolrGeocoder t = c.solr_geocode(to);
        System.out.println(f.getNamedLatLon());

        p.setFrom(f.getNamedLatLon());
        p.setTo(t.getNamedLatLon());

        TripPlan tp = c.planner();
        System.out.print(tp.plan.from.name);
    }
}
