package tests;

import clients.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class GetOrdersTest {

    @Test
    @DisplayName("Получение списка заказов")
    public void getAllOrders(){
        Response response = OrderClient.getAllOrders();
        response.then().assertThat().body("orders", hasSize(greaterThan(0))).and().statusCode(200);
    }
}
