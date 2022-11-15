package org.example.pojo;

public class CourierRegister {
    private String login;
    private String password;
    private String firstName;

    //с параметрами
    public CourierRegister(String login, String password, String firstName){
        this.firstName = firstName;
        this.login = login;
        this.password = password;
    }

    //без параметров
    public CourierRegister(){
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
