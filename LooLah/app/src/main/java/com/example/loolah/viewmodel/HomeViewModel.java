package com.example.loolah.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Toilet;
import com.example.loolah.util.LiveDataWrapper;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> toiletListMutableLiveData;
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> filteredToiletListMutableLiveData;

    public HomeViewModel() {

    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getToiletList() {
        if (toiletListMutableLiveData == null) toiletListMutableLiveData = new MutableLiveData<>();
        return toiletListMutableLiveData;
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getFilteredToiletList() {
        if (filteredToiletListMutableLiveData == null)
            filteredToiletListMutableLiveData = new MutableLiveData<>();
        return filteredToiletListMutableLiveData;
    }
}
