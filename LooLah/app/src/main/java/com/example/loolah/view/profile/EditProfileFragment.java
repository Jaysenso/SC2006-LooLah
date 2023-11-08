package com.example.loolah.view.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;

public class EditProfileFragment extends Fragment {
    private ImageButton btn_edit_profile_picture;

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
            Navigation.findNavController(v).navigate(R.id.action_editProfileFragment_to_profileFragment);
        });

        btn_edit_profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // changeProfilePicture();
            }
        });

        return edit_profile_fragment;
    }

    private void saveUserProfile() {
        // TODO: Implement the logic to save the user's profile here
    }


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;


    private void changeProfilePicture() {
        /*
        // User to pick an image from the gallery or capture a new photo.
        Intent pictureIntent = new Intent();
        pictureIntent.setType("image/*");
        pictureIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent[] intents = new Intent[]{pictureIntent, cameraIntent};

        // Create a chooser for the user to select from gallery or camera.
        Intent chooserIntent = Intent.createChooser(pictureIntent, "Select Image or Take Photo");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);

        startActivityForResult(chooserIntent, REQUEST_IMAGE_PICK);

         */
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            // User picked an image from the gallery.
            if (data != null) {
                // Handle the selected image (data.getData() is the URI of the selected image).
                // You can display it, upload it, or perform any other action you need.
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // User took a new photo using the camera.
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Handle the captured image (imageBitmap) here.
            }
        }
    }
}
