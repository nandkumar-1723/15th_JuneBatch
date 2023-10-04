package Advance;

import Utils.*;
import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.json.simple.parser.*;
import org.testng.annotations.*;

import java.io.*;
import java.util.*;

import static Utils.ReadPropFile.readPropFile;

/**
 * @author Nandkumar Babar
 */
public class AddAttachment {

    String cookie;
    String issueId;

    @Test(priority = 1)
    public void loginJira() throws IOException, ParseException {

        String requestBody = ReadJsonFile.readJsonFile("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/java/inputJsons/login.json");

        //Actual login code
        Response response = RestAssured.given().baseUri(readPropFile("url")).body(requestBody).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();

        //To read the json respone and find cookie value.
        JSONObject js = new JSONObject(response.asString());
        cookie = "JSESSIONID=" + js.getJSONObject("session").get("value").toString();
        System.out.println(cookie);
    }

    @Test(priority = 2)
    public void createTask() throws IOException, ParseException {
        //  note - We are getting the URL from global to understand more please refer the read properties cod in login

        //To get the request body (BUG)
        String requestBody = ReadJsonFile.readJsonFile("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/java/inputJsons/CreateBug.json");

        //Create bug code
        Response response = RestAssured.given().baseUri(readPropFile("url")).body(requestBody).header("Cookie", cookie).contentType(ContentType.JSON)
                .when().post("/rest/api/2/issue")
                .then().log().all().extract().response();

        //To get the issue id for PUT- Delete - Get call.
        JSONObject js = new JSONObject(response.asString());
        issueId = js.get("key").toString();
        System.out.println(issueId);

    }


    @Test(priority = 3)
    public void addAttachment() throws IOException {

        File f = new File("/home/nandkumar/Videos/15th June API/15th_JuneBatch/src/main/resources/FishModel.png");

        Response response = RestAssured.given().baseUri(readPropFile("url")).header("Cookie", cookie).contentType(ContentType.MULTIPART)
                .multiPart("file", f).header("X-Atlassian-Token", "no-check").when()
                .post("/rest/api/2/issue/"+issueId+"/attachments").then().log().all().extract().response();


    }
}

