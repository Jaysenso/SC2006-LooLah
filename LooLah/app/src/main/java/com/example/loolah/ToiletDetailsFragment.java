package com.example.loolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class ToiletDetailsFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View toilet_details_fragment = inflater.inflate(R.layout.fragment_toilet_details, container, false);

        ImageButton btn_back = toilet_details_fragment.findViewById(R.id.ib_toilet_details_back);

        btn_back.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        return toilet_details_fragment;
    }
}
