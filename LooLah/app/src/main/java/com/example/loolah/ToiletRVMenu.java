package com.example.loolah;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ToiletRVMenu extends AppCompatActivity {


    ArrayList<ToiletModel> toiletModels = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toilet_rvmenu);

        RecyclerView recyclerView = findViewById(R.id.toilet_RecyclerView);
        setUpToiletModels();

        Toilet_RVAdapter adapter = new Toilet_RVAdapter(this,toiletModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void setUpToiletModels() {
        try {
            CSVReaderUtil(getResources().openRawResource(R.raw.toiletdatarecyclerviewtest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void CSVReaderUtil(InputStream inputStream) throws IOException {
        String[] record = null;
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
                    toiletModels.add(new ToiletModel(name,address));
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