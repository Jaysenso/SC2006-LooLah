package com.example.loolah.view.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.loolah.util.SpinnerUtil;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

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
                    if (toiletList != null && toiletList.size() == 0)
                        binding.tvHomeNoToilets.setVisibility(View.VISIBLE);
                    else binding.tvHomeNoToilets.setVisibility(View.GONE);
                    adapter.setHomeToiletList(toiletList);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), "Unable to retrieve nearby toilets.", Toast.LENGTH_SHORT).show();
                    break;
                case LOADING:
                    break;
            }
        });

        if (checkLocationPermission()) getLocation();
        else requestLocationPermission();

        setSpinnerAdapter(binding.spHomeFilterType, SpinnerUtil.getType());
        setSpinnerAdapter(binding.spHomeFilterDistrict, SpinnerUtil.getDistrict());
        setSpinnerAdapter(binding.spHomeFilterDistance, SpinnerUtil.getDistance());
        setSpinnerAdapter(binding.spHomeFilterRating, SpinnerUtil.getRating());

        binding.srlHomeToilets.setOnRefreshListener(() -> {
            if (checkLocationPermission()) {
                binding.tvHomeNoPermission.setVisibility(View.GONE);
                getLocation();
                binding.srlHomeToilets.setRefreshing(false);
            } else binding.tvHomeNoPermission.setVisibility(View.VISIBLE);
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkLocationPermission()) {
            binding.tvHomeNoPermission.setVisibility(View.GONE);
            getLocation();
        } else binding.tvHomeNoPermission.setVisibility(View.VISIBLE);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) viewModel.getToilets(location);
            else
                Toast.makeText(getContext(), "Please turn on your location and restart the app.", Toast.LENGTH_SHORT).show();
        });
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void onSelectToilet(View view, Toilet toilet) {
        Bundle bundle = new Bundle();
        bundle.putString("toiletId", toilet.getToiletId());
        bundle.putString("toiletName", toilet.getName());
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_toiletDetailsFragment, bundle);
    }

    public void onClickSearch() {
        viewModel.filterToilets(binding.etHomeSearch.getText().toString(), SpinnerUtil.getTypeValue(binding.spHomeFilterType.getSelectedItemPosition()), SpinnerUtil.getDistrictValue(binding.spHomeFilterDistrict.getSelectedItemPosition()), SpinnerUtil.getDistanceValue(binding.spHomeFilterDistance.getSelectedItemPosition()), SpinnerUtil.getRatingValue(binding.spHomeFilterRating.getSelectedItemPosition()));
    }

    public void setSpinnerAdapter(Spinner spinner, String[] objects) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, objects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
