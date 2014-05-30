package com.opentransittools.client;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * "response":{
 *    "numFound":7,
 *    "start":0,
 *    "maxScore":12.36915,
 *    "docs":[
 *        {"id":"airport-214-5", "type":"2",   "type_name":"Airport","name":"XXXXXXX","address":"XXXXX NE Airport Way","city":"Xxxxland","lon":-XXX.XXXX,"lat":XX.XXXX,"score":XX.XXXXX},
 *        {"id":"stops-10574",   "type":"stop","type_name":"Stop ID","name":"XXXXX MAX", (no address element) "city":"Zzzzland","lon":-ZZZ.ZZZZZ, "lat":ZZ.ZZZZZZ,"score":1.8827744, "vtype":"5","stop_id":"10574","amenities":"Bench;Schedule Display;Shelter;Garbage Can;Lighting at Stop;Ticket Machine","routes":"90::MAX Red Line:","route_stops":",\"MAX Red Line\",0,\"To Portland International Airport\",true,false,false",}
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

    /** NOTE: I'm an inner class that represents the outer SOLR response */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SolrResponse
    {
        @JsonProperty("response")
        public SolrGeocoder response;
    }

    /** 
     *  {"id":"airport-214-5", "type":"2",   "type_name":"Airport","name":"ZZZX","address":"ZZZZZ NE Airport Way","city":"Zzzzzland","lon":-ZZZ.ZZZZZZ,"lat":ZZ.ZZZZZ,"score":12.36915},
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
        if(this.results != null && this.results.length > 0)
        {
            if(this.results.length == 1 || dominantFirst())
                ret_val = getNamedLatLon(this.results[0]);
        }
        return ret_val;
    }

    public boolean hasResults()
    {
        return this.results != null && this.results.length > 0;
    }

    public boolean dominantFirst(double factor)
    {
        boolean ret_val = false;
        if(hasResults())
        {
            if(this.results.length == 1 || this.results[0].score > this.results[1].score * factor)
                ret_val = true;
        }
        return ret_val;
    }
    public boolean dominantFirst()
    {
        return dominantFirst(2.0);
    }

    public List<String> getNamedLatLonList()
    {
        List<String> ret_val = new ArrayList<String>();
        if(this.results != null)
        for(Geocode g : this.results)
        {
            String r = g.getNamedLatLon();
            if(r != null)
                ret_val.add(r);
        }
        return ret_val;
    }

    /** */
    public static class Error
    {
        public Integer id;
        public String msg;
    }

    /** utility class to return a SolrGeocoder object */
    public static SolrGeocoder make_geocode(URL url, ObjectMapper json) throws Exception
    {
        SolrResponse rep = json.readValue(url, SolrResponse.class);
        return rep.response;
    }
    public static SolrGeocoder make_geocode(URL url) throws Exception
    {
        ObjectMapper json = new ObjectMapper();
        return make_geocode(url, json);
    }
    public static SolrGeocoder make_geocode(String search_term) throws Exception
    {
        ParamParser p = new ParamParser();
        URL url = p.makeSolrGeoUrl(search_term);
        return make_geocode(url);
    }

    public static void main(String[] args) throws Exception
    {
        String search_term = "PDX";
        if(args.length >= 1) search_term = args[0];

        SolrGeocoder geo = SolrGeocoder.make_geocode(search_term);
        String out = geo.getNamedLatLon();

        System.out.println();
        System.out.println(out);
    }

    public static void Xmain(String[] args) throws Exception
    {
        String file = "geocode.json";
        if(args.length >= 1) file = args[0];

        ObjectMapper json = new ObjectMapper();
        SolrResponse rep = json.readValue(new File(file), SolrResponse.class);
        SolrGeocoder geo = rep.response;
        if(geo != null)
        {
            String r = geo.getNamedLatLon();
            if(r != null)
                System.out.println(r);
            else
            {
                for(String l : geo.getNamedLatLonList())
                    System.out.println(l);
            }
        }
   }
}
