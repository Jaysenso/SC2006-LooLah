package com.example.loolah;

import android.util.Log;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
public class ToiletDataManager {
    //singleton class to manage the toilet data CSV/list (ensuring there is only one instance of the list and can be accessed from anywhere in the app)
    private static ToiletDataManager instance;
    private ArrayList<ToiletData> toiletDataList;

    private ToiletDataManager() {
        toiletDataList = new ArrayList<>();
    }

    public static ToiletDataManager getInstance() {
        if(instance == null)
            instance = new ToiletDataManager();
        return instance;
    }

    public ArrayList<ToiletData>  getToiletDataList() {return toiletDataList;}

    public void addToToiletData(ToiletData toiletData) {toiletDataList.add(toiletData);}


    //to convert each entries in toilet CSV into objects in "toiletDataList" list
    public void CSVReaderUtil(String filePath) throws IOException {
        String[] record = null;
        //pass in the file location
        try{
            CSVReader reader = new CSVReader(new FileReader(filePath));
            //Parsing the entries
            while((record =  reader.readNext()) != null) {
                if(record.length >= 4) {
                    String name = record[0];
                    String address = record[1];
                    double latitude = Double.parseDouble(record[2]);
                    double longitude = Double.parseDouble(record[3]);
                    //instantiate an object with the necessary information
                    ToiletData toiletData = new ToiletData(name,address, latitude, longitude);
                    //add it to our arrayList
                    addToToiletData(toiletData);
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
