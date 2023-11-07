package com.example.loolah.view.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.R;
import com.example.loolah.adapter.HomeToiletListAdapter;
import com.example.loolah.databinding.FragmentHomeBinding;
import com.example.loolah.model.Toilet;
import com.example.loolah.viewmodel.HomeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeToiletListAdapter.OnItemClickListener {
    private FusedLocationProviderClient fusedLocationClient;
    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private HomeToiletListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getActivity());
        binding.setHomeView(this);

        adapter = new HomeToiletListAdapter();
        adapter.setOnItemClickListener(this);
        binding.rvHomeToilets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvHomeToilets.setAdapter(adapter);

        viewModel.getFilteredToiletList().observe(getViewLifecycleOwner(), toilets -> {
            switch (toilets.getStatus()) {
                case SUCCESS:
                    ArrayList<Toilet> toiletList = toilets.getData();
                    if (toiletList.size() == 0) binding.tvHomeNoToilets.setVisibility(View.VISIBLE);
                    else binding.tvHomeNoToilets.setVisibility(View.INVISIBLE);
                    adapter.setHomeToiletList(toiletList);
                    break;
                case ERROR:
                    Log.d("TEST", "error");
                    break;
                case LOADING:
                    break;
            }
        });
        if (checkLocationPermission()) getLocation();
        else requestLocationPermission();

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

    @Override
    public void onResume() {
        super.onResume();
        if (checkLocationPermission()) getLocation();
        else binding.tvHomeNoPermission.setVisibility(View.VISIBLE);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> viewModel.getToilets(location));
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void onSelectToilet(View view, Toilet toilet) {
        Bundle bundle = new Bundle();
        bundle.putString("toiletId", toilet.getToiletId());

        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_toiletDetailsFragment, bundle);
    }
}
