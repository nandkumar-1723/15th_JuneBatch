package Basic;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.response.*;
import org.json.*;
import org.testng.annotations.*;

/**
 * @author Nandkumar Babar
 */
public class UserStory {
    String cookie="Not updated";
    String issueID="AM-0";

     @Test(priority = 1)
    public void loginJira(){
        Response reponse = RestAssured.given().baseUri("http://localhost:8080").body("{\n" +
                        "     \"username\": \"nandkumar\",\n" +
                        "      \"password\": \"nandkumar\" \n" +
                        " }").contentType(ContentType.JSON).when().post("/rest/auth/1/session")
                .then().log().all().extract().response();

        JSONObject js = new JSONObject(reponse.asString());
         cookie = "JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(reponse.getStatusCode());

    }

    @Test(priority = 2)
    public void createUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:8080").body("{\n" +
                        "   \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "           \"key\": \"AM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Adding a user story for 15th june students\",\n" +
                        "       \"issuetype\": {\n" +
                        "           \"name\": \"Story\"\n" +
                        "       }\n" +
                        "}\n" +
                        "}").header("Cookie", cookie)
                .contentType(ContentType.JSON).when().post("/rest/api/2/issue")
                .then().log().all().extract().response();

        JSONObject js =new JSONObject(response.asString());
         issueID = js.get("key").toString();
    }

    @Test(priority = 3)
    public void getUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:8080").header("Cookie", cookie)
                .contentType(ContentType.JSON)
                .when().get("/rest/api/2/issue/"+issueID+"").then().log().all().extract().response();
        System.out.println(response.getStatusCode());
    }
    @Test(priority = 4)
    public void updateUserStory(){
        Response response = RestAssured.given().baseUri("http://localhost:8080").body("{\n" +
                        "   \"fields\": {\n" +
                        "       \"project\": {\n" +
                        "           \"key\": \"AM\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Update the story for 15th june students\",\n" +
                        "       \"issuetype\": {\n" +
                        "           \"name\": \"Story\"\n" +
                        "       }\n" +
                        "}\n" +
                        "}")
                .header("Cookie", cookie).contentType(ContentType.JSON).when().put("/rest/api/2/issue/" + issueID + "")
                .then().log().all().extract().response();
    }
    @Test(priority = 5)
    public void deleteUserStory() throws Exception {
        Response response = RestAssured.given().baseUri("http://localhost:8080").header("Cookie", cookie).contentType(ContentType.JSON)
                .when().delete("/rest/api/2/issue/" + issueID+"").then().log().all().extract().response();
        System.out.println(response.getStatusCode());
        if (response.getStatusCode()!=204){
            throw new Exception("status code is not 204");
        }
    }
}
