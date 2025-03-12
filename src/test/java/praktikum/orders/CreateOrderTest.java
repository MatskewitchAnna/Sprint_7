package praktikum.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Service;

import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrdersSteps orderStep = new OrdersSteps();

    private final List<String> color;
    private final OrdersClient ordersClient = new OrdersClient();
    private Orders order;
    private Response response;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "{index}: Выбран цвет: {0}")
    public static Object[][] createOrderWithDifferentColors() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = Service.BASE_URL;
        order = new Orders("Иван", "Васильевич", "ул. Лубянка 23",
                "1", "+7 777 666 55 44", 5, "2024-10-10",
                "Очень хочу кататься", color);
    }

    @After
    public void tearDown() {
        try {
            String orderId = response.then().extract().path("track").toString();
            ordersClient.cancelOrder(orderId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Создание заказа с выбором разных цветов самоката")
    @Description("Заказ создаётся при выборе указанных цветов")
    public void paramCreateOrderTest() {
        response = orderStep.orderCreate(order);
        orderStep.compareTrackNotNull(response);
    }
}
