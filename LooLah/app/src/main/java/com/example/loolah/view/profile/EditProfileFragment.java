package com.example.loolah.view.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.loolah.R;
import com.example.loolah.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {
    private ImageButton btn_edit_profile_picture;
    private User currentUser;
    private Uri selectedImageUri;
    private String profilePictureUrl;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View edit_profile_fragment = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        Button btn_save = edit_profile_fragment.findViewById(R.id.btn_edit_profile_save);
        btn_edit_profile_picture = edit_profile_fragment.findViewById(R.id.ib_edit_profile_picture);
        ImageButton btn_back = edit_profile_fragment.findViewById(R.id.ib_edit_profile_back);

        btn_back.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        btn_save.setOnClickListener(v -> {
            saveUserProfile(v);

        });

        btn_edit_profile_picture.setOnClickListener(v -> changeProfilePicture());

        loadUserData();

        return edit_profile_fragment;
    }

    private void loadUserData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            FirebaseFirestore.getInstance().collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                currentUser = document.toObject(User.class);

                                if (currentUser != null) {
                                    currentUser.setOriginalUsername(currentUser.getUsername());
                                    currentUser.setOriginalProfilePicUrl(currentUser.getProfilePicUrl());
                                }

                                updateUIWithUserData();
                                Log.d(TAG, "User data loaded successfully");
                            } else {
                                Log.e(TAG, "No such document");
                            }
                        } else {
                            Log.e(TAG, "Error getting user data", task.getException());
                        }
                    });
        } else {
            Log.e(TAG, "Firebase user is null");
        }
    }

    private void saveUserProfile(View view) {
        boolean successful = true;
        if (currentUser == null) {
            Log.e(TAG, "currentUser is null in saveUserProfile()");
            return;
        }

        Log.d(TAG, "saveUserProfile() called");
        EditText etUsername = requireView().findViewById(R.id.et_edit_profile_username);
        String newUsername = etUsername.getText().toString();

        // Check if the username has changed
        if (!newUsername.equals(currentUser.getUsername())) {
            FirebaseFirestore.getInstance().collection("users").document(currentUser.getUserId()).update("username", newUsername).addOnSuccessListener(unused -> {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(newUsername).build();
                FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
            });
        }

        // Check if the profile picture has changed
        if (selectedImageUri != null && !selectedImageUri.toString().equals(profilePictureUrl)) {
            StorageReference profileImgUrlRef = FirebaseStorage.getInstance().getReference().child("images/profile/" + currentUser.getUserId());
            profileImgUrlRef.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
                profileImgUrlRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    FirebaseFirestore.getInstance().collection("users").document(currentUser.getUserId()).update("profilePicUrl", uri).addOnSuccessListener(unused -> {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);
                    });
                });
            });
        }

        // Update only if changes were made
        if (successful) {
            Toast.makeText(requireContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment);
        } else {
            // TODO: Add failed
            Toast.makeText(requireContext(), "No changes made to the profile", Toast.LENGTH_SHORT).show();
        }
    }

    private static final int REQUEST_IMAGE_PICK = 2;

    private void changeProfilePicture() {
        Intent pictureIntent = new Intent();
        pictureIntent.setType("image/*");
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(pictureIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                selectedImageUri = data.getData();

                CircleImageView profileImageView = requireView().findViewById(R.id.civ_edit_profile_profile_image);
                if (profileImageView != null) {
                    Glide.with(requireContext())
                            .load(selectedImageUri)
                            .into(profileImageView);
                } else {
                    Log.e(TAG, "Profile image view is null");
                }
            } else {
                Log.e(TAG, "No data or data URI is null");
            }
        } else {
            Log.e(TAG, "Image selection failed. Request code: " + requestCode + ", Result code: " + resultCode);
        }
    }

    private void updateUIWithUserData() {
        if (currentUser != null) {
            EditText etUsername = requireView().findViewById(R.id.et_edit_profile_username);

            if (currentUser.getUsername() != null) {
                etUsername.setText(currentUser.getUsername());
            } else {
                Log.e(TAG, "User's username is null");
                etUsername.setText("DefaultUsername");
            }

            CircleImageView profileImageView = requireView().findViewById(R.id.civ_edit_profile_profile_image);

            if (selectedImageUri != null) {
                Glide.with(requireContext())
                        .load(selectedImageUri)
                        .into(profileImageView);
            } else if (currentUser.getProfilePicUrl() != null && !currentUser.getProfilePicUrl().isEmpty()) {
                Glide.with(requireContext())
                        .load(currentUser.getProfilePicUrl())
                        .into(profileImageView);
            }
        } else {
            Log.e(TAG, "User is null");
        }
    }
}
