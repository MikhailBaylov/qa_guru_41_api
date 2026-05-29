package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.example.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class WdHubStatusTestsHw extends TestBase {

    @DisplayName("Checking status 401 if unauthorized user")
    @Test
    public void unauthorizedUserStatusTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @DisplayName("Checking status 400 if bad request")
    @Test
    public void badRequestStatusTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status?test=id")
                .then()
                .log().all()
                .statusCode(400);
    }

    @DisplayName("Checking schema")
    @Test
    public void schemaTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_schema.json"));
    }

    @DisplayName("Checking status 400 if post request")
    @Test
    public void postRequestStatusTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .post("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(400);
    }

    @DisplayName("Checking status 401 if bad password")
    @Test
    public void badPasswordStatusTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "12")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @DisplayName("Checking OK status")
    @Test
    public void status200Test() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @DisplayName("Checking ready message")
    @Test
    public void readyMessageTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", equalTo(true));
    }

    @DisplayName("Checking message")
    @Test
    public void messageTest() {
        given()
                .log().uri()
                .log().method()
                .log().headers()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", containsString("Selenoid"));
    }

}
