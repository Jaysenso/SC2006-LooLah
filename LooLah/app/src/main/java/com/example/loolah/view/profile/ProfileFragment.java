package com.example.loolah.view.profile;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.R;
import com.example.loolah.adapter.ProfileReviewListAdapter;
import com.example.loolah.databinding.FragmentProfileBinding;
import com.example.loolah.model.Review;
import com.example.loolah.viewmodel.ProfileViewModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements ProfileReviewListAdapter.OnItemClickListener {
    private ProfileViewModel viewModel;
    private FragmentProfileBinding binding;
    private ProfileReviewListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setProfileView(this);

        adapter = new ProfileReviewListAdapter();
        adapter.setOnItemClickListener(this);
        binding.rvProfileReviews.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvProfileReviews.setAdapter(adapter);

        viewModel.getProfile().observe(getViewLifecycleOwner(), userLiveDataWrapper -> {
            switch (userLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    if (userLiveDataWrapper.getData() != null) binding.setUser(userLiveDataWrapper.getData());
                    break;
                case ERROR:
                    Toast toast = Toast.makeText(getContext(), userLiveDataWrapper.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case LOADING:
                    break;
            }
        });
        viewModel.getUserProfile();

        viewModel.getReviewList().observe(getViewLifecycleOwner(), reviewListLiveDataWrapper -> {
            switch(reviewListLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    ArrayList<Review> reviews = reviewListLiveDataWrapper.getData();
                    if (reviews.size() == 0) binding.tvProfileNoReviews.setVisibility(View.VISIBLE);
                    else binding.tvProfileNoReviews.setVisibility(View.INVISIBLE);

                    if (reviews != null) {
                        adapter.setProfileReviewList(reviews);
                        adapter.setProfileReviewToiletList(viewModel.getReviewToiletList());
                    }
                    break;
                case ERROR:
                    Toast toast = Toast.makeText(getContext(), reviewListLiveDataWrapper.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                    break;
                case LOADING:
                    break;
            }
        });
        viewModel.getProfileReviews();

        return binding.getRoot();
    }

    public void onClickSettings() {
        Bundle bundle = new Bundle();
        bundle.putString("profilePicUrl", binding.getUser().getProfilePicUrl());
        Navigation.findNavController(getView()).navigate(R.id.action_profileFragment_to_settingFragment);
    }

    public void onSelectReview(View view, Review review) {
        Bundle bundle = new Bundle();
        bundle.putString("toiletId", review.getToiletId());

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_toiletDetailsFragment, bundle);
    }
}
