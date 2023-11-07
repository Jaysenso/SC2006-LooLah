package com.example.loolah.model;

public class ReviewDetails extends Review {
    private String toiletPhotoUrl;
    private String toiletName;
    private boolean toiletVerified;
    private String creatorUsername;
    private String creatorProfilePicUrl;
    private boolean liked;

    public ReviewDetails() {
        super();
        this.liked = false;
    }

    public String getToiletPhotoUrl() {
        return toiletPhotoUrl;
    }

    public void setToiletPhotoUrl(String toiletPhotoUrl) {
        this.toiletPhotoUrl = toiletPhotoUrl;
    }

    public String getToiletName() {
        return toiletName;
    }

    public void setToiletName(String toiletName) {
        this.toiletName = toiletName;
    }

    public boolean isToiletVerified() {
        return toiletVerified;
    }

    public void setToiletVerified(boolean toiletVerified) {
        this.toiletVerified = toiletVerified;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public String getCreatorProfilePicUrl() {
        return creatorProfilePicUrl;
    }

    public void setCreatorProfilePicUrl(String creatorProfilePicUrl) {
        this.creatorProfilePicUrl = creatorProfilePicUrl;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
