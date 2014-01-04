package com.opentransittools.test;

import java.io.File;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.opentransittools.client.TripPlan;

public class Test
{
    static String readFile(String path) throws Exception
    {
        String text = new Scanner( new File(path), "UTF-8" ).useDelimiter("\\A").next();
        return text;
    }

    public static void main(String[] args) throws Exception
    {
        String file = "test.json";
        if(args.length >= 1) file = args[0];

        ObjectMapper mapper = new ObjectMapper();
        TripPlan p = mapper.readValue(new File(file), TripPlan.class);
        System.out.print(p.plan.from.name);
   }
}
