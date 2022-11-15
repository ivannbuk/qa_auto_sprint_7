package tests;


import clients.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.pojo.OrderCreate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String deliveryDate;
    private String comment;
    private String[] color;
    private int rentTime;

    String orderTrack;

    @After
    public void tearDown(){
        OrderClient.deleteOrder(orderTrack);
    }

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation,
                           String phone, int rentTime, String deliveryDate, String comment, String[] color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData(){
        return new Object[][]{
                { "Sumerikov", "Arcacha", "Samara, 14 apt.", "4", "+7 812 335 25 45", 5, "2020-05-05",
                        "Vasiliy, come back to Samara", new String [] { "GRAY" } },
                { "Sumerikov", "Arcacha", "Samara, 14 apt.", "4", "++7 812 335 25 45", 5, "2020-05-05",
                        "Vasiliy, come back to Samara", new String [] { "RED" } },
                { "Sumerikov", "Arcacha", "Samara, 14 apt.", "4", "++7 812 335 25 45", 5, "2020-05-05",
                        "Vasiliy, come back to Samara", new String [] { "GRAY", "RED" } },
                { "Sumerikov", "Arcacha", "Samara, 14 apt.", "4", "++7 812 335 25 45", 5, "2020-05-05",
                        "Vasiliy, come back to Samara", new String [] { } },
                { "Sumerikov", "Arcacha", "Samara, 14 apt.", "4", "++7 812 335 25 45", 5, "2020-05-05",
                        "Vasiliy, come back to Samara", new String [] { "BLACK" } },
        };
    }

    @Test
    @DisplayName("Создание заказов с разными цветами")
    public void createOrderParameterizedColorScooterTest() {
        OrderCreate orderCreate  = new OrderCreate(firstName, lastName, address,
                                metroStation, phone, deliveryDate, comment, color, rentTime);
        Response createResponse = OrderClient.createNewOrder(orderCreate);
        OrderClient.comparingSuccessfulOrderSet(createResponse, SC_CREATED);
        orderTrack = OrderClient.getOrderTrack(createResponse);

    }
}
