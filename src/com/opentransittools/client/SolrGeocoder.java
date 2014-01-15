package com.opentransittools.client;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//  http://www.cowtowncoder.com/blog/archives/2012/03/entry_467.html

/**
 * "response":{
 *    "numFound":7,
 *    "start":0,
 *    "maxScore":12.36915,
 *    "docs":[
 *        {"id":"airport-214-5", "type":"2",   "type_name":"Airport","name":"PDX","address":"7000 NE Airport Way","city":"Portland","lon":-122.599266,"lat":45.58985,"score":12.36915},
 *        {"id":"stops-10574",   "type":"stop","type_name":"Stop ID","name":"Cascades MAX", (no address element)  "city":"Portland","lon":-122.55829, "lat":45.57239,"score":1.8827744, "vtype":"5","stop_id":"10574","amenities":"Bench;Schedule Display;Shelter;Garbage Can;Lighting at Stop;Ticket Machine","routes":"90::MAX Red Line:","route_stops":",\"MAX Red Line\",0,\"To Portland International Airport\",true,false,false",}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolrGeocoder
{
    @JsonProperty("numFound")
    public Integer count;

    @JsonProperty("docs")
    public Geocode results[];

    @JsonProperty("error")
    public Error errror;


    /** 
     *  {"id":"airport-214-5", "type":"2",   "type_name":"Airport","name":"PDX","address":"7000 NE Airport Way","city":"Portland","lon":-122.599266,"lat":45.58985,"score":12.36915},
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geocode
    {
        @JsonProperty("name")
        public String description;

        @JsonProperty("lat")
        public Double lat;

        @JsonProperty("lon")
        public Double lng;

        @JsonProperty("score")
        public Double score;

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
        String file = "geocode.json";
        if(args.length >= 1) file = args[0];

        ObjectMapper jsonMapper = new ObjectMapper();
        SolrGeocoder geo = jsonMapper.readValue(new File(file), SolrGeocoder.class);
        System.out.println(geo.getNamedLatLon());
   }
}
