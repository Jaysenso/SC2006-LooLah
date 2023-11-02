package com.example.loolah.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String userId;
    private String email;
    private String username;
    private String profilePicUrl;
    private ArrayList<String> favorites;

    public User(String userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.profilePicUrl = null;
        this.favorites = new ArrayList<>();
    }

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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites() {
        this.favorites = favorites;
    }
}
