package couriertest;

import client.Courier;
import client.CourierClient;
import client.CourierCredentials;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierNegativeTest {

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    private final String login;
    private final String password;
    private final String firstName;

    public CreateCourierNegativeTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

@Parameterized.Parameters
    public static Object[][] getCourierParam() {
        return new Object[][] {
                {"", "1234", "Слай"},
                {"Рокки", "", "Слай"},
                {null, "1234", "Слай"},
                {"Рокки", null, "Слай"},
        };
}

@Test
@Description("If one of the fields (login/password) is missing, the endpoint returns an error")
    public void createCourierWithoutOneOfParamTest() {
    Courier courier = new Courier(login, password, firstName);
    ValidatableResponse createNegativeResponse = courierClient.create(courier);
    int createNegativeResponseStatusCode = createNegativeResponse.extract().statusCode();
    assertEquals(createNegativeResponseStatusCode, 400);
    String createNegativeResponseErrorMessage = createNegativeResponse.extract().path("message");
    assertEquals(createNegativeResponseErrorMessage, "Недостаточно данных для создания учетной записи");

    if (createNegativeResponseStatusCode == 201) {
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    }

}

@After
    public void cleanUp() {
        courierClient.delete(courierId);
}

}
