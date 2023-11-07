package com.example.loolah.model;

public class ToiletDetails extends Toilet {
    private String crowdLevel;
    private boolean reviewed;

    public ToiletDetails() {
        super();
    }

    public String getCrowdLevel() {
        return crowdLevel;
    }

    public void setCrowdLevel(String crowdLevel) {
        this.crowdLevel = crowdLevel;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }
}
