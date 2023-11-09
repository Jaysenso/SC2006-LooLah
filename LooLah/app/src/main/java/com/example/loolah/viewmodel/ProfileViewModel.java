package com.example.loolah.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.ReviewDetails;
import com.example.loolah.model.Toilet;
import com.example.loolah.model.User;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileViewModel extends ViewModel {
    private final FirebaseUser user;
    FirebaseFirestore db;
    private final CollectionReference uColRef;
    private final CollectionReference rColRef;
    private final CollectionReference tColRef;

    private MutableLiveData<LiveDataWrapper<User>> profileMutableLiveData;
    private MutableLiveData<LiveDataWrapper<ArrayList<ReviewDetails>>> reviewListMutableLiveData;

    public ProfileViewModel() {
        super();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uColRef = db.collection("users");
        rColRef = db.collection("reviews");
        tColRef = db.collection("toilets");
    }

    public MutableLiveData<LiveDataWrapper<User>> getProfile() {
        if (profileMutableLiveData == null) profileMutableLiveData = new MutableLiveData<>();
        return profileMutableLiveData;
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<ReviewDetails>>> getReviewList() {
        if (reviewListMutableLiveData == null) reviewListMutableLiveData = new MutableLiveData<>();
        return reviewListMutableLiveData;
    }

    public void getUserProfile() {
        profileMutableLiveData.setValue(LiveDataWrapper.loading(null));
        uColRef.whereEqualTo("userId", user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.getDocuments().size() > 0) profileMutableLiveData.setValue(LiveDataWrapper.success(Objects.requireNonNull(queryDocumentSnapshots.getDocuments().get(0).toObject(User.class))));
            else profileMutableLiveData.setValue(LiveDataWrapper.error("User not found", null));
        });
    }

    public void getProfileReviews() {
        ArrayList<ReviewDetails> reviewList = new ArrayList<>();

        reviewListMutableLiveData.setValue(LiveDataWrapper.loading(null));
        rColRef.whereEqualTo("creatorId", user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                ReviewDetails review = document.toObject(ReviewDetails.class);
                if (review != null) {
                    tColRef.whereEqualTo("toiletId", review.getToiletId()).get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                        Toilet toilet = queryDocumentSnapshots1.getDocuments().get(0).toObject(Toilet.class);
                        if (toilet != null) {
                            review.setToiletName(toilet.getName());
                            review.setToiletPhotoUrl(toilet.getDisplayPhoto());
                            review.setToiletVerified(toilet.isVerified());
                            reviewList.add(review);
                        }

                        reviewListMutableLiveData.setValue(LiveDataWrapper.success(reviewList));
                    });
                }
            }

            reviewListMutableLiveData.setValue(LiveDataWrapper.success(reviewList));
        });
    }
}
