package com.example.loolah.view.ToiletDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;

public class ToiletGalleryFragment extends Fragment {
    boolean isFav = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View toilet_details_fragment = inflater.inflate(R.layout.fragment_toilet_gallery, container, false);

        // Button to go back
        ImageButton btn_back = toilet_details_fragment.findViewById(R.id.ib_toilet_gallery_back);
        btn_back.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        // Button to toggle favorite state
        ImageButton btn_favorite = toilet_details_fragment.findViewById(R.id.ib_toilet_gallery_favorite);
        btn_favorite.setOnClickListener(v -> {
            if (isFav) {
                btn_favorite.setImageResource(R.drawable.ic_toilet_details_favorite);
            } else {
                btn_favorite.setImageResource(R.drawable.ic_toilet_details_favorited);
            }
            isFav = !isFav;
        });

        GridView gv_gallery = toilet_details_fragment.findViewById(R.id.gv_toilet_gallery);
        gv_gallery.setColumnWidth((getResources().getDisplayMetrics().widthPixels - 50) / 3 );
        int[] imageResources = {R.drawable.img_toilet_details, R.drawable.img_toilet_details, R.drawable.img_toilet_details,R.drawable.img_toilet_details};
        ImageAdapter adapter = new ImageAdapter(getContext(), imageResources);
        gv_gallery.setAdapter(adapter);

        return toilet_details_fragment;
    }
}
