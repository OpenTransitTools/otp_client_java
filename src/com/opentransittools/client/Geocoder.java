package com.opentransittools.client;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

//  http://www.cowtowncoder.com/blog/archives/2012/03/entry_467.html

/**
 * <geocoderResults>
 *     <count>2</count>
 *     <results>
 *         <result><description>834 SE LAMBERT CIRCLE</description><lat>45.490943</lat><lng>-122.406561</lng></result>
 *         <result><description>834 SE LAMBERT ST</description><lat>45.468602</lat><lng>-122.657627</lng></result>
 *     </results>
 * </geocoderResults> 
 */
public class Geocoder
{
    public Integer count;
    public Geocode results[];
    public Error errror;


    /** 
     *  http://maps.trimet.org/geocoder/geocode?address=834%20SE%20Lambert
     */
    public static class Geocode
    {
        public String description;
        public Double lat;
        public Double lng;

        public String getNamedLatLon()
        {
            String ret_val = null;
            try
            {
                ret_val = String.format("%s::%s,%s", description, lat, lng); 
            }
            catch(Exception e)
            {}
            return ret_val;
        }
    }

    public String getNamedLatLon(Geocode g)
    {
        return g != null ? g.getNamedLatLon() : null;
    }

    public String getNamedLatLon()
    {
        String ret_val = null;
        if(this.results != null && this.count > 0)
            ret_val = getNamedLatLon(this.results[0]);
        return ret_val;
    }
    
    /** 
     */
    public static class Error
    {
        public Integer id;
        public String msg;
    }

    public static void main(String[] args) throws Exception
    {
        String file = "geocode.xml";
        if(args.length >= 1) file = args[0];

        XmlMapper xmlMapper = new XmlMapper();
        Geocoder geo = xmlMapper.readValue(new File(file), Geocoder.class);
        System.out.println(geo.results[0].description);
   }
}
