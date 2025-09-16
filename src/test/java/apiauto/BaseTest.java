package apiauto.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    @BeforeClass
    public void setup() {
        // base URI untuk semua test
        RestAssured.baseURI = "https://reqres.in";

        // optional: tambahkan filter untuk Allure logging
        RestAssured.filters(new AllureRestAssured());
    }
}
