package com.opentransittools.client.java;


public class TripPlan
{
    private RequestParameters m_requestParameters;
    private Plan m_plan;


    public static class RequestParameters
    {
        private String m_time;

        public String getTime() {return m_time;}
        public void setTime(String time) {m_time = time; }
    }
    public RequestParameters getRequestParameters() {return m_requestParameters;}
    public void setRequestParameters(RequestParameters requestParameters) {m_requestParameters = requestParameters;}

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
