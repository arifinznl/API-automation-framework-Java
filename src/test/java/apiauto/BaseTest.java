package apiauto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class BaseTest {
    @BeforeClass
    public static void setup() {
        // jika TLS/SSL bermasalah
        RestAssured.useRelaxedHTTPSValidation();

        // base url
        RestAssured.baseURI = "https://reqres.in";

        // ----- Tanpa autentikasi proxy -----
        // RestAssured.proxy("proxy.host.com", 8080);

        // ----- Dengan autentikasi proxy (basic) -----
        // ganti user/pass/host/port sesuai milikmu
        String proxyHost = "proxy.host.com";
        int proxyPort = 8080;
        String proxyUser = "username";
        String proxyPass = "password";

        // set Java Authenticator agar Java mengirim credentials ke proxy
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(proxyUser, proxyPass.toCharArray());
            }
        });

        // aktifkan proxy untuk RestAssured
        //RestAssured.proxy(proxyHost, proxyPort);
    }
}
