package com.example.loolah.Reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;

public class AddReviewFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View addReview_fragment = inflater.inflate(R.layout.fragment_add_review, container, false);

        ImageButton btnBack = addReview_fragment.findViewById(R.id.ib_add_review_back);
        btnBack.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        return addReview_fragment;
    }

}

