package com.opentransittools.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import com.opentransittools.client.TripPlan;

/**
 * NOTE:not all elements are being reflected back by this class...I've chosen a sub-set of elements that I'm interested in.
 *      But if there are other elements of interest, it's pretty easy to add below. 
 */
public class TripPlan_version_1_0 extends TripPlan
{
    /** used to move values into known OTP 0.10.x TripPlan format */
    public Boolean normalize()
    {
        return true;
    }
    /**
     * old 0.10.x version:
     * stopId:{"agencyId":"TriMet","id":"10579"}
     *
     * new 1.0 version:
     * stopId:"TriMet:10579"
     */
    public static class Stop
    {
        protected Boolean isVersion_1_0 = false;


        public String agencyId;
        public String id;
    }

    public static class Plan
    {
        @JsonProperty("from")
        public Place from;
        @JsonProperty("to")
        public Place to;

        public static class Place
        {
            @JsonProperty("stopId")
            public String stopId;
        }
    }

    /** utiltity method ... most useful for testing static data from a file */
    public static TripPlan planFromFile(ObjectMapper mapper, String filePath) throws Exception
    {
        TripPlan_version_1_0 p = mapper.readValue(new File(filePath), TripPlan_version_1_0.class);
        p.normalize();
        return (TripPlan) p;
    }
}
