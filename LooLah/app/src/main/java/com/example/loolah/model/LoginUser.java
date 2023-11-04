package com.example.loolah.model;

import android.util.Patterns;

public class LoginUser {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginUser(String email, String username, String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public boolean isPasswordMatching() {
        return password.equals(confirmPassword);
    }
}
