package praktikum.orders;

import io.restassured.response.Response;
import praktikum.Service;

import static io.restassured.RestAssured.given;

public class OrdersClient extends Service {
    public static final String ORDER_CREATE_PATH = "/api/v1/orders";
    public static final String ORDER_CANCEL_PATH = "/api/v1/orders/cancel";

    public Response createOrder(Orders order) {
        return given()
                .spec(getBaseSpecification())
                .and()
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH);
    }

    public Response orderList() {
        return given()
                .spec(getBaseSpecification())
                .when()
                .get(ORDER_CREATE_PATH);
    }

    public Response cancelOrder(String orderId) {
        String orderData = "{ \"track\": " + orderId + "}";
        return given()
                .spec(getBaseSpecification())
                .and()
                .body(orderData)
                .when()
                .put(ORDER_CANCEL_PATH);
    }
}
