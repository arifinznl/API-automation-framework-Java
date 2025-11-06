package apiauto;

import apiauto.config.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import org.json.JSONObject;

public class TestBoundaryCases extends BaseTest {

    @Test
    public void testGetUsers_PageZero() {
        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/api/users?page=0")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void testGetUsers_PageMax() {
        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/api/users?page=9999")
                .then().log().all()
                .assertThat().statusCode(200); // Reqres return kosong tapi sukses
    }

    @Test
    public void testPostCreateUser_LongName() {
        String longName = "A".repeat(300);
        String job = "Tester";

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("name", longName);
        bodyObj.put("job", job);

        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .header("Content-Type", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("/api/users")
                .then().log().all()
                .assertThat().statusCode(201);
    }
}
