package com.opentransittools.client;

import java.util.regex.Pattern;

public class Utils
{
    public static class Modes
    {
        public static String BUS     = "BUS";
        public static String TRAIN   = "TRAIN";
        public static String RAIL    = "RAIL";
        public static String TRAM    = "TRAM";
        public static String GONDOLA = "GONDOLA";

        public static String CAR     = "CAR";

        public static String WALK    = "WALK";
        public static String BICYCLE = "BICYCLE";

        public static String TRANSIT_MODES = BUS + "|" + TRAIN + "|" + RAIL + "|" + TRAM + "|" + GONDOLA;
        public static String RAIL_MODES = TRAIN + "|" + RAIL + "|" + TRAM + "|" + GONDOLA;
        public static Pattern TRANSIT_PATTERN = Pattern.compile(TRANSIT_MODES);
        public static Pattern RAIL_PATTERN = Pattern.compile(RAIL_MODES);

        public static Boolean isWalk(String mode)
        {
            Boolean ret_val = false;
            if(mode != null && mode.equals(WALK))
                ret_val = true;
            return ret_val;
        }

        public static Boolean isBike(String mode)
        {
            Boolean ret_val = false;
            if(mode != null && mode.equals(BICYCLE))
                ret_val = true;
            return ret_val;
        }

        public static Boolean isBus(String mode)
        {
            Boolean ret_val = false;
            if(mode != null && mode.equals(BUS))
                ret_val = true;
            return ret_val;
        }

        public static Boolean isRail(String mode)
        {
            Boolean ret_val = false;
            if(mode != null && mode.matches(RAIL_MODES))
                ret_val = true;
            return ret_val;
        }

        public static Boolean isTransit(String mode)
        {
            Boolean ret_val = false;
            if(mode != null && mode.matches(TRANSIT_MODES))
                ret_val = true;
            return ret_val;
        }
    }

    /** check to see if string appears to be a stop id */
    public static boolean isStopId(String place)
    {
        boolean retVal = false;
        if(place != null && place.length() > 0)
        {
            // if no spaces in place, assume it's an id
            if(place.indexOf(" ")   == -1 &&
               place.indexOf("%20") == -1)
                retVal = true;
        }
        return retVal;
    }
}
