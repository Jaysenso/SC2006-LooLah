package com.example.loolah.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.loolah.R;
import com.example.loolah.databinding.FragmentHomeBinding;
import com.example.loolah.model.Toilet;
import com.example.loolah.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setHomeView(this);

        viewModel.getFilteredToiletList().observe(getViewLifecycleOwner(), toilets -> {
            switch (toilets.getStatus()) {
                case SUCCESS:
                    ArrayList<Toilet> toiletList = toilets.getData();
                    if (toiletList.size() == 0) binding.tvHomeNoToilets.setVisibility(View.VISIBLE);
                    else binding.tvHomeNoToilets.setVisibility(View.INVISIBLE);
                    break;
                case ERROR:
                    break;
                case LOADING:
                    break;
            }
        });

        String[] toilet_types = new String[]{"Type", "Bus Interchange", "Club", "Coffeeshop", "Foodcourt", "Government Office", "Market & Food Centre", "MRT Station", "Park", "Pier", "Place of worship", "Private Office", "Restaurant", "Shopping Centre", "Tourist Attraction", "Community Centre", "Food Court", "Dormitory", "Industrial Complex"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHomeFilterType.setAdapter(adapter);

        String[] toilet_districts = new String[]{"District", "Central", "North East", "North West", "South East", "South West"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHomeFilterDistrict.setAdapter(adapter);

        String[] toilet_distance = new String[]{"Distance", "< 5m", "< 10m", "< 15m", "< 20m", "< 25m"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_distance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHomeFilterDistance.setAdapter(adapter);

        String[] toilet_rating = new String[]{"Rating", "1 star", "2 star", "3 star", "4 star", "5 star"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_rating);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHomeFilterRating.setAdapter(adapter);

        return binding.getRoot();
    }
}
