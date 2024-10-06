package praktikum.orders;

import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;
import praktikum.Service;


public class ListOfOrdersTest {
    private final OrdersSteps orderStep = new OrdersSteps();
    private final OrdersClient ordersClient = new OrdersClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = Service.BASE_URL;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("В тело ответа возвращается список заказов")
    public void getListOfOrdersTest() {
        Response response = ordersClient.orderList();
        orderStep.printResponseBodyToConsole("Список заказов: ", response, Service.DETAIL_LOG);
        response
                .then()
                .statusCode(HTTP_OK).assertThat().body("orders", notNullValue());
    }
}
