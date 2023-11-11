package com.example.loolah.view.Reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.loolah.R;
import com.example.loolah.databinding.FragmentAddReviewBinding;
import com.example.loolah.viewmodel.ReviewViewModel;

import java.util.concurrent.atomic.AtomicInteger;

public class AddReviewFragment extends Fragment{
    private ReviewViewModel viewModel;
    private FragmentAddReviewBinding binding;
    boolean isStar1Selected = false;
    boolean isStar2Selected = false;
    boolean isStar3Selected = false;
    boolean isStar4Selected = false;
    boolean isStar5Selected = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        View addReview_fragment = inflater.inflate(R.layout.fragment_add_review, container, false);

        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        //binding.setAddReviewView(this);

        ImageButton btnBack = addReview_fragment.findViewById(R.id.ib_add_review_back);

        //Back button
        btnBack.setOnClickListener(v -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        //Stars
        ImageButton btnStar1 = addReview_fragment.findViewById(R.id.ib_add_review_rating1);
        ImageButton btnStar2 = addReview_fragment.findViewById(R.id.ib_add_review_rating2);
        ImageButton btnStar3 = addReview_fragment.findViewById(R.id.ib_add_review_rating3);
        ImageButton btnStar4 = addReview_fragment.findViewById(R.id.ib_add_review_rating4);
        ImageButton btnStar5 = addReview_fragment.findViewById(R.id.ib_add_review_rating5);

        AtomicInteger rating = new AtomicInteger(0);
        btnStar1.setOnClickListener(v -> {
            if(isStar1Selected){
                btnStar1.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar2.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar3.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar4.setImageResource(R.drawable.ic_toilet_review_star);
                btnStar5.setImageResource(R.drawable.ic_toilet_review_star);
                rating.set(1);
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
                rating.set(2);
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
                rating.set(3);
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
                rating.set(4);
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
                rating.set(5);
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

        Button btnPost = addReview_fragment.findViewById(R.id.btn_add_review_post);
        btnPost.setOnClickListener(v -> {
            EditText text = addReview_fragment.findViewById(R.id.add_review_comment);
            String reviewDesc = text.getText().toString();
            //how to retrieve int rating = 5;
            String toiletId = "test";

            viewModel.postReview(reviewDesc,rating.get(),toiletId);

            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            navHostFragment.getNavController().navigateUp();
        });

        //Upload Photo
        /*Button uploadPhotosbtn = addReview_fragment.findViewById(R.id.btn_add_review_upload_photos);
        uploadPhotosbtn.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            // Start the activity to open the gallery
            startActivityForResult(galleryIntent, REQUEST_CODE); // Define REQUEST_CODE as an integer constant

            // REQUEST_CODE is used to identify the result when the user picks an image.
        });*/

        //Select Location
        /*Button selectLocationbtn = addReview_fragment.findViewById(R.id.btn_add_review_select_location);
        selectLocationbtn.setOnClickListener(v ->{
        });*/
        return addReview_fragment;
    }
}

