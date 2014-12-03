package com.opentransittools.client;


import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * The purpose of Configuration is to read a properties file, and expose the content theirin via the 
 * named enumerated params 
 * 
 * @author  Frank Purcell (purcellf@trimet.org) -- taken from code by Kohsuke Kawaguchi
 * @date    Dec 1, 2014
 * @version Revision: 1.0
 * @since   1.0
 */
public enum Configuration 
{
    OTP_URL,
    GEOCODER_URL,
    SOLR_URL
    ;
    
    private static final ResourceBundle rb = getResourceBundle();
    public static ResourceBundle getResourceBundle()
    {
        ResourceBundle retVal = null;
        try {
            retVal = ResourceBundle.getBundle(Configuration.class.getName());
        }
        catch(Exception e) {
            String name = Configuration.class.getName();
            name = name.substring(name.lastIndexOf('.')+1);
            retVal = ResourceBundle.getBundle(name);
        }
        return retVal; 
    }
    
    
    public static Configuration construct(String value)
    {
        Configuration retVal = null;
        
        try
        {
            value = value.trim();
            
            for(Configuration k : Configuration.values())
            {
                if(value.equals(k.get("NO MATCH")))
                {
                    retVal = k;
                    break;
                }
            }
        }
        catch(Exception _)
        {
        }

        return retVal;
    }

    public String get() throws Exception
    {
        synchronized(Configuration.class)
        {
            return rb.getString(name());
        }
    }

    public String get(String def)
    {
        String retVal = null;
        try
        {
            retVal = get();
        }
        catch(Exception _)
        {
        }
        
        if(retVal == null || retVal.length() < 1)
            retVal = def;

        return retVal;
    }


    public int get(int def)
    {
        String tmp = get(Integer.toString(def));
        //return IntUtils.getIntFromString(tmp);
        return 2;
    }    


    public long get(long def)
    {
        String tmp = get(Long.toString(def));
        //return IntUtils.getLongFromString(tmp);
        return 2;
    }    
    

    public boolean get(boolean defVal)
    {
        boolean retVal = defVal;
        try
        {
            String r = get();
            if(r != null && r.equalsIgnoreCase("true"))
                retVal = true;
        }
        catch(Exception _)
        {
        }

        return retVal;
    }

    public boolean is()
    {
        return get(false);
    }
    
    public String format(Object ... args)
    {
        try
        {
            synchronized(Configuration.class) 
            {
                return MessageFormat.format(rb.getString(name()), args);
            }
        }
        catch(Exception e)
        {
        }
        
        return null;
    }
    
    public static void main(String[] z) throws Exception
    {
        System.out.println(Configuration.OTP_URL.get());
        System.out.println(Configuration.GEOCODER_URL.get());
    }
}
