package com.example.loolah.view.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.loolah.R;
import com.example.loolah.util.drawableToBitmapUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap google_map;
    private FusedLocationProviderClient fusedlocationClient;
    private Location currentLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View map_fragment = inflater.inflate(R.layout.fragment_map, container, false);
        fusedlocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (checkLocationPermission()) getLocation();
        else requestLocationPermission();

        String[] toilet_types = new String[]{"Type", "Bus Interchange", "Club", "Coffeeshop", "Foodcourt", "Government Office", "Market & Food Centre", "MRT Station", "Park", "Pier", "Place of worship", "Private Office", "Restaurant", "Shopping Centre", "Tourist Attraction", "Community Centre", "Food Court", "Dormitory", "Industrial Complex"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) map_fragment.findViewById(R.id.sp_map_filter_type)).setAdapter(adapter);

        String[] toilet_districts = new String[]{"District", "Central", "North East", "North West", "South East", "South West"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) map_fragment.findViewById(R.id.sp_map_filter_district)).setAdapter(adapter);

        String[] toilet_distance = new String[]{"Distance", "< 5m", "< 10m", "< 15m", "< 20m", "< 25m"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_distance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) map_fragment.findViewById(R.id.sp_map_filter_distance)).setAdapter(adapter);

        String[] toilet_rating = new String[]{"Rating", "1 star", "2 star", "3 star", "4 star", "5 star"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_rating);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) map_fragment.findViewById(R.id.sp_map_filter_rating)).setAdapter(adapter);

        SupportMapFragment google_map_fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fcv_map_google);
        google_map_fragment.getMapAsync(this);

        return map_fragment;
    }

    @SuppressLint("MissingPermission")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchView sv_map = getView().findViewById(R.id.sv_map_search);
        ImageButton search_button = getView().findViewById(R.id.btn_home_search);
        FloatingActionButton fab = getView().findViewById(R.id.btn_get_current_location);

        sv_map.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchQuery) {
                String location = sv_map.getQuery().toString();
                List<Address> addressList = null;

                if (location != null) {
                    Geocoder geocoder = new Geocoder(requireContext());

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                        if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                            google_map.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title("Location"));
                            google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {return false;}
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLocation != null) {
                    LatLng user_location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(user_location, 16));
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        google_map = map;
        readCSV();

        if (checkLocationPermission()) getLocation();
        else requestLocationPermission();

        LatLng user_location = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

        google_map.addMarker(new MarkerOptions()
                .position(user_location)
                .title("Current Location"));
        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(user_location, 16));

        readCSV();
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedlocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) currentLocation = location;
            else Toast.makeText(getContext(), "Please turn on your location and restart the app", Toast.LENGTH_SHORT).show();
        });
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void readCSV() {
        try {
            //Read the CSV file
            InputStream inputStream = getResources().openRawResource(R.raw.toiletlocationlatlong);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<LatLng> latLngList = new ArrayList<LatLng>();

            Drawable toilet_marker = getResources().getDrawable(R.drawable.ic_toilet_location);
            BitmapDescriptor bitMap_toilet_marker = drawableToBitmapUtil.drawableToBitmap(toilet_marker);

            //Read through the rows and populate LatLng arraylist with the corresponding latitude and longitude
            String line;
            while ((line = reader.readLine()) != null) {
                double lat = Double.parseDouble(line.split(",")[0]);
                double lon = Double.parseDouble(line.split(",")[1]);
                latLngList.add(new LatLng(lat, lon));
            }
            //plot on googleMap using latLngList
            for (LatLng pos : latLngList) {
                google_map.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Location")
                        .icon(bitMap_toilet_marker));
            }
        } catch (IOException e) { //error handling
            e.printStackTrace();
        }
    }

}

