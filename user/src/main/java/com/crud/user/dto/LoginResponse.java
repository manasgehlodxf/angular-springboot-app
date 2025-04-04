package com.crud.user.dto;

public class LoginResponse {

    private String token;
    private Long expiresIn;
    private String role;

    public Long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getRole() {
        return role;
    }

    public LoginResponse setRole(String role) {
        this.role = role;
        return this;
    }
}
