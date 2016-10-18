package com.opentransittools.client;

import java.nio.file.Paths;
import java.net.URL;
import java.util.logging.Logger;

import com.opentransittools.client.SolrGeocoder;
import com.opentransittools.client.TripPlan;
import com.opentransittools.client.OtpGeocoder;
import com.opentransittools.client.ParamParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

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

    protected static final Logger LOG = Logger.getLogger(OtpClient.class.getCanonicalName());

    public OtpClient(ParamParser p)
    {
        this.m_json = OtpClient.jsonMapper();
        this.m_xml  = new XmlMapper();
        this.m_params = p;
    }

    public static ObjectMapper jsonMapper()
    {
        ObjectMapper om = new ObjectMapper();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return om;
    }

    public TripPlan planner()
    {
        TripPlan ret_val = null;
        try
        {
            ret_val = m_json.readValue(this.m_params.makeOtpUrl(), TripPlan.class);
            ret_val.postProcess();
        }
        catch(Exception e)
        {
            LOG.severe(e.toString());
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
            LOG.severe(e.toString());
        }
        return ret_val;
    }

    public SolrGeocoder solr_geocode(String geo)
    {
        SolrGeocoder ret_val = null;
        try
        {
            URL url = this.m_params.makeSolrGeoUrl(geo);
            ret_val = SolrGeocoder.make_geocode(url, m_json);
        }
        catch(Exception e)
        {
            LOG.severe(e.toString());
        }
        return ret_val;
    }

    public boolean isReady()
    {
        return this.m_params.isReady();
    }

    public static void client(String[] args) throws Exception
    {
        String from = "ZOO";
        String to = "PDX";
        if(args.length >= 1) from = args[0];
        if(args.length >= 2) to   = args[1];
        ParamParser p = new ParamParser();
        OtpClient c = new OtpClient(p);

        SolrGeocoder f = c.solr_geocode(from);
        SolrGeocoder t = c.solr_geocode(to);
        System.out.println(f.getNamedLatLon());
        System.out.println(t.getNamedLatLon());

        p.setFrom(f.getNamedLatLon());
        p.setTo(t.getNamedLatLon());

        System.out.print("\nOUTPUT:");
        if(c.isReady())
        {
            System.out.print("Start Planning...\n");
            TripPlan tp = c.planner();
            tp.postProcess();
            System.out.print("...Done Planning\n");
            print(tp);
        }
        else
        {
            System.out.print("\nSorry...the planner isn't ready to be called.  You might be missing a valid place to geocode, etc...");
        }
    }

    public static void replan(String[] args) throws Exception
    {
        String file;
        if(args[0].endsWith(".json")) {
            file = args[0];
        }
        else if (args[0].equals("new")) {
            //file = "src/com/opentransittools/test/new/zoo_ohsu.json";
            file = "src/com/opentransittools/test/new/interline.json";
        }
        else {
            //file = "src/com/opentransittools/test/old/zoo_ohsu.json";
            file = "src/com/opentransittools/test/old/interline.json";
        }
        String path = Paths.get(file).toAbsolutePath().toString();

        TripPlan tp = TripPlan.planFromFile(OtpClient.jsonMapper(), path);
        tp.postProcess();
        //print(tp);
        System.out.print(tp.toString());
    }

    public static void print(TripPlan tp) throws Exception
    {
        System.out.print("\n\n");
        System.out.print(tp.plan.from.name);
        System.out.print("  Trip duration = " + tp.plan.itineraries[0].duration);
        System.out.print("  Num transfers = " + tp.plan.itineraries[0].transfers);
    }

    public static void main(String[] args) throws Exception
    {
        System.out.print(args[0]);
        if(args.length > 0 && (args[0].endsWith(".json") || args[0].equals("old") || args[0].equals("new") ))
            replan(args);
        else
            client(args);
    }
}
