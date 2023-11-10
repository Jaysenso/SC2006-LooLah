package com.example.loolah.view.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.R;
import com.example.loolah.adapter.ToiletReviewListAdapter;
import com.example.loolah.databinding.FragmentToiletDetailsBinding;
import com.example.loolah.model.ReviewDetails;
import com.example.loolah.viewmodel.ToiletDetailsViewModel;

import java.util.ArrayList;

public class ToiletDetailsFragment extends Fragment implements ToiletReviewListAdapter.OnItemClickListener {

    private ToiletDetailsViewModel viewModel;
    private FragmentToiletDetailsBinding binding;
    private ToiletReviewListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String toiletId = getArguments() != null ? getArguments().getString("toiletId") : null;

        viewModel = new ViewModelProvider(requireActivity()).get(ToiletDetailsViewModel.class);

        binding = FragmentToiletDetailsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setToiletDetailsView(this);

        adapter = new ToiletReviewListAdapter();
        adapter.setOnItemClickListener(this);
        binding.rvToiletDetailsReviews.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvToiletDetailsReviews.setAdapter(adapter);

        viewModel.getToilet().observe(getViewLifecycleOwner(), toiletLiveDataWrapper -> {
            switch (toiletLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    binding.setToilet(toiletLiveDataWrapper.getData());
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Unable to retrieve toilet information.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });
        viewModel.getToiletData(getContext(), getResources(), toiletId);

        viewModel.getReviewList().observe(getViewLifecycleOwner(), reviewListLiveDataWrapper -> {
            switch (reviewListLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    ArrayList<ReviewDetails> reviews = reviewListLiveDataWrapper.getData();
                    if (reviews != null && reviews.size() == 0)
                        binding.tvToiletDetailsNoReviews.setVisibility(View.VISIBLE);
                    else binding.tvToiletDetailsNoReviews.setVisibility(View.GONE);

                    adapter.setToiletReviewList(reviews);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Unable to retrieve toilet reviews.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });
        viewModel.getToiletReviews(toiletId);

        return binding.getRoot();
    }

    public void onClickBack() {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment != null) navHostFragment.getNavController().navigateUp();
    }

    public void onSelectReviewLike(ReviewDetails review) {
        if (review.isLiked()) viewModel.unlikeReview(review.getReviewId(), review.getCreatorId());
        else viewModel.likeReview(review.getReviewId(), review.getCreatorId());
    }

    public void onClickFavorite(String toiletId, Boolean favorited) {
        if (!favorited) {
            viewModel.addFavoriteToilet(toiletId);
            Log.d("TEST", "fav toilet added");
        }
        else
        {
            viewModel.removeFavoriteToilet(toiletId);
            Log.d("TEST", "fav toilet removed");
        }
    }

    public void onClickAddReview(View view) {
        Navigation.findNavController(view).navigate(R.id.action_toiletDetailsFragment_to_reviewFragment);
    }

    public void onClickEditReview(View view) {
        Navigation.findNavController(view).navigate(R.id.action_toiletDetailsFragment_to_reviewFragment);
    }

    public void onClickGallery(View view) {
        Navigation.findNavController(view).navigate(R.id.action_toiletDetailsFragment_to_toiletGalleryFragment);
    }

    public void onClickDirection(String name, double latitude, double longitude) {
        Uri googleMapUri = Uri.parse("geo:0,0?q=" + name + "@" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, googleMapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null)
            startActivity(mapIntent);
    }
}
