package praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.Service;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersSteps {
    private final OrdersClient ordersClient = new OrdersClient();

    @Step("Create order")
    public Response orderCreate(Orders order){
        Response response = ordersClient.createOrder(order);
        printResponseBodyToConsole("Создание заказа: ", response, Service.DETAIL_LOG);
        return response;
    }

    @Step("Compare track is not null")
    public void compareTrackNotNull(Response response){
        response
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }

    @Step("Print response body")
    public void printResponseBodyToConsole(String headerText, Response response, boolean detailedLog){
        if (detailedLog)
            System.out.println(headerText + response.body().asString());
    }
}
