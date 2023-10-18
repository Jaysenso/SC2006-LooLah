package com.example.loolah;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;
import java.io.*;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        //on Map creation - camera will zoom into Singapore by default
        LatLng singapore = new LatLng(1.3521, 103.8198);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 12));
        //read geolocation csv and plot pins on the map
        readCSV();

    }

    private void readCSV() {
        try {
            //Read the CSV file
            InputStream inputStream = getResources().openRawResource(R.raw.toiletlocationlatlong);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));

            ArrayList<LatLng> latLngList = new ArrayList<LatLng>();

            //Read through the rows and populate LatLng arraylist with the corresponding latitude and longitude
            String line = "";
            while ((line = reader.readLine()) != null) {
                double lat = Double.parseDouble(line.split(",")[0]);
                double lon = Double.parseDouble(line.split(",")[1]);
                latLngList.add(new LatLng(lat, lon));
            }

            //plot on googleMap using latLngList
            for(LatLng pos : latLngList){
                googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title("Location"));
            }

        } catch (IOException e) { //error handling
            e.printStackTrace();
        }
    }

}


