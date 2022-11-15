package tests;

import clients.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.pojo.CourierLogin;
import org.example.pojo.CourierRegister;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class CreateCourierTest {
    String courierId;
    CourierRegister courier = new CourierRegister("Johnny735a", "Vzaperty159", "JohnnyWinchester");

    @After
    public void tearDown(){
        CourierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Создание нового курьера, корректные данные")
    public void creatingANewCourierValidData(){
        Response createResponse = CourierClient.createNewCourier(this.courier);
        CourierClient.comparingTheActualResponseWithTheSuccessfulOne(createResponse,
                "ok", SC_CREATED);

        CourierLogin courierLogin = new CourierLogin(this.courier.getLogin(), this.courier.getPassword());
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        courierId = CourierClient.getCourierId(logInResponse);
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void creationOfACourierEmptyLogin() {
        CourierRegister courier = new CourierRegister("", RandomStringUtils.randomAlphanumeric(5),
                RandomStringUtils.randomAlphanumeric(5));
        Response response = CourierClient.createNewCourier(courier);
        CourierClient.comparingOfTheErroneousActualOne(response, SC_BAD_REQUEST,
                "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void creationOfACourierPasswordIsEmpty() {
        CourierRegister courier = new CourierRegister(RandomStringUtils.randomAlphanumeric(5),
                "", RandomStringUtils.randomAlphanumeric(5));

        Response response = CourierClient.createNewCourier(courier);
        CourierClient.comparingOfTheErroneousActualOne(response, SC_BAD_REQUEST,
                "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера с уже существующим логином")
    public void creatingACourierNonUniqueLogin() {
        CourierRegister courier = new CourierRegister("ninja", RandomStringUtils.randomAlphanumeric(5),
                RandomStringUtils.randomAlphanumeric(5));
        Response response = CourierClient.createNewCourier(courier);
        CourierClient.comparingOfTheErroneousActualOne(response, SC_CONFLICT,
                "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Создание курьера без имени")
    public void creationOfACourierNameIsEmpty() {
        String login = RandomStringUtils.randomAlphanumeric(5);
        String password = RandomStringUtils.randomAlphanumeric(5);

        CourierRegister courier = new CourierRegister(login, password, null);

        Response response = CourierClient.createNewCourier(courier);
        CourierClient.comparingTheActualResponseWithTheSuccessfulOne(response,
                "ok", SC_CREATED);

        CourierLogin courierLogin = new CourierLogin(login, password);
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        courierId = CourierClient.getCourierId(logInResponse);

    }

    @Test
    @DisplayName("Создание курьера с незаполненными полями")
    public void creationOfACourierTheFieldsAreNotFilled() {
        CourierRegister courier = new CourierRegister("", "", "");
        Response response = CourierClient.createNewCourier(courier);
        CourierClient.comparingOfTheErroneousActualOne(response, SC_BAD_REQUEST,
                "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьеров с уже используемыми данными")
    public void creatingTwoDuplicateCouriers() {
        Response response = CourierClient.createNewCourier(this.courier);
        CourierClient.comparingTheActualResponseWithTheSuccessfulOne(response, "ok", SC_CREATED);

        Response secondResponse = CourierClient.createNewCourier(this.courier);
        CourierClient.comparingOfTheErroneousActualOne(secondResponse, SC_CONFLICT,
                "Этот логин уже используется. Попробуйте другой.");

        CourierLogin courierLogin = new CourierLogin(this.courier.getLogin(), this.courier.getPassword());
        Response logInResponse = CourierClient.loginCourier(courierLogin);

        courierId = CourierClient.getCourierId(logInResponse);

    }
}

