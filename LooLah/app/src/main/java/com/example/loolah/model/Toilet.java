package com.example.loolah.model;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.loolah.R;
import com.example.loolah.model.enums.ToiletDistrict;
import com.example.loolah.model.enums.ToiletType;
import com.example.loolah.util.NumberUtil;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Toilet {
    private String toiletId;
    private String name;
    private String address;
    private double longitude;
    private double latitude;
    private String geoHash;
    private double distance;
    private ToiletType type;
    private ToiletDistrict district;
    private HashMap<String, Boolean> accessibility;
    private int reviewCount;
    private double rating;
    private ArrayList<String> photoUrl;
    private boolean verified;

    public Toilet() {
    }

    public Toilet(String name, String address, double longitude, double latitude, ToiletType type, ToiletDistrict district) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.geoHash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(latitude, longitude));
        this.type = type;
        this.district = district;
        this.accessibility = new HashMap<>();
        this.accessibility.put("female", true);
        this.accessibility.put("male", true);
        this.accessibility.put("handicap", true);
        this.accessibility.put("child", true);
        this.reviewCount = 0;
        this.rating = 0;
        this.photoUrl = new ArrayList<>();
        this.verified = true;
    }

    public Toilet(String name, String address, double longitude, double latitude, ToiletType type, ToiletDistrict district, boolean female, boolean male, boolean handicap, boolean child) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.geoHash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(latitude, longitude));
        this.type = type;
        this.district = district;
        this.accessibility = new HashMap<>();
        this.accessibility.put("female", female);
        this.accessibility.put("male", male);
        this.accessibility.put("handicap", handicap);
        this.accessibility.put("child", child);
        this.reviewCount = 0;
        this.rating = 0;
        this.photoUrl = new ArrayList<>();
        this.verified = true;
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

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }

    public double getDistance() {
        return distance;
    }

    public String getDistanceStr() {
        return NumberUtil.format((long) distance);
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public int getReviewCount() {
        return reviewCount;
    }

    public String getReviewCountStr() {
        return String.valueOf(reviewCount);
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getRating() {
        return rating;
    }

    public String getRatingStr() {
        return new DecimalFormat("0.0").format(rating);
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<String> getPhotoUrl() {
        return photoUrl;
    }

    public String getDisplayPhoto() {
        return photoUrl.size() > 0 ? photoUrl.get(0) : null;
    }

    public void setPhotoUrl(ArrayList<String> photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @BindingAdapter({"load_toilet_image"})
    public static void loadToiletImage(CircleImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_toilet)
                .fallback(R.drawable.ic_toilet)
                .into(view);
    }
}
