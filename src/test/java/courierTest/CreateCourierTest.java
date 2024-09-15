package courierTest;

import clientVault.Courier;
import clientVault.CourierClient;
import clientVault.CourierCredentials;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("Рокки", "1234", "Слай");
    }

    @After
    public void cleanUp() {
        courierClient.delete(courierId);
    }

    @Test
    @Description("The courier can be created, the request returns the correct response code, in the response body: ok: true")
    public void createCourierPositiveTest() {
        ValidatableResponse createResponse = courierClient.create(courier);
        int createStatusCode = createResponse.extract().statusCode();
        assertEquals(createStatusCode, 201);
        boolean created = createResponse.extract().path("ok");
        assertTrue(created);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals(loginStatusCode, 200);

        courierId = loginResponse.extract().path("id");
        assertNotEquals(courierId, 0);
    }

    @Test
    @Description("You can't create two identical couriers, creating a user with a login that already exists returns an error")
    @Issue("BUG_001")
    public void canNotCreateTwoIdenticalCourierTest() {
        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse createIdenticalResponse = courierClient.create(courier);
        int createIdenticalStatusCode = createIdenticalResponse.extract().statusCode();
        assertEquals(createIdenticalStatusCode, 409);
        String createIdenticalError = createIdenticalResponse.extract().path("message");
        assertEquals(createIdenticalError, "Этот логин уже используется");

    }

}
