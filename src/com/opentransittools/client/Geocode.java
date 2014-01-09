package com.opentransittools.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * NOTE:not all elements are being reflected back by this class...I've chosen a sub-set of elements that I'm interested in.
 *      But if there are other elements of interest, it's pretty easy to add below. 
 */
public class Geocode
{
    @JsonProperty("geocode")
    public Geocode geocode;
    @JsonProperty("error")
    public Error errror;


    /** 
     * 
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geocode
    {
        @JsonProperty("time")
        public String time;
    }

    /** 
     *  "error":{"id":500,"msg":"We're sorry. The trip planner is temporarily unavailable. Please try again later.","missing":null,"noPath":true}
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error
    {
        @JsonProperty("id")
        public Integer id;
        @JsonProperty("msg")
        public String msg;
    }
}
