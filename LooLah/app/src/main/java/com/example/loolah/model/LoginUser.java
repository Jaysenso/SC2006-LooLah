package com.example.loolah.model;

public class LoginUser {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private String currentPassword;

    // Login
    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Register
    public LoginUser(String email, String username, String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Change Password
    public LoginUser(String currentPassword, String password, String confirmPassword) {
        this.currentPassword = currentPassword;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isPasswordMatching() {
        return password.equals(confirmPassword);
    }
}
