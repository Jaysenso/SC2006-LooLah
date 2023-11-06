package com.example.loolah.model;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.loolah.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class User {
    private String userId;
    private String email;
    private String username;
    private String profilePicUrl;
    private ArrayList<String> favorites;
    private int photoCount;
    private int reviewCount;
    private int likesCount;

    public User() {}

    public User(String userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.profilePicUrl = null;
        this.favorites = new ArrayList<>();
        this.photoCount = 0;
        this.reviewCount = 0;
        this.likesCount = 0;
    }

    public User(String userId, String email, String username, String profilePicUrl, ArrayList<String> favorites, int photoCount, int reviewCount, int likesCount) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.favorites = favorites;
        this.photoCount = photoCount;
        this.reviewCount = reviewCount;
        this.likesCount = likesCount;
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

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public String getPhotoCountStr() {
        return String.valueOf(photoCount);
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public String getReviewCountStr() {
        return String.valueOf(reviewCount);
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getLikesCountStr() {
        return String.valueOf(likesCount);
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    @BindingAdapter({"load_user_image"})
    public static void loadUserImage(CircleImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.img_profile_placeholder)
                .fallback(R.drawable.img_profile_placeholder)
                .into(view);
    }
}
