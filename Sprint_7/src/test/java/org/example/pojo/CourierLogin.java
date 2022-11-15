package org.example.pojo;

public class CourierLogin {
    private String login;
    private String password;

    // с параметрами
    public CourierLogin(String login, String password){
        this.login = login;
        this.password = password;
    }

    //без параметров
    public CourierLogin(){
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
