package clients;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.example.pojo.CourierLogin;
import org.example.pojo.CourierRegister;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class CourierClient {
    static String courierLogin = "/api/v1/courier/login";
    static String courierRegister = "/api/v1/courier";
    static String courierDelete = "/api/v1/courier/";

    @Step("Создание курьера {courier}")
    public static Response createNewCourier(CourierRegister courier){
        return given()
                .spec(Specifications.requestSpec())
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(courierRegister);
    }

    @Step("Логин курьера")
    public static Response loginCourier(CourierLogin courier){
        return given()
                .spec(Specifications.requestSpec())
                .header("Content-type", "application/json")
                .body(courier)
                .post(courierLogin);
    }

    @Step("Удаление курьера")
    public static Response deleteCourier(String id) {
        return given()
                .spec(Specifications.requestSpec())
                .header("Content-type", "application/json")
                .delete(courierDelete + id);
    }

    @Step("Получение ID курьера")
    public static String getCourierId(Response response){
        String courierId = response.then().extract().body().asString();
        JsonPath jsonPath = new JsonPath(courierId);
        return jsonPath.getString("id");
    }

    @Step("Сравнение фактического кода ответа с ожидаемым")
    public static void comparingTheActualResponseWithTheSuccessfulOne(Response response, String responseString,
                                                                      int responseStatusCode) {
        response.then().assertThat().body(responseString, equalTo(true)).and().statusCode(responseStatusCode);
    }

    @Step("Сравнение фактического кода логина с ожидаемым")
    public static void comparingSuccessfulLoginActual(Response response, int responseStatusCode) {
        response.then().assertThat().body("id", not(0)).and().statusCode(responseStatusCode);
    }

    @Step("Сравнение некорректного кода ответа с фактическим")
    public static void comparingOfTheErroneousActualOne(Response response, int responseStatusCode,
                                                        String message) {
        response.then().assertThat().body("message", equalTo(message)).and().statusCode(responseStatusCode);
    }
}
