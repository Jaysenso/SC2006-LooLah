package com.example.loolah.viewmodel;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.ReviewDetails;
import com.example.loolah.model.Toilet;
import com.example.loolah.model.ToiletDetails;
import com.example.loolah.model.User;
import com.example.loolah.util.LiveDataWrapper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ToiletDetailsViewModel extends ViewModel {
    private final FirebaseUser user;
    private final CollectionReference uColRef;
    private final CollectionReference tColRef;
    private final CollectionReference rColRef;
    private MutableLiveData<LiveDataWrapper<ToiletDetails>> toiletMutableLiveData;
    private MutableLiveData<LiveDataWrapper<ArrayList<ReviewDetails>>> reviewListMutableLiveData;

    public ToiletDetailsViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        uColRef = FirebaseFirestore.getInstance().collection("users");
        tColRef = FirebaseFirestore.getInstance().collection("toilets");
        rColRef = FirebaseFirestore.getInstance().collection("reviews");
    }

    public MutableLiveData<LiveDataWrapper<ToiletDetails>> getToilet() {
        if (toiletMutableLiveData == null) toiletMutableLiveData = new MutableLiveData<>();
        return toiletMutableLiveData;
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<ReviewDetails>>> getReviewList() {
        if (reviewListMutableLiveData == null) reviewListMutableLiveData = new MutableLiveData<>();
        return reviewListMutableLiveData;
    }

    public void getToiletData(String toiletId) {
        toiletMutableLiveData.setValue(LiveDataWrapper.loading(null));
        tColRef.whereEqualTo("toiletId", toiletId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ToiletDetails toilet = queryDocumentSnapshots.getDocuments().get(0).toObject(ToiletDetails.class);

            if (toilet != null) {
                rColRef.whereEqualTo("toiletId", toilet.getToiletId()).whereEqualTo("creatorId", user.getUid()).count().get(AggregateSource.SERVER).addOnSuccessListener(aggregateQuerySnapshot -> {
                    if (aggregateQuerySnapshot.getCount() == 1) toilet.setReviewed(true);
                    toiletMutableLiveData.setValue(LiveDataWrapper.success(toilet));
                });
                uColRef.whereEqualTo("userId", user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots1.getDocuments()) {
                        User user = document.toObject(User.class);
                        if(user.getFavorites().contains(toilet.getToiletId()))
                        {
                            toilet.setFavorited(true);
                        }
                        else
                        {
                            toilet.setFavorited(false);
                        }
                    }

                });
            } else
                toiletMutableLiveData.setValue(LiveDataWrapper.error("Toilet does not exist", null));
        });
    }

    public void getToiletReviews(String toiletId) {
        ArrayList<ReviewDetails> reviewList = new ArrayList<>();

        reviewListMutableLiveData.setValue(LiveDataWrapper.loading(null));
        rColRef.whereEqualTo("toiletId", toiletId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                ReviewDetails review = document.toObject(ReviewDetails.class);

                if (review != null) {
                    uColRef.whereEqualTo("userId", review.getCreatorId()).get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                        DocumentSnapshot userDocument = queryDocumentSnapshots1.getDocuments().get(0);
                        review.setCreatorUsername(userDocument.getString("username"));
                        review.setCreatorProfilePicUrl(userDocument.getString("profilePicUrl"));
                        if (review.getLikedBy().contains(user.getUid())) review.setLiked(true);
                        reviewList.add(review);

                        reviewListMutableLiveData.setValue(LiveDataWrapper.success(reviewList));
                    });
                }
            }

            reviewListMutableLiveData.setValue(LiveDataWrapper.success(reviewList));
        });
    }

    public void likeReview(String reviewId) {
        rColRef.document(reviewId).update("likedBy", FieldValue.arrayUnion(user.getUid()));
    }

    public void unlikeReview(String reviewId) {
        rColRef.document(reviewId).update("likedBy", FieldValue.arrayRemove(user.getUid()));
    }

    public void addFavoriteToilet(String toiletId) {
        uColRef.document(user.getUid()).update("favorites", FieldValue.arrayUnion(toiletId))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ToiletDetails favoriteToilet = toiletMutableLiveData.getValue().getData();
                        favoriteToilet.setFavorited(true);
                        toiletMutableLiveData.setValue(LiveDataWrapper.success(favoriteToilet));
                    }
                });
    }

    public void removeFavoriteToilet(String toiletId) {
        uColRef.document(user.getUid()).update("favorites", FieldValue.arrayRemove(toiletId));
        ToiletDetails favoriteToilet = toiletMutableLiveData.getValue().getData();
        favoriteToilet.setFavorited(false);
        toiletMutableLiveData.setValue(LiveDataWrapper.success(favoriteToilet));
    }
}
