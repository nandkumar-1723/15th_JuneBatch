package Advance;

import org.json.*;
import org.json.simple.parser.*;
import org.testng.annotations.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class updateJson {

    @Test(priority = 1)
    public void updateJson() throws IOException, ParseException {

        FileReader fr = new FileReader("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/java/inputJsons/CreateBug.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();
        JSONObject js = new JSONObject(requestBody);

        //Update the issue type
        js.getJSONObject("fields").getJSONObject("issuetype").put("name","Task");

        //Update the summary
        js.getJSONObject("fields").put("summary","Updating by using automation");

        //Add any key if already not present
        js.getJSONObject("fields").put("name","VIRAT");

        System.out.println(js);

    }
}
