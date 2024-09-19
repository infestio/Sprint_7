package orderTest;

import client.Order;
import client.OrderClient;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized .class)
public class CreateOrderTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    private final String [] color;


    public CreateOrderTest(String [] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderParam() {
        return new Object[][] {
                {new String []{"BLACK"}},
                {new String []{"GREY"}},
                {new String []{"BLACK", "GREY"}},
                {new String []{}},
        };
    }

    @Test
    @Description("We check all the points of the task Creating an order")
    public void createOrderWithDifferentColorTest() {

        Order order = new Order("Арни",
                "Стальной",
                "Бульвар Голливуд, 25",
                4,
                "+7 800 355 35 35",
                5,
                "2024-06-13",
                "I'll be back",
                color);
        ValidatableResponse createOrderResponse = orderClient.createOrder(order);
        int createOrderResponseStatusCode = createOrderResponse.extract().statusCode();
        assertEquals(createOrderResponseStatusCode, 201);
        String createOrderResponseBody = createOrderResponse.extract().body().asString();
        assertTrue(createOrderResponseBody.contains("track"));
        int orderTrack = createOrderResponse.extract().path("track");
        assertNotEquals(orderTrack, 0);

    }

}
