package apiauto;

import apiauto.config.BaseTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class TestNegativeCases extends BaseTest {

    @Test
    public void testGetUser_InvalidId() {
        int invalidUserId = 9999;

        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/api/users/" + invalidUserId)
                .then().log().all()
                .assertThat().statusCode(404);
    }

    @Test
    public void testPostCreateUser_MissingField() {
        String valueName = "Zaenal";
        String valueJob  = "";

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);  //missing job

        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("/api/users")
                .then().log().all()
                .assertThat().statusCode(201) // Reqres.in tetap return 201 walau field kurang
                .assertThat().body("name", Matchers.equalTo(valueName))
                .assertThat().body("job", Matchers.equalTo(valueJob));;
    }

    @Test
    public void testPostCreateUser_WrongType() {
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("name", 12345); // salah tipe (integer)
        bodyObj.put("job", true);   // salah tipe (boolean)

        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("/api/users")
                .then().log().all()
                .assertThat().statusCode(201); // Reqres tetap menerima (mock API)
    }
}
