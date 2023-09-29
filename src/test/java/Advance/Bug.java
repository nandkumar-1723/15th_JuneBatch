package Advance;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.json.simple.parser.*;
import org.testng.annotations.*;

import java.io.*;
import java.util.*;

/**
 * @author Nandkumar Babar
 */
public class Bug {

    @Test(priority = 1)
    public void loginJira() throws IOException, ParseException {

        //Read the url from properties file
        FileReader fr = new FileReader("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/resources/globalValues.properties");
        Properties pr =new Properties();
        pr.load(fr);
       String url = pr.getProperty("url");

       //Read the body from the json file.
        FileReader jsonFile = new FileReader("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/java/inputJsons/login.json");
        JSONParser jp = new JSONParser();
        Object requestBody = jp.parse(jsonFile);

        //Actual login code
        Response response = RestAssured.given().baseUri(url).body(requestBody.toString()).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();

        //To read the json respone and find cookie value.
        JSONObject js = new JSONObject(response.asString());
        String cookie = "JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(cookie);
    }

}
