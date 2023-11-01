package com.example.loolah.model;

import com.example.loolah.model.enums.ToiletDistrict;
import com.example.loolah.model.enums.ToiletType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Toilet {
    private String toiletId;
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private ToiletType type;
    private ToiletDistrict district;
    private HashMap<String, Boolean> accessibility;
    private ArrayList<String> reviews;
    private int reviewCount;
    private double rating;
    private ArrayList<String> photoUrl;

    public Toilet(String name, String address, double longitude, double latitude, ToiletType type, ToiletDistrict district) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.district = district;
        accessibility = new HashMap<>();
        accessibility.put("female", true);
        accessibility.put("male", true);
        accessibility.put("wheelchair", true);
        accessibility.put("child", true);
        reviews = new ArrayList<>();
        reviewCount = 0;
        rating = 0;
        photoUrl = new ArrayList<>();
    }

    public Toilet(String name, String address, double longitude, double latitude, ToiletType type, ToiletDistrict district, boolean female, boolean male, boolean wheelchair, boolean child) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.district = district;
        accessibility = new HashMap<>();
        accessibility.put("female", female);
        accessibility.put("male", male);
        accessibility.put("wheelchair", wheelchair);
        accessibility.put("child", child);
        reviews = new ArrayList<>();
        reviewCount = 0;
        rating = 0;
        photoUrl = new ArrayList<>();
    }

    public String getToiletId() {
        return toiletId;
    }

    public void setToiletId(String toiletId) {
        this.toiletId = toiletId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public ToiletType getType() {
        return type;
    }

    public void setType(ToiletType type) {
        this.type = type;
    }

    public ToiletDistrict getDistrict() {
        return district;
    }

    public void setDistrict(ToiletDistrict district) {
        this.district = district;
    }

    public HashMap<String, Boolean> getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(HashMap<String, Boolean> accessibility) {
        this.accessibility = accessibility;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(ArrayList<String> photoUrl) {
        this.photoUrl = photoUrl;
    }
}
