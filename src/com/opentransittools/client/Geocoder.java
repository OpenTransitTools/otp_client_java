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
//@JacksonXmlRootElement("geocoderResults")
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
        public String time;
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
        List entries = xmlMapper.readValue(new File(file), List.class);

        ObjectMapper jsonMapper = new ObjectMapper();
        String json = jsonMapper.writeValueAsString(entries);
        System.out.println(json);
   }
}
