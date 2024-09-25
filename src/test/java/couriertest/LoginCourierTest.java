package couriertest;

import client.Courier;
import client.CourierClient;
import client.CourierCredentials;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("Рокки", "1234", "Слай");
        courierClient.create(courier);
    }

    @Test
    @Description("The courier can log in, a successful endpoint returns an ID")
    public void loginCourierPositiveTest() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginResponseStatusCode = loginResponse.extract().statusCode();
        assertEquals(loginResponseStatusCode, 200);
        String loginResponseBody = loginResponse.extract().body().asString();
        assertTrue(loginResponseBody.contains("id"));
        courierId = loginResponse.extract().path("id");
        assertNotEquals(courierId, 0);

    }

    @After
    public void cleanUp() {
        courierClient.delete(courierId);
    }
}
