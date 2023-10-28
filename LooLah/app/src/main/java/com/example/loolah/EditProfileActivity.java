package com.example.loolah;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button btnSave;
    private Button btnChangePP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextName = findViewById(R.id.editTextName);
        btnSave = findViewById(R.id.btnSave);
        btnChangePP = findViewById(R.id.btnChangePP);

        // Set click listeners for buttons
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });

        btnChangePP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeProfilePicture();
            }a
        });
    }

    private void saveUserProfile() {
        // TODO: Implement the logic to save the user's profile here
    }

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private void changeProfilePicture() {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
}
