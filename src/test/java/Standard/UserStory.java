package Standard;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.json.simple.parser.*;
import org.testng.annotations.*;

import java.io.*;

/**
 * @author Nandkumar Babar
 */
public class UserStory {
    String cookie="Not updated";
    String issueID="AM-0";

    @Test(priority = 1)
    public void LoginJira() throws IOException, ParseException {

        FileReader fr = new FileReader("/home/nandkumar/Videos/15ThJune/src/main/java/inputJsons/login.json");
        JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response reponse = RestAssured.given().baseUri("http://localhost:9009").body(requestBody)
                .contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();

        JSONObject js = new JSONObject(reponse.asString());
        cookie = "JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(reponse.getStatusCode());

    }
    @Test(priority = 2)
    public void createUserStory() throws IOException, ParseException {

     FileReader fr =new FileReader("/home/nandkumar/Videos/15ThJune/src/main/java/inputJsons/" +
             "createUserStory.json");
     JSONParser jp = new JSONParser();
        String requestBody = jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri("http://localhost:9009")
                .body(requestBody).header("Cookie", cookie)
                .contentType(ContentType.JSON).when().post("/rest/api/2/issue")
                .then().log().all().extract().response();

        JSONObject js =new JSONObject(response.asString());
        issueID = js.get("key").toString();
        System.out.println(issueID);
    }


}
