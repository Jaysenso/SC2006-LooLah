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
    boolean isPlay = false;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View toilet_details_fragment = inflater.inflate(R.layout.fragment_toilet_details, container, false);

        ImageButton btn_back = toilet_details_fragment.findViewById(R.id.ib_toilet_details_back);

        btn_back.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        ImageButton btn_fav = toilet_details_fragment.findViewById(R.id.ib_toilet_details_favorite);

        btn_fav.setOnClickListener(v -> {
            if(isPlay){
                btn_fav.setImageResource(R.drawable.ic_toilet_details_favorite);//sets button to default
            }else{
                btn_fav.setImageResource(R.drawable.ic_toilet_details_favorited);//sets button to favorited
            }
            isPlay = !isPlay; // reverse
        });

        return toilet_details_fragment;
    }
}
