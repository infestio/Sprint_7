package orderTest;

import clientVault.OrderClient;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GettingListOfOrdersTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void gettingListOfOrdersPositiveTest() {
        ValidatableResponse gettingListOfOrdersResponse = orderClient.gettingListOfOrders();
        int gettingListOfOrdersResponseStatusCode = gettingListOfOrdersResponse.extract().statusCode();
        assertEquals(gettingListOfOrdersResponseStatusCode, 200);
        ArrayList<String> gettingListOfOrdersResponseBody = gettingListOfOrdersResponse.extract().path("orders");
        assertNotEquals(gettingListOfOrdersResponseBody.size(), 0);
    }
}
