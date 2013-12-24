import java.io.File;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;


public class test
{
    static String readFile(String path) throws Exception
    {
        String text = new Scanner( new File(path), "UTF-8" ).useDelimiter("\\A").next();
        return text;
    }

    public static void main(String[] args) throws Exception
    {
        //String json = readFile("test.json");
        ObjectMapper mapper = new ObjectMapper();
        TripPlan p = mapper.readValue(new File("test.json"), TripPlan.class);
   }
}
