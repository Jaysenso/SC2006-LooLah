package com.example.loolah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View home_fragment = inflater.inflate(R.layout.fragment_home, container, false);

        String[] toilet_types = new String[]{"Type", "Bus Interchange", "Club", "Coffeeshop", "Foodcourt", "Government Office", "Market & Food Centre", "MRT Station", "Park", "Pier", "Place of worship", "Private Office", "Restaurant", "Shopping Centre", "Tourist Attraction", "Community Centre", "Food Court", "Dormitory", "Industrial Complex"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) home_fragment.findViewById(R.id.sp_home_filter_type)).setAdapter(adapter);

        String[] toilet_districts = new String[]{"District", "Central", "North East", "North West", "South East", "South West"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) home_fragment.findViewById(R.id.sp_home_filter_district)).setAdapter(adapter);

        String[] toilet_distance = new String[]{"Distance", "< 5m", "< 10m", "< 15m", "< 20m", "< 25m"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_distance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) home_fragment.findViewById(R.id.sp_home_filter_distance)).setAdapter(adapter);

        String[] toilet_rating = new String[]{"Rating", "1 star", "2 star", "3 star", "4 star", "5 star"};
        adapter = new ArrayAdapter<>(this.requireContext(), R.layout.item_spinner, toilet_rating);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) home_fragment.findViewById(R.id.sp_home_filter_rating)).setAdapter(adapter);

        LinearLayout row_toilet = home_fragment.findViewById(R.id.lnl_toilet_row);
        row_toilet.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_toiletDetailsFragment);
        });

        return home_fragment;
    }
}
