package com.example.loolah.view.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;
import com.example.loolah.databinding.FragmentAddReviewBinding;
import com.example.loolah.viewmodel.ReviewViewModel;
public class AddReviewFragment extends Fragment{
    private ReviewViewModel viewModel;
    private FragmentAddReviewBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);
        String toiletId = getArguments() != null ? getArguments().getString("toiletId") : null;

        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setAddReviewView(this);

        // Observe the user LiveData
        viewModel.getProfile().observe(getViewLifecycleOwner(), userLiveDataWrapper -> {
            switch (userLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    if (userLiveDataWrapper.getData() != null)
                        binding.setUser(userLiveDataWrapper.getData());
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Unable to user information.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });
        viewModel.getUserProfile();
 
        return binding.getRoot();
    }

    public void onClickStar(int rating){
        binding.setRating(rating);
    }

    public void onClickPost(){
        String reviewDesc=binding.addReviewComment.getText().toString();
        int rating = binding.getRating();

        String toiletId = getArguments() != null ? getArguments().getString("toiletId") : null;
        viewModel.postReview(reviewDesc,rating,toiletId);
        NavHostFragment.findNavController(this).navigateUp();
    }

    public void onClickBack(){
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment != null) navHostFragment.getNavController().navigateUp();
    }

    public void onClickSelectLocation(View view){
        Navigation.findNavController(view).navigate((R.id.action_addReviewFragment_to_selectLocationFragment));
    }
}

