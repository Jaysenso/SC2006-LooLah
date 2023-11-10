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
import com.example.loolah.model.ReviewDetails;
import com.example.loolah.viewmodel.ProfileViewModel;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements ProfileReviewListAdapter.OnItemClickListener {
    private FragmentProfileBinding binding;
    private ProfileReviewListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProfileViewModel viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

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
                    if (userLiveDataWrapper.getData() != null)
                        binding.setUser(userLiveDataWrapper.getData());
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "User not found.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });
        viewModel.getUserProfile();

        viewModel.getReviewList().observe(getViewLifecycleOwner(), reviewListLiveDataWrapper -> {
            switch (reviewListLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    ArrayList<ReviewDetails> reviews = reviewListLiveDataWrapper.getData();
                    if (reviews != null && reviews.size() == 0)
                        binding.tvProfileNoReviews.setVisibility(View.VISIBLE);
                    else binding.tvProfileNoReviews.setVisibility(View.GONE);
                    adapter.setProfileReviewList(reviews);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Unable to retrieve user reviews.", Toast.LENGTH_SHORT).show();
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
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_settingFragment);
    }

    public void onSelectReview(View view, ReviewDetails review) {
        Bundle bundle = new Bundle();
        bundle.putString("toiletId", review.getToiletId());

        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_toiletDetailsFragment, bundle);
    }
}
