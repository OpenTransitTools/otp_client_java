package com.opentransittools.client;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * NOTE:not all elements are being reflected back by this class...I've chosen a sub-set of elements that I'm interested in.
 *      But if there are other elements of interest, it's pretty easy to add below. 
 */
public class TripPlan
{
    @JsonProperty("requestParameters")
    public RequestParameters requestParameters;
    @JsonProperty("plan")
    public Plan plan;
    @JsonProperty("error")
    public Error error;

    public String toString() {
        String retVal = "TripPlan:";
        if(this.plan != null)
            retVal += this.plan.toString();
        if(this.error != null)
            retVal += this.error.toString();
        return retVal;
    }

    public static class Plan {
        public Itinerary itineraries[];

        public Place from;
        public Place to;

        public static class Place {
            public String name;
        }

        public static class Leg {
            public Place from;
            public Place to;
            public Boolean interlineWithPreviousLeg;
        }

        public static class Itinerary {
            public Integer transfers;
            public Long duration;
            public Leg legs[];
        }
    }

    public static class RequestParameters {

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

        public String toString() {
            String retVal = "";
            if(this.msg != null) {
                retVal += "\n\tERROR " + this.msg;
            }
            return retVal;
        }
    }

    /** set error */
    public static void setErrorMsg(TripPlan tp, String msg, Integer id) {
        if(tp != null)
        {
            if(tp.error == null)
                tp.error = new Error();
            tp.error.msg = msg;
            tp.error.id = id;
        }
    }
    public static void setErrorMsg(TripPlan tp, String msg) { setErrorMsg(tp, msg, 20000); }

    public static String getErrorMsg(TripPlan tp, String defVal) {
        String retVal = defVal;
        if(tp != null && tp.error != null && tp.error.msg.length() > 0)
            retVal = tp.error.msg;
        return retVal;
    }
    public static String getErrorMsg(TripPlan tp) {
        return getErrorMsg(tp, "Sorry, the trip planner is temporarily unavailable. Please try again later.");
    }

    public static TripPlan planFromFile(ObjectMapper mapper, String filePath) throws Exception
    {
        TripPlan p = mapper.readValue(new File(filePath), TripPlan_version_0_10.class);
        return p;
    }
}
