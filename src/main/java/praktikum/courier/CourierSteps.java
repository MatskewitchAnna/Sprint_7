package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.Service;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierSteps {
    private final CourierClient courierClient = new CourierClient();

    @Step("Create courier")
    public Response courierCreate(Courier courier){
        Response response = courierClient.createCourier(courier);
        printResponseBody("Создание курьера: ", response, Service.DETAIL_LOG);
        return response;
    }

    @Step("Login courier")
    public Response courierLogin(Courier courier){
        Response response = courierClient.loginCourier(courier);
        printResponseBody("Авторизация курьера: ", response, Service.DETAIL_LOG);
        return response;
    }

    @Step("Delete courier by id")
    public void courierDelete(String courierId){
        Response response = courierClient.deleteCourier(courierId);
        printResponseBody("Удаление курьера: ", response, Service.DETAIL_LOG);
    }

    @Step("Compare result to true")
    public void compareResultToTrue(Response response, int statusCode){
        response
                .then()
                .assertThat()
                .log().all()
                .statusCode(statusCode)
                .body("ok", is(true));
    }

    @Step("Compare result message to text")
    public void compareResultMessageToText(Response response, int statusCode, String text){
        response
                .then()
                .log().all()
                .statusCode(statusCode)
                .and()
                .assertThat()
                .body("message", is(text));
    }

    @Step("Print response body")
    public void printResponseBody(String headerText, Response response, boolean detailedLog){
        if (detailedLog)
            System.out.println(headerText + response.body().asString());
    }

    @Step("Compare id is not null")
    public void compareIdNotNull(Response response){
        response
                .then()
                .assertThat()
                .log().all()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }
}
