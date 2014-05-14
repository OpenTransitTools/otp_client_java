package com.opentransittools.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public Error errror;

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
             * {"agencyId":"TriMet","id":"10579"}
             */
            public static class Stop 
            {
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
            public Integer transers;
            @JsonProperty("fare")
            public Fare fare;
            @JsonProperty("legs")
            public Leg legs[];
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
         *       "agencyName":null,"agencyId":null,"agencyUrl":null,"agencyTimeZoneOffset":0,
         *       "tripId":null,"tripShortName":null,"interlineWithPreviousLeg":null,
         *       "headsign":null,
         *       "routeId":null,"routeShortName":null,"routeLongName":null,
         *       "routeColor":null,"routeTextColor":null,
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
    }
}
