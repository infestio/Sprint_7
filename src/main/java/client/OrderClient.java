package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String LIST_ORDERS_PATH = "/api/v1/orders";

    @Step("Create an order using endpoint /api/v1/orders")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .log()
                .all()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .log()
                .all();
    }

    @Step("We receive a general list of orders")
    public ValidatableResponse gettingListOfOrders() {
        return given()
                .log()
                .all()
                .spec(getBaseSpec())
                .when()
                .get(LIST_ORDERS_PATH)
                .then()
                .log()
                .all();
    }

}
