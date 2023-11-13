package com.example.loolah.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Review;
import com.example.loolah.model.ReviewDetails;
import com.example.loolah.model.ToiletDetails;
import com.example.loolah.model.User;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
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
    private MutableLiveData<LiveDataWrapper<ToiletDetails>> toiletMutableLiveData;
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
    public MutableLiveData<LiveDataWrapper<ToiletDetails>> getToilet() {
        if (toiletMutableLiveData == null) toiletMutableLiveData = new MutableLiveData<>();
        return toiletMutableLiveData;
    }

    public void getToiletData(Context context, Resources resources, String toiletId) {
        toiletMutableLiveData.setValue(LiveDataWrapper.loading(null));
        tColRef.document(toiletId).get().addOnSuccessListener(documentSnapshot -> {
            ToiletDetails toilet = documentSnapshot.toObject(ToiletDetails.class);

            if (toilet != null) {
                rColRef.whereEqualTo("toiletId", toilet.getToiletId()).whereEqualTo("creatorId", user.getUid()).count().get(AggregateSource.SERVER).addOnSuccessListener(aggregateQuerySnapshot -> {
                    if (aggregateQuerySnapshot.getCount() == 1) toilet.setReviewed(true);
                    toiletMutableLiveData.setValue(LiveDataWrapper.success(toilet));
                }).addOnFailureListener(e -> toiletMutableLiveData.setValue(LiveDataWrapper.error(e.getMessage(), null)));
            } else
                toiletMutableLiveData.setValue(LiveDataWrapper.error("Toilet does not exist", null));
        }).addOnFailureListener(e -> toiletMutableLiveData.setValue(LiveDataWrapper.error(e.getMessage(), null)));
    }



    public void onPostClick(String toiletID){
        Review review = new Review(reviewMutableLiveData.getValue().getReviewId(),ratingMutableLiveData.getValue(),reviewDescMutableLiveData.getValue(),user.getUid(),toiletID);
        reviewMutableLiveData.setValue(review);
    }

    public void postReview(String reviewDesc,int rating,String toiletId, List<Uri> selectedImageUris){

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check if the user is logged in
        if (user != null) {
            //DocumentReference newReviewRef = db.collection("reviews").document();
            Review review = new Review();
            review.setCreatorId(firebaseUser.getUid());
            review.setToiletId(toiletId);
            review.setRating(rating);
            review.setDescription(reviewDesc);

            rColRef.add(review)
                    .addOnSuccessListener(documentReference -> {
                        rColRef.document(documentReference.getId())
                                .update("reviewId", documentReference.getId())
                                .addOnSuccessListener(aVoid -> {
                                    tColRef.document(toiletId).update("reviewCount", FieldValue.increment(1), "rating", FieldValue.increment(rating)).addOnSuccessListener(unused -> {
                                        uColRef.document(user.getUid()).update("reviewCount", FieldValue.increment(1)).addOnSuccessListener(unused1 -> {
                                            for (Uri selectedImageUri : selectedImageUris) {
                                                StorageReference toiletImgUrlRef = FirebaseStorage.getInstance().getReference().child("images/toilet/" + selectedImageUri.getLastPathSegment());
                                                toiletImgUrlRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
                                                    toiletImgUrlRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                                        tColRef.document(toiletId).update("photoUrl", FieldValue.arrayUnion(uri)).addOnSuccessListener(unused2 -> {
                                                            uColRef.document(user.getUid()).update("photoCount", FieldValue.increment(1));
                                                        });
                                                    });
                                                });
                                            }
                                        });
                                    });
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Error updating review", e);
                                });
                        reviewMutableLiveData.setValue(review);
                    })
                    .addOnFailureListener(e -> {
                    });
        }
    }

    public void editReview(String reviewDesc,int rating,String toiletId){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (user != null) {
            // Query for the review with the specified user ID and toilet ID
            rColRef.whereEqualTo("creatorId", firebaseUser.getUid())
                    .whereEqualTo("toiletId", toiletId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {

                            String reviewId = documentSnapshot.getId();
                            DocumentReference reviewRef = rColRef.document(reviewId);
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("description", reviewDesc);
                            updates.put("rating", rating);
                            updates.put("toiletId", toiletId);
                            reviewRef.update(updates)
                                    .addOnSuccessListener(aVoid -> {})
                                    .addOnFailureListener(e -> {});
                            return;  // Assuming there is at most one review for a specific user and toilet
                        }

                        // If no matching review is found, you can handle it here
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
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
