package com.example.loolah;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ToiletRVMenu extends AppCompatActivity {

    private ArrayList<ToiletModel> toiletDataList;
    private ArrayList<ToiletModel> filteredList;
    private Toilet_RVAdapter rv_adapter;
    private RecyclerView recyclerView;
    private SearchView searchView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toilet_rvmenu);

        //spinner
        Spinner map_filter = (Spinner)findViewById(R.id.sp_map_filter_type);
        Spinner toilet_district = (Spinner)findViewById(R.id.sp_map_filter_district);
        Spinner toilet_distance = (Spinner)findViewById(R.id.sp_map_filter_distance);
        Spinner toilet_rating = (Spinner)findViewById(R.id.sp_map_filter_rating);

        String[] toilet_types = new String[]{"Type", "Bus Interchange", "Club", "Coffeeshop", "Foodcourt", "Government Office", "Market & Food Centre", "MRT Station", "Park", "Pier", "Place of worship", "Private Office", "Restaurant", "Shopping Centre", "Tourist Attraction", "Community Centre", "Food Court", "Dormitory", "Industrial Complex"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ToiletRVMenu.this, R.layout.item_spinner, toilet_types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        map_filter.setAdapter(adapter);

        String[] toilet_districts = new String[]{"District", "Central", "North East", "North West", "South East", "South West"};
        adapter = new ArrayAdapter<>(ToiletRVMenu.this, R.layout.item_spinner, toilet_districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toilet_district.setAdapter(adapter);

        String[] toilet_distances = new String[]{"Distance", "< 5m", "< 10m", "< 15m", "< 20m", "< 25m"};
        adapter = new ArrayAdapter<>(ToiletRVMenu.this, R.layout.item_spinner, toilet_distances);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toilet_distance.setAdapter(adapter);

        String[] toilet_ratings = new String[]{"Rating", "1 star", "2 star", "3 star", "4 star", "5 star"};
        adapter = new ArrayAdapter<>(ToiletRVMenu.this, R.layout.item_spinner, toilet_ratings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toilet_rating.setAdapter(adapter);

        //for searchview & recyclerview
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.toilet_RecyclerView);
        setUpToiletModels();

        rv_adapter = new Toilet_RVAdapter(this, toiletDataList);
        recyclerView.setAdapter(rv_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }


    private void setUpToiletModels() {
        try {
            CSVReaderUtil(getResources().openRawResource(R.raw.toiletdatarecyclerviewtest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filter(String constraint) {
        filteredList = new ArrayList<>();

        if(constraint == null || constraint.length() == 0) {
            filteredList.addAll(toiletDataList);
        }
        else {
            String filterPattern = constraint.toString().toLowerCase().trim();

            for(ToiletModel toilet : toiletDataList) {
                if(toilet.getName().toLowerCase().contains(filterPattern)) {
                    filteredList.add(toilet);
                }
            }
            rv_adapter.filterList(filteredList);
        }
    }

    public void CSVReaderUtil(InputStream inputStream) throws IOException {
        String[] record = null;
        toiletDataList = new ArrayList<>();
        //pass in the file location
        try{
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            //Parsing the entries
            while((record =  reader.readNext()) != null) {
                if(record.length >= 2) {
                    String name = record[0];
                    String address = record[1];
                    //double latitude = Double.parseDouble(record[2]);
                    //double longitude = Double.parseDouble(record[3]);
                    //add it to our arrayList
                    //toiletModels.add(new ToiletModel(name,address, latitude, longitude));
                    toiletDataList.add(new ToiletModel(name,address));
                }
            }
            reader.close();
        }
        catch(Exception e) {
            Log.wtf("MyActivity", "Error reading data file on line " + record, e);
            e.printStackTrace();
        }

    }

}