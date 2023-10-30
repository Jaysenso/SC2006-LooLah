package com.example.loolah.model;

public class User {
    private String userId;
    private String email;
    private String username;
    private String password;
    private String profilePicUrl;
    private String[] favorites;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String[] getFavorites() {
        return favorites;
    }

    public void setFavorites() {
        this.favorites = favorites;
    }
}
