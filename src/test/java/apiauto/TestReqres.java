package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;

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
    public void updateUser() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200) // response PUT di reqres.in seharusnya 200 OK
                .body("job", equalTo("zion resident"));
    }
}
