package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.Service;

import static java.net.HttpURLConnection.*;

public class CreateCourierTest {
    private final CourierSteps step = new CourierSteps();
    private Courier courierRandom;

    @Before
    public void setUp() {
        RestAssured.baseURI = Service.BASE_URL;
        courierRandom = CourierRandomData.randomCourier();
    }

    @After
    public void tearDown() {
        try {
            Response responseLogin = step.courierLogin(courierRandom);
            String courierId = responseLogin.then().extract().path("id").toString();
            step.courierDelete(courierId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Курьер создан, если все данные введены корректно")
    public void createNewCourierTest() {
        Response response = step.courierCreate(courierRandom);
        step.compareResultToTrue(response, HTTP_CREATED);
    }

    @Test
    @DisplayName("Создание нового курьера без передачи логина")
    @Description("Курьер не создаётся, если не указан логин")
    public void createCourierWithoutLoginTest() {
        courierRandom.setLogin("");
        Response response = step.courierCreate(courierRandom);
        step.compareResultMessageToText(response, HTTP_BAD_REQUEST, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без передачи пароля")
    @Description("Курьер не создаётся, если не указан пароль")
    public void createCourierWithoutPasswordTest() {
        courierRandom.setPassword("");
        Response response = step.courierCreate(courierRandom);
        step.compareResultMessageToText(response, HTTP_BAD_REQUEST, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание уже существующего курьера")
    @Description("Курьер не создаётся, если ввести данные уже существующего курьера")
    public void createDuplicateCourierTest() {
        step.courierCreate(courierRandom);
        Response response = step.courierCreate(courierRandom);
        step.compareResultMessageToText(response, HTTP_CONFLICT, "Этот логин уже используется. Попробуйте другой.");
    }
}
