package com.example.loolah.view.reviews;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loolah.R;
import com.example.loolah.adapter.PhotoGridAdapter;
import com.example.loolah.databinding.FragmentAddReviewBinding;
import com.example.loolah.model.ToiletDetails;
import com.example.loolah.view.home.ImageAdapter;
import com.example.loolah.viewmodel.ReviewViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddReviewFragment extends Fragment{
    private ReviewViewModel viewModel;
    private FragmentAddReviewBinding binding;
    private static final int REQUEST_IMAGE_PICK = 2;
    private List<Uri> selectedImageUris = new ArrayList<>();
    private PhotoGridAdapter photoGridAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ReviewViewModel.class);

        binding = FragmentAddReviewBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setAddReviewView(this);

        //why is the gridview still empty even after selecting image?
        photoGridAdapter = new PhotoGridAdapter(getContext(),selectedImageUris);
        GridView gvUploadedPhotos = binding.getRoot().findViewById(R.id.gv_uploaded_photos);
        gvUploadedPhotos.setAdapter(photoGridAdapter);

        String toiletName = getArguments() != null ? getArguments().getString("toiletName") : null;
        if (toiletName!=null){
            binding.showLocation.setText(toiletName);
        }
        String toiletId = getArguments() != null ? getArguments().getString("toiletId") : null;
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

        viewModel.getToilet().observe(getViewLifecycleOwner(), toiletLiveDataWrapper -> {
            switch (toiletLiveDataWrapper.getStatus()) {
                case SUCCESS:
                    ToiletDetails toilet = toiletLiveDataWrapper.getData();
                    binding.setToilet(toilet);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Unable to retrieve toilet information.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });
        viewModel.getToiletData(getContext(), getResources(), toiletId);

        return binding.getRoot();
    }

    public void onClickStar(int rating){
        binding.setRating(rating);
    }

    public void onClickPost(Boolean reviewed){
        String reviewDesc=binding.addReviewComment.getText().toString();
        int rating = binding.getRating();
        String toiletId = getArguments() != null ? getArguments().getString("toiletId") : null;
        if(!reviewed)
            viewModel.postReview(reviewDesc,rating,toiletId);
        //Navigation.findNavController(view).navigate((R.id.action_addReviewFragment_to_toiletDetailsFragment));
        else
            viewModel.editReview(reviewDesc,rating,toiletId);
        NavHostFragment.findNavController(this).navigateUp();

    }

    public void onClickBack(){
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        if (navHostFragment != null) navHostFragment.getNavController().navigateUp();
    }

    public void onClickSelectLocation(View view){
        Navigation.findNavController(view).navigate((R.id.action_addReviewFragment_to_selectLocationFragment));
    }

    public void onClickUploadPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Enable multiple image selection
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            if (data != null&& data.getData() != null) {
                // Single image selected
                Uri selectedImageUri = data.getData();
                handleSelectedImage(selectedImageUri);
            } else if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri selectedImageUri = clipData.getItemAt(i).getUri();
                    handleSelectedImage(selectedImageUri);
                }
            }
        }
    }
    private void handleSelectedImage(Uri selectedImageUri) {
        // Add the selected image URI to your list
        selectedImageUris.add(selectedImageUri);
        // Notify the adapter that the dataset has changed
        photoGridAdapter.notifyDataSetChanged();
    }

}

