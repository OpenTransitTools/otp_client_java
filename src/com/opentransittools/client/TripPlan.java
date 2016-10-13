package com.opentransittools.client;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * TripPlan is the OTP 0.10.x version response Parser.  Other versions of OTP will be supported by sub-classing TripPlan,
 * and overridding the 0.10.x specific code to adapt to changes in the OTP response.  To make other code built with
 * the TripPlan object structure work newer code, a there's a 'nomalize' routine which can be used to assign element vaales
 * to elements in TripPlan
 *
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

    public Boolean normalize()
    {
        return true;
    }

    /**
     *  "plan":{"date":1387848986000,
     *          "from":{"name":"834 SE LA MBERT ST, PORTLAND","stopId":null,"stopCode":null,"lon":-122.6569759304584,"lat":45.46838276495563,"arrival":null,"departure":null,"orig":null,"zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.6569759304584,45.46838276495563]}"},
     *          "to":{"name":"Art Museum, Portland (Stop ID 6493)","stopId":null,"stopCode":null,"lon":-122.68399,"lat":45.516304,"arrival":null,"departure":null,"orig":null,"zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.68399,45.516304]}"},
     *          "itineraries":[
     *          {
     *  "duration":5851000,"startTime":1387848986000,"endTime":1387854837000,"walkTime":5851,"transitTime":0,"waitingTime":0,"walkDistance":7522.2365911245715,"elevationLost":67.94127849117022,"elevationGained":66.95316905912915,"transfers":-1,"fare":null,
     *  "legs":[
     *      {"startTime":1387848986000,"endTime":1387854837000,"distance":7522.2365911245715,"mode":"WALK","route":"","agencyName":null,"agencyUrl":null,"agencyTimeZoneOffset":0,
     *       "routeColor":null,"routeId":null,"routeTextColor":null,"interlineWithPreviousLeg":null,"tripShortName":null,"headsign":null,"agencyId":null,"tripId":null,
     *       "from":
     *          {"name":"834 SE LA MBERT ST, PORTLAND","stopId":null,"stopCode":null,"lon":-122.6569759304584,"lat":45.46838276495563,"arrival":null,"departure":null,"orig":"834 SE LA MBERT ST, PORTLAND","zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.6569759304584,45.46838276495563]}"},
     *       "to":
     *          {"name":"Art Museum, Portland (Stop ID 6493)","stopId":null,"stopCode":null,"lon":-122.68399,"lat":45.516304,"arrival":1387854837000,"departure":1387854837000,"orig":"Art Museum, Portland (Stop ID 6493)","zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.68399,45.516304]}"},
     *       "legGeometry":{"points":"kpotGbmskVk@?mCW","levels":null,"length":294},
     *       "notes":[{"text":"Caution!"}],
     *       "alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}],
     *       "routeShortName":null,"routeLongName":null,"boardRule":null,"alightRule":null,"rentedBike":null,"duration":5851000,"bogusNonTransitLeg":false,
     *       "steps":[
     *           {"distance":24.44480017811421,"relativeDirection":null,"streetName":"834 SE LA MBERT ST, PORTLAND","absoluteDirection":"NORTH","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6569759304584,"lat":45.46838276495563,"elevation":"0,14.1,5,14.2,15,14.2,24,14.2"},
     *           {"distance":1636.7473567151435,"relativeDirection":"RIGHT","streetName":"Southeast McLoughlin Boulevard","absoluteDirection":"NORTHWEST","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6519503,"lat":45.4887532,"elevation":"0,...","alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}]},
     *       ]}
     *  ],
     *  "tooSloped":false
     *                    }
     *                ]
     *        }
     */
    public static class Plan
    {
        @JsonProperty("date")
        public String date;

        @JsonProperty("from")
        public Place from;
        @JsonProperty("to")
        public Place to;

        @JsonProperty("itineraries")
        public Itinerary itineraries[];

        public String toString() {
            String retVal = "";
            if(this.itineraries != null) {
                retVal += "\n\tPlan with " + this.itineraries.length + " itineraries:";
                for(Itinerary i : this.itineraries)
                    retVal += i.toString();
            }
            return retVal;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Place
        {
            @JsonProperty("name")
            public String name;

            @JsonProperty("lon")
            public Double lon;
            @JsonProperty("lat")
            public Double lat;

            @JsonProperty("stopId")
            public Stop stopId;
            @JsonProperty("stopCode")
            public String stopCode;
            @JsonProperty("stopIndex")
            public String stopIndex;
            @JsonProperty("arrival")
            public String arrival;
            @JsonProperty("departure")
            public String departure;
            @JsonProperty("orig")
            public String orig;
            @JsonProperty("zoneId")
            public String zoneId;

            /**
             * old 0.10.x version:
             * stopId:{"agencyId":"TriMet","id":"10579"}
             *
             * new 1.0 version:
             * stopId:"TriMet:10579"
             */
            public static class Stop
            {
                public Stop()
                {
                    // dummy constructor
                    // @see http://stackoverflow.com/questions/7625783/jsonmappingexception-no-suitable-constructor-found-for-type-simple-type-class
                }

                public Stop(String agencyStopId)
                {
                    //isVersion_1_0 = true;

                    String z[] = agencyStopId.split(":");
                    this.agencyId = z[0];
                    this.id = z[1];
                }

                @JsonProperty("agencyId")
                public String agencyId;

                @JsonProperty("id")
                public String id;
            }
        }

        /**
         *  "duration":5851000,"startTime":1387848986000,"endTime":1387854837000,"walkTime":5851,"transitTime":0,"waitingTime":0,"walkDistance":7522.2365911245715,"elevationLost":67.94127849117022,"elevationGained":66.95316905912915,"transfers":-1,"fare":null,
         *  "legs":[
         *   {
         *       "startTime":1387848986000,"endTime":1387854837000,"distance":7522.2365911245715,"mode":"WALK","route":"","agencyName":null,"agencyUrl":null,"agencyTimeZoneOffset":0,
         *       "routeColor":null,"routeId":null,"routeTextColor":null,"interlineWithPreviousLeg":null,"tripShortName":null,"headsign":null,"agencyId":null,"tripId":null,
         *       "from":
         *          {"name":"834 SE LA MBERT ST, PORTLAND","stopId":null,"stopCode":null,"lon":-122.6569759304584,"lat":45.46838276495563,"arrival":null,"departure":null,"orig":"834 SE LA MBERT ST, PORTLAND","zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.6569759304584,45.46838276495563]}"},
         *       "to":
         *          {"name":"Art Museum, Portland (Stop ID 6493)","stopId":null,"stopCode":null,"lon":-122.68399,"lat":45.516304,"arrival":1387854837000,"departure":1387854837000,"orig":"Art Museum, Portland (Stop ID 6493)","zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.68399,45.516304]}"},
         *       "legGeometry":{"points":"kpotGbmskVk@?mCW","levels":null,"length":294},
         *       "notes":[{"text":"Caution!"}],
         *       "alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}],
         *       "routeShortName":null,"routeLongName":null,"boardRule":null,"alightRule":null,"rentedBike":null,"duration":5851000,"bogusNonTransitLeg":false,
         *       "steps":[
         *           {"distance":24.44480017811421,"relativeDirection":null,"streetName":"834 SE LA MBERT ST, PORTLAND","absoluteDirection":"NORTH","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6569759304584,"lat":45.46838276495563,"elevation":"0,14.1,5,14.2,15,14.2,24,14.2"},
         *           {"distance":1636.7473567151435,"relativeDirection":"RIGHT","streetName":"Southeast McLoughlin Boulevard","absoluteDirection":"NORTHWEST","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6519503,"lat":45.4887532,"elevation":"0,...","alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}]},
         *       ]
         *   }
         */
        public static class Itinerary
        {
            @JsonProperty("duration")
            public Long duration;
            @JsonProperty("startTime")
            public Long startTime;
            @JsonProperty("endTime")
            public Long endTime;
            @JsonProperty("walkTime")
            public Long walkTime;
            @JsonProperty("transitTime")
            public Long transitTime;
            @JsonProperty("waitingTime")
            public Long waitingTime;

            @JsonProperty("walkDistance")
            public Double walkDistance;
            @JsonProperty("elevationLost")
            public Double elevationLost;
            @JsonProperty("elevationGained")
            public Double elevationGained;
            @JsonProperty("tooSloped")
            public Boolean tooSloped;

            @JsonProperty("transfers")
            public Integer transfers;
            @JsonProperty("fare")
            public Fare fare;
            @JsonProperty("legs")
            public Leg legs[];

            public String toString() {
                String retVal = "";
                if(this.legs != null) {
                    retVal += "\n\t\tItin with " + this.legs.length + " legs:";
                    for(Leg l : this.legs)
                        retVal += "\n\t\t\t: " + l.toString();
                }
                return retVal;
            }
        }

        /**
         *   {
         *       "from":
         *       "to":
         *       "legGeometry":{"points":"kpotGbmskVk@?mCW","levels":null,"length":294},
         *       "mode":"WALK","route":"",
         *
         *       "startTime":1387848986000,"endTime":1387854837000,
         *       "distance":7522.2365911245715,
         *       "duration":5851000,
         *
         *       <leg
         *            mode="TRAM"
         *            tripId="5032854"
         *            tripBlockId="9050"
         *            routeId="90"
         *            route="MAX Red Line"
         *            routeLongName="MAX Red Line"
         *            headsign="City Center & Beaverton TC"
         *            routeType="0"
         *            agencyId="TriMet"
         *            agencyTimeZoneOffset="0"
         *            agencyUrl="http://trimet.org"
         *            agencyName="TriMet"
         *       >
         *
         *       <leg
         *            mode="BUS"
         *            tripId="5022279"
         *            tripBlockId="808"
         *            routeId="8"
         *            route="8"
         *            routeShortName="8"
         *            routeLongName="Jackson Park/NE 15th"
         *            headsign="Marquam Hill"
         *            routeType="0"
         *            agencyId="TriMet"
         *            agencyTimeZoneOffset="0"
         *            agencyUrl="http://trimet.org"
         *            agencyName="TriMet"
         *       >
         *
         *       "boardRule":null,"alightRule":null,"rentedBike":null,"bogusNonTransitLeg":false,
         *
         *       "notes":[{"text":"Caution!"}],
         *       "alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}],
         *       "steps":[
         *           {"distance":24.44480017811421,"relativeDirection":null,"streetName":"834 SE LA MBERT ST, PORTLAND","absoluteDirection":"NORTH","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6569759304584,"lat":45.46838276495563,"elevation":"0,14.1,5,14.2,15,14.2,24,14.2"},
         *           {"distance":1636.7473567151435,"relativeDirection":"RIGHT","streetName":"Southeast McLoughlin Boulevard","absoluteDirection":"NORTHWEST","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6519503,"lat":45.4887532,"elevation":"0,...","alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}]},
         *       ]
         *   }
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Leg
        {
            @JsonProperty("from")
            public Place from;
            @JsonProperty("to")
            public Place to;
            @JsonProperty("mode")
            public String mode;

            @JsonProperty("tripId")
            public String trip;
            @JsonProperty("tripBlockId")
            public String block;
            @JsonProperty("interlineWithPreviousLeg")
            public Boolean interlineWithPreviousLeg = false;

            @JsonProperty("routeId")
            public String routeId;
            @JsonProperty("routeShortName")
            public String routeShortName;
            @JsonProperty("routeLongName")
            public String routeLongName;
            @JsonProperty("route")
            public String otpInterlineRouteName;
            @JsonProperty("routeType")
            public String routeType;
            @JsonProperty("headsign")
            public String headsign;
            @JsonProperty("agencyId")
            public String agencyId;

            @JsonProperty("startTime")
            public Long startTime;
            @JsonProperty("endTime")
            public Long endTime;
            @JsonProperty("distance")
            public Double distance;
            @JsonProperty("duration")
            public Double duration;

            @JsonProperty("notes")
            public Note notes[];

            @JsonProperty("steps")
            public Step steps[];

            @JsonProperty("legGeometry")
            public LegGeometry legGeometry;

            /** make route and headsign string */
            public String routeNameAndDestination(String sep)
            {
                String retVal = this.routeName();
                if(this.headsign != null && this.headsign.length() > 0)
                {
                    retVal = retVal + sep + this.headsign;
                    retVal = retVal.trim();
                }
                return retVal;
            }
            public String routeNameAndDestination()
            {
                return routeNameAndDestination(" to ");
            }

            /** make route name */
            public String routeName()
            {
                String retVal = "";
                if(this.routeShortName != null && this.routeShortName.length() > 0)
                    retVal = this.routeShortName;
                if(this.routeLongName != null && this.routeLongName.length() > 0)
                    if(retVal.length() > 0)
                        retVal += "-";
                retVal += this.routeLongName;
                return retVal;
            }


            /**
             * "notes":[{"text":"Caution!"}],
             * "alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}],
             *
             * TODO: move to 'Alert' at some point (note was just a simple alerts hack)
             */
            public static class Note
            {
                @JsonProperty("text")
                public String text;
            }

            /**
             *       "steps":[
             *           {"distance":24.44480017811421,"relativeDirection":null,"streetName":"834 SE LA MBERT ST, PORTLAND","absoluteDirection":"NORTH","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6569759304584,"lat":45.46838276495563,"elevation":"0,14.1,5,14.2,15,14.2,24,14.2"},
             *           {"distance":1636.7473567151435,"relativeDirection":"RIGHT","streetName":"Southeast McLoughlin Boulevard","absoluteDirection":"NORTHWEST","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6519503,"lat":45.4887532,"elevation":"0,...","alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}]},
             *       ]
             */
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Step
            {
                @JsonProperty("streetName")
                public String streetName;
                @JsonProperty("lon")
                public Double lon;
                @JsonProperty("lat")
                public Double lat;

                @JsonProperty("distance")
                public Double distance;
                @JsonProperty("absoluteDirection")
                public String absoluteDirection;
                @JsonProperty("relativeDirection")
                public String relativeDirection;

                @JsonProperty("stayOn")
                public Boolean stayOn;

                // NOTE: not using elevation in my work...the String used to work with older
                //       version of OTP, but no longer.  Jackson mentions something about an array
                //@JsonProperty("elevation")
                //public String elevation;
            }

            /**
             *  "legGeometry":{"points":"kpotGbmskVk@?mCW","levels":null,"length":294},
             */
            public static class LegGeometry
            {
                @JsonProperty("points")
                public String points;
                @JsonProperty("length")
                public Integer length;
                @JsonProperty("levels")
                public Integer levels;
            }
        }

        /**
         * "fare":{"regular":{"currency":{"symbol":"$","currency":"USD","currencyCode":"USD","defaultFractionDigits":2},"cents":250}}
         */
        public static class Fare
        {
            @JsonProperty("fare")
            public FareDetail fare;

            public static class FareDetail
            {
                @JsonProperty("regular")
                public Currency regular;

                /**
                 * "regular":{"currency":{"symbol":"$","currency":"USD","currencyCode":"USD","defaultFractionDigits":2},"cents":250}}}
                 */
                public static class Currency
                {
                    @JsonProperty("currency")
                    public CurrencyDetail currency;

                    @JsonProperty("cents")
                    public Integer cents;

                    /**
                     * "currency":{"symbol":"$","currency":"USD","currencyCode":"USD","defaultFractionDigits":2}
                     */
                    public static class CurrencyDetail
                    {
                        @JsonProperty("symbol")
                        public String symbol;
                        @JsonProperty("currency")
                        public String currency;
                        @JsonProperty("currencyCode")
                        public String currencyCode;
                        @JsonProperty("defaultFractionDigits")
                        public Integer defaultFractionDigits;
                    }
                }
            }

        }
    }


    /**
     * "requestParameters":{"optimize":"QUICK","time":"10:15pm","arriveBy":"false","maxWalkDistance":"840.0","fromPlace":"834 SE LA MBERT ST, PORTLAND::45.468384,-122.65718","toPlace":"Art Museum, Portland (Stop ID 6493)::45.516304,-122.68399","date":"2013 -12-20","maxHours":"6","mode":"WALK"},
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RequestParameters
    {
        @JsonProperty("time")
        public String time;
        @JsonProperty("date")
        public String date;
        @JsonProperty("optimize")
        public String optimize;
        @JsonProperty("maxHours")
        public String maxHours;
        @JsonProperty("mode")
        public String mode;
        @JsonProperty("arriveBy")
        public String arriveBy;
        @JsonProperty("maxWalkDistance")
        public String maxWalkDistance;
        @JsonProperty("fromPlace")
        public String fromPlace;
        @JsonProperty("toPlace")
        public String toPlace;
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

    public String toString() {
        String retVal = "TripPlan:";
        if(this.plan != null)
            retVal += this.plan.toString();
        if(this.error != null)
            retVal += this.error.toString();
        return retVal;
    }

    /** utiltity method ... most useful for testing static data from a file */
    public static TripPlan planFromFile(ObjectMapper mapper, String filePath) throws Exception
    {
        TripPlan p = mapper.readValue(new File(filePath), TripPlan.class);
        return p;
    }
}
