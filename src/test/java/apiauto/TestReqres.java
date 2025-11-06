package apiauto;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.qameta.allure.restassured.AllureRestAssured;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestReqres extends apiauto.config.BaseTest {


    @Test
    public void testGetListUsers()  {

        File jsonSchema = new File("src/test/resources/jsonSchema/GetSingleUserSchema.json");

//        RestAssured.baseURI = "https://reqres.in";
        RestAssured
                .given().when()
                .get("/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(2))
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));
    }

    @Test
    public void testPostCreateUser() {
        String valueName = "Zaenal";
        String valueJob  = "QA";

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(valueName))
                .assertThat().body("job", Matchers.equalTo(valueJob));
    }


    @Test
    public void testPutUser() {

//        RestAssured.baseURI = "https://reqres.in";

        RestAssured.baseURI = "https://reqres.in";

        int userId = 2;
        String newName = "updatedUser";

        // Ambil data lama dulu (biar konsisten)
        String fname = given().when().get("api/users/" + userId).getBody().jsonPath().get("data.first_name");
        String lname = given().when().get("api/users/" + userId).getBody().jsonPath().get("data.last_name");
        String avatar = given().when().get("api/users/" + userId).getBody().jsonPath().get("data.avatar");
        String email = given().when().get("api/users/" + userId).getBody().jsonPath().get("data.email");
        System.out.println("Last name before = " + lname);


        // Body hanya update last_name saja
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", 2);
        bodyMap.put("last_name", "Weaver");
        bodyMap.put("avatar", "https://reqres.in/img/faces/2-image.jpg");
        bodyMap.put("first_name", "updatedUser");
        bodyMap.put("email", "janet.weaver@reqres.in");
        JSONObject jsonObject = new JSONObject(bodyMap);

        // Body hanya update last_name saja
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", 2);
        bodyMap.put("last_name", "Weaver");
        bodyMap.put("avatar", "https://reqres.in/img/faces/2-image.jpg");
        bodyMap.put("first_name", "updatedUser");
        bodyMap.put("email", "janet.weaver@reqres.in");
        JSONObject jsonObject = new JSONObject(bodyMap);

        given().log().all()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .when()
                .patch("api/users/" + userId)
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));

        System.out.println("Last name after = " + newName);

    }

    @Test
    public void testPatchUser() {
        // Define baseURI
//        RestAssured.baseURI = "https://reqres.in/";

        // Data to update
        int userId = 3;
        String newName = "updatedUser";

        // Test PATCH user id 3 -> update first_name
        // First, get the first name of user id 3
        String fname = given().when().get("api/users/" + userId)
                .getBody().jsonPath().get("data.first_name");
        System.out.println("name before = " + fname);

        // Change the first name to "updatedUser"
        // Create body request with HashMap and convert it to json
        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("first_name", newName);
        JSONObject jsonObject = new JSONObject(bodyMap);

        given().log().all()
                .header("x-api-key", "reqres-free-v1")   // API untuk bypass koneksi
                .header("Content-Type", "application/json") // set the header to accept json
                .body(jsonObject.toString())                // convert jsonObject to string format
                .patch("api/users/" + userId)               // send PATCH request
                .then().log().all()                         // log response
                .assertThat().statusCode(200)               // assert status code
                .assertThat().body("first_name", Matchers.equalTo(newName)); // assert updated name

        System.out.println("name after = " + newName);
    }

    @Test
    public void testDeleteUser(){

        int userToDelete = 4;

        given().log().all()
                .header("x-api-key", "reqres-free-v1")      // API untuk bypass koneksi
                .header("Content-Type", "application/json") // set the header to accept json
                .when().delete("api/users/" + userToDelete)
                .then()
                .log().all()
                .assertThat().statusCode(204);
    }

}
