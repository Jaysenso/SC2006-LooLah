package com.example.loolah.view.ToiletDetails;

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

        // Button to go back
        ImageButton btn_back = toilet_details_fragment.findViewById(R.id.ib_toilet_details_back);
        btn_back.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        // Button to toggle favorite state
        ImageButton btn_favorite = toilet_details_fragment.findViewById(R.id.ib_toilet_details_favorite);
        btn_favorite.setOnClickListener(v -> {
            if (isPlay) {
                btn_favorite.setImageResource(R.drawable.ic_toilet_details_favorite);
            } else {
                btn_favorite.setImageResource(R.drawable.ic_toilet_details_favorited);
            }
            isPlay = !isPlay;
        });

        // Button to navigate to GalleryActivity
        ImageButton btn_gallery = toilet_details_fragment.findViewById(R.id.ib_toilet_details_gallery);
        btn_gallery.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_toiletDetailsFragment_to_toiletGalleryFragment);
        });

        // Button to toggle to Add Review page
        Button btn_review = toilet_details_fragment.findViewById(R.id.btn_toilet_details_review);
        btn_review.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_toiletDetailsFragment_to_reviewFragment);
        });

        return toilet_details_fragment;
    }
}
