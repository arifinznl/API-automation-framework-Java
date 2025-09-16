package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestReqres {


    @Test
    public void testGetListUsers()  {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured
                .given().when()
                .get("/api/users?page=2")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("per_page", Matchers.equalTo(6))
                .assertThat().body("page", Matchers.equalTo(2));
    }

//    @Test
//    public void createUser() {
//
//        given()
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .body("{\"name\":\"Zaenal\",\"job\":\"QA\"}")
//                .when()
//                .post("/api/users")
//                .then()
//                .log().all()
//                .statusCode(201)   // Expect 201 Created
//                .body("name", equalTo("Zaenal"))
//                .body("job", equalTo("QA"));
//    }

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

}
