package com.example.loolah.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loolah.model.Toilet;
import com.example.loolah.model.User;
import com.example.loolah.model.enums.ToiletDistrict;
import com.example.loolah.model.enums.ToiletType;
import com.example.loolah.util.LiveDataWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {
    private final FirebaseUser user;
    FirebaseFirestore db;
    private final CollectionReference uColRef;
    private final CollectionReference tColRef;
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> favouritesToiletListMutableLiveData  = new MutableLiveData<>();
    private MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> filteredToiletListMutableLiveData = new MutableLiveData<>();
    private ArrayList<Toilet> favouritesToiletList;

    public FavoriteViewModel() {
        super();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uColRef = db.collection("users");
        tColRef = db.collection("toilets");
        favouritesToiletList = new ArrayList<>();
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getFavoritesToiletListMutableLiveData() {
        if (favouritesToiletListMutableLiveData == null)
            favouritesToiletListMutableLiveData = new MutableLiveData<>();
        return favouritesToiletListMutableLiveData;
    }

    public MutableLiveData<LiveDataWrapper<ArrayList<Toilet>>> getFilteredToiletList() {
        if (filteredToiletListMutableLiveData == null)
            filteredToiletListMutableLiveData = new MutableLiveData<>();
        return filteredToiletListMutableLiveData;
    }

    public ArrayList<Toilet> getFavoritesToiletList() {
        if (favouritesToiletList == null) favouritesToiletList = new ArrayList<>();
        return favouritesToiletList;
    }

    public void getFavoriteToilets() {
        ArrayList<String> favoritesList = new ArrayList<>();
        favouritesToiletList = new ArrayList<>();

        favouritesToiletListMutableLiveData.setValue(LiveDataWrapper.loading(null));

        uColRef.whereEqualTo("userId", user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                User user = document.toObject(User.class);
                if (user.getFavorites().size() > 0)
                {
                    for (int i = 0; i <user.getFavorites().size();i++)
                    {
                        favoritesList.add(user.getFavorites().get(i));
                        if (favoritesList.get(i) != null) {
                            tColRef.whereEqualTo("toiletId", favoritesList.get(i)).get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                                favouritesToiletList.add(queryDocumentSnapshots1.getDocuments().get(0).toObject(Toilet.class));
                                Log.d("TEST", "getting fav toilet list");
                                favouritesToiletListMutableLiveData.setValue(LiveDataWrapper.success(favouritesToiletList));
                                filteredToiletListMutableLiveData.setValue(LiveDataWrapper.success(favouritesToiletList));
                            });
                        }
                    }
                }
                else {
                    favouritesToiletListMutableLiveData.setValue(LiveDataWrapper.success(favouritesToiletList));
                    filteredToiletListMutableLiveData.setValue(LiveDataWrapper.success(favouritesToiletList));
                }

            }
        });
    }

    public void filterToilets(String keyword, ToiletType type, ToiletDistrict district , int rating) {
        try {
            ArrayList<Toilet> toiletList = favouritesToiletListMutableLiveData.getValue().getData();
            ArrayList<Toilet> filteredToiletList = new ArrayList<>();

            for (Toilet toilet : toiletList) {
                if (toilet.getName().toLowerCase().contains(keyword.toLowerCase()) && toilet.getRating() >= rating && (type == null || toilet.getType() == type) && (district == null || toilet.getDistrict() == district)) {
                    filteredToiletList.add(toilet);
                }
            }

            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.success(filteredToiletList));
            Log.d("TEST", "filteredToiletList");
        } catch (Exception e) {
            filteredToiletListMutableLiveData.setValue(LiveDataWrapper.error("Something went wrong when getting toilets", null));
            Log.d("TEST", "list error");
        }
    }
}
