package com.example.loolah.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.LoginUser;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordViewModel extends ViewModel {
    private final FirebaseUser user;
    private MutableLiveData<LiveDataWrapper<Boolean>> changePasswordStatusMutableLiveData;

    public ChangePasswordViewModel() {
        super();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public MutableLiveData<LiveDataWrapper<Boolean>> getChangePasswordStatus() {
        if (changePasswordStatusMutableLiveData == null) changePasswordStatusMutableLiveData = new MutableLiveData<>();
        return changePasswordStatusMutableLiveData;
    }

    public void savePassword(LoginUser loginUser) {
        changePasswordStatusMutableLiveData.setValue(LiveDataWrapper.loading(null));

        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), loginUser.getCurrentPassword());
        user.reauthenticate(credential).addOnSuccessListener(unused -> {
            user.updatePassword(loginUser.getPassword())
                    .addOnSuccessListener(success -> changePasswordStatusMutableLiveData.setValue(LiveDataWrapper.success(true)))
                    .addOnFailureListener(e -> changePasswordStatusMutableLiveData.setValue(LiveDataWrapper.error("Current password is incorrect", null)));
        }).addOnFailureListener(e -> changePasswordStatusMutableLiveData.setValue(LiveDataWrapper.error(e.getMessage(), null)));
    }
}
