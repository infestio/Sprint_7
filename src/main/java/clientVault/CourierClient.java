package clientVault;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";
    private static final String DELETE_PATH = "/api/v1/courier/";

    @Step
    public ValidatableResponse create(Courier courier) {
        return given()
                .log()
                .all()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log()
                .all();
    }

    @Step
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .log()
                .all()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(LOGIN_PATH)
                .then()
                .log()
                .all();
    }

    @Step
    public ValidatableResponse delete(int courierId) {
        return given()
                .log()
                .all()
                .spec(getBaseSpec())
                .when()
                .delete(DELETE_PATH + courierId)
                .then()
                .log()
                .all();
    }
}
