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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginCourierNegativeTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("Рокки", "1234", "Слай");
        courierClient.create(courier);
    }

    private final String login;
    private final String password;
    private final int expectedCode;
    private final String expectedErrorMessage;

    public LoginCourierNegativeTest(String login, String password, int expectedCode, String expectedErrorMessage) {
        this.login = login;
        this.password = password;
        this.expectedCode = expectedCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getLoginParam() {
        return new Object[][]{
                {"Арни", "4321", 404, "Учетная запись не найдена"},
                {"Роккки", "1234", 404, "Учетная запись не найдена"},
                {"Рокки", "12345", 404, "Учетная запись не найдена"},
                {"", "1234", 400, "Недостаточно данных для входа"},
                {"Рокки", "", 400, "Недостаточно данных для входа"},
                {"Рокки", null, 400, "Недостаточно данных для входа"},
                {null, "1234", 400, "Недостаточно данных для входа"},
        };
    }

    @Test
    @Description("The test implements all negative scenarios for the login endpoint on the task")
    @Issue("BUG_002. 504 Gateway time out in loginCourierAllNegativeTest[5]")
    public void loginCourierAllNegativeTest() {
        CourierCredentials courierCredentials = new CourierCredentials(login, password);
        ValidatableResponse loginNoParamResponse = courierClient.login(courierCredentials);
        int loginNoParamResponseStatusCode = loginNoParamResponse.extract().statusCode();
        assertEquals(loginNoParamResponseStatusCode, expectedCode);
        String loginNoParamResponseErrorMessage = loginNoParamResponse.extract().path("message");
        assertEquals(loginNoParamResponseErrorMessage, expectedErrorMessage);

        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
    }

    @After
    public void cleanUp() {
        courierClient.delete(courierId);
    }

}