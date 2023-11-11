package com.example.loolah.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Review;
import com.example.loolah.model.Toilet;
import com.example.loolah.model.ToiletDetails;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReviewViewModel extends ViewModel {
    private final FirebaseUser user;
    private final FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    private final CollectionReference uColRef;
    private final CollectionReference rColRef;
    private final CollectionReference tColRef;
    private MutableLiveData<Review> reviewMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> toiletMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> reviewDescMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> ratingMutableLiveData = new MutableLiveData<>();
    private ArrayList<Review> reviewList;

    public ReviewViewModel() {
        super();
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uColRef = db.collection("users");
        rColRef = db.collection("reviews");
        tColRef = db.collection("toilets");
        reviewList = new ArrayList<>();
    }
    public MutableLiveData<Integer> getRatingMutableLiveData() {
        return ratingMutableLiveData;
    }

    public void setRating(int rating) {
        ratingMutableLiveData.setValue(rating);
    }

    public void onPostClick(String toiletID){
        Review review = new Review(ratingMutableLiveData.getValue(),reviewDescMutableLiveData.getValue(),user.getUid(),toiletID);
        reviewMutableLiveData.setValue(review);
    }

    public void postReview(String reviewDesc,int rating,String toiletId){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check if the user is logged in
        if (user != null) {
            Review review = new Review();
            review.setCreatorId(firebaseUser.getUid());
            review.setToiletId(toiletId);
            review.setRating(rating);
            review.setDescription(reviewDesc);

            rColRef.add(review)
                    .addOnSuccessListener(documentReference -> {
                        // Update the LiveData with the success state containing the created Review object
                        reviewMutableLiveData.setValue(review);
                    }).addOnFailureListener(e -> {
                        // Update the LiveData with an error state if adding to Firestore fails
                        //reviewMutableLiveData.setValue(LiveDataWrapper.error(e.getMessage(), null));
                    });
        }
    }
}
