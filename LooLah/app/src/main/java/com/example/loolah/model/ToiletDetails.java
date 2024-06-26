package com.example.loolah.model;

public class ToiletDetails extends Toilet {
    private int crowdLevel;
    private boolean reviewed;
    private boolean favorited;

    public ToiletDetails() {
        super();
    }

    public int getCrowdLevel() {
        return crowdLevel;
    }

    public void setCrowdLevel(int crowdLevel) {
        this.crowdLevel = crowdLevel;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
