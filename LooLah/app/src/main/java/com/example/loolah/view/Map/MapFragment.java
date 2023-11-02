package com.example.loolah.view.Map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap google_map;
    private SearchView sv_map;
    private RecyclerView rv_map;

    private String savedSearchQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View map_fragment = inflater.inflate(R.layout.fragment_map, container, false);

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

        ImageButton btn_map_search = map_fragment.findViewById(R.id.btn_map_search);
        btn_map_search.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mapFragment_to_toiletRVMenu);
        });
        return map_fragment;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sv_map = getView().findViewById(R.id.sv_map_search);
        rv_map = getView().findViewById(R.id.toiletRVMenu);
        SupportMapFragment google_map_fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fcv_map_google);

        if(savedInstanceState != null)
            savedSearchQuery = savedInstanceState.getString("SearchQuery");

        sv_map.setOnClickListener(v-> {
                Navigation.findNavController(v).navigate(R.id.action_mapFragment_to_toiletRVMenu);
        });
        sv_map.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(savedSearchQuery != null)
                    updateMap(savedSearchQuery);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });
        google_map_fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        google_map = map;
        //on Map creation - camera will zoom into Singapore by default
        LatLng singapore = new LatLng(1.3521, 103.8198);
        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 12));
        //read geolocation csv and plot pins on the map
        readCSV();
    }
    private void updateMap(String searchQuery) {
        List<Address> addressList = null;

        if (searchQuery != null) {
            Geocoder geocoder = new Geocoder(requireContext());
            try {
                addressList = geocoder.getFromLocationName(searchQuery,1);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            google_map.addMarker(new MarkerOptions().position(latLng).title("Location"));
            google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    private void readCSV() {
        try {
            //Read the CSV file
            InputStream inputStream = getResources().openRawResource(R.raw.toiletlocationlatlong);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<LatLng> latLngList = new ArrayList<LatLng>();

            //Read through the rows and populate LatLng arraylist with the corresponding latitude and longitude
            String line;
            while ((line = reader.readLine()) != null) {
                double lat = Double.parseDouble(line.split(",")[0]);
                double lon = Double.parseDouble(line.split(",")[1]);
                latLngList.add(new LatLng(lat, lon));
            }
            //plot on googleMap using latLngList
            for(LatLng pos : latLngList){
                google_map.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Location"));
            }
        } catch (IOException e) { //error handling
            e.printStackTrace();
        }
    }

}

