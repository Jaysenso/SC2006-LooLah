package com.example.loolah.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashViewModel extends ViewModel {
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;

    public MutableLiveData<FirebaseUser> getFirebaseUser() {
        if (firebaseUserMutableLiveData == null) firebaseUserMutableLiveData = new MutableLiveData<>();
        return firebaseUserMutableLiveData;
    }

    public void getCurrentUser() {
        firebaseUserMutableLiveData.setValue(FirebaseAuth.getInstance().getCurrentUser());
    }
}
