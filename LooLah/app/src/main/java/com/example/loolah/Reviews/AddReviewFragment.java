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

    boolean isStar1Selected = false;
    boolean isStar2Selected = false;
    boolean isStar3Selected = false;
    boolean isStar4Selected = false;
    boolean isStar5Selected = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View addReview_fragment = inflater.inflate(R.layout.fragment_add_review, container, false);

        ImageButton btnBack = addReview_fragment.findViewById(R.id.ib_add_review_back);

        //Back button
        btnBack.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        //Stars
        ImageButton btnStar1 = addReview_fragment.findViewById(R.id.ib_add_review_toilet_rating1);
        ImageButton btnStar2 = addReview_fragment.findViewById(R.id.ib_add_review_toilet_rating2);
        ImageButton btnStar3 = addReview_fragment.findViewById(R.id.ib_add_review_toilet_rating3);
        ImageButton btnStar4 = addReview_fragment.findViewById(R.id.ib_add_review_toilet_rating4);
        ImageButton btnStar5 = addReview_fragment.findViewById(R.id.ib_add_review_toilet_rating5);

        btnStar1.setOnClickListener(v -> {
            if(isStar1Selected){
                btnStar1.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar5.setImageResource(R.drawable.ic_toilet_review_star);
            }
            else{
                btnStar1.setImageResource(R.drawable.ic_toilet_review_starred);
            }
            isStar1Selected = !isStar1Selected;
        });

        btnStar2.setOnClickListener(v -> {
            if(isStar2Selected){
                btnStar1.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar5.setImageResource(R.drawable.ic_toilet_review_star);
            }
            else{
                btnStar1.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_starred);
            }
            isStar2Selected = !isStar2Selected;
        });

        btnStar3.setOnClickListener(v -> {
            if(isStar3Selected){
                btnStar1.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar5.setImageResource(R.drawable.ic_toilet_review_star);
            }
            else{
                btnStar1.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_starred);
            }
            isStar3Selected = !isStar3Selected;
        });

        btnStar4.setOnClickListener(v -> {
            if(isStar4Selected){
                btnStar1.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar5.setImageResource(R.drawable.ic_toilet_review_star);
            }
            else{
                btnStar1.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_starred);
            }
            isStar4Selected = !isStar4Selected;
        });

        btnStar5.setOnClickListener(v -> {
            if(isStar5Selected){
                btnStar1.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar5.setImageResource(R.drawable.ic_toilet_review_star);
            }
            else{
                btnStar1.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_starred);
                btnStar5.setImageResource(R.drawable.ic_toilet_review_starred);
            }
            isStar5Selected = !isStar5Selected;
        });

        return addReview_fragment;
    }

}

