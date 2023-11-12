package com.example.loolah.model;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class Review {
    private String reviewId;
    private int rating;
    private String description;
    private ArrayList<String> likedBy;
    private String creatorId;
    private String toiletId;

    public Review(){}

    public Review(String reviewID,int rating,String description,String creatorID,String toiletId){
        this.reviewId=reviewID;
        this.rating=rating;
        this.description=description;
        this.creatorId=creatorID;
        this.toiletId=toiletId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public int getRating() {
        return rating;
    }

    @Exclude
    public String getRatingStr() {
        return String.valueOf(rating);
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getToiletId() {
        return toiletId;
    }

    public void setToiletId(String toiletId) {
        this.toiletId = toiletId;
    }
}
