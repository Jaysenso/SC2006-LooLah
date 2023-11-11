package com.example.loolah.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Toilet;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ReviewLocationViewModel extends ViewModel {
    private final CollectionReference tColRef;
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> toiletListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> filteredToiletListMutableLiveData = new MutableLiveData<>();

    public ReviewLocationViewModel() {
        tColRef = FirebaseFirestore.getInstance().collection("toilets");
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getFilteredToiletList() {
        if (filteredToiletListMutableLiveData == null)
            filteredToiletListMutableLiveData = new MutableLiveData<>();
        return filteredToiletListMutableLiveData;
    }

    public void filterToilets(String keyword) {
        try {
            ArrayList<Toilet> toiletList = toiletListMutableLiveData.getValue().getData();
            ArrayList<Toilet> filteredToiletList = new ArrayList<>();

            for (Toilet toilet : toiletList) {
                if (toilet.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredToiletList.add(toilet);
                }
            }

            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.success(filteredToiletList));
        } catch (Exception e) {
            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.error("Something went wrong when getting toilets", null));
        }
    }
}
