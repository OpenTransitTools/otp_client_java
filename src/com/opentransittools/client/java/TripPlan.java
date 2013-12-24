public class TripPlan 
{
    public static class RequestParameters
    {
    }

    public static class Plan
    {
        public static class From
        {
            private String m_name;
            public  String getName() { return m_name; }
            public  void   setName(String n) { m_name = n; }
        }
    }
}
