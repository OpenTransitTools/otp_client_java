package com.opentransittools.client;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * The purpose of Configuration is to read a properties file, and expose the content theirin via the named enumerated params 
 * 
 * @author  Frank Purcell (purcellf@trimet.org) -- borrowed from code by Kohsuke Kawaguchi
 * @date    Apr 18, 2007
 * @version Revision: 1.0
 * @since   1.0
 */

public enum Configuration 
{
    GEO_URL,
    OTP_URL,
    SOLR_URL,
    ;

    //final Properties properties = new Properties();
    static Properties rb = null;
    static {
        try {
            rb = new Properties();
            rb.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("Configuration.properties"));
        }
        catch(Exception e) {}
    };
    //private static ResourceBundle rb = ResourceBundle.getBundle("Configuration");


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
            //return rb.getString(name());
            return rb.getProperty(name());
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


    public boolean get(boolean defVal)
    {
        boolean retVal = defVal;
        try
        {
            String r = get();
            if(r != null && r.equalsIgnoreCase("True"))
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
                return MessageFormat.format(rb.getProperty(name()), args);
                //return MessageFormat.format(rb.getString(name()), args);
            }
        }
        catch(Exception e)
        {
        }
        
        return null;
    }

    public static void main(String[] z) throws Exception
    {
        System.out.println(Configuration.SOLR_URL.get());
        System.out.println(Configuration.OTP_URL.get());
        System.out.println(Configuration.class.getName());
    }
}
