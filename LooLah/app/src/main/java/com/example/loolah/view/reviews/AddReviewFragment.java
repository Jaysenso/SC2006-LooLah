package com.example.loolah.view.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;
import com.example.loolah.databinding.FragmentAddReviewBinding;
import com.example.loolah.viewmodel.ReviewViewModel;

import java.util.concurrent.atomic.AtomicInteger;

public class AddReviewFragment extends Fragment{
    private ReviewViewModel viewModel;
    private FragmentAddReviewBinding binding;
    private int selectedRating = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReviewViewModel viewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        View addReview_fragment = inflater.inflate(R.layout.fragment_add_review, container, false);

        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setAddReviewView(this);

        // Observe the user LiveData
        viewModel.getProfile().observe(getViewLifecycleOwner(), userLiveDataWrapper -> {
            // Update the binding with the user data
            binding.setUser(userLiveDataWrapper.getData());
        });
        viewModel.getUserProfile();
 
        return binding.getRoot();
    }

    public void onClickStar(int rating){
        selectedRating=rating;
    }

    public int getSelectedRating(){
        return selectedRating;
    }
    public void onClickPost(){
        String reviewDesc=binding.addReviewComment.getText().toString();
        int rating = selectedRating;

        String toiletId = getArguments() != null ? getArguments().getString("toiletId") : null;
        viewModel.postReview(reviewDesc,rating,toiletId);
        NavHostFragment.findNavController(this).navigateUp();
    }

    public void onClickBack(){
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment != null) navHostFragment.getNavController().navigateUp();
    }
}

