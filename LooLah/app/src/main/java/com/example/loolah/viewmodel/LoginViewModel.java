package com.example.loolah.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.LoginUser;
import com.example.loolah.model.User;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth firebaseAuth;

    private MutableLiveData<String> emailMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> passwordMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginUser> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LiveDataWrapper<User>> authenticatedUserMutableLiveData = new MutableLiveData<>();

    public LoginViewModel() {
        super();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<String> getEmail() {
        if (emailMutableLiveData == null) emailMutableLiveData = new MutableLiveData<>();
        return emailMutableLiveData;
    }

    public MutableLiveData<String> getPassword() {
        if (passwordMutableLiveData == null) passwordMutableLiveData = new MutableLiveData<>();
        return passwordMutableLiveData;
    }

    public MutableLiveData<LoginUser> getUser() {
        if (userMutableLiveData == null) userMutableLiveData = new MutableLiveData<>();
        return userMutableLiveData;
    }

    public MutableLiveData<LiveDataWrapper<User>> getAuthenticatedUser() {
        if (authenticatedUserMutableLiveData == null)
            authenticatedUserMutableLiveData = new MutableLiveData<>();
        return authenticatedUserMutableLiveData;
    }

    public void onLoginClick() {
        LoginUser user = new LoginUser(emailMutableLiveData.getValue(), passwordMutableLiveData.getValue());
        userMutableLiveData.setValue(user);
    }

    public void login() {
        authenticatedUserMutableLiveData.setValue(LiveDataWrapper.loading(null));

        firebaseAuth.signInWithEmailAndPassword(emailMutableLiveData.getValue(), passwordMutableLiveData.getValue()).addOnSuccessListener(authResult -> {
            FirebaseUser firebaseUser = authResult.getUser();
            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(), firebaseUser.getDisplayName());
            if (firebaseUser.getPhotoUrl() != null)
                user.setProfilePicUrl(firebaseUser.getPhotoUrl().toString());

            authenticatedUserMutableLiveData.setValue(LiveDataWrapper.success(user));
        }).addOnFailureListener(command -> authenticatedUserMutableLiveData.setValue(LiveDataWrapper.error(command.getMessage(), null)));
    }
}
