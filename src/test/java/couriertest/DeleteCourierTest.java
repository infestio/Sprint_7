package couriertest;

import client.Courier;
import client.CourierClient;
import client.CourierCredentials;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteCourierTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("Рокки", "1234", "Слай");
        courierClient.create(courier);
    }

    @Test
    @Description("This test only checks the operation of the Delete endpoint, the test does not apply to the additional task")
    public void deleteCourierTest() {
        ValidatableResponse loginCourier = courierClient.login(CourierCredentials.from(courier));
        int courierId = loginCourier.extract().path("id");
        ValidatableResponse deleteCourier = courierClient.delete(courierId);
        int deleteStatusCode = deleteCourier.extract().statusCode();
        assertEquals(deleteStatusCode, 200);
        boolean deleteResponse = deleteCourier.extract().path("ok");
        assertTrue(deleteResponse);

    }
}
