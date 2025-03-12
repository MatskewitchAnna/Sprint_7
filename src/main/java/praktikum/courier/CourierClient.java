package praktikum.courier;

import io.restassured.response.Response;
import praktikum.Service;

import static io.restassured.RestAssured.given;

public class CourierClient extends Service {
    private static final String COURIER_CREATE_PATH = "/api/v1/courier";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    private static final String COURIER_DELETE_PATH = "/api/v1/courier/";

    public Response loginCourier(Courier courier) {
        return given()
                .spec(getBaseSpecification())
                .and()
                .body(courier)
                .when()
                .post(COURIER_LOGIN_PATH);
    }

    public Response createCourier(Courier courier) {
        return given()
                .spec(getBaseSpecification())
                .and()
                .body(courier)
                .log().all()
                .when()
                .post(COURIER_CREATE_PATH);
    }

    public Response deleteCourier(String id) {
        return given()
                .spec(getBaseSpecification())
                .delete(COURIER_DELETE_PATH + id);
    }
}
