package com.example.loolah.view.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.R;
import com.example.loolah.adapter.LocationListAdapter;
import com.example.loolah.databinding.FragmentSelectlocationBinding;
import com.example.loolah.model.Toilet;
import com.example.loolah.util.SpinnerUtil;
import com.example.loolah.viewmodel.ReviewLocationViewModel;

import java.util.ArrayList;

public class SelectLocationFragment extends Fragment implements LocationListAdapter.OnItemClickListener {
    private ReviewLocationViewModel viewModel;
    private FragmentSelectlocationBinding binding;
    private LocationListAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(ReviewLocationViewModel.class);
        binding = FragmentSelectlocationBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        //binding.setLocationView(this);

        adapter = new LocationListAdapter();
        adapter.setOnItemClickListener(this);
        binding.rvLocationToilets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvLocationToilets.setAdapter(adapter);

        viewModel.getFilteredToiletList().observe(getViewLifecycleOwner(),toilets->{
            switch (toilets.getStatus()) {
                case SUCCESS:
                    ArrayList<Toilet> toiletList = toilets.getData();
                    if (toiletList != null && toiletList.size() == 0)
                        binding.locationNoToilets.setVisibility(View.VISIBLE);
                    else binding.locationNoToilets.setVisibility(View.GONE);
                    adapter.setToiletList(toiletList);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "No such toilet.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });

        return binding.getRoot();
    }

    public void onClickSearch() {
        viewModel.filterToilets(binding.etLocationSearch.getText().toString());
    }
    @Override
    public void onSelectToilet(View view, Toilet toilet) {
        Bundle bundle = new Bundle();
        bundle.putString("toiletId", toilet.getToiletId());

        Navigation.findNavController(view).navigate(R.id.action_selectLocationFragment_to_addReviewFragment, bundle);
    }
}
