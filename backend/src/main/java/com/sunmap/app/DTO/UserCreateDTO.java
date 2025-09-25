package com.sunmap.app.DTO;

public class UserCreateDTO {
    private String email;
    private String password;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
