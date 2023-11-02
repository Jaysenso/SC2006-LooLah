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

public class RegisterViewModel extends ViewModel {
    private FirebaseAuth firebaseAuth;
    private CollectionReference userColRef;

    private MutableLiveData<String> emailMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> usernameMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> passwordMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> confirmPasswordMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LoginUser> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LiveDataWrapper<User>> authenticatedUserMutableLiveData = new MutableLiveData<>();

    public RegisterViewModel() {
        super();
        firebaseAuth = FirebaseAuth.getInstance();
        userColRef = FirebaseFirestore.getInstance().collection("users");
    }

    public MutableLiveData<String> getEmail() {
        if (emailMutableLiveData == null) emailMutableLiveData = new MutableLiveData<>();
        return emailMutableLiveData;
    }

    public MutableLiveData<String> getUsername() {
        if (usernameMutableLiveData == null) usernameMutableLiveData = new MutableLiveData<>();
        return usernameMutableLiveData;
    }

    public MutableLiveData<String> getPassword() {
        if (passwordMutableLiveData == null) passwordMutableLiveData = new MutableLiveData<>();
        return passwordMutableLiveData;
    }

    public MutableLiveData<String> getConfirmPassword() {
        if (confirmPasswordMutableLiveData == null)
            confirmPasswordMutableLiveData = new MutableLiveData<>();
        return confirmPasswordMutableLiveData;
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

    public void onRegisterClick() {
        LoginUser user = new LoginUser(emailMutableLiveData.getValue(), usernameMutableLiveData.getValue(), passwordMutableLiveData.getValue(), confirmPasswordMutableLiveData.getValue());
        userMutableLiveData.setValue(user);
    }

    public void register() {
        authenticatedUserMutableLiveData.setValue(LiveDataWrapper.loading(null));

        firebaseAuth.createUserWithEmailAndPassword(emailMutableLiveData.getValue(), passwordMutableLiveData.getValue()).addOnSuccessListener(authResult -> {
            FirebaseUser firebaseUser = authResult.getUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(usernameMutableLiveData.getValue()).build();
            firebaseUser.updateProfile(profileUpdates).addOnSuccessListener(unused -> {
                User user = new User(firebaseUser.getUid(), firebaseUser.getEmail(), firebaseUser.getDisplayName());
                userColRef.document(user.getUserId()).set(user).addOnSuccessListener(documentReference -> authenticatedUserMutableLiveData.setValue(LiveDataWrapper.success(user))).addOnFailureListener(Throwable::printStackTrace);
            }).addOnFailureListener(Throwable::printStackTrace);
        }).addOnFailureListener(Throwable::printStackTrace);
    }
}
