package ReadFiles;

import org.json.*;
import org.json.simple.parser.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class ReadJsonFile {
    public static void main(String[] args) throws IOException, ParseException {
        FileReader fr = new FileReader("src/main/java/inputJsons/practice.json");
        JSONParser jp =new JSONParser();
        String body = jp.parse(fr).toString();
        JSONObject js =new JSONObject(body);

        //Find the length of array
        int lengthOfArray = js.getJSONArray("students").length();
        System.out.println("Length of array is:- "+lengthOfArray);

        //Student-1 name
        String name = js.getJSONArray("students").getJSONObject(0).get("name").toString();
        System.out.println(name);

        //Student-3 id
        String id = js.getJSONArray("students").getJSONObject(2).get("id").toString();
        System.out.println(id);

        //Student-2 (second mokck marks)
        String marks = js.getJSONArray("students").getJSONObject(1).getJSONArray("marks")
                .getJSONObject(1).get("secondMock").toString();
        System.out.println(marks);

        //student-3 (current city)
        String city = js.getJSONArray("students").getJSONObject(2).getJSONObject("city").
                get("current").toString();
        System.out.println(city);
    }
}
