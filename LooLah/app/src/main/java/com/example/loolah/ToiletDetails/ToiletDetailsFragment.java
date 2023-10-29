package com.example.loolah.ToiletDetails;

import android.content.Intent;
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

public class ToiletDetailsFragment extends Fragment {
    boolean isPlay = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View toilet_details_fragment = inflater.inflate(R.layout.fragment_toilet_details, container, false);

        // Button to navigate to GalleryActivity
        ImageButton btnGallery = toilet_details_fragment.findViewById(R.id.ib_toilet_details_gallery);
        btnGallery.setOnClickListener(v -> {
            // Create an Intent to start the GalleryActivity
            Intent galleryIntent = new Intent(getContext(), GalleryActivity.class);
            startActivity(galleryIntent);
        });

        // Button to go back
        ImageButton btnBack = toilet_details_fragment.findViewById(R.id.ib_toilet_details_back);
        btnBack.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        // Button to toggle favorite state
        ImageButton btnFavorite = toilet_details_fragment.findViewById(R.id.ib_toilet_details_favorite);
        btnFavorite.setOnClickListener(v -> {
            if (isPlay) {
                btnFavorite.setImageResource(R.drawable.ic_toilet_details_favorite);
            } else {
                btnFavorite.setImageResource(R.drawable.ic_toilet_details_favorited);
            }
            isPlay = !isPlay;
        });

        // Button to toggle to Add Review page
        //idk why there are issues after i add this part..
        /*Button btnToiletDetailsReview = toilet_details_fragment.findViewById(R.id.btn_toilet_details_review);
        btnToiletDetailsReview.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_toiletDetailsFragment_to_reviewFragment);
        });*/

        return toilet_details_fragment;
    }
}
