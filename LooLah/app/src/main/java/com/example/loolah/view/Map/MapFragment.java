package com.example.loolah.view.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.loolah.R;
import com.example.loolah.model.Toilet;
import com.example.loolah.util.drawableToBitmapUtil;
import com.example.loolah.viewmodel.MapViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap google_map;
    private FusedLocationProviderClient fusedLocationClient;
    private MapViewModel viewModel;
    private Location currentLocation;
    private AutocompleteSupportFragment autocompleteFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View map_fragment = inflater.inflate(R.layout.fragment_map, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        autocompleteFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), getString(R.string.google_maps_key), Locale.ENGLISH);
        }
        autocompleteFragment.setCountries("SG");

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

                if (searchQuery != null) {
                    Geocoder geocoder = new Geocoder(requireContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(searchQuery, 1);

                        if (addressList != null) {
                            google_map.clear();
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            google_map.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title("Current Location"));
                            google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                        }
                        syncMapData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {return false;}
        });

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                if(place != null)
                    sv_map.setQuery(place.getName(),true);
                Log.i("AUTOCOMPLETE", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i("AUTOCOMPLETE", "An error occurred: " + status);
            }

        });

        fab.setOnClickListener(v -> {
            if(currentLocation != null) {
                LatLng user_location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                google_map.addMarker(new MarkerOptions()
                        .position(user_location)
                        .title("Current Location"));
                google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(user_location, 14));
            }
        });
        
        search_button.setOnClickListener(v -> {
            if(sv_map.getQuery() != null) {
                sv_map.setQuery(sv_map.getQuery(),true);
            }
        });
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        google_map = map;
        viewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);

        boolean success = google_map.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));
        if (!success) {
            Log.e("MAP_STYLE", "Style parsing failed.");
        }

        syncMapData();

        LatLng user_location = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        google_map.addMarker(new MarkerOptions()
                .position(user_location)
                .title("Current Location"));

        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(user_location, 14));

        google_map.setOnMarkerClickListener(marker -> {
            String toiletId = (String) marker.getTag();
            if (toiletId != null) {
                Bundle bundle = new Bundle();
                bundle.putString("toiletId", (String) marker.getTag());
                Navigation.findNavController(requireView()).navigate(R.id.action_mapFragment_to_toiletDetailsFragment, bundle);
            }
            return true;
        });
    }

    private void syncMapData() {

        viewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);
        Drawable toilet_marker = ResourcesCompat.getDrawable(getResources(),R.drawable.ic_toilet_location, null);
        BitmapDescriptor bitMap_toilet_marker = drawableToBitmapUtil.drawableToBitmap(toilet_marker);

        viewModel.getToilets();
        viewModel.getToiletList().observe(getViewLifecycleOwner(), LiveDataWrapper -> {
            switch (LiveDataWrapper.getStatus()) {
                case SUCCESS:
                    ArrayList<Toilet> toiletArrayList = LiveDataWrapper.getData();

                    if(toiletArrayList != null) {
                        for (Toilet toilet : toiletArrayList) {
                            google_map.addMarker(new MarkerOptions()
                                            .position(new LatLng(toilet.getLatitude(), toilet.getLongitude()))
                                            .title(toilet.getName() + "" + toilet.getToiletId())
                                            .icon(bitMap_toilet_marker))
                                    .setTag(toilet.getToiletId());
                            Log.d("MAP_LOCATION", "Toilet name : " + toilet.getName() + " Toilet Lat/Lng " + toilet.getLatitude() + " " + toilet.getLongitude());
                        }
                    }
                    if (toiletArrayList != null) Log.d("TEST", "error");
                    else { break; }
                case ERROR:
                    Log.d("TEST", "error");
                    break;
                case LOADING:
                    break;
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
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
    //Legacy Method
    private void readCSV() {
        try {
            //Read the CSV file
            InputStream inputStream = getResources().openRawResource(R.raw.toiletlocationlatlong);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<LatLng> latLngList = new ArrayList<>();

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

