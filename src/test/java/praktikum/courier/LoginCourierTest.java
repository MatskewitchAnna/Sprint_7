package praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.Service;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class LoginCourierTest {
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
    @DisplayName("Авторизация существующего курьера")
    @Description("Авторизация успешна при вводе корректных данных")
    public void loginCourierPositiveTest() {
        step.courierCreate(courierRandom);
        Response response = step.courierLogin(courierRandom);
        step.compareIdNotNull(response);
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Нельзя авторизоваться без ввода логина")
    public void loginCourierWithoutLoginNameTest() {
        courierRandom.setLogin("");
        Response response = step.courierLogin(courierRandom);
        step.compareResultMessageToText(response, HTTP_BAD_REQUEST, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Нельзя авторизоваться без ввода пароля")
    public void loginCourierWithoutPasswordTest() {
        courierRandom.setPassword("");
        Response response = step.courierLogin(courierRandom);
        step.compareResultMessageToText(response, HTTP_BAD_REQUEST, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация с некорректным логином")
    @Description("Ошибка авторизации при вводе неверного логина")
    public void loginCourierIncorrectLogin() {
        courierRandom.setLogin("11111");
        Response response = step.courierLogin(courierRandom);
        step.compareResultMessageToText(response, HTTP_NOT_FOUND, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация с некорректным паролем")
    @Description("Ошибка авторизации при вводе неверного пароля")
    public void loginCourierIncorrectPassword() {
        courierRandom.setPassword("222");
        Response response = step.courierLogin(courierRandom);
        step.compareResultMessageToText(response, HTTP_NOT_FOUND, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Авторизоваться невозможно, если курьер не зарегестрирован")
    public void loginCourierNotExistedTest() {
        Response response = step.courierLogin(courierRandom);
        step.compareResultMessageToText(response, HTTP_NOT_FOUND, "Учетная запись не найдена");
    }
}
