package com.example.loolah.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Review;
import com.example.loolah.model.User;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReviewViewModel extends ViewModel {
    private final FirebaseUser user;
    private MutableLiveData<LiveDataWrapper<User>> profileMutableLiveData;
    private final FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    private final CollectionReference uColRef;
    private final CollectionReference rColRef;
    private final CollectionReference tColRef;
    private MutableLiveData<Review> reviewMutableLiveData = new MutableLiveData<>();
    //private MutableLiveData<String> toiletMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> reviewDescMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> ratingMutableLiveData = new MutableLiveData<>();
    //private ArrayList<Review> reviewList;

    public ReviewViewModel() {
        super();
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uColRef = db.collection("users");
        rColRef = db.collection("reviews");
        tColRef = db.collection("toilets");
    }

    public MutableLiveData<Review> getReview() {
        if (reviewMutableLiveData == null) reviewMutableLiveData = new MutableLiveData<>();
        return reviewMutableLiveData;
    }

    public void onPostClick(String toiletID){
        Review review = new Review(reviewMutableLiveData.getValue().getReviewId(),ratingMutableLiveData.getValue(),reviewDescMutableLiveData.getValue(),user.getUid(),toiletID);
        reviewMutableLiveData.setValue(review);
    }

    public void postReview(String reviewDesc,int rating,String toiletId){

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check if the user is logged in
        if (user != null) {
            //DocumentReference newReviewRef = db.collection("reviews").document();
            Review review = new Review();
            review.setCreatorId(firebaseUser.getUid());
            review.setToiletId(toiletId);
            review.setRating(rating);
            review.setDescription(reviewDesc);
            //review.setReviewId(newReviewRef.getId());

            rColRef.add(review)
                    .addOnSuccessListener(documentReference -> {
                        // Update the review object with the generated reviewID
                        review.setReviewId(documentReference.getId());
                        //to update the reviewId
                        rColRef.document(documentReference.getId())
                                .set(review)
                                .addOnSuccessListener(aVoid -> {
                                })
                                .addOnFailureListener(e -> {
                                    // Handle the failure to update the review in Firestore
                                    Log.e("Firestore", "Error updating review", e);
                                });
                        // Update the LiveData with the success state containing the created Review object
                        reviewMutableLiveData.setValue(review);
                    })
                    .addOnFailureListener(e -> {
                        // Update the LiveData with an error state if adding to Firestore fails
                        // reviewMutableLiveData.setValue(LiveDataWrapper.error(e.getMessage(), null));
                    });
        }
    }
    /*public void likeReview(String reviewId, String creatorId) {
        rColRef.document(reviewId).update("likedBy", FieldValue.arrayUnion(user.getUid())).addOnSuccessListener(unused -> uColRef.document(creatorId).update("likesCount", FieldValue.increment(1)));
    }
    public void addFavoriteToilet(String toiletId) {
        uColRef.document(user.getUid()).update("favorites", FieldValue.arrayUnion(toiletId)).addOnSuccessListener(aVoid -> {
            ToiletDetails favoriteToilet = Objects.requireNonNull(toiletMutableLiveData.getValue()).getData();
            if (favoriteToilet != null) {
                favoriteToilet.setFavorited(true);
                toiletMutableLiveData.setValue(LiveDataWrapper.success(favoriteToilet));
            }
        });
    }*/
    public void editReview(String reviewId,String reviewDesc,int rating,String toiletId){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (user != null) {
            DocumentReference reviewRef = rColRef.document(reviewId);

            Map<String, Object> updates = new HashMap<>();
            updates.put("description", reviewDesc);
            updates.put("rating", rating);
            updates.put("toiletId", toiletId);

            reviewRef.update(updates)
                    .addOnSuccessListener(aVoid -> {

                    })
                    .addOnFailureListener(e -> {
                    });
        }
    }

    public MutableLiveData<LiveDataWrapper<User>> getProfile() {
        if (profileMutableLiveData == null) profileMutableLiveData = new MutableLiveData<>();
        return profileMutableLiveData;
    }

    public void getUserProfile() {
        profileMutableLiveData.setValue(LiveDataWrapper.loading(null));
        uColRef.document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> profileMutableLiveData.setValue(LiveDataWrapper.success(Objects.requireNonNull(documentSnapshot.toObject(User.class))))).addOnFailureListener(e -> profileMutableLiveData.setValue(LiveDataWrapper.error(e.getMessage(), null)));
    }
}
