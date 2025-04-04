package com.crud.user.dto;

import com.crud.user.enums.Role;

public class RegisterUserDto {

    private String name;
    private String email;
    private String password;
    private String status;
    private String mobileNumber;
    private Role role; // 🔹 Add role field

    // ✅ Getters & Setters with Fluent API
    public String getStatus() {
        return status;
    }

    public RegisterUserDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public RegisterUserDto setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegisterUserDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public RegisterUserDto setRole(Role role) {
        this.role = role;
        return this;
    }
}
