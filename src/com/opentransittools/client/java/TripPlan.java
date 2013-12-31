package com.opentransittools.client.java;


public class TripPlan
{
    private RequestParameters m_requestParameters;
    private Plan m_plan;

    /** 
     * "requestParameters":{"optimize":"QUICK","time":"10:15pm","arriveBy":"false","maxWalkDistance":"840.0","fromPlace":"834 SE LA MBERT ST, PORTLAND::45.468384,-122.65718","toPlace":"Art Museum, Portland (Stop ID 6493)::45.516304,-122.68399","date":"2013 -12-20","maxHours":"6","mode":"WALK"},
     * @author purcellf
     */
    public static class RequestParameters
    {
        private String m_time;

        public String getTime() {return m_time;}
        public void setTime(String time) {m_time = time; }
    }
    public RequestParameters getRequestParameters() {return m_requestParameters;}
    public void setRequestParameters(RequestParameters requestParameters) {m_requestParameters = requestParameters;}

    /**
     *        "plan":{"date":1387848986000,
     *                "from":{"name":"834 SE LA MBERT ST, PORTLAND","stopId":null,"stopCode":null,"lon":-122.6569759304584,"lat":45.46838276495563,"arrival":null,"departure":null,"orig":null,"zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.6569759304584,45.46838276495563]}"},
     *                "to":{"name":"Art Museum, Portland (Stop ID 6493)","stopId":null,"stopCode":null,"lon":-122.68399,"lat":45.516304,"arrival":null,"departure":null,"orig":null,"zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.68399,45.516304]}"},
     *                "itineraries":[
     *                    {
     *                      "duration":5851000,"startTime":1387848986000,"endTime":1387854837000,"walkTime":5851,"transitTime":0,"waitingTime":0,"walkDistance":7522.2365911245715,"elevationLost":67.94127849117022,"elevationGained":66.95316905912915,"transfers":-1,"fare":null,
     *                      "legs":[
     *                          {"startTime":1387848986000,"endTime":1387854837000,"distance":7522.2365911245715,"mode":"WALK","route":"","agencyName":null,"agencyUrl":null,"agencyTimeZoneOffset":0,
     *                           "routeColor":null,"routeId":null,"routeTextColor":null,"interlineWithPreviousLeg":null,"tripShortName":null,"headsign":null,"agencyId":null,"tripId":null,
     *                           "from":
     *                              {"name":"834 SE LA MBERT ST, PORTLAND","stopId":null,"stopCode":null,"lon":-122.6569759304584,"lat":45.46838276495563,"arrival":null,"departure":null,"orig":"834 SE LA MBERT ST, PORTLAND","zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.6569759304584,45.46838276495563]}"},
     *                           "to":
     *                              {"name":"Art Museum, Portland (Stop ID 6493)","stopId":null,"stopCode":null,"lon":-122.68399,"lat":45.516304,"arrival":1387854837000,"departure":1387854837000,"orig":"Art Museum, Portland (Stop ID 6493)","zoneId":null,"geometry":"{\"type\": \"Point\", \"coordinates\": [-122.68399,45.516304]}"},
     *                           "legGeometry":{"points":"kpotGbmskVk@?mCW","levels":null,"length":294},
     *                           "notes":[{"text":"Caution!"}],
     *                           "alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}],
     *                           "routeShortName":null,"routeLongName":null,"boardRule":null,"alightRule":null,"rentedBike":null,"duration":5851000,"bogusNonTransitLeg":false,
     *                           "steps":[
     *                               {"distance":24.44480017811421,"relativeDirection":null,"streetName":"834 SE LA MBERT ST, PORTLAND","absoluteDirection":"NORTH","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6569759304584,"lat":45.46838276495563,"elevation":"0,14.1,5,14.2,15,14.2,24,14.2"},
     *                               {"distance":1636.7473567151435,"relativeDirection":"RIGHT","streetName":"Southeast McLoughlin Boulevard","absoluteDirection":"NORTHWEST","exit":null,"stayOn":false,"bogusName":false,"lon":-122.6519503,"lat":45.4887532,"elevation":"0,...","alerts":[{"alertHeaderText":{"translations":{"entry":[{"key":"en","value":"Caution!"}]},"someTranslation":"Caution!"}}]},
     *                           ]}
     *                      ],
     *                      "tooSloped":false
     *                    }
     *                ]
     *        }
     */
    public static class Plan
    {
        private From m_from;

        public static class From
        {
            private String m_name;
            public  String getName() { return m_name; }
            public  void   setName(String n) { m_name = n; }
        }
        public From getFrom() {return m_from;          }
        public void setFrom(From from) {m_from = from; }

    }
    public Plan getPlan() {return m_plan;    }
    public void setPlan(Plan plan) {m_plan = plan;    }
}

public static class Zoo{}